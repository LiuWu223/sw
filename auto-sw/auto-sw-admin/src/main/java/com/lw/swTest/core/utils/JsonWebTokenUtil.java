package com.lw.swTest.core.utils;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.lw.swTest.core.statics.GlobalStaticVariable;
import com.lw.swTest.system.entity.SystemUser;

public class JsonWebTokenUtil {

    /**
     * 解析Token里面的用户信息
     * @param token token
     * @return
     */
    public static SystemUser parseTokenUser (String token) {
        JWT jwt = JWTUtil.parseToken(token);
        // 解析token里面的用户信息
        Object object = jwt.getPayload(GlobalStaticVariable.QUESTION_USER);
        return JSONUtil.toBean(JSONUtil.toJsonStr(object), SystemUser.class);
    }

}
