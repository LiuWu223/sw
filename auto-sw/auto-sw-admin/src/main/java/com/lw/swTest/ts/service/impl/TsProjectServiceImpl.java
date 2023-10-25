package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.mapper.TsProjectMapper;
import com.lw.swTest.ts.service.ITsProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@Service
public class TsProjectServiceImpl extends ServiceImpl<TsProjectMapper, TsProject> implements ITsProjectService {
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param projectName 项目名称
     * @return f
     */
    @Override
    public IPage<TsProject> pageTsProject(IPage page, String projectName) {
        return baseMapper.pageProject(page,projectName);
    }

    @Override
    public TsProject selectProjectByNameAndUserId(String projectName, String userId) {
        return baseMapper.selectProjectByNameAndUserId(projectName,userId);
    }

    @Override
    public TsProject selectProjectByName(String projectName) {
        return baseMapper.selectProjectByName(projectName);
    }
}
