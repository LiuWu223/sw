package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsCaseStep;
import com.lw.swTest.ts.service.ITsCaseStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试用例步骤表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsCaseStep")
@Api(tags = "ts测试步骤管理",value = "ts测试步骤管理")
public class TsCaseStepController {
    private final ITsCaseStepService caseStepService;

    public TsCaseStepController(ITsCaseStepService caseStepService) {
        this.caseStepService = caseStepService;
    }

    /**
     * 分页查询测试测试步骤信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询测试步骤信息", notes = "分页查询测试步骤信息", httpMethod = "GET")
    @GetMapping("/pageCaseStep")
    public ResponseResult pageCaseStep (@RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize,
                                        String stepContent) {
        IPage<TsCaseStep> iPage = new Page<>(currentPage, pageSize);
        IPage<TsCaseStep> page = caseStepService.pageCaseStep(iPage,stepContent);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改测试步骤信息
     * @param tsCaseStep 测试步骤信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改测试步骤信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/caseStepSaveOrUp")
    public ResponseResult caseStepSaveOrUp (@RequestBody TsCaseStep tsCaseStep) {
        // ID，用来判断是修改还是新增
        Long id = tsCaseStep.getId();
        if (id == null || id == 0){
            boolean save = caseStepService.saveOrUpdate(tsCaseStep);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = caseStepService.saveOrUpdate(tsCaseStep);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除测试步骤信息
     * @param caseStepIds 测试步骤信息列表
     * @return 返
     */
    @DeleteMapping("/removeCaseStepIds")
    @ApiOperation(value = "多选删除测试步骤信息", notes = "多选删除测试步骤信息", httpMethod = "DELETE")
    public ResponseResult removeCaseStepIds (Long[] caseStepIds) {
        if (caseStepIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(caseStepIds);
        boolean removes = caseStepService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
