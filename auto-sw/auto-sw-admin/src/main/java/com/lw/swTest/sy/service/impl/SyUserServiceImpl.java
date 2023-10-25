package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.mapper.SyUserMapper;
import com.lw.swTest.sy.service.ISyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyUserServiceImpl extends ServiceImpl<SyUserMapper, SyUser> implements ISyUserService {

    /**
     * 通过用户名查找用户信息
     * @param userAccount 用户账号
     * @return 返回baseMapper
     */
    @Override
    public List<SyUser> selectUserName(String userAccount) {
        return baseMapper.selectUserName(userAccount);
    }

    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param userAccount 用户账号
     * @return f
     */
    @Override
    public IPage<SyUser> pageUser(IPage page, String userName, String userAccount, String roleName) {
        return baseMapper.pageUser(page,userName,userAccount,roleName);
    }

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public SyUser selectUserById(Long userId) {
        return baseMapper.selectUserById(userId);
    }


    @Override
    public int updateUser(SyUser syUser) {
        return baseMapper.updateUser(syUser);
    }

    @Override
    public int insertUser(SyUser syUser) {
        return baseMapper.insertUser(syUser);
    }
    @Override
    public int upUserIp(Long userId, String ipv,String path) {
        return baseMapper.upUserIp(userId,ipv,path);
    }

    @Override
    public SyUser selectByUserIp(String ipv) {
        return baseMapper.selectByUserIp(ipv);
    }
}
