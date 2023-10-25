package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyAuthority;
import com.lw.swTest.sy.service.ISyAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syAuthority")
@Api(tags = "sy权限管理",value = "sy权限管理")
public class SyAuthorityController {
    private final ISyAuthorityService iSyAuthorityService;

    public SyAuthorityController(ISyAuthorityService iSyAuthorityService) {
        this.iSyAuthorityService = iSyAuthorityService;
    }


    /**
     * 分页查询权限信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param authorityName 权限名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询权限信息", notes = "分页查询权限信息", httpMethod = "GET")
    @GetMapping("/pageAuthority")
    public ResponseResult pageAuthority (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String authorityName) {
        IPage<SyAuthority> iPage = new Page<>(currentPage, pageSize);
        IPage<SyAuthority> page = iSyAuthorityService.pageAuthority(iPage, authorityName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改权限信息
     * @param syAuthority 权限信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改权限信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/syAuthoritySaveOrUp")
    public ResponseResult syAuthoritySaveOrUp (@RequestBody SyAuthority syAuthority) {
        // ID，用来判断是修改还是新增
        Long id = syAuthority.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<SyAuthority> authority = iSyAuthorityService.selectAuthorityName(syAuthority.getAuthorityName());
            if( authority.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = iSyAuthorityService.saveOrUpdate(syAuthority);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = iSyAuthorityService.saveOrUpdate(syAuthority);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除权限信息
     * @param authorityIds 权限信息列表
     * @return 返
     */
    @DeleteMapping("/removeAuthorityIds")
    @ApiOperation(value = "多选删除权限信息", notes = "多选删除权限信息", httpMethod = "DELETE")
    public ResponseResult removeAuthorityIds (Long[] authorityIds) {
        if (authorityIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(authorityIds);
        boolean removes = iSyAuthorityService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
