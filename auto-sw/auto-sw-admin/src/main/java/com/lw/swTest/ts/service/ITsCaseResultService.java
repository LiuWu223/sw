package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 测试用例预期表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsCaseResultService extends IService<TsCaseResult> {
    /**
     * 分页查询预期结果信息
     * @param page 分页参数
     * @param resultContent 预期结果内容
     * @return f
     */
    IPage<TsCaseResult> pageCaseResult(IPage page, String resultContent);
}
