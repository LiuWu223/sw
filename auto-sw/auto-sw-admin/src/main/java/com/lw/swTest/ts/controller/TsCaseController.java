package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.service.ITsCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试用例表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsCase")
@Api(tags = "ts测试用例管理",value = "ts测试用例管理")
public class TsCaseController {
    private final ITsCaseService caseService;
    public TsCaseController(ITsCaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * 分页查询测试测试用例信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param caseName 测试用例名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询测试用例信息", notes = "分页查询测试用例信息", httpMethod = "GET")
    @GetMapping("/pageTsCase")
    public ResponseResult pageTsCase (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String caseName) {
        IPage<TsCase> iPage = new Page<>(currentPage, pageSize);
        IPage<TsCase> page = caseService.pageTsCase(iPage, caseName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改测试用例信息
     * @param tsCases 测试用例信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改测试用例信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/tsCaseSaveOrUp")
    public ResponseResult tsCaseSaveOrUp (@RequestBody TsCase tsCases) {
        // ID，用来判断是修改还是新增
        Long id = tsCases.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<TsCase> cases = caseService.selectCaseName(tsCases.getCaseName());
            if( cases.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = caseService.saveOrUpdate(tsCases);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = caseService.saveOrUpdate(tsCases);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除测试用例信息
     * @param tsCaseIds 测试用例信息列表
     * @return 返
     */
    @DeleteMapping("/removeTsCaseIds")
    @ApiOperation(value = "多选删除测试用例信息", notes = "多选删除测试用例信息", httpMethod = "DELETE")
    public ResponseResult removeTsCaseIds (Long[] tsCaseIds) {
        if (tsCaseIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(tsCaseIds);
        boolean removes = caseService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
