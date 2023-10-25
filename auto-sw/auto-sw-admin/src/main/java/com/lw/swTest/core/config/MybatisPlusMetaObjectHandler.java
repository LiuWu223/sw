package com.lw.swTest.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lw.swTest.core.statics.GlobalStaticVariable;
import com.lw.swTest.core.utils.JsonWebTokenUtil;
import com.lw.swTest.system.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MybatisPlus新增修改全局填充
 * @author  lw
 * @since 4/19/2023 9:30 AM
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {


    /**
     * 自动添加时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(GlobalStaticVariable.AUTHORIZATION);
        if (token == null){
            this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
            this.strictInsertFill(metaObject, "deleteFlag", () -> false, Boolean.class);
        } else {
            SystemUser systemUser = JsonWebTokenUtil.parseTokenUser(token);
            String un = systemUser.getUserName();
            this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
            this.strictInsertFill(metaObject, "deleteFlag", () -> false, Boolean.class);
            this.strictInsertFill(metaObject, "createBy", () -> un, String.class);
            this.strictInsertFill(metaObject, "updateBy", () -> un, String.class);
        }
    }

    /**
     * 自动更新时间
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(GlobalStaticVariable.AUTHORIZATION);
        SystemUser systemUser = JsonWebTokenUtil.parseTokenUser(token);
        String un = systemUser.getUserName();
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateBy", un, metaObject);
    }
}
