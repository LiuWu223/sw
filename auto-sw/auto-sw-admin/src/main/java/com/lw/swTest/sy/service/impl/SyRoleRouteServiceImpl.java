package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.lw.swTest.sy.entity.SyUserRole;
import com.lw.swTest.sy.mapper.SyRoleRouteMapper;
import com.lw.swTest.sy.service.ISyRoleRouteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色路由表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyRoleRouteServiceImpl extends ServiceImpl<SyRoleRouteMapper, SyRoleRoute> implements ISyRoleRouteService {
    /**
     * 分页查询角色路由信息
     * @param page 分页参数
     * @param routeLabel 角色Label
     * @param roleName 角色名称
     * @return f
     */
    @Override
    public IPage<SyRoleRoute> pageRoleRoute(IPage page, String routeLabel, String roleName) {
        return baseMapper.pageRoleRoute(page,routeLabel,roleName);
    }
    @Override
    public List<SyRoleRoute> selectRoleIdByRoute(Long roleId) {
        return baseMapper.selectRoleIdByRoute(roleId);
    }
}
