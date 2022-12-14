package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.utils.R;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;


/**
 * 属性分组
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(AttrGroupEntity attrGroupEntity,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                  HttpServletRequest request){
        IPage<AttrGroupEntity> page = new Page<>(current, size);
        IPage<AttrGroupEntity> attrGroupPage = attrGroupService.listPage(attrGroupEntity, page);

        return R.ok().put("data", page);
    }

    /**
     * 根据分类id查询所属属性分组列表
     *
     * @param catelogId
     * @return
     */
    @GetMapping("/list/{catelogId}")
    public R list(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupEntity> attrGroupList = attrGroupService.listByCatelogId(catelogId);
        return R.ok().put("data", attrGroupList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * 根据分组id查询关联的属性列表
     *
     * @param attrGroupId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/attrs/attrgroup/{attrGroupId}")
    public R getAttrsByAttrGroupId(@NotBlank @PathVariable("attrGroupId") Long attrGroupId,
                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size){
        IPage page =  new Page<>(current, size);
        IPage<AttrEntity> data = relationService.getAttrsByAttributeId(page, attrGroupId);

        return R.ok().put("data", data);
    }

    /**
     * 查询当前分类下没有绑定分组的属性
     *
     * @param catelogId 分类id
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/attrs/category/{catelogId}")
    public R getAttrsNoAttrGroup(@NotBlank @PathVariable("catelogId") Long catelogId,
                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size){
        IPage page =  new Page<>(current, size);
        IPage<AttrEntity> data = relationService.getAttrsNoAttrGroup(page, catelogId);

        return R.ok().put("data", data);
    }

}
