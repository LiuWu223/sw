package com.lw.swTest.ts.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.interceptor.LoginInterceptor;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.core.statics.GlobalStaticVariable;
import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.ts.OV.TsUserProjectOV;
import com.lw.swTest.ts.entity.TsProject;
import com.lw.swTest.ts.service.ITsProjectService;
import com.lw.swTest.ts.service.ITsUserProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@RestController
@RequestMapping("/ts/tsProject")
@Api(tags = "ts测试项目管理",value = "ts测试项目管理")
public class TsProjectController {
    private final ITsProjectService projectService;
    private final ITsUserProjectService userProjectService;
    public TsProjectController(ITsProjectService projectService, ITsUserProjectService userProjectService) {
        this.projectService = projectService;
        this.userProjectService = userProjectService;
    }

    /**
     * 分页查询测试项目信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param projectName 项目名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询项目信息", notes = "分页查询项目信息", httpMethod = "GET")
    @GetMapping("/pageTsProject")
    public ResponseResult pageTsProject (@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      String projectName) {
        IPage<TsProject> iPage = new Page<>(currentPage, pageSize);
        IPage<TsProject> page = projectService.pageTsProject(iPage, projectName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改项目信息
     * @param tsUserProjectOV 项目实体类，用户项目实体类
     * @return 无
     */
    @ApiOperation(value = "新增/修改项目信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/tsProjectSaveOrUp")
    public ResponseResult tsProjectSaveOrUp (HttpServletRequest request,@RequestBody TsUserProjectOV tsUserProjectOV) {
        // 获取token信息
        String token = request.getHeader(GlobalStaticVariable.AUTHORIZATION);
        JWT jwt = JWTUtil.parseToken(token);
        Map users = (Map) jwt.getPayload(GlobalStaticVariable.QUESTION_USER);

        // ID，用来判断是修改还是新增
        if (tsUserProjectOV.getTsProject().getId() == null){

            tsUserProjectOV.getTsProject().setUserId(String.valueOf(users.get("id")));
            // 新增项目
            TsProject selectProject = projectService.selectProjectByNameAndUserId(tsUserProjectOV.getTsProject().getProjectName(),tsUserProjectOV.getTsProject().getUserId());
            if (selectProject != null) { return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST); }
            boolean saveProject = projectService.saveOrUpdate(tsUserProjectOV.getTsProject());
            if (!saveProject) { return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL); }

            // 查询id
            TsProject selectProject2 = projectService.selectProjectByName(tsUserProjectOV.getTsProject().getProjectName());

            tsUserProjectOV.getTsUserProject().setProjectId(String.valueOf(selectProject2.getId()));
            tsUserProjectOV.getTsUserProject().setUserId(String.valueOf(users.get("id")));

            // 新增用户项目
            boolean saveUserProject = userProjectService.saveOrUpdate(tsUserProjectOV.getTsUserProject());
            if (!saveUserProject) { return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL); }

            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        } else {
            // 修改项目
            boolean saveProject = projectService.saveOrUpdate(tsUserProjectOV.getTsProject());
            if (!saveProject) { return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL); }
        }

        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除项目信息
     * @param tsProjectIds 项目信息列表
     * @return 返
     */
    @DeleteMapping("/removeTsProjectIds")
    @ApiOperation(value = "多选删除项目信息", notes = "多选删除项目信息", httpMethod = "DELETE")
    public ResponseResult removeTsProjectIds (Long[] tsProjectIds) {
        if (tsProjectIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> projectList = Arrays.asList(tsProjectIds);
        boolean removes = projectService.removeBatchByIds(projectList);
        for (Long projectId : projectList) {
            userProjectService.removeUserProjectByProjectId(String.valueOf(projectId));
        }
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
