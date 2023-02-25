package com.gulimall.storage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.storage.bo.StockBO;
import com.gulimall.storage.constant.PurchaseNeedEnum;
import com.gulimall.storage.constant.PurchaseOrderEnum;
import com.gulimall.storage.dto.PurchaseFinishDTO;
import com.gulimall.storage.dto.PurchaseItemFinishDTO;
import com.gulimall.storage.entity.PurchaseDTO;
import com.gulimall.storage.entity.PurchaseDetailEntity;
import com.gulimall.storage.entity.PurchaseEntity;
import com.gulimall.storage.mapper.PurchaseMapper;
import com.gulimall.storage.service.PurchaseDetailService;
import com.gulimall.storage.service.PurchaseService;
import com.gulimall.storage.service.WareSkuService;
import com.gulimall.storage.vo.PurchaseOrderVO;
import com.gulimall.storage.vo.PurchaseSelectVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    @Resource
    private PurchaseDetailService purchaseDetailService;

    @Resource
    private WareSkuService wareSkuService;

    /**
     * 分页查询采购单信息
     *
     * @param page
     * @param purchaseDTO
     * @return
     */
    @Override
    public IPage<PurchaseOrderVO> queryPage(IPage<PurchaseOrderVO> page, PurchaseDTO purchaseDTO) {
        return purchaseMapper.queryPage(page, purchaseDTO);
    }

    /**
     * 查询所有新建/已分配的采购单信息
     *
     * @return
     */
    @Override
    public List<PurchaseSelectVO> listPurchasesSelectVOs() {
        return purchaseMapper.listPurchasesSelectVOs();
    }

    /**
     * 采购单id集合
     *
     * @param orderIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrders(List<Long> orderIds) {
        // 1、查询出这些采购单中新建/已分配的采购单
        List<PurchaseEntity> purchaseOrders = purchaseMapper.listPurchaseOrders(orderIds);

        // 2、更新采购需求的状态
        List<Long> receivedOrderIds = purchaseOrders.stream().map(PurchaseEntity::getId).collect(Collectors.toList());
        purchaseDetailService.update()
                .set("status", PurchaseNeedEnum.PURCHASING.getStatus())
                .in("purchase_id", receivedOrderIds)
                .update();

        // 3、更新采购单的状态
        for (PurchaseEntity purchaseOrder : purchaseOrders) {
            purchaseOrder.setStatus(PurchaseOrderEnum.RECEIVED.getStatus());
        }
        this.updateBatchById(purchaseOrders);

    }

    /**
     * 完成采购单
     *
     * @param finishDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishOrders(PurchaseFinishDTO finishDTO) {
        // 1、更新采购项状态
        List<PurchaseItemFinishDTO> items = finishDTO.getItems();
        if (CollectionUtil.isEmpty(items)) {
            throw new RuntimeException("采购项数据不能为空!");
        }
        Map<Long, PurchaseItemFinishDTO> itemMap = detailPruchaseItemFinishDTO(items);
        // 1.2、查询需要更新的采购项
        List<PurchaseDetailEntity> detailist = purchaseDetailService.query().in("id", itemMap.keySet()).list();
        // TODO 设置一个flag变量，如果有采购项的采购数量，不等于需要采购的数量，则视为失败，整个采购单视为异常
        Integer flag = PurchaseOrderEnum.FINISHED.getStatus();
        for (PurchaseDetailEntity purchaseDetailEntity : detailist) {
            // 获取采购项的采购完成信息
            PurchaseItemFinishDTO itemFinishDTO = itemMap.get(purchaseDetailEntity.getId());
            // 设置实际采购数量
            purchaseDetailEntity.setRealNumber(itemFinishDTO.getNumber());
            // 设置备注
            purchaseDetailEntity.setRemark(itemFinishDTO.getRemark());
            // 假设完成
            purchaseDetailEntity.setStatus(PurchaseNeedEnum.FINISHED.getStatus());
            if (!NumberUtil.equals(purchaseDetailEntity.getSkuNum(), itemFinishDTO.getNumber())) {
                flag = PurchaseOrderEnum.FAILED.getStatus();
                // 当前采购项视为未完成，修改状态
                purchaseDetailEntity.setStatus(PurchaseNeedEnum.FAILED.getStatus());

            }
        }
        // 批量更新
        purchaseDetailService.updateBatchById(detailist);

        // 2、更新采购单状态
        Long orderId = finishDTO.getOrderId();
        PurchaseEntity purchaseEntity = this.getById(orderId);
        purchaseEntity.setStatus(flag);
        this.updateById(purchaseEntity);

        // 3、更新商品库存
        List<StockBO> stockBOs = detailist.stream().map(detail -> {
            StockBO stockBO = new StockBO();
            stockBO.setSkuId(detail.getSkuId());
            stockBO.setWareId(detail.getWareId());
            stockBO.setRealNumber(detail.getRealNumber());

            return stockBO;
        }).collect(Collectors.toList());
        wareSkuService.addStock(stockBOs);
    }

    private Map<Long, PurchaseItemFinishDTO> detailPruchaseItemFinishDTO(List<PurchaseItemFinishDTO> items) {
        Map<Long, PurchaseItemFinishDTO> map = new HashMap<>();

        for (PurchaseItemFinishDTO item : items) {
            map.put(item.getItemId(), item);
        }

        return map;
    }
}
