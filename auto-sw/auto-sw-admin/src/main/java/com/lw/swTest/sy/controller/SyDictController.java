package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.sy.service.ISyDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syDict")
@Api(tags = "sy字典管理",value = "sy字典管理")
public class SyDictController {
    private final ISyDictService iSyDictService;

    public SyDictController(ISyDictService iSyDictService) {
        this.iSyDictService = iSyDictService;
    }


    /**
     * 分页查询字典信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param dictType 字典类型
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息", httpMethod = "GET")
    @GetMapping("/pageDict")
    public ResponseResult pageDict (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String dictType) {
        IPage<SyDict> iPage = new Page<>(currentPage, pageSize);
        IPage<SyDict> page = iSyDictService.pageDict(iPage, dictType);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改字典信息
     * @param syDict 字典信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改字典信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/syDictSaveOrUp")
    public ResponseResult syDictSaveOrUp (@RequestBody SyDict syDict) {
        // ID，用来判断是修改还是新增
        Long id = syDict.getId();
        if (id == null || id == 0){
            boolean save = iSyDictService.saveOrUpdate(syDict);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = iSyDictService.saveOrUpdate(syDict);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 查询指定字典类型
     * @param dictType 字典类型
     * @return 无
     */
    @ApiOperation(value = "查询指定字典类型", notes = "查询指定字典类型", httpMethod = "GET")
    @GetMapping("/selectDictType/{dictType}")
    public ResponseResult selectDictType (@PathVariable("dictType") String dictType) {
        List<SyDict> dictList = iSyDictService.selectDictType(dictType);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS,dictList);
    }

    /**
     * 多选删除字典信息
     * @param dictIds 字典信息列表
     * @return 返
     */
    @DeleteMapping("/removeDictIds")
    @ApiOperation(value = "多选删除字典信息", notes = "多选删除字典信息", httpMethod = "DELETE")
    public ResponseResult removeDictIds (Long[] dictIds) {
        if (dictIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listDicts = Arrays.asList(dictIds);
        boolean removes = iSyDictService.removeBatchByIds(listDicts);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
