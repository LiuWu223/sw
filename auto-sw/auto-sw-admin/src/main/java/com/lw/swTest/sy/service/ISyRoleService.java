package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyRoleService extends IService<SyRole> {
    /**
     * 查找角色名
     * @param roleName 角色名称
     * @return 返
     */
    List<SyRole> selectRoleName(String roleName);

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param roleName 角色名称
     * @param roleCode 角色Code
     * @return f
     */
    IPage<SyRole> pageRole(IPage page, String roleName, String roleCode);

    SyRole selectRoleByName(String roleName);
}
