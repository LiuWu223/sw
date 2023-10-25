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
 * 项目任务表
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@Getter
@Setter
@TableName("ts_project_task")
@ApiModel(value = "TsProjectTask对象", description = "项目任务表")
public class TsProjectTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目任务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务ID")
    private Long taskId;

    @ApiModelProperty("项目ID")
    private Long projectId;

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
    @ApiModelProperty("测试任务主题")
    private String taskName;

    @TableField(exist = false)
    @ApiModelProperty("测试任务主题")
    private String taskType;

    @TableField(exist = false)
    @ApiModelProperty("预估时间")
    private String estimateTime;

    @TableField(exist = false)
    @ApiModelProperty("实际时间")
    private String realityTime;

    @TableField(exist = false)
    @ApiModelProperty("优先级（字典ID）")
    private String dictIdFirst;

    @TableField(exist = false)
    @ApiModelProperty("状态（字典ID）")
    private String dictIdType;

    @TableField(exist = false)
    @ApiModelProperty("测试设备")
    private String devices;

    @TableField(exist = false)
    @ApiModelProperty("三方任务")
    private String tripartite;
}
