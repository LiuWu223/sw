package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.mapper.SyRoleMapper;
import com.lw.swTest.sy.service.ISyRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyRoleServiceImpl extends ServiceImpl<SyRoleMapper, SyRole> implements ISyRoleService {
    /**
     * 通过角色名查找角色信息
     * @param roleName 角色名称
     * @return 返回baseMapper
     */
    @Override
    public List<SyRole> selectRoleName(String roleName) {
        return baseMapper.selectRoleName(roleName);
    }

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param roleName 角色名称
     * @param roleCode 角色Code
     * @return f
     */
    @Override
    public IPage<SyRole> pageRole(IPage page, String roleName, String roleCode) {
        return baseMapper.pageRole(page,roleName,roleCode);
    }

    @Override
    public SyRole selectRoleByName(String roleName) {
        return baseMapper.selectRoleByName(roleName);
    }
}
