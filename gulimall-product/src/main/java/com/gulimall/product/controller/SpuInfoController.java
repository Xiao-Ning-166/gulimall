package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.dto.SpuQueryDTO;
import com.gulimall.product.dto.SpuSaveDTO;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.service.SpuInfoService;
import com.gulimall.product.vo.SpuInfoVO;
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

import java.util.Arrays;


/**
 * spu信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/spuinfo")
@Api(tags = "spu信息接口")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询spu信息列表", notes = "分页查询spu信息列表", response = R.class)
    public R list(SpuQueryDTO spuQueryDTO,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage<SpuInfoVO> page = new Page<>(current, size);
        IPage<SpuInfoVO> spuInfoPage = spuInfoService.queryPage(spuQueryDTO, page);

        return R.ok(spuInfoPage);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据id查询spu信息", notes = "根据id查询spu信息", response = R.class)
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok(spuInfo);
    }

    /**
     * 发布商品-保存spu信息
     */
    @PostMapping("/save")
    @ApiOperation(value = "发布商品-保存spu信息", notes = "发布商品-保存spu信息", response = R.class)
    public R save(@Validated @RequestBody SpuSaveDTO spuSaveDTO) {
        spuInfoService.publishProduct(spuSaveDTO);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改spu信息", notes = "修改spu信息", response = R.class)
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除spu信息", notes = "批量删除spu信息", response = R.class)
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

    /**
     * 商品上架
     *
     * @param id
     * @return
     */
    @GetMapping("/putaway")
    @ApiOperation(value = "商品上架", notes = "商品上架", response = R.class)
    public R spuPutaway(@RequestParam("id") String id) {
        spuInfoService.putaway(id);
        return R.success("上架成功");
    }

}
