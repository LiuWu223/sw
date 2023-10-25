package com.lw.swTest.ts.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.swtest.entity.SwtestTask;
import com.lw.swTest.sy.service.ISyDictService;
import com.lw.swTest.ts.OV.TsProjectTaskTOV;
import com.lw.swTest.ts.OV.TsTaskCaseOV;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.lw.swTest.ts.service.ITsCaseService;
import com.lw.swTest.ts.service.ITsProjectTaskService;
import com.lw.swTest.ts.service.ITsTaskDetailsService;
import com.lw.swTest.ts.service.ITsTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 测试任务表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsTask")
@Api(tags = "ts任务管理",value = "ts任务管理")
public class TsTaskController {
    private final ITsTaskService taskService;
    private final ITsCaseService tsCaseService;
    private final ITsTaskDetailsService taskDetailsService;
    private final ITsProjectTaskService taskProjectTaskService;
    private final ISyDictService sysDictService;
    public TsTaskController(ITsTaskService taskService, ITsCaseService tsCaseService, ITsTaskDetailsService taskDetailsService, ITsProjectTaskService taskProjectTaskService, ISyDictService sysDictService) {
        this.taskService = taskService;
        this.tsCaseService = tsCaseService;
        this.taskDetailsService = taskDetailsService;
        this.taskProjectTaskService = taskProjectTaskService;
        this.sysDictService = sysDictService;
    }

    /**
     * 分页查询测试任务信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param taskName 任务名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询Task信息", notes = "分页查询Task信息", httpMethod = "GET")
    @GetMapping("/pageTask")
    public ResponseResult pageTask (@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      String taskName) {
        IPage<TsTask> iPage = new Page<>(currentPage, pageSize);
        IPage<TsTask> page = taskService.pageTask(iPage, taskName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改task信息
     * @param tsProjectTaskTOV 任务与项目信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改任务信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/taskSaveOrUp")
    public ResponseResult taskSaveOrUp (@RequestBody TsProjectTaskTOV tsProjectTaskTOV) {
        // ID，用来判断是修改还是新增
        Long id = tsProjectTaskTOV.getTsTask().getId();
        Long dictFirstId = sysDictService.selectDict(tsProjectTaskTOV.getTsTask().getDictIdFirst()).getId();
        Long dictTypeId = sysDictService.selectDict(tsProjectTaskTOV.getTsTask().getDictIdType()).getId();
        // 转换字典
        tsProjectTaskTOV.getTsTask().setDictIdFirst(String.valueOf(dictFirstId));
        tsProjectTaskTOV.getTsTask().setDictIdType(String.valueOf(dictTypeId));
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<TsTask> tasks = taskService.selectTaskName(tsProjectTaskTOV.getTsTask().getTaskName());
            if( tasks.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = taskService.saveOrUpdate(tsProjectTaskTOV.getTsTask());
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            // 新增测试项目测试任务
            TsTask ta = taskService.getTaskTimiTNoe();
            tsProjectTaskTOV.getTsProjectTask().setTaskId(ta.getId());
            boolean projectTaskSave = taskProjectTaskService.saveOrUpdate(tsProjectTaskTOV.getTsProjectTask());
            if (!projectTaskSave) {return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);}
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = taskService.saveOrUpdate(tsProjectTaskTOV.getTsTask());
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    @ApiOperation(value = "新增测试信息", notes = "新增测试信息", httpMethod = "POST")
    @PostMapping("/taskSaveOrUpTaskCase")
    public ResponseResult taskSaveOrUpTaskCase (@RequestBody TsTaskCaseOV tsTaskCaseOV) {
        Long taskId = tsTaskCaseOV.getTsTask().getId();
        if (taskId == null) {
            Long dictFirstId = sysDictService.selectDict(tsTaskCaseOV.getTsTask().getDictIdFirst()).getId();
            Long dictTypeId = sysDictService.selectDict(tsTaskCaseOV.getTsTask().getDictIdType()).getId();
            // 转换字典
            tsTaskCaseOV.getTsTask().setDictIdFirst(String.valueOf(dictFirstId));
            tsTaskCaseOV.getTsTask().setDictIdType(String.valueOf(dictTypeId));
            // 新增任务
            boolean taskSave = taskService.saveOrUpdate(tsTaskCaseOV.getTsTask());
            if (!taskSave) {return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);}
            TsTask ta = taskService.getTaskTimiTNoe();
            tsTaskCaseOV.getTsTaskDetails().setTaskId(ta.getId());
            tsTaskCaseOV.getTsProjectTask().setTaskId(ta.getId());
            // 新增任务用例
            for (Long caseId : tsTaskCaseOV.getCaseIds()) {
                tsTaskCaseOV.getTsTaskDetails().setCaseId(caseId);
                TsTaskDetails a = new TsTaskDetails();
                a.setCaseId(caseId);
                a.setTaskId(ta.getId());
                boolean taskDetaSave = taskDetailsService.saveOrUpdate(a);
                if (!taskDetaSave) {
                    return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
                }
            }
            // 新增测试项目测试任务
            boolean projectTaskSave = taskProjectTaskService.saveOrUpdate(tsTaskCaseOV.getTsProjectTask());
            if (!projectTaskSave) {return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);}
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改项目
        Long dictFirstId = sysDictService.selectDict(tsTaskCaseOV.getTsTask().getDictIdFirst()).getId();
        Long dictTypeId = sysDictService.selectDict(tsTaskCaseOV.getTsTask().getDictIdType()).getId();
        // 转换字典
        tsTaskCaseOV.getTsTask().setDictIdFirst(String.valueOf(dictFirstId));
        tsTaskCaseOV.getTsTask().setDictIdType(String.valueOf(dictTypeId));
        // 修改任务
        boolean taskSave = taskService.saveOrUpdate(tsTaskCaseOV.getTsTask());
        if (!taskSave) {return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);}
        TsTask ta = taskService.getTaskTimiTNoe();
        tsTaskCaseOV.getTsTaskDetails().setTaskId(ta.getId());
        tsTaskCaseOV.getTsProjectTask().setTaskId(ta.getId());

        // 查询出用户原有测试用例
        List<TsTaskDetails> userCase = taskDetailsService.selectTaskCaseByTaskId(ta.getId());
        // 求得用户上传与存在的差集
        ArrayList<Long> list1 = new ArrayList<>(List.of());
        for (TsTaskDetails uc1 :userCase) {
            list1.add(uc1.getCaseId());
        }

        // 用户已存在
        //用户上传
        List<Long> list2 = new ArrayList<>(Arrays.asList(tsTaskCaseOV.getCaseIds()));
        ArrayList<Long> remos = new ArrayList<>(List.of());
        // 遍历用例已存在
        for (Long i : list1) {
            // 判断用户上传的是否有i
            if (!list2.contains(i)) {
                // 删除
                for (TsTaskDetails c :userCase) {
                    if (Objects.equals(c.getCaseId(), i)){
                        remos.add(c.getId());
                        break;
                    }
                }
            }

        }
//        System.out.println(remos);
//        System.out.println("=============删除的==================");
        if (remos.size() > 0) {
            boolean removes = taskDetailsService.removeBatchByIds(remos);
            if (!removes) { return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL); }
        }

        for (Long i : list2) {
            // 判断用户上传的是否有i
            if (!list1.contains(i)) {
                // 新增
//                System.out.println(i);
//                System.out.println("=============新增的==================");
                TsTaskDetails a = new TsTaskDetails();
                a.setCaseId(i);
                a.setTaskId(ta.getId());
                boolean taskDetaSave = taskDetailsService.saveOrUpdate(a);
                if (!taskDetaSave) { return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL); }
            }
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }
    /**
     * 多选删除Task信息
     * @param taskIds Task信息列表
     * @return 返
     */
    @DeleteMapping("/removeTaskIds")
    @ApiOperation(value = "多选删除task信息", notes = "多选删除task信息", httpMethod = "DELETE")
    public ResponseResult removeTaskIds (Long[] taskIds) {
        if (taskIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(taskIds);
        boolean removes = taskService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }

    @ApiOperation(value = "根据taskId查询展开任务信息", notes = "根据taskId查询展开任务信息", httpMethod = "GET")
    @GetMapping("/selectTaskByTaskId/{taskId}")
    public ResponseResult selectTaskByTaskId (@PathVariable("taskId") Long taskId) {
        TsTask task = taskService.selectTaskById(taskId);
        List<TsTaskDetails> taskCase = taskDetailsService.selectTaskCaseByTaskId(taskId);
        Map<String, Object> taskCaseList = new HashMap<>();
        taskCaseList.put("task",task);
        taskCaseList.put("taskCase",taskCase);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, taskCaseList);
    }

}
