package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.lw.swTest.ts.service.ITsCaseResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试用例预期表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsCaseResult")
@Api(tags = "ts测试预期管理",value = "ts测试预期管理")
public class TsCaseResultController {
    private final ITsCaseResultService caseService;
    public TsCaseResultController(ITsCaseResultService caseService) {
        this.caseService = caseService;
    }

    /**
     * 分页查询测试测试预期信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询测试预期信息", notes = "分页查询测试预期信息", httpMethod = "GET")
    @GetMapping("/pageCaseResult")
    public ResponseResult pageCaseResult (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                          String resultContent) {
        IPage<TsCaseResult> iPage = new Page<>(currentPage, pageSize);
        IPage<TsCaseResult> page = caseService.pageCaseResult(iPage,resultContent);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改测试预期信息
     * @param tsCaseResult 测试预期信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改测试预期信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/caseResultSaveOrUp")
    public ResponseResult caseResultSaveOrUp (@RequestBody TsCaseResult tsCaseResult) {
        // ID，用来判断是修改还是新增
        Long id = tsCaseResult.getId();
        if (id == null || id == 0){
            boolean save = caseService.saveOrUpdate(tsCaseResult);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = caseService.saveOrUpdate(tsCaseResult);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除测试预期信息
     * @param caseResultIds 测试预期信息列表
     * @return 返
     */
    @DeleteMapping("/removeCaseResultIds")
    @ApiOperation(value = "多选删除测试预期信息", notes = "多选删除测试预期信息", httpMethod = "DELETE")
    public ResponseResult removeCaseResultIds (Long[] caseResultIds) {
        if (caseResultIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(caseResultIds);
        boolean removes = caseService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
