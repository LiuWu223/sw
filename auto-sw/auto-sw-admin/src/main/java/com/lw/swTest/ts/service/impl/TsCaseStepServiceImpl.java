package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.lw.swTest.ts.entity.TsCaseStep;
import com.lw.swTest.ts.mapper.TsCaseStepMapper;
import com.lw.swTest.ts.service.ITsCaseStepService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用例步骤表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsCaseStepServiceImpl extends ServiceImpl<TsCaseStepMapper, TsCaseStep> implements ITsCaseStepService {
    /**
     * 分页查询步骤信息
     * @param page 分页参数
     * @param stepContent 步骤内容
     * @return f
     */
    @Override
    public IPage<TsCaseStep> pageCaseStep(IPage page, String stepContent) {
        return baseMapper.pageCaseStep(page,stepContent);
    }
}
