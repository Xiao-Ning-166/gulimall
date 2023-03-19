package com.gulimall.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.api.storage.model.dto.SkuStorageDTO;
import com.gulimall.storage.entity.WareSkuEntity;
import com.gulimall.storage.vo.WareSkuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    /**
     * 分页查询商品库存列表
     *
     * @param page
     * @param storageId 仓库id
     * @param skuId
     * @return
     */
    IPage<WareSkuVO> queryPage(IPage<WareSkuVO> page, @Param("storageId") String storageId, @Param("skuId") String skuId);

    /**
     * 查询商品库存
     *
     * @param skuIds
     * @return
     */
    List<SkuStorageDTO> listSkuWareBySkuIds(@Param("skuIds") List<Long> skuIds);
}
