package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
public interface TsProjectMapper extends BaseMapper<TsProject> {
    /**
     * 分页查询项目信息
     * @param page 分页参数
     * @param projectName 项目名称
     * @return f
     */
    IPage<TsProject> pageProject(IPage page, @Param("projectName") String projectName);

    TsProject selectProjectByNameAndUserId(@Param("projectName") String projectName,@Param("userId") String userId);


    TsProject selectProjectByName(@Param("projectName") String projectName);
}
