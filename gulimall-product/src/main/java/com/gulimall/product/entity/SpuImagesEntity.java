package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * spu图片信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_spu_images")
@ApiModel(value = "spu图片信息对象")
public class SpuImagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * spu_id
	 */
	@ApiModelProperty(value = "关联的spu id")
	private Long spuId;
	/**
	 * 图片名
	 */
	@ApiModelProperty(value = "图片名")
	private String imgName;
	/**
	 * 图片地址
	 */
	@ApiModelProperty(value = "图片地址")
	private String imgUrl;
	/**
	 * 顺序
	 */
	@ApiModelProperty(value = "顺序")
	private Integer imgSort;
	/**
	 * 是否默认图
	 */
	@ApiModelProperty(value = "是否默认图")
	private Integer defaultImg;

}
