package com.lw.swTest.ts.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 测试任务详情表
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Getter
@Setter
@TableName("ts_task_details")
@ApiModel(value = "TsTaskDetails对象", description = "测试任务详情表")
public class TsTaskDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("测试任务详情表")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("测试任务ID")
    private Long taskId;

    @ApiModelProperty("测试用例ID")
    private Long caseId;

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
    @ApiModelProperty("项目名称")
    private String  projectName;

    @TableField(exist = false)
    @ApiModelProperty("测试用例名称")
    private String  caseName;

    @TableField(exist = false)
    @ApiModelProperty("测试用例URL")
    private String  caseUrl;

    @TableField(exist = false)
    @ApiModelProperty("测试设备")
    private String  device;
}
