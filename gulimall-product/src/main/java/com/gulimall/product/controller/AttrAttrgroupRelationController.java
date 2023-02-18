package com.gulimall.product.controller;

import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.vo.AttrAttrgroupRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;



/**
 * 属性&属性分组关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/attrattrgrouprelation")
@Api(tags = "属性属性分组接口")
public class AttrAttrgroupRelationController {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取属性属性分组列表", notes = "获取属性属性分组列表", response = R.class)
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrAttrgroupRelationService.queryPage(params);

        return R.ok(page);
    }

    /**
     * 批量保存
     */
    @PostMapping("/save/relation")
    @ApiOperation(value = "批量保存属性、属性分组关系", notes = "批量保存属性、属性分组关系", response = R.class)
    public R save(@Validated @RequestBody AttrAttrgroupRelationVO attrAttrgroupRelationVO){
        attrAttrgroupRelationService.saveRelation(attrAttrgroupRelationVO);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{attrGroupId}")
    @ApiOperation(value = "批量解除属性、属性分组的关联", notes = "批量解除属性、属性分组的关联", response = R.class)
    public R deleteRelation(@PathVariable("attrGroupId") Long attrGroupId, @RequestBody Long[] ids){
        attrAttrgroupRelationService.deleteRelation(attrGroupId, Arrays.asList(ids));

        return R.success();
    }

}
