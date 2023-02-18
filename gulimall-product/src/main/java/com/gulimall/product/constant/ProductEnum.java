package com.gulimall.product.constant;

/**
 * 商品枚举类
 *
 * @author xiaoning
 * @date 2023/01/02
 */
public class ProductEnum {

    /**
     * 属性类型
     */
    public enum attrType {
        /**
         * 销售属性
         */
        SALE(0),
        /**
         * 基本属性
         */
        BASE(1);

        private final Integer value;

        attrType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

}
