package com.lw.swTest.ts.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.swtest.entity.SwtestTask;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.service.ITsProjectTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 项目任务表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
@RestController
@RequestMapping("/ts/tsProjectTask")
@Api(tags = "ts项目任务管理",value = "ts项目任务管理")
public class TsProjectTaskController {
    private final ITsProjectTaskService tsProjectTaskService;
    public TsProjectTaskController(ITsProjectTaskService taskService) {
        this.tsProjectTaskService = taskService;
    }

    /**
     * 分页查询测试任务信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询项目任务信息", notes = "分页查询项目任务信息", httpMethod = "GET")
    @GetMapping("/pageProjectTask")
    public ResponseResult pageProjectTask (@RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize") Integer pageSize) {
        IPage<TsProjectTask> iPage = new Page<>(currentPage, pageSize);
        IPage<TsProjectTask> page = tsProjectTaskService.pageProjectTask(iPage);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改项目任务信息
     * @param tsProjectTask 项目任务信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改项目任务信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/projectTaskSaveOrUp")
    public ResponseResult projectTaskSaveOrUp (@RequestBody TsProjectTask tsProjectTask) {
        // ID，用来判断是修改还是新增
        Long id = tsProjectTask.getId();
        if (id == null || id == 0){
            boolean save = tsProjectTaskService.saveOrUpdate(tsProjectTask);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = tsProjectTaskService.saveOrUpdate(tsProjectTask);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除项目任务信息
     * @param projectTaskIds 项目任务信息列表
     * @return 返
     */
    @DeleteMapping("/removeProjectTaskIds")
    @ApiOperation(value = "多选删除task信息", notes = "多选删除task信息", httpMethod = "DELETE")
    public ResponseResult removeProjectTaskIds (Long[] projectTaskIds) {
        if (projectTaskIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(projectTaskIds);
        boolean removes = tsProjectTaskService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }

    /**
     * 查询项目任务
     * @param projectId 项目ID
     * @return 无
     */
    @ApiOperation(value = "查询项目任务", notes = "查询项目任务", httpMethod = "GET")
    @GetMapping("/selectTaskByProjectId/{projectId}")
    public ResponseResult selectTaskByProjectId (@PathVariable("projectId") String projectId) {
        List<TsProjectTask> dictList = tsProjectTaskService.selectTaskByProjectId(projectId);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS,dictList);
    }

    @ApiOperation(value = "写入任务", notes = "写入任务", httpMethod = "GET")
    @GetMapping("/writeTask/{projectId}")
    public void exportTaks(HttpServletResponse response,@PathVariable("projectId") String projectId) throws IOException {
        List<TsProjectTask> dictList = tsProjectTaskService.selectTaskByProjectId(projectId);
        // 通过工具类创建writer，默认创建xls格式(相当于创建Excel表格)
        ExcelWriter writer = ExcelUtil.getWriter();
        // 定义Excel表头
        // 自定义标题别名
        writer.addHeaderAlias("taskId", "序号");
        writer.addHeaderAlias("devices", "devices号");
        writer.addHeaderAlias("devices", "机型");
        writer.addHeaderAlias("taskName", "测试设备");
        writer.addHeaderAlias("taskNotes", "测试备注");
        writer.addHeaderAlias("userAccount", "添加用户账号");
        writer.addHeaderAlias("createBy", "创建人");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.setOnlyAlias(true);
        // out为OutputStream，需要写出到的目标流
        writer.write(dictList, true);
        // 响应给用户去下载
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        // test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename=%E5%AD%A6%E6%A0%A1%E4%BF%A1%E6%81%AF.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        // 此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

}
