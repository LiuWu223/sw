package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsSection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试项表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface ITsSectionService extends IService<TsSection> {
    /**
     * 通过测试项名称查找测试项信息
     * @param sectionName 测试项名称
     * @return 返回
     */
    List<TsSection> selectSectionName(String sectionName);
    /**
     * 分页查询测试项信息
     * @param page 分页参数
     * @param sectionName 测试项名称
     * @return f
     */
    IPage<TsSection> pageSection(IPage page, String sectionName);

    List<TsSection> selectSectionByModuleId(Long moduleId);
}
