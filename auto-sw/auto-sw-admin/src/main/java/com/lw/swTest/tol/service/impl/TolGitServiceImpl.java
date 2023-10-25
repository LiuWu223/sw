package com.lw.swTest.tol.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.tol.entity.TolAdb;
import com.lw.swTest.tol.entity.TolGit;
import com.lw.swTest.tol.mapper.TolGitMapper;
import com.lw.swTest.tol.service.ITolGitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * git项目管理表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@Service
public class TolGitServiceImpl extends ServiceImpl<TolGitMapper, TolGit> implements ITolGitService {
    /**
     * 通过git名称查找git信息
     * @param gitName gitName
     * @return 返回
     */
    @Override
    public List<TolGit> selectGitName(String gitName) {
        return baseMapper.selectGitName(gitName);
    }
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param gitName gitName
     * @return f
     */
    @Override
    public IPage<TolGit> pageGit(IPage page, String gitName) {
        return baseMapper.pageGit(page,gitName);
    }
}
