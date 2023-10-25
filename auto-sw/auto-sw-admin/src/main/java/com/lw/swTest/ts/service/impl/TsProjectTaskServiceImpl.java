package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.mapper.TsProjectTaskMapper;
import com.lw.swTest.ts.service.ITsProjectTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目任务表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@Service
public class TsProjectTaskServiceImpl extends ServiceImpl<TsProjectTaskMapper, TsProjectTask> implements ITsProjectTaskService {
    /**
     * 分页查询项目任务信息
     * @param page 分页参数
     * @return f
     */
    @Override
    public IPage<TsProjectTask> pageProjectTask(IPage page) {
        return baseMapper.pageProjectTask(page);
    }

    /**
     * 查询项目任务
     * @param projectId 项目ID
     * @return 返回
     */
    @Override
    public List<TsProjectTask> selectTaskByProjectId(String projectId) {
        return baseMapper.selectTaskByProjectId(projectId);
    }

    @Override
    public List<TsProjectTask> selectTaskByProjectIdReNewTime(String projectId) {
        return baseMapper.selectTaskByProjectId(projectId);
    }
}
