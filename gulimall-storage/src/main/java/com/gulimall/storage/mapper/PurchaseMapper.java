package com.gulimall.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.storage.entity.PurchaseDTO;
import com.gulimall.storage.entity.PurchaseEntity;
import com.gulimall.storage.vo.PurchaseOrderVO;
import com.gulimall.storage.vo.PurchaseSelectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Mapper
public interface PurchaseMapper extends BaseMapper<PurchaseEntity> {

    /**
     * 分页查询采购单信息
     *
     * @param page
     * @param purchaseDTO
     * @return
     */
    IPage<PurchaseOrderVO> queryPage(@Param("page") IPage<PurchaseOrderVO> page, @Param("purchaseDTO") PurchaseDTO purchaseDTO);

    /**
     * 查询新建/已分配的采购单信息
     *
     * @return
     */
    List<PurchaseSelectVO> listPurchasesSelectVOs();

    /**
     * 查询新建/已分配的采购单集合
     *
     * @param orderIds
     * @return
     */
    List<PurchaseEntity> listPurchaseOrders(@Param("orderIds") List<Long> orderIds);
}
