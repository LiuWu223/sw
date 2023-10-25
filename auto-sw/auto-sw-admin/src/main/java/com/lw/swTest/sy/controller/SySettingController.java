package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SySetting;
import com.lw.swTest.sy.service.ISySettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统设置表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/sySetting")
@Api(tags = "sy设置管理",value = "sy设置管理")
public class SySettingController {
    private final ISySettingService settingService;

    public SySettingController(ISySettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * 分页查询设置信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param userId 用户ID
     * @param settingTheme 自动主题
     * @param settingLingo 自动语言
     * @param settingAutoLogin 自动登录
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询设置信息", notes = "分页查询设置信息", httpMethod = "GET")
    @GetMapping("/pageSetting")
    public ResponseResult pageUser (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String userId,
                                    String settingTheme,
                                    String settingLingo,
                                    String settingAutoLogin) {
        IPage<SySetting> iPage = new Page<>(currentPage, pageSize);
        IPage<SySetting> page = settingService.pageSetting(iPage, userId, settingTheme, settingLingo, settingAutoLogin);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改设置信息
     * @param sySetting 设置信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改设置信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/settingSaveOrUp")
    public ResponseResult settingSaveOrUp (@RequestBody SySetting sySetting) {
        // 用户ID，用来判断是修改还是新增
        Long id = sySetting.getId();
        if (id == null || id == 0){
            boolean save = settingService.saveOrUpdate(sySetting);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = settingService.saveOrUpdate(sySetting);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除设置信息
     * @param settingIds 设置信息列表
     * @return 返
     */
    @DeleteMapping("/removeSettingIds")
    @ApiOperation(value = "多选删除设置信息", notes = "多选删除设置信息", httpMethod = "DELETE")
    public ResponseResult removeSettingIds (Long[] settingIds) {
        if (settingIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listSettingIds = Arrays.asList(settingIds);
        boolean removes = settingService.removeBatchByIds(listSettingIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
