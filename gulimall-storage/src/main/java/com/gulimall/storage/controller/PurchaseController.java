package com.gulimall.storage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.dto.PurchaseFinishDTO;
import com.gulimall.storage.entity.PurchaseDTO;
import com.gulimall.storage.entity.PurchaseEntity;
import com.gulimall.storage.service.PurchaseService;
import com.gulimall.storage.vo.PurchaseOrderVO;
import com.gulimall.storage.vo.PurchaseSelectVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 采购信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@RestController
@RequestMapping("/purchase/orders")
@Api(value = "采购单接口")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @GetMapping
    public R list(PurchaseDTO purchaseDTO,
                  @RequestParam(name = "current", defaultValue = "1") Integer current,
                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        IPage<PurchaseOrderVO> page = new Page<>(current, size);
        IPage<PurchaseOrderVO> purchaseOrderPage = purchaseService.queryPage(page, purchaseDTO);

        return R.ok(purchaseOrderPage);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok(purchase);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody PurchaseEntity purchase) {
        purchaseService.save(purchase);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody PurchaseEntity purchase) {
        purchaseService.updateById(purchase);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));

        return R.success();
    }


    /**
     * 查询所有新建/已分配的采购单
     *
     * @return
     */
    @GetMapping("/available")
    public R getAvailablePurchases() {
        List<PurchaseSelectVO> orderSelectVOs = purchaseService.listPurchasesSelectVOs();
        return R.ok(orderSelectVOs);
    }

    /**
     * 领取采购单
     *
     * @param orderIds
     * @return
     */
    @PutMapping("/receive")
    public R receivePurchaseOrders(@RequestBody List<Long> orderIds) {
        purchaseService.receiveOrders(orderIds);
        return R.success();
    }

    /**
     * 完成采购单
     *
     * @param purchaseFinishDTO
     * @return
     */
    @PutMapping("/finish")
    public R finishPurchaseOrder(@Validated @RequestBody PurchaseFinishDTO purchaseFinishDTO) {
        purchaseService.finishOrders(purchaseFinishDTO);
        return R.success();
    }
}
