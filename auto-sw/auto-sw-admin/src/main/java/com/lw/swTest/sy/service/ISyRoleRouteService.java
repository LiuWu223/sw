package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyUserRole;

import java.util.List;

/**
 * <p>
 * 角色路由表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyRoleRouteService extends IService<SyRoleRoute> {
    /**
     * 分页查询角色路由信息
     * @param page 分页参数
     * @param routeLabel 角色Label
     * @param roleName 角色名称
     * @return f
     */
    IPage<SyRoleRoute> pageRoleRoute(IPage page, String routeLabel, String roleName);

    List<SyRoleRoute> selectRoleIdByRoute(Long roleId);
}
