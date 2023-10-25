package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.lw.swTest.ts.entity.TsCaseStep;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 测试用例步骤表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsCaseStepService extends IService<TsCaseStep> {
    /**
     * 分页查询步骤信息
     * @param page 分页参数
     * @param stepContent 步骤内容
     * @return f
     */
    IPage<TsCaseStep> pageCaseStep(IPage page, String stepContent);
}
