package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SySetting;
import com.lw.swTest.sy.mapper.SySettingMapper;
import com.lw.swTest.sy.service.ISySettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SySettingServiceImpl extends ServiceImpl<SySettingMapper, SySetting> implements ISySettingService {
    /**
     * 分页查询设置信息
     * @param page 分页信息
     * @param userId 用户ID
     * @param settingTheme 自动主题
     * @param settingLingo 自动语言
     * @param settingAutoLogin 自动登录
     * @return 返回分页数据
     */
    @Override
    public IPage<SySetting> pageSetting(IPage page, String userId, String settingTheme,String settingLingo,String settingAutoLogin) {
        return baseMapper.pageSetting(page,userId,settingTheme,settingLingo,settingAutoLogin);
    }
}
