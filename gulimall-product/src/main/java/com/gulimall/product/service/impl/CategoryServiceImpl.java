package com.gulimall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.cache.constant.CacheConstants;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.constant.ProductCacheConstants;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 以树形结构返回类型列表
     *
     * @return
     */
    @Override
    public IPage<CategoryEntity> listWithTree(CategoryEntity categoryEntity, IPage<CategoryEntity> page) {
        // 1、查询所有分类
        List<CategoryEntity> allCategoryList = query()
                .like(StringUtils.isNotBlank(categoryEntity.getName()), "name", categoryEntity.getName())
                .list();

        // 2、组装分类树形结构
        // 2.1、找到一级分类
        List<CategoryEntity> topCategoryList = allCategoryList.stream().filter((category) -> {
            // 返回一级分类
            return category.getParentCid() == 0;
        }).map((category) -> {
            // 查找所有子分类
            category.setChildren(getChildren(category, allCategoryList));
            return category;
        }).sorted((category1, category2) -> {
            // 分类进行排序
            return (category1.getSort() == null ? 0 : category1.getSort()) - (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList());

        long from = (page.getCurrent() - 1) * page.getSize();
        long size = page.getSize();

        List<CategoryEntity> result = new ArrayList<CategoryEntity>();
        for (long i = from; i < from + size && i < topCategoryList.size(); i++) {
            result.add(topCategoryList.get(Math.toIntExact(i)));
        }

        page.setTotal(topCategoryList.size());
        page.setRecords(result);
        page.setPages(new Double(Math.ceil(topCategoryList.size() / page.getSize())).longValue());

        // 3、返回一级分类集合
        return page;
    }

    /**
     * 获取目标分类的所有子分类
     *
     * @param currentCategory 目标分类
     * @param allCategoryList 所有分类
     * @return 目标分类的子分类集合（包含子分类的子分类）
     */
    private List<CategoryEntity> getChildren(CategoryEntity currentCategory, List<CategoryEntity> allCategoryList) {

        List<CategoryEntity> children = allCategoryList.stream().filter((category) -> {
            // 找到目标分类的所有子分类
            return category.getParentCid().equals(currentCategory.getCatId());
        }).map((category) -> {
            // 找到子分类的子分类
            category.setChildren(getChildren(category, allCategoryList));
            // 设置父分类名称
            category.setParentName(currentCategory.getName());
            return category;
        }).sorted((category1, category2) -> {
            // 将子分类进行排序
            return (category1.getSort() == null ? 0 : category1.getSort()) - (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList());

        // 返回子分类集合
        return children;
    }


    /**
     * 通过id批量删除类别
     *
     * @param ids
     */
    @Override
    public void removeCategoryByIds(List<Long> ids) {
        // TODO 检查类别是否被其他地方引用，如果引用，则不能删除
        removeByIds(ids);
    }

    /**
     * 返回1、2级类别数据
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listSelectTree() {
        // 1、查询所有1、2级类别数据
        List<CategoryEntity> categoryList = query().lt("cat_level", 3).list();

        // 2、构造树形结构
        List<CategoryEntity> topCategoryList = categoryList.stream().filter((category) -> {
            return category.getCatLevel() == 1;
        }).map((category) -> {
            // 查询次级分类
            List<CategoryEntity> children = getChildren(category, categoryList);
            category.setChildren(children);
            return category;
        }).sorted((c1, c2) -> {
            // 排序
            return (c1.getSort() == null ? 0 : c1.getSort()) - (c2.getSort() == null ? 0 : c2.getSort());
        }).collect(Collectors.toList());

        return topCategoryList;
    }

    /**
     * 以树形结构返回全部分类数据
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listTree() {
        // 1、查询缓存
        String categoryCacheKey = StrUtil.format(
            "{}:{}:{}",
            CacheConstants.CACHE_ROOT_PREFIX,
            CacheConstants.PRODUCT_CACHE_PREFIX,
            ProductCacheConstants.CATEGORY_CACHE
        );
        String categoryDataStr = stringRedisTemplate.opsForValue().get(categoryCacheKey);

        // 2、判断缓存是否命中
        if (StrUtil.isBlank(categoryDataStr)) {
            // 缓存未命中，

            // 加锁解决缓存击穿（大量请求访问同一个过期的key，导致大量请求数据库压力过大）
            RLock lock = redissonClient.getLock(categoryCacheKey + CacheConstants.CACHE_KEY_SEPARATOR + "lock");
            // 上锁
            lock.lock();
            try {
                // 再次查询缓存
                categoryDataStr = stringRedisTemplate.opsForValue().get(categoryCacheKey);
                if (StrUtil.isNotBlank(categoryDataStr)) {
                    // 缓存命中
                    return JSONUtil.toBean(categoryDataStr, new TypeReference<List<CategoryEntity>>() {}, false);
                }

                // 查询数据库
                List<CategoryEntity> topCategoryList = getCategoryEntitiesFormDB();

                if (CollectionUtil.isEmpty(topCategoryList)) {
                    // 缓存null值，防止缓存穿透（请求一个不存在的值）
                    topCategoryList = Collections.EMPTY_LIST;
                }

                String categoryStr = JSONUtil.toJsonStr(topCategoryList);
                // 3、存入缓存，过期时间1小时
                stringRedisTemplate.opsForValue().set(
                    categoryCacheKey,
                    categoryStr,
                    ProductCacheConstants.CACHE_EXPIRE_TIME,
                    TimeUnit.SECONDS
                );

                return topCategoryList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // 解锁
                lock.unlock();
            }
        }

        // 3、缓存命中
        List<CategoryEntity> categories = JSONUtil.toBean(categoryDataStr, new TypeReference<List<CategoryEntity>>() {}, false);
        return categories;
    }

    @NotNull
    private List<CategoryEntity> getCategoryEntitiesFormDB() {
        // 1、查询全部分类数据
        List<CategoryEntity> allCategoryList = this.query().list();

        // 2、组装树形数据
        List<CategoryEntity> topCategoryList = allCategoryList.stream().filter((category) -> {
            return category.getParentCid() == 0;
        }).map((category) -> {
            // 查找所有子分类
            category.setChildren(getChildren(category, allCategoryList));
            return category;
        }).sorted((category1, category2) -> {
            // 分类进行排序
            return (category1.getSort() == null ? 0 : category1.getSort()) - (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList());

        return topCategoryList;
    }

}
