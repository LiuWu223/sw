package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.service.ITsUserProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户项目表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@RestController
@RequestMapping("/ts/tsUserProject")
@Api(tags = "ts用户项目管理",value = "ts用户项目管理")
public class TsUserProjectController {
    private final ITsUserProjectService tsUserProjectService;
    public TsUserProjectController(ITsUserProjectService taskService) {
        this.tsUserProjectService = taskService;
    }

    /**
     * 分页查询测试任务信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询用户项目信息", notes = "分页查询用户项目信息", httpMethod = "GET")
    @GetMapping("/pageUserProject")
    public ResponseResult pageUserProject (@RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize") Integer pageSize) {
        IPage<TsUserProject> iPage = new Page<>(currentPage, pageSize);
        IPage<TsUserProject> page = tsUserProjectService.pageUserProject(iPage);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改用户项目信息
     * @param tsUserProject 用户项目信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改用户项目信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/userProjectSaveOrUp")
    public ResponseResult userProjectSaveOrUp (@RequestBody TsUserProject tsUserProject) {
        // ID，用来判断是修改还是新增
        Long id = tsUserProject.getId();
        if (id == null || id == 0){
            boolean save = tsUserProjectService.saveOrUpdate(tsUserProject);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = tsUserProjectService.saveOrUpdate(tsUserProject);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除用户项目信息
     * @param userProjectIds 用户项目信息列表
     * @return 返
     */
    @DeleteMapping("/removeUserProjectIds")
    @ApiOperation(value = "多选删除task信息", notes = "多选删除task信息", httpMethod = "DELETE")
    public ResponseResult removeUserProjectIds (Long[] userProjectIds) {
        if (userProjectIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(userProjectIds);
        boolean removes = tsUserProjectService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }


    /**
     * 查询用户项目
     * @param userId 用户ID
     * @return 无
     */
    @ApiOperation(value = "ts查询用户项目", notes = "ts查询用户项目", httpMethod = "GET")
    @GetMapping("/selectProjectByUserId/{userId}")
    public ResponseResult selectProjectByUserId (@PathVariable("userId") String userId) {
        List<TsUserProject> dictList = tsUserProjectService.selectProjectByUserId(userId);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS,dictList);
    }
}
