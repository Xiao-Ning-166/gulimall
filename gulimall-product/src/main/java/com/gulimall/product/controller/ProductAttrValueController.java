package com.gulimall.product.controller;

import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.dto.SpuBaseAttrDTO;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.service.ProductAttrValueService;
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
import java.util.List;
import java.util.Map;



/**
 * spu属性值
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/product-attr/values")
@Api(tags = "spu属性接口")
public class ProductAttrValueController {
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "分页查询spu属性列表", notes = "分页查询spu属性列表", response = R.class)
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productAttrValueService.queryPage(params);

        return R.ok(page);
    }

    /**
     * 列表
     */
    @GetMapping("/{spuId}")
    @ApiOperation(value = "分页查询spu属性列表", notes = "分页查询spu属性列表", response = R.class)
    public R getAttrValuesBySpuId(@PathVariable("spuId") Long SpuId) {
        List<ProductAttrValueEntity> data = productAttrValueService.listAttrValuesBySpuId(SpuId);
        return R.ok(data);
    }

    /**
     * 列表
     */
    @PutMapping("/{spuId}")
    @ApiOperation(value = "分页查询spu属性列表", notes = "分页查询spu属性列表", response = R.class)
    public R updateAttrValuesBySpuId(@PathVariable("spuId") Long spuId,
                                     @RequestBody List<SpuBaseAttrDTO> attrValues) {
        productAttrValueService.updateAttrValuesBySpuId(spuId, attrValues);
        return R.success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据id查询spu属性信息", notes = "根据id查询spu属性信息", response = R.class)
    public R info(@PathVariable("id") Long id){
		ProductAttrValueEntity productAttrValue = productAttrValueService.getById(id);

        return R.ok(productAttrValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存spu属性信息", notes = "保存spu属性信息", response = R.class)
    public R save(@RequestBody ProductAttrValueEntity productAttrValue){
		productAttrValueService.save(productAttrValue);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改spu属性信息", notes = "修改spu属性信息", response = R.class)
    public R update(@RequestBody ProductAttrValueEntity productAttrValue){
		productAttrValueService.updateById(productAttrValue);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除spu属性信息", notes = "删除spu属性信息", response = R.class)
    public R delete(@RequestBody Long[] ids){
		productAttrValueService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

}
