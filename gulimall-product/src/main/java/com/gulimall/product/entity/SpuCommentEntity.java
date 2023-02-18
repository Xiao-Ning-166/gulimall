package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_spu_comment")
@ApiModel(value = "商品评价对象")
public class SpuCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * sku_id
	 */
	@ApiModelProperty(value = "关联的sku id")
	private Long skuId;
	/**
	 * spu_id
	 */
	@ApiModelProperty(value = "关联的spu id")
	private Long spuId;
	/**
	 * 商品名字
	 */
	@ApiModelProperty(value = "商品名称")
	private String spuName;
	/**
	 * 会员昵称
	 */
	@ApiModelProperty(value = "会员昵称")
	private String memberNickName;
	/**
	 * 星级
	 */
	@ApiModelProperty(value = "星级")
	private Integer star;
	/**
	 * 会员ip
	 */
	@ApiModelProperty(value = "会员ip")
	private String memberIp;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 显示状态[0-不显示，1-显示]
	 */
	@ApiModelProperty(value = "显示状态[0-不显示，1-显示]")
	private Integer showStatus;
	/**
	 * 购买时属性组合
	 */
	@ApiModelProperty(value = "购买时属性组合")
	private String spuAttributes;
	/**
	 * 点赞数
	 */
	@ApiModelProperty(value = "点赞数")
	private Integer likesCount;
	/**
	 * 回复数
	 */
	@ApiModelProperty(value = "回复数")
	private Integer replyCount;
	/**
	 * 评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]
	 */
	@ApiModelProperty(value = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
	private String resources;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "用户头像")
	private String memberIcon;
	/**
	 * 评论类型[0 - 对商品的直接评论，1 - 对评论的回复]
	 */
	@ApiModelProperty(value = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
	private Integer commentType;

}
