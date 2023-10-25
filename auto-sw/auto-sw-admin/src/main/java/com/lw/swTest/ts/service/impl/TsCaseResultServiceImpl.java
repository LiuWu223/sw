package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.lw.swTest.ts.mapper.TsCaseResultMapper;
import com.lw.swTest.ts.service.ITsCaseResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用例预期表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsCaseResultServiceImpl extends ServiceImpl<TsCaseResultMapper, TsCaseResult> implements ITsCaseResultService {
    /**
     * 分页查询预期结果信息
     * @param page 分页参数
     * @param resultContent 预期结果内容
     * @return f
     */
    @Override
    public IPage<TsCaseResult> pageCaseResult(IPage page, String resultContent) {
        return baseMapper.pageCaseResult(page,resultContent);
    }
}
