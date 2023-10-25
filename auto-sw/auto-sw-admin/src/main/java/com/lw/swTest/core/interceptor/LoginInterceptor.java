package com.lw.swTest.core.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.statics.GlobalStaticVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public void jsonMss(HttpServletResponse response,String code,String msg){
        // 创建json返回对象  用于前端返回信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        JSONObject res = new JSONObject();
        // 否则不允许通行
        try {
            res.set("code:", code);
            res.set("message:", msg);
            out = response.getWriter();
            out.append(res.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        // token
        String token = request.getHeader(GlobalStaticVariable.AUTHORIZATION);
        // 访问路径
        String requestURI = request.getRequestURI();
        // 判断token是否为空
        if (StrUtil.isBlank(token)) {
            // 如果为空则不允许通行
            log.info(requestURI);
            log.info("未登陆用户");
            jsonMss(response,String.valueOf(ResponseCodeEnum.LOGIN_NOT.getCode()),ResponseCodeEnum.LOGIN_NOT.getMessage());
            return false;
        }
        try {
            // 验证token是否有效
            if (JWTUtil.verify(token, GlobalStaticVariable.JWT_SECRET.getBytes())) {
                // 解析token
                JWT jwt = JWTUtil.parseToken(token);
                // 解析token里面的用户信息
                Object object = jwt.getPayload(GlobalStaticVariable.QUESTION_USER);
                log.info("当前操作用户："+object.toString());
                // 判断用户信息是否为空
                if (object == null) {
                    // 如果为空则不允许通行
                    jsonMss(response,String.valueOf(ResponseCodeEnum.LOGIN_NOT.getCode()),ResponseCodeEnum.LOGIN_NOT.getMessage());
                    return false;
                }
                // 解析token里面的当前时间
                Long tokenTime = Long.parseLong(jwt.getPayload(GlobalStaticVariable.CURRENT_TIME).toString());
                // 判断当前时间是否为空
                if (tokenTime == null) {
                    // 如果为空则不允许通行
                    jsonMss(response,String.valueOf(ResponseCodeEnum.LOGIN_NOT.getCode()),ResponseCodeEnum.LOGIN_NOT.getMessage());
                    return false;
                }
                // 获取当前时间
                Long currentTime = System.currentTimeMillis();
                // 计算当前时间和token的时间差值
                long time = currentTime - tokenTime;
                // 计算差值的分钟数
                long min = time / 1000L / 60L / 60L;
                // 定义token的有效时间 一天
                long validity = 24;
                // 判断token是否在有效时间内
                if (min < validity) {
                    // 如果在有效时间内则允许通行
                    return true;
                } else {
                    // 否则不允许通行
                    log.info("token已过期，请重新登录！");
                    jsonMss(response,String.valueOf(ResponseCodeEnum.TOKEN_LAPSE.getCode()),ResponseCodeEnum.TOKEN_LAPSE.getMessage());
                    return false;
                }
            } else {
                return false;
            }
        } catch (JWTException e) {
            log.error(String.valueOf(e));
            log.info("非法的token");
            jsonMss(response,String.valueOf(ResponseCodeEnum.TOKEN_ILLEGAL.getCode()),ResponseCodeEnum.TOKEN_ILLEGAL.getMessage());
            return false;
        }
    }
}
