package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsSection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试项表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface TsSectionMapper extends BaseMapper<TsSection> {
    /**
     * 通过测试项名称查找测试项信息
     * @param sectionName 测试项名称
     * @return 返回
     */
    List<TsSection> selectSectionName(@Param("sectionName") String sectionName);

    /**
     * 分页查询测试项信息
     * @param page 分页参数
     * @param sectionName 测试项名称
     * @return f
     */
    IPage<TsSection> pageSection(IPage page, @Param("sectionName") String sectionName);
    List<TsSection> selectSectionByModuleId(@Param("moduleId") Long moduleId);
}
