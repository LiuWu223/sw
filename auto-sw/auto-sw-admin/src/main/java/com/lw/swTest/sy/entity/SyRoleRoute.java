package com.lw.swTest.sy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 角色路由表
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Getter
@Setter
@TableName("sy_role_route")
@ApiModel(value = "SyRoleRoute对象", description = "角色路由表")
public class SyRoleRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色路由ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("路由ID")
    private Long routeId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") // 表示返回时间类型
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 表示接收时间类型
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") // 表示返回时间类型
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 表示接收时间类型
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty("删除标志")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleteFlag;

    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private String routeLabel;

    @TableField(exist = false)
    private String routeComponent;

    @TableField(exist = false)
    private String routePath;

    @TableField(exist = false)
    private String routeName;

    @TableField(exist = false)
    private String routeIcon;

    @TableField(exist = false)
    private String routeHigher;

    @TableField(exist = false)
    private Boolean routeHidden;

    @TableField(exist = false)
    private List<Object> children;

}
