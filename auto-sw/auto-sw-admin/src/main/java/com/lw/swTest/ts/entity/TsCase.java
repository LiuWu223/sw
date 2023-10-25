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
 * 测试用例表
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Getter
@Setter
@TableName("ts_case")
@ApiModel(value = "TsCase对象", description = "测试用例表")
public class TsCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("测试用例ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("测试项ID")
    private Long sectionId;

    @ApiModelProperty("用例名称")
    private String caseName;

    @ApiModelProperty("用例序号")
    private String caseNumber;

    @ApiModelProperty("字典表ID(等级)")
    private Long dictId;

    @ApiModelProperty("测试用例测试URL")
    private String caseUrl;

    @ApiModelProperty("前置条件")
    private String caseCondition;

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
}
