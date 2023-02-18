package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品评价回复关系
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_comment_replay")
@ApiModel(value = "商品评价回复关系对象")
public class CommentReplayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 评论id
	 */
	@ApiModelProperty(value = "评论id")
	private Long commentId;
	/**
	 * 回复id
	 */
	@ApiModelProperty(value = "回复id")
	private Long replyId;

}
