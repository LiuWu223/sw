package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleAuthority;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.lw.swTest.sy.mapper.SyRoleAuthorityMapper;
import com.lw.swTest.sy.service.ISyRoleAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyRoleAuthorityServiceImpl extends ServiceImpl<SyRoleAuthorityMapper, SyRoleAuthority> implements ISyRoleAuthorityService {
    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param authorityName 权限名称
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    @Override
    public IPage<SyRoleAuthority> pageRoleAuthority(IPage page, String authorityName, String roleName) {
        return baseMapper.pageRoleAuthority(page,authorityName,roleName);
    }
}
