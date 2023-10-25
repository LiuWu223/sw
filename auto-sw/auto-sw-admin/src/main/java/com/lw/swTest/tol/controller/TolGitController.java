package com.lw.swTest.tol.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.tol.entity.TolGit;
import com.lw.swTest.tol.service.ITolGitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * git项目管理表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@RestController
@RequestMapping("/tol/tolGit")
@Api(tags = "tolGit管理",value = "tolGit管理")
public class TolGitController {
    private final ITolGitService tolGitService;

    public TolGitController(ITolGitService tolGitService) {
        this.tolGitService = tolGitService;
    }

    /**
     * 分页查询git信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param gitName git名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询git信息", notes = "分页查询git信息", httpMethod = "GET")
    @GetMapping("/pageGit")
    public ResponseResult pageGit (@RequestParam("currentPage") Integer currentPage,
                                   @RequestParam("pageSize") Integer pageSize,
                                   String gitName) {
        IPage<TolGit> iPage = new Page<>(currentPage, pageSize);
        IPage<TolGit> page = tolGitService.pageGit(iPage, gitName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改git信息
     * @param tolGit git信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改git信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/gitSaveOrUp")
    public ResponseResult gitSaveOrUp (@RequestBody TolGit tolGit) {
        // 用户ID，用来判断是修改还是新增
        Long id = tolGit.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<TolGit> users = tolGitService.selectGitName(tolGit.getGitName());
            if( users.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = tolGitService.saveOrUpdate(tolGit);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = tolGitService.saveOrUpdate(tolGit);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除git信息
     * @param gitIds git信息列表
     * @return 返
     */
    @DeleteMapping("/removeGitIds")
    @ApiOperation(value = "多选删除git信息", notes = "多选删除git信息", httpMethod = "DELETE")
    public ResponseResult removeGitIds (Long[] gitIds) {
        if (gitIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(gitIds);
        boolean removes = tolGitService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
