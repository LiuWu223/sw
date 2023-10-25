package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.lw.swTest.ts.service.ITsTaskDetailsService;
import com.lw.swTest.ts.service.ITsTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试任务详情表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsTaskDetails")
@Api(tags = "ts任务详情管理",value = "ts任务详情管理")
public class TsTaskDetailsController {
    private final ITsTaskDetailsService tsTaskDetailsService;
    public TsTaskDetailsController(ITsTaskDetailsService taskService) {
        this.tsTaskDetailsService = taskService;
    }

    /**
     * 分页查询测试任务信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询任务详情信息", notes = "分页查询任务详情信息", httpMethod = "GET")
    @GetMapping("/pageTaskDetails")
    public ResponseResult pageTaskDetails (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize) {
        IPage<TsTaskDetails> iPage = new Page<>(currentPage, pageSize);
        IPage<TsTaskDetails> page = tsTaskDetailsService.pageTaskDetails(iPage);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改任务详情信息
     * @param tsTaskDetails 任务详情信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改任务详情信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/taskDetailsSaveOrUp")
    public ResponseResult taskDetailsSaveOrUp (@RequestBody TsTaskDetails tsTaskDetails) {
        // ID，用来判断是修改还是新增
        Long id = tsTaskDetails.getId();
        if (id == null || id == 0){
            boolean save = tsTaskDetailsService.saveOrUpdate(tsTaskDetails);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = tsTaskDetailsService.saveOrUpdate(tsTaskDetails);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除任务详情信息
     * @param taskDetailsIds 任务详情信息列表
     * @return 返
     */
    @DeleteMapping("/removeTaskDetailsIds")
    @ApiOperation(value = "多选删除task信息", notes = "多选删除task信息", httpMethod = "DELETE")
    public ResponseResult removeTaskDetailsIds (Long[] taskDetailsIds) {
        if (taskDetailsIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(taskDetailsIds);
        boolean removes = tsTaskDetailsService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
