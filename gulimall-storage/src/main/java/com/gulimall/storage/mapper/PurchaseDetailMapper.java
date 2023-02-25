package com.gulimall.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.storage.dto.PurchaseNeedDTO;
import com.gulimall.storage.entity.PurchaseDetailEntity;
import com.gulimall.storage.vo.PurchaseNeedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Mapper
public interface PurchaseDetailMapper extends BaseMapper<PurchaseDetailEntity> {

    /**
     * 分页查询采购需求信息
     *
     * @param page
     * @param purchaseNeedDTO
     * @return
     */
    IPage<PurchaseNeedVO> queryPage(IPage<PurchaseNeedVO> page, @Param("purchaseNeedDTO") PurchaseNeedDTO purchaseNeedDTO);
}
