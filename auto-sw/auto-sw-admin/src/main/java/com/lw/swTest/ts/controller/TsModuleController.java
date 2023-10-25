package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsSection;
import com.lw.swTest.ts.service.ITsCaseService;
import com.lw.swTest.ts.service.ITsModuleService;
import com.lw.swTest.ts.service.ITsSectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试用例模块表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/ts/tsModule")
@Api(tags = "ts模块管理",value = "ts模块管理")
public class TsModuleController {
    private final ITsModuleService moduleService;
    private final ITsSectionService sectionService;
    private final ITsCaseService caseService;
    public TsModuleController(ITsModuleService moduleService, ITsSectionService sectionService, ITsCaseService caseService) {
        this.moduleService = moduleService;
        this.sectionService = sectionService;
        this.caseService = caseService;
    }

    /**
     * 分页查询测试模块信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param moduleName 模块名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询Module信息", notes = "分页查询Module信息", httpMethod = "GET")
    @GetMapping("/pageModule")
    public ResponseResult pageModule (@RequestParam("currentPage") Integer currentPage,
                                     @RequestParam("pageSize") Integer pageSize,
                                     String moduleName) {
        IPage<TsModule> iPage = new Page<>(currentPage, pageSize);
        IPage<TsModule> page = moduleService.pageModule(iPage, moduleName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改module信息
     * @param module Module信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改Module信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/moduleSaveOrUp")
    public ResponseResult moduleSaveOrUp (@RequestBody TsModule module) {
        // ID，用来判断是修改还是新增
        Long id = module.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<TsModule> modules = moduleService.selectModuleName(module.getModuleName());
            if( modules.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = moduleService.saveOrUpdate(module);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = moduleService.saveOrUpdate(module);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除Module信息
     * @param moduleIds Module信息列表
     * @return 返
     */
    @DeleteMapping("/removeModuleIds")
    @ApiOperation(value = "多选删除module信息", notes = "多选删除module信息", httpMethod = "DELETE")
    public ResponseResult removeModuleIds (Long[] moduleIds) {
        if (moduleIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(moduleIds);
        boolean removes = moduleService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }


    /**
     * 查询用例模块
     * @return 无
     */
    @ApiOperation(value = "查询模块项目测试用例", notes = "查询模块项目测试用例", httpMethod = "GET")
    @GetMapping("/selectModulesAndSectionAndCase")
    public ResponseResult selectTaskByProjectId () {
        List<TsModule> moduleList = moduleService.selectModules();
        int index1 = 0;
        for (TsModule moduleIs : moduleList) {
            List<TsSection> sectionList = sectionService.selectSectionByModuleId(moduleIs.getId());
            moduleList.get(index1).setSection(sectionList);
            index1 += 1;
            int index2 = 0;
            for (TsSection sections : sectionList) {
                List<TsCase> caseList = caseService.selectCaseBySectionId(sections.getId());
                sectionList.get(index2).setChildren(caseList);
                index2 += 1;
            }
        }
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS,moduleList);
    }
}
