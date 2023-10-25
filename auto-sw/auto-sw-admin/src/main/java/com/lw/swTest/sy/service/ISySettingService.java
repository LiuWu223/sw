package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SySetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISySettingService extends IService<SySetting> {
    /**
     * 分页查询设置信息
     * @param page 分页信息
     * @param userId 用户ID
     * @param settingTheme 自动主题
     * @param settingLingo 自动语言
     * @param settingAutoLogin 自动登录
     * @return 返回分页数据
     */
    IPage<SySetting> pageSetting(IPage page, String userId, String settingTheme,String settingLingo,String settingAutoLogin);
}
