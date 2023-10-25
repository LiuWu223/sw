package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.ts.entity.TsModule;

import java.util.List;

/**
 * <p>
 * 测试用例表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsCaseService extends IService<TsCase> {
    /**
     * 通过测试用例名称查找测试用例信息
     * @param caseName 测试用例名称
     * @return 返回
     */
    List<TsCase> selectCaseName(String caseName);
    /**
     * 分页查询测试用例信息
     * @param page 分页参数
     * @param CaseName 测试用例名称
     * @return f
     */
    IPage<TsCase> pageTsCase(IPage page, String CaseName);

    List<TsCase> selectCaseBySectionId(Long sectionId);
}
