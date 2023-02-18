package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * spu商品介绍
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_spu_info_desc")
@ApiModel(value = "spu商品介绍图片关联对象")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@ApiModelProperty(value = "spuId")
	private Long spuId;

	/**
	 * 商品介绍
	 */
	@ApiModelProperty(value = "商品介绍")
	private String decript;

}
