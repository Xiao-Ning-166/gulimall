package com.gulimall.storage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.storage.dto.MergeNeedDTO;
import com.gulimall.storage.dto.PurchaseNeedDTO;
import com.gulimall.storage.entity.PurchaseDetailEntity;
import com.gulimall.storage.entity.PurchaseEntity;
import com.gulimall.storage.mapper.PurchaseDetailMapper;
import com.gulimall.storage.mapper.PurchaseMapper;
import com.gulimall.storage.service.PurchaseDetailService;
import com.gulimall.storage.vo.PurchaseNeedVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetailEntity> implements PurchaseDetailService {

    @Resource
    private PurchaseDetailMapper purchaseDetailMapper;

    @Resource
    private PurchaseMapper purchaseMapper;

    /**
     * 分页查询采购需求信息
     *
     * @param page
     * @param purchaseNeedDTO
     * @return
     */
    @Override
    public IPage<PurchaseNeedVO> queryPage(IPage<PurchaseNeedVO> page, PurchaseNeedDTO purchaseNeedDTO) {
        return purchaseDetailMapper.queryPage(page, purchaseNeedDTO);
    }

    /**
     * 合并采购需求
     *
     * @param mergeNeedDTO
     */
    @Override
    public void mergeNeed(MergeNeedDTO mergeNeedDTO) {
        Long orderId = mergeNeedDTO.getOrderId();
        PurchaseEntity purchase = purchaseMapper.selectById(orderId);
        if (Objects.isNull(purchase)) {
            purchase = new PurchaseEntity();
            purchaseMapper.insert(purchase);
        }

        List<String> needIds = mergeNeedDTO.getNeedIds();
        List<PurchaseDetailEntity> details = this.query().in("id", needIds).list();
        for (PurchaseDetailEntity detail : details) {
            detail.setPurchaseId(purchase.getId());
        }

        this.updateBatchById(details);
    }
}
