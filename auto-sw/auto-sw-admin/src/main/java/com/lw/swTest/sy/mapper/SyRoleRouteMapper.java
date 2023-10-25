package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色路由表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyRoleRouteMapper extends BaseMapper<SyRoleRoute> {
    /**
     * 分页查询角色路由信息
     * @param page 分页参数
     * @param routeLabel 角色Label
     * @param roleName 角色名称
     * @return f
     */
    IPage<SyRoleRoute> pageRoleRoute(IPage page, @Param("routeLabel") String routeLabel, @Param("roleName") String roleName);

    List<SyRoleRoute> selectRoleIdByRoute(@Param("roleId") Long roleId);
}
