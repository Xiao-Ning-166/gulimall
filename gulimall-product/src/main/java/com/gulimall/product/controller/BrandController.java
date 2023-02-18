package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
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
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;



/**
 * 品牌
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌接口")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询
     *
     * @param brandEntity
     * @param current
     * @param size
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询品牌列表", notes = "分页查询品牌列表", response = R.class)
    public R list(BrandEntity brandEntity,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                  HttpServletRequest request){
        IPage<BrandEntity> page = new Page<>(current, size);
        IPage<BrandEntity> brandPage = brandService.listPage(brandEntity, page);

        return R.ok(brandPage);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    @ApiOperation(value = "根据id查询品牌信息", notes = "根据id查询品牌信息", response = R.class)
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok(brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存品牌信息", notes = "保存品牌信息", response = R.class)
    public R save(@Validated @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改品牌信息", notes = "修改品牌信息", response = R.class)
    public R update(@Validated @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/status")
    @ApiOperation(value = "修改品牌显示状态", notes = "修改品牌显示状态", response = R.class)
    public R updateStatus(@RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除品牌信息", notes = "批量删除品牌信息", response = R.class)
    public R delete(@NotEmpty @RequestBody Long[] brandIds){
		brandService.removeBrandByIds(Arrays.asList(brandIds));

        return R.success();
    }

}
