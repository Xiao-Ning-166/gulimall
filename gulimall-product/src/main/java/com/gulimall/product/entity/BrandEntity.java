package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gulimall.common.core.valid.Select;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	private Long brandId;

	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名称不能为空")
	private String name;

	/**
	 * 品牌logo地址
	 */
	@URL(message = "品牌logo地址必须为合法url地址")
	@NotEmpty(message = "品牌logo地址不能为空")
	private String logo;

	/**
	 * 介绍
	 */
	private String descript;

	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@Select(values = {0, 1}, message = "必须在规定值中")
	private Integer showStatus;

	/**
	 * 检索首字母
	 */
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是a-z或A-Z范围")
	private String firstLetter;

	/**
	 * 排序
	 */
	@NotNull
	@Min(value = 0, message = "序号必须大于等于0")
	private Integer sort;

	/**
	 * 是否删除。0-未删除 1-已删除
	 */
	@TableLogic(value = "0", delval = "1")
	private Integer isDelete;

}
