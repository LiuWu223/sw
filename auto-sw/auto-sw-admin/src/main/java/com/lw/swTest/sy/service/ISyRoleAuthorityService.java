package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleAuthority;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyRoleRoute;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyRoleAuthorityService extends IService<SyRoleAuthority> {
    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param authorityName 权限名称
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    IPage<SyRoleAuthority> pageRoleAuthority(IPage page, String authorityName, String roleName);
}
