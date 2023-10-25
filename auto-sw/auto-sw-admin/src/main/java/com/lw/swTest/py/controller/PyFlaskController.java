package com.lw.swTest.py.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.py.entity.PyFlask;
import com.lw.swTest.py.service.IPyFlaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * PythonFlask接口 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@RestController
@RequestMapping("/py/pyFlask")
@Api(tags = "pyFlask管理",value = "pyFlask管理")
public class PyFlaskController {
    private final IPyFlaskService pyFlaskService;

    public PyFlaskController(IPyFlaskService pyFlaskService) {
        this.pyFlaskService = pyFlaskService;
    }

    /**
     * 分页查询flask信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param flaskName flask名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询flask信息", notes = "分页查询flask信息", httpMethod = "GET")
    @GetMapping("/pageFlask")
    public ResponseResult pageFlask (@RequestParam("currentPage") Integer currentPage,
                                   @RequestParam("pageSize") Integer pageSize,
                                   String flaskName) {
        IPage<PyFlask> iPage = new Page<>(currentPage, pageSize);
        IPage<PyFlask> page = pyFlaskService.pageFlask(iPage, flaskName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改flask信息
     * @param pyFlask flask信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改flask信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/flaskSaveOrUp")
    public ResponseResult flaskSaveOrUp (@RequestBody PyFlask pyFlask) {
        // ID，用来判断是修改还是新增
        Long id = pyFlask.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<PyFlask> users = pyFlaskService.selectFlaskName(pyFlask.getFlaskName());
            if( users.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = pyFlaskService.saveOrUpdate(pyFlask);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = pyFlaskService.saveOrUpdate(pyFlask);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除flask信息
     * @param flaskIds flask信息列表
     * @return 返
     */
    @DeleteMapping("/removeFlaskIds")
    @ApiOperation(value = "多选删除flask信息", notes = "多选删除flask信息", httpMethod = "DELETE")
    public ResponseResult removeFlaskIds (Long[] flaskIds) {
        if (flaskIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(flaskIds);
        boolean removes = pyFlaskService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
