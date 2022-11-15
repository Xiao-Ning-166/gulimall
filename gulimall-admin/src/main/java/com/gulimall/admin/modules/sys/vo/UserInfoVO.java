package com.gulimall.admin.modules.sys.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gulimall.admin.common.validator.group.AddGroup;
import com.gulimall.admin.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息类
 *
 * @author xiaoning
 * @date 2022/11/01
 */
@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;

    /**
     * 创建时间
     */
    private Date createTime;

}
