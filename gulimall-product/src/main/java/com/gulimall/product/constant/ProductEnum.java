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


    /**
     * spu发布状态
     */
    public enum publishStatus {
        /**
         * 已上架
         */
        PUBLISHED(1),
        /**
         * 下架
         */
        UN_PUBLISHED(0);

        private final Integer value;

        publishStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

}
