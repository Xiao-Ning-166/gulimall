package com.gulimall.coupon.feign;

import com.gulimall.api.coupon.dto.SpuBoundDTO;
import com.gulimall.api.coupon.feign.SpuBoundFeignClient;
import com.gulimall.common.core.vo.R;
import com.gulimall.coupon.entity.SpuBoundsEntity;
import com.gulimall.coupon.service.SpuBoundsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoning
 * @date 2023/02/06
 */
@RestController
public class SpuBoundFeignController implements SpuBoundFeignClient {

    @Autowired
    private SpuBoundsService spuBoundsService;

    /**
     * 保存spu积分信息
     *
     * @param spuBoundDTO spu积分参数
     * @return
     */
    @Override
    public R<?> saveSpuBound(SpuBoundDTO spuBoundDTO) {
        SpuBoundsEntity spuBoundsEntity = new SpuBoundsEntity();
        BeanUtils.copyProperties(spuBoundDTO, spuBoundsEntity);
        spuBoundsService.save(spuBoundsEntity);

        return R.success();
    }
}
