package com.lw.swTest.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.ts.entity.TsSection;
import com.lw.swTest.ts.service.ITsSectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 测试项表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@RestController
@RequestMapping("/py/tsSection")
@Api(tags = "ts测试项管理",value = "ts测试项管理")
public class TsSectionController {
    private final ITsSectionService sectionService;
    public TsSectionController(ITsSectionService sectionService) {
        this.sectionService = sectionService;
    }

    /**
     * 分页查询测试测试项信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param sectionName 测试项名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询测试项信息", notes = "分页查询测试项信息", httpMethod = "GET")
    @GetMapping("/pageSection")
    public ResponseResult pageSection (@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      String sectionName) {
        IPage<TsSection> iPage = new Page<>(currentPage, pageSize);
        IPage<TsSection> page = sectionService.pageSection(iPage, sectionName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改测试项信息
     * @param section 测试项信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改测试项信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/sectionSaveOrUp")
    public ResponseResult sectionSaveOrUp (@RequestBody TsSection section) {
        // ID，用来判断是修改还是新增
        Long id = section.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<TsSection> sections = sectionService.selectSectionName(section.getSectionName());
            if( sections.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = sectionService.saveOrUpdate(section);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = sectionService.saveOrUpdate(section);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除测试项信息
     * @param sectionIds 测试项信息列表
     * @return 返
     */
    @DeleteMapping("/removeSectionIds")
    @ApiOperation(value = "多选删除测试项信息", notes = "多选删除测试项信息", httpMethod = "DELETE")
    public ResponseResult removeSectionIds (Long[] sectionIds) {
        if (sectionIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(sectionIds);
        boolean removes = sectionService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }

}
