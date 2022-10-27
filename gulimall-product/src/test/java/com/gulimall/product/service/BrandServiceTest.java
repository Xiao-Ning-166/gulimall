package com.gulimall.product.service;

import com.gulimall.product.entity.BrandEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xiaoning
 * @date 2022/10/24
 */
@SpringBootTest
class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Test
    void testSave() {
        BrandEntity brand = new BrandEntity();
        brand.setName("华为");
        brand.setDescript("华为手机");
        brandService.save(brand);
    }

    @Test
    void testQuery() {
        List<BrandEntity> list = brandService.list();
        System.out.println(list);
    }
}
