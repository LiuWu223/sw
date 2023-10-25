package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyAuthority;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.mapper.SyAuthorityMapper;
import com.lw.swTest.sy.service.ISyAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyAuthorityServiceImpl extends ServiceImpl<SyAuthorityMapper, SyAuthority> implements ISyAuthorityService {
    /**
     * 通过权限名称查找权限信息
     * @param authorityName 权限名称
     * @return 返回
     */
    @Override
    public List<SyAuthority> selectAuthorityName(String authorityName) {
        return baseMapper.selectAuthorityName(authorityName);
    }

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param authorityName 权限名称
     * @return f
     */
    @Override
    public IPage<SyAuthority> pageAuthority(IPage page, String authorityName) {
        return baseMapper.pageAuthority(page,authorityName);
    }
}
