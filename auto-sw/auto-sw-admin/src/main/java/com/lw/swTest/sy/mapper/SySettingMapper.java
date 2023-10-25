package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SySetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统设置表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SySettingMapper extends BaseMapper<SySetting> {
    /**
     * 分页查询设置信息
     * @param page 分页信息
     * @param userId 用户ID
     * @param settingTheme 自动主题
     * @param settingLingo 自动语言
     * @param settingAutoLogin 自动登录
     * @return 返回分页数据
     */
    IPage<SySetting> pageSetting(IPage page, @Param("userId") String userId,
                                 @Param("settingTheme") String settingTheme,
                                 @Param("settingLingo") String settingLingo,
                                 @Param("settingAutoLogin") String settingAutoLogin);
}
