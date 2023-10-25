package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.entity.SyUserRole;
import com.lw.swTest.sy.mapper.SyUserRoleMapper;
import com.lw.swTest.sy.service.ISyUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyUserRoleServiceImpl extends ServiceImpl<SyUserRoleMapper, SyUserRole> implements ISyUserRoleService {
    /**
     * 分页查询用户角色信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param roleName 角色名称
     * @return f
     */
    @Override
    public IPage<SyUserRole> pageUserRole(IPage page, String userName, String roleName) {
        return baseMapper.pageUserRole(page,userName,roleName);
    }

    /**
     * 通过用户ID查询用户角色ID
     * @param userId 用户ID
     * @return 用户角色ID
     */
    @Override
    public SyUserRole selectUserIdByRoleId(Long userId) {
        return baseMapper.selectUserIdByRoleId(userId);
    }

    /**
     * 修改用户角色信息
     * @param syUserRole 用户角色信息
     * @return 影响行
     */
    @Override
    public int updateUserRole(SyUserRole syUserRole) {
        return baseMapper.updateUserRole(syUserRole);
    }

    @Override
    public int insertUserRole(SyUserRole syUserRole) {
        return baseMapper.insertUserRole(syUserRole);
    }

    @Override
    public int removeUserRoleByUserId(Long userId) {
        return baseMapper.removeUserRoleByUserId(userId);
    }
}
