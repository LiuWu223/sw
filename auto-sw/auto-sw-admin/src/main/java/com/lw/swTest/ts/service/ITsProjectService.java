package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.entity.TsProject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
public interface ITsProjectService extends IService<TsProject> {
    /**
     * 分页查询项目信息
     * @param page 分页参数
     * @param ProjectName 项目名称
     * @return f
     */
    IPage<TsProject> pageTsProject(IPage page, String ProjectName);

    TsProject selectProjectByName(String ProjectName);

    TsProject selectProjectByNameAndUserId(String ProjectName, String userId);
}
