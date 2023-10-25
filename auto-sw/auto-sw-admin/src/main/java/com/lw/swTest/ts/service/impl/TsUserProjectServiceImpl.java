package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.mapper.TsUserProjectMapper;
import com.lw.swTest.ts.service.ITsUserProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户项目表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@Service
public class TsUserProjectServiceImpl extends ServiceImpl<TsUserProjectMapper, TsUserProject> implements ITsUserProjectService {
    /**
     * 分页查询用户项目信息
     * @param page 分页参数
     * @return f
     */
    @Override
    public IPage<TsUserProject> pageUserProject(IPage page) {
        return baseMapper.pageUserProject(page);
    }

    /**
     * 查询用户项目
     * @param userId 用户ID
     * @return 返回
     */
    @Override
    public List<TsUserProject> selectProjectByUserId(String userId) {
        return baseMapper.selectProjectByUserId(userId);
    }

    @Override
    public int removeUserProjectByProjectId(String projectId) {
        return baseMapper.removeUserProjectByProjectId(projectId);
    }
}
