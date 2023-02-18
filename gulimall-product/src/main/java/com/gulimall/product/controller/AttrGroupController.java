package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.vo.AttrGroupResponseVO;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;


/**
 * 属性分组
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/attrgroup")
@Api(tags = "属性分组接口")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取属性分组列表", notes = "获取属性分组列表", response = R.class)
    public R list(AttrGroupEntity attrGroupEntity,
                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                  HttpServletRequest request) {
        IPage<AttrGroupEntity> page = new Page<>(current, size);
        IPage<AttrGroupEntity> attrGroupPage = attrGroupService.listPage(attrGroupEntity, page);

        return R.ok(page);
    }

    /**
     * 根据分类id查询所属属性分组列表
     *
     * @param catelogId
     * @return
     */
    @GetMapping("/list/{catelogId}")
    @ApiOperation(value = "根据分类id查询所属属性分组列表", notes = "根据分类id查询所属属性分组列表", response = R.class)
    public R list(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupEntity> attrGroupList = attrGroupService.listByCatelogId(catelogId);
        return R.ok(attrGroupList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    @ApiOperation(value = "根据id查询属性分组信息", notes = "根据id查询属性分组信息", response = R.class)
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        return R.ok(attrGroup);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存属性分组信息", notes = "保存属性分组信息", response = R.class)
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改属性分组信息", notes = "修改属性分组信息", response = R.class)
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除属性分组", notes = "批量删除属性分组", response = R.class)
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.success();
    }

    /**
     * 根据分组id查询关联的属性列表
     *
     * @param attrGroupId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/attrs/attrgroup/{attrGroupId}")
    @ApiOperation(value = "根据分组id查询关联的属性列表", notes = "根据分组id查询关联的属性列表", response = R.class)
    public R getAttrsByAttrGroupId(@NotBlank @PathVariable("attrGroupId") Long attrGroupId,
                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage page = new Page<>(current, size);
        IPage<AttrEntity> data = relationService.getAttrsByAttributeId(page, attrGroupId);

        return R.ok(data);
    }

    /**
     * 查询当前分类下没有绑定分组的属性
     *
     * @param catelogId 分类id
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/attrs/category/{catelogId}")
    @ApiOperation(value = "根据分类id查询该分类下没有绑定分组的属性列表", notes = "根据分类id查询该分类下没有绑定分组的属性列表", response = R.class)
    public R getAttrsNoAttrGroup(@NotBlank @PathVariable("catelogId") Long catelogId,
                                 @RequestParam(value = "current", defaultValue = "1") Integer current,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage page = new Page<>(current, size);
        IPage<AttrEntity> data = relationService.getAttrsNoAttrGroup(page, catelogId);

        return R.ok(data);
    }

    /**
     * 查询分类下的所有属性分组和属性分组下的属性列表
     *
     * @param catelogId 分类id
     * @return
     */
    @GetMapping("/attrs")
    @ApiOperation(value = "根据分类id查询该分类下所有属性分组和属性分组下的属性列表", notes = "根据分类id查询该分类下所有属性分组和属性分组下的属性列表", response = R.class)
    public R listAttrGroupWithAttr(@RequestParam(value = "catelogId", required = true) Long catelogId) {
        List<AttrGroupResponseVO> attrGroups = attrGroupService.listAttrGroupWithAttr(catelogId);
        return R.ok(attrGroups);
    }

}
