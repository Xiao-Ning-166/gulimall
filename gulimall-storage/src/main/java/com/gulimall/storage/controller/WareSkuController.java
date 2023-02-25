package com.gulimall.storage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.entity.WareSkuEntity;
import com.gulimall.storage.service.WareSkuService;
import com.gulimall.storage.vo.WareSkuVO;
import io.swagger.annotations.Api;
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

import java.util.Arrays;


/**
 * 商品库存
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@RestController
@RequestMapping("/ware-sku/stocks")
@Api(value = "商品库存接口")
public class WareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 分页查询商品库存列表
     *
     * @param storageId 仓库id
     * @param skuId     商品sku id
     * @param current   当前页码
     * @param size      每页大小
     * @return
     */
    @GetMapping
    public R list(String storageId, String skuId,
                  @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        IPage<WareSkuVO> page = new Page<>(current, size);
        IPage<WareSkuVO> wareSkuPage = wareSkuService.queryPage(page, storageId, skuId);

        return R.ok(wareSkuPage);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok(wareSku);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

}
