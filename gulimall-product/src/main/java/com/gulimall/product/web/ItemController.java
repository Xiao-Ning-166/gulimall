package com.gulimall.product.web;

import com.gulimall.common.core.exception.GulimallException;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.vo.SkuItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaoning
 * @date 2023/04/17
 */
@Slf4j
@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {

        SkuItemVO skuItemVO = null;
        try {
            skuItemVO = skuInfoService.getSkuDetailBySkuId(skuId);
        } catch (Exception e) {
            log.error("查询sku详情出错，原因：{}", e.getMessage());
            throw new GulimallException("查询sku详情出错，原因：" + e.getMessage());
        }

        model.addAttribute("skuItem", skuItemVO);

        return "item";
    }

}
