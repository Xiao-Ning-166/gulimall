package com.gulimall.storage.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.product.feign.SkuInfoFeignClient;
import com.gulimall.api.storage.model.dto.SkuStorageDTO;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.bo.StockBO;
import com.gulimall.storage.entity.WareSkuEntity;
import com.gulimall.storage.mapper.WareSkuMapper;
import com.gulimall.storage.service.WareSkuService;
import com.gulimall.storage.vo.WareSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuMapper wareSkuMapper;

    @Resource
    private SkuInfoFeignClient skuInfoFeignClient;

    /**
     * 分页查询商品库存列表
     *
     * @param page
     * @param storageId 仓库id
     * @param skuId     sku id
     * @return
     */
    @Override
    public IPage<WareSkuVO> queryPage(IPage<WareSkuVO> page, String storageId, String skuId) {
        return wareSkuMapper.queryPage(page, storageId, skuId);
    }

    /**
     * 添加库存
     *
     * @param stockBOs
     */
    @Override
    public void addStock(List<StockBO> stockBOs) {
        // 1、查询sku信息
        List<Long> skuIds = stockBOs.stream().map(StockBO::getSkuId).collect(Collectors.toList());
        R<?> result = skuInfoFeignClient.getSkuInfoByIds(skuIds);
        if (!result.isSuccess()) {
            throw new RuntimeException("远程接口调用失败：" + result.getMessage());
        }
        Map<Long, String> map = detailResult(result.getData());

        // 2、添加库存
        List<WareSkuEntity> wareSkus = stockBOs.stream().map(stockBO -> {
            // 查询库存信息
            WareSkuEntity wareSkuEntity = this.query()
                    .eq("sku_id", stockBO.getSkuId())
                    .eq("ware_id", stockBO.getWareId()).one();
            if (Objects.isNull(wareSkuEntity)) {
                wareSkuEntity = new WareSkuEntity();
                wareSkuEntity.setSkuId(stockBO.getSkuId());
                wareSkuEntity.setWareId(stockBO.getWareId());
                wareSkuEntity.setSkuName(map.getOrDefault(stockBO.getSkuId(), ""));
            }
            wareSkuEntity.setStock(wareSkuEntity.getStock() + stockBO.getRealNumber());

            return wareSkuEntity;
        }).collect(Collectors.toList());

        this.saveOrUpdateBatch(wareSkus);
    }

    private Map<Long, String> detailResult(Object data) {
        Map<Long, String> map = new HashMap<>();
        JSONArray jsonArray = JSONUtil.parseArray(data);
        for (JSONObject jsonObject : jsonArray.jsonIter()) {
            map.put(Convert.toLong(jsonObject.get("id")), StrUtil.toString(jsonObject.get("skuName")));
        }

        return map;
    }

    /**
     * 查询sku库存
     *
     * @param skuIds
     * @return
     */
    @Override
    public List<SkuStorageDTO> listSkuWareBySkuIds(List<Long> skuIds) {
        return wareSkuMapper.listSkuWareBySkuIds(skuIds);
    }
}
