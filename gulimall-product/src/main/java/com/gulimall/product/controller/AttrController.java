package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.vo.AttributeResponseVO;
import com.gulimall.product.vo.AttributeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * 商品属性
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/attr")
@Api(tags = "属性接口")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取商品属性列表", notes = "获取商品属性列表", response = R.class)
    public R list(AttrEntity attrEntity,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                  HttpServletRequest request){
        IPage<AttrEntity> page = new Page<>(current, size);
        IPage<AttributeResponseVO> attrPage = attrService.listPage(attrEntity, page);

        return R.ok(attrPage);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    @ApiOperation(value = "通过商品属性id获取商品属性信息", notes = "通过商品属性id获取商品属性信息", response = R.class)
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok(attr);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存商品属性信息", notes = "保存商品属性信息", response = R.class)
    public R save(@Validated @RequestBody AttributeVO attr){
		attrService.saveAttribute(attr);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改商品属性信息", notes = "修改商品属性信息", response = R.class)
    public R update(@Validated @RequestBody AttributeVO attr){
		attrService.updateAttributeById(attr);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除商品属性", notes = "批量删除商品属性", response = R.class)
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeAttributeByIds(Arrays.asList(attrIds));

        return R.success();
    }


    /**
     * 查询分类下全部的销售属性
     *
     * @return
     */
    @GetMapping("/sales/{catalogId}")
    @ApiOperation(value = "查询分类下全部的销售属性", notes = "查询分类下全部的销售属性", response = R.class)
    public R searchSaleAttributes(@PathVariable("catalogId") Long catalogId) {
        List<AttributeVO> attributeVOs = attrService.listSaleAttributes(catalogId);

        return R.ok(attributeVOs);
    }


}
