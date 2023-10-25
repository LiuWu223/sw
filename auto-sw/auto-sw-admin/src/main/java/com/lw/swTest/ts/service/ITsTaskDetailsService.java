package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试任务详情表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsTaskDetailsService extends IService<TsTaskDetails> {
    /**
     * 分页查询任务信息
     * @param page 分页参数
     * @return f
     */
    IPage<TsTaskDetails> pageTaskDetails(IPage page);

    List<TsTaskDetails> selectTaskCaseByTaskId(Long taskId);
}
