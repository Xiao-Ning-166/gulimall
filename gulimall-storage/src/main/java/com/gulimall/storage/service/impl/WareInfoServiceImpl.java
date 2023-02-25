package com.gulimall.storage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.storage.dto.WareInfoDTO;
import com.gulimall.storage.entity.WareInfoEntity;
import com.gulimall.storage.mapper.WareInfoMapper;
import com.gulimall.storage.service.WareInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfoEntity> implements WareInfoService {

    /**
     * 分页查询仓库信息
     *
     * @param page    分页条件
     * @param keyword 关键词
     * @return
     */
    @Override
    public IPage<WareInfoEntity> queryPage(IPage<WareInfoEntity> page, String keyword) {
        LambdaQueryWrapper<WareInfoEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        IPage<WareInfoEntity> wareInfoPage = this.query()
                .like(StrUtil.isNotBlank(keyword), "name", keyword)
                .or()
                .like(StrUtil.isNotBlank(keyword),"address", keyword)
                .or()
                .like(StrUtil.isNotBlank(keyword),"area_code", keyword).page(page);
        return wareInfoPage;
    }

    /**
     * 保存仓库信息
     *
     * @param wareInfoDTO
     */
    @Override
    public void saveWareInfo(WareInfoDTO wareInfoDTO) {
        WareInfoEntity wareInfoEntity = new WareInfoEntity();
        BeanUtils.copyProperties(wareInfoDTO, wareInfoEntity);
        this.save(wareInfoEntity);
    }
}
