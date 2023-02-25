package com.gulimall.storage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.dto.MergeNeedDTO;
import com.gulimall.storage.dto.PurchaseNeedDTO;
import com.gulimall.storage.entity.PurchaseDetailEntity;
import com.gulimall.storage.service.PurchaseDetailService;
import com.gulimall.storage.vo.PurchaseNeedVO;
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


/**
 * 采购需求接口
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@RestController
@RequestMapping("/purchase-detail/needs")
@Api(value = "采购需求接口")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @GetMapping
    public R list(PurchaseNeedDTO purchaseNeedDTO,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage<PurchaseNeedVO> page = new Page<>(current, size);
        IPage<PurchaseNeedVO> purchaseNeedPage = purchaseDetailService.queryPage(page, purchaseNeedDTO);

        return R.ok(purchaseNeedPage);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok(purchaseDetail);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail) {
        purchaseDetailService.save(purchaseDetail);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail) {
        purchaseDetailService.updateById(purchaseDetail);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Long[] ids) {
        purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

    /**
     * 合并采购需求
     */
    @PutMapping("/merge")
    public R update(@Validated @RequestBody MergeNeedDTO mergeNeedDTO) {
        purchaseDetailService.mergeNeed(mergeNeedDTO);

        return R.success();
    }

}
