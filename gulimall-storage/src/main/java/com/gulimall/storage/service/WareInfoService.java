package com.gulimall.storage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.storage.dto.WareInfoDTO;
import com.gulimall.storage.entity.WareInfoEntity;

/**
 * 仓库信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
public interface WareInfoService extends IService<WareInfoEntity> {


    /**
     * 分页查询仓库信息
     *
     * @param page    分页条件
     * @param keyword 关键词
     * @return
     */
    IPage<WareInfoEntity> queryPage(IPage<WareInfoEntity> page, String keyword);

    /**
     * 保存仓库信息
     *
     * @param wareInfoDTO
     */
    void saveWareInfo(WareInfoDTO wareInfoDTO);
}

