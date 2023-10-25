package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.entity.SyUserRole;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyUserRoleService extends IService<SyUserRole> {
    /**
     * 分页查询用户角色信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param roleName 角色名称
     * @return f
     */
    IPage<SyUserRole> pageUserRole(IPage page, String userName, String roleName);

    SyUserRole selectUserIdByRoleId(Long userId);

    /**
     * 修改用户角色信息
     * @param syUserRole 用户角色信息
     * @return 影响行
     */
    int updateUserRole(SyUserRole syUserRole);

    int insertUserRole(SyUserRole syUserRole);

    int removeUserRoleByUserId(Long userId);
}
