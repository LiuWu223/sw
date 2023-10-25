package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.mapper.TsCaseMapper;
import com.lw.swTest.ts.service.ITsCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试用例表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsCaseServiceImpl extends ServiceImpl<TsCaseMapper, TsCase> implements ITsCaseService {
    /**
     * 通过测试用例名称查找测试用例信息
     * @param caseName 测试用例名称
     * @return 返回
     */
    @Override
    public List<TsCase> selectCaseName(String caseName) {
        return baseMapper.selectCaseName(caseName);
    }
    /**
     * 分页查询测试用例信息
     * @param page 分页参数
     * @param caseName 测试用例名称
     * @return f
     */
    @Override
    public IPage<TsCase> pageTsCase(IPage page, String caseName) {
        return baseMapper.pageCase(page,caseName);
    }

    @Override
    public List<TsCase> selectCaseBySectionId(Long sectionId) {
        return baseMapper.selectCaseBySectionId(sectionId);
    }
}
