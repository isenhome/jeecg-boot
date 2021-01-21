package org.jeecg.modules.dsp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.DspStrategy;
import org.jeecg.modules.dsp.service.IDspStrategyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: dsp_strategy
 * @Author: jeecg-boot
 * @Date: 2020-12-30
 * @Version: V1.0
 */
@Api(tags = "dsp_strategy")
@RestController
@RequestMapping("/dsp/dspStrategy")
@Slf4j
public class DspStrategyController extends JeecgController<DspStrategy, IDspStrategyService> {
    @Autowired
    private IDspStrategyService dspStrategyService;

    /**
     * 分页列表查询
     *
     * @param dspStrategy
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_strategy-分页列表查询")
    @ApiOperation(value = "dsp_strategy-分页列表查询", notes = "dsp_strategy-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspStrategy dspStrategy,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspStrategy> queryWrapper = QueryGenerator.initQueryWrapper(dspStrategy, req.getParameterMap());
        Page<DspStrategy> page = new Page<DspStrategy>(pageNo, pageSize);
        IPage<DspStrategy> pageList = dspStrategyService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspStrategy
     * @return
     */
    @AutoLog(value = "dsp_strategy-添加")
    @ApiOperation(value = "dsp_strategy-添加", notes = "dsp_strategy-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspStrategy dspStrategy) {
        dspStrategyService.save(dspStrategy);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspStrategy
     * @return
     */
    @AutoLog(value = "dsp_strategy-编辑")
    @ApiOperation(value = "dsp_strategy-编辑", notes = "dsp_strategy-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspStrategy dspStrategy) {
        dspStrategyService.updateById(dspStrategy);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_strategy-通过id删除")
    @ApiOperation(value = "dsp_strategy-通过id删除", notes = "dsp_strategy-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspStrategyService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_strategy-批量删除")
    @ApiOperation(value = "dsp_strategy-批量删除", notes = "dsp_strategy-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspStrategyService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_strategy-通过id查询")
    @ApiOperation(value = "dsp_strategy-通过id查询", notes = "dsp_strategy-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspStrategy dspStrategy = dspStrategyService.getById(id);
        if (dspStrategy == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspStrategy);
    }


    @RequestMapping(value = "/dailyLimit", method = RequestMethod.POST)
    public Result<?> dailyLimit(@RequestBody Map<String, String> model) {
        String strategyId = model.get("strategyId");
        String dailyLimit = model.get("data");
        DspStrategy dspStrategy = new DspStrategy();
        dspStrategy.setId(strategyId);
        dspStrategy.setDailyLimit(dailyLimit);
        dspStrategyService.updateById(dspStrategy);
        return Result.OK("设置成功！");
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public Result<?> resource(@RequestBody Map<String, String> model) {
        String strategyId = model.get("strategyId");
        String resource = model.get("data");
        DspStrategy dspStrategy = new DspStrategy();
        dspStrategy.setId(strategyId);
        dspStrategy.setResource(resource);
        dspStrategyService.updateById(dspStrategy);
        return Result.OK("设置成功！");
    }
}
