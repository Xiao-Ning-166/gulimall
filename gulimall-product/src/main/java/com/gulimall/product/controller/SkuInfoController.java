package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.dto.SkuQueryDTO;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.vo.SkuInfoVO;
import com.gulimall.product.vo.SpuInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * sku信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/skuinfo")
@Api(tags = "sku信息接口")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询sku信息列表", notes = "分页查询sku信息列表", response = R.class)
    public R list(SkuQueryDTO skuQueryDTO,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size){
        IPage<SkuInfoVO> page = new Page<>(current, size);
        IPage<SkuInfoVO> skuInfoPage = skuInfoService.queryPage(skuQueryDTO, page);

        return R.ok(skuInfoPage);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{skuId}")
    @ApiOperation(value = "根据id查询sku信息列表", notes = "根据id查询sku信息列表", response = R.class)
    public R info(@PathVariable("skuId") Long skuId){
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok(skuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存sku信息列表", notes = "保存sku信息列表", response = R.class)
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改sku信息列表", notes = "修改sku信息列表", response = R.class)
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除sku信息列表", notes = "删除sku信息列表", response = R.class)
    public R delete(@RequestBody Long[] skuIds){
		skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.success();
    }

}
