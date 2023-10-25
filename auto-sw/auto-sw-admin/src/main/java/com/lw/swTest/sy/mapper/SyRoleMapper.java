package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.sy.entity.SyRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyRoleMapper extends BaseMapper<SyRole> {
    /**
     * 通过角色名查找角色信息
     * @param roleName 角色名称
     * @return 返回
     */
    List<SyRole> selectRoleName(@Param("roleName") String roleName);

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param roleName 角色名称
     * @param roleCode 角色账号
     * @return f
     */
    IPage<SyRole> pageRole(IPage page, @Param("roleName") String roleName, @Param("roleCode") String roleCode);

    SyRole selectRoleByName(@Param("roleName") String roleName);
}
