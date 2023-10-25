package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.mapper.TsTaskMapper;
import com.lw.swTest.ts.service.ITsTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试任务表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsTaskServiceImpl extends ServiceImpl<TsTaskMapper, TsTask> implements ITsTaskService {
    /**
     * 通过任务名称查找任务信息
     * @param taskName 任务名称
     * @return 返回
     */
    @Override
    public List<TsTask> selectTaskName(String taskName) {
        return baseMapper.selectTaskName(taskName);
    }
    /**
     * 分页查询任务信息
     * @param page 分页参数
     * @param taskName 任务名称
     * @return f
     */
    @Override
    public IPage<TsTask> pageTask(IPage page, String taskName) {
        return baseMapper.pageTask(page,taskName);
    }

    @Override
    public TsTask getTaskTimiTNoe() {
        return baseMapper.getTaskTimiTNoe();
    }

    @Override
    public TsTask selectTaskById(Long taskId) {
        return baseMapper.selectTaskById(taskId);
    }
}
