package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.sy.entity.SyRoleRoute;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyRoleAuthorityMapper extends BaseMapper<SyRoleAuthority> {
    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param authorityName 权限名称
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    IPage<SyRoleAuthority> pageRoleAuthority(IPage page, @Param("authorityName") String authorityName, @Param("roleName") String roleName);
}
