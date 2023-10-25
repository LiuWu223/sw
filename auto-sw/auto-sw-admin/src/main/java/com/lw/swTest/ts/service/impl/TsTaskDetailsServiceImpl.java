package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.lw.swTest.ts.mapper.TsTaskDetailsMapper;
import com.lw.swTest.ts.service.ITsTaskDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试任务详情表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsTaskDetailsServiceImpl extends ServiceImpl<TsTaskDetailsMapper, TsTaskDetails> implements ITsTaskDetailsService {
    /**
     * 分页查询任务详情信息
     * @param page 分页参数
     * @return f
     */
    @Override
    public IPage<TsTaskDetails> pageTaskDetails(IPage page) {
        return baseMapper.pageTaskDetails(page);
    }

    @Override
    public List<TsTaskDetails> selectTaskCaseByTaskId(Long taskId) {
        return baseMapper.selectTaskCaseByTaskId(taskId);
    }
}
