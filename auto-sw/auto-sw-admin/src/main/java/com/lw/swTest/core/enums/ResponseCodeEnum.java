package com.lw.swTest.core.enums;

import lombok.Getter;

/**
 * 响应编码枚举
 * @author 4/19/2023 9:30 AM
 */
@Getter
public enum ResponseCodeEnum {

    // 查询成功
    SELECT_SUCCESS(1000, "查询成功"),
    // 查询失败
    SELECT_FAIL(1001, "查询失败"),
    // 保存成功
    SAVE_SUCCESS(1002, "保存成功"),
    // 保存失败
    SAVE_FAIL(1003, "保存失败"),
    // 修改成功
    UPDATE_SUCCESS(1004, "修改成功"),
    // 修改失败
    UPDATE_FAIL(1005, "修改失败"),
    // 删除成功
    REMOVE_SUCCESS(1006, "删除成功"),
    // 删除失败
    REMOVE_FAIL(1007, "删除失败"),
    // 上传成功
    UPLOAD_SUCCESS(1008, "上传成功"),
    // 导入成功
    IMPORT_SUCCESS(1009, "导入成功"),
    // 导入失败
    IMPORT_FAIL(1010, "导入失败"),
    // token失效
    TOKEN_LAPSE(1011,"token失效！"),
    // 非法tokne
    TOKEN_ILLEGAL(1012,"非法tokne！"),
    // 未登陆
    LOGIN_NOT(1013,"未登陆，请登录！"),
    // 账号已存在
    SAVE_EXIST(1014, "已被占用，请修改重试！！"),
    // 账号已存在
    SAVE_NOT_EXIST(1015, "账号可以注册"),
    // 登录成功
    LOGIN_SUCCESS(1016,"登录成功！"),
    // 登录成功
    LOGIN_FAIL(1017,"登录失败，请检查账号或者密码是否正确！！"),
    CTRL_FAIL(1018,"操作失败！！"),
    ;

    private Integer code;

    private String message;

    ResponseCodeEnum (Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
