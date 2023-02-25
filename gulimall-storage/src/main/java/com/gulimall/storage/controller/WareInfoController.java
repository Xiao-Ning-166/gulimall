package com.gulimall.storage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.dto.WareInfoDTO;
import com.gulimall.storage.entity.WareInfoEntity;
import com.gulimall.storage.service.WareInfoService;
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
 * 仓库信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@RestController
@RequestMapping("/ware-info/storages")
@Api(value = "仓库信息接口")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 分页查询仓库信息
     *
     * @param keyword 查询关键词
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @GetMapping
    public R list(String keyword,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size) {

        IPage<WareInfoEntity> page = new Page<>(current, size);
        IPage<WareInfoEntity> wareInfoPage = wareInfoService.queryPage(page, keyword);
        return R.ok(wareInfoPage);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        WareInfoEntity wareInfo = wareInfoService.getById(id);

        return R.ok(wareInfo);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@Validated @RequestBody WareInfoDTO wareInfoDTO) {
        wareInfoService.saveWareInfo(wareInfoDTO);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.updateById(wareInfo);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    // @RequiresPermissions("storage:wareinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        wareInfoService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

}
