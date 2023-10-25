package com.lw.swTest.tol.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.service.ISyRoleService;
import com.lw.swTest.tol.entity.TolAdb;
import com.lw.swTest.tol.service.ITolAdbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * ADB命令管理表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@RestController
@RequestMapping("/tol/tolAdb")
@Api(tags = "tolAdb管理",value = "tolAdb管理")
public class TolAdbController {
    private final ITolAdbService tolAdbService;

    public TolAdbController(ITolAdbService tolAdbService) {
        this.tolAdbService = tolAdbService;
    }

    /**
     * 分页查询adb信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param adbName adb名称
     * @param adbDir adb命令
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询adb信息", notes = "分页查询adb信息", httpMethod = "GET")
    @GetMapping("/pageAdb")
    public ResponseResult pageAdb (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String adbName,
                                    String adbDir) {
        IPage<TolAdb> iPage = new Page<>(currentPage, pageSize);
        IPage<TolAdb> page = tolAdbService.pageAdb(iPage, adbName, adbDir);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改adb信息
     * @param tolAdb adb信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改adb信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/adbSaveOrUp")
    public ResponseResult adbSaveOrUp (@RequestBody TolAdb tolAdb) {
        // 用户ID，用来判断是修改还是新增
        Long id = tolAdb.getId();
        if (id == null || id == 0){
            // 新增，需要判断用户是否重复
            List<TolAdb> users = tolAdbService.selectAdbName(tolAdb.getAdbName());
            if( users.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = tolAdbService.saveOrUpdate(tolAdb);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = tolAdbService.saveOrUpdate(tolAdb);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除adb信息
     * @param adbIds adb信息列表
     * @return 返
     */
    @DeleteMapping("/removeAdbIds")
    @ApiOperation(value = "多选删除adb信息", notes = "多选删除adb信息", httpMethod = "DELETE")
    public ResponseResult removeAdbIds (Long[] adbIds) {
        if (adbIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(adbIds);
        boolean removes = tolAdbService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
