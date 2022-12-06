package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.utils.R;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.vo.AttributeResponseVO;
import com.gulimall.product.vo.AttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;



/**
 * 商品属性
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(AttrEntity attrEntity,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                  HttpServletRequest request){
        IPage<AttrEntity> page = new Page<>(current, size);
        IPage<AttributeResponseVO> attrPage = attrService.listPage(attrEntity, page);

        return R.ok().put("data", attrPage);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated @RequestBody AttributeVO attr){
		attrService.saveAttribute(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated @RequestBody AttributeVO attr){
		attrService.updateAttributeById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeAttributeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
