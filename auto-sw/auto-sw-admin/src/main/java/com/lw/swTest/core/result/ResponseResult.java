package com.lw.swTest.core.result;

import com.lw.swTest.core.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一响应结果
 * @author lw
 * @since 4/19/2023 9:30 AM
 */
@Getter
@Setter
@ApiModel(value = "ResponseResult对象", description = "统一响应结果对象")
public class ResponseResult {

    @ApiModelProperty("响应编码")
    private Integer code;

    @ApiModelProperty("响应是否成功")
    private Boolean success;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应数据")
    private Object data;

    ResponseResult (Integer code, Boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回无数据成功消息
     * @param responseCodeEnum 响应编码枚举
     * @return
     */
    public static ResponseResult ok (ResponseCodeEnum responseCodeEnum) {
        return ResponseResult.ok(responseCodeEnum, null);
    }

    /**
     * 返回有数据成功消息
     * @param responseCodeEnum 响应编码枚举
     * @param data 返回数据
     * @return
     */
    public static ResponseResult ok (ResponseCodeEnum responseCodeEnum, Object data) {
        ResponseResult responseResult = new ResponseResult(responseCodeEnum.getCode(), true, responseCodeEnum.getMessage(), data);
        return responseResult;
    }

    /**
     * 返回无数据错误消息
     * @param responseCodeEnum 响应编码枚举
     * @return
     */
    public static ResponseResult error (ResponseCodeEnum responseCodeEnum) {
        return ResponseResult.error(responseCodeEnum, null);
    }

    /**
     * 返回有数据错误消息
     * @param responseCodeEnum 响应编码枚举
     * @param data
     * @return
     */
    public static ResponseResult error (ResponseCodeEnum responseCodeEnum, Object data) {
        ResponseResult responseResult = new ResponseResult(responseCodeEnum.getCode(), false, responseCodeEnum.getMessage(), data);
        return responseResult;
    }

}
