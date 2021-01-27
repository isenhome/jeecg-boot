package org.jeecg.modules.dsp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.DspRptCommonDaily;
import org.jeecg.modules.dsp.entity.DspRptResult;
import org.jeecg.modules.dsp.service.IDspRptCommonDailyService;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: dsp_rpt_common_daily
 * @Author: jeecg-boot
 * @Date: 2021-01-22
 * @Version: V1.0
 */
@Api(tags = "dsp_rpt_common_daily")
@RestController
@RequestMapping("/dsp/dspRptCommonDaily")
@Slf4j
public class DspRptCommonDailyController extends JeecgController<DspRptCommonDaily, IDspRptCommonDailyService> {
    @Autowired
    private IDspRptCommonDailyService dspRptCommonDailyService;

    /**
     * 报表查询
     *
     * @param start      开始时间
     * @param end        结束时间
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-报告查询")
    @ApiOperation(value = "dsp_rpt_common_daily-报告查询", notes = "dsp_rpt_common_daily-报告查询")
    @GetMapping(value = "/allReport")
    public Result<?> report(
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date end
    ) {
        List<DspRptCommonDaily> list = dspRptCommonDailyService.getReport(start, end);
        List<DspRptResult> result = new ArrayList<>();
        for (DspRptCommonDaily item : list) {
            result.add(new DspRptResult(item));
        }
        return Result.OK(result);
    }

    /**
     * 报表查询
     *
     * @param campaignId 编号
     * @param dim        维度
     * @param start      开始时间
     * @param end        结束时间
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-报告查询")
    @ApiOperation(value = "dsp_rpt_common_daily-报告查询", notes = "dsp_rpt_common_daily-报告查询")
    @GetMapping(value = "/report")
    public Result<?> report(
            String campaignId,
            String dim,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date end
    ) {
        List<DspRptCommonDaily> list = dspRptCommonDailyService.getReport(campaignId, dim, start, end);
        List<DspRptResult> result = new ArrayList<>();
        for (DspRptCommonDaily item : list) {
            result.add(new DspRptResult(item));
        }
        return Result.OK(result);
    }

    /**
     * 分页列表查询
     *
     * @param dspRptCommonDaily
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-分页列表查询")
    @ApiOperation(value = "dsp_rpt_common_daily-分页列表查询", notes = "dsp_rpt_common_daily-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspRptCommonDaily dspRptCommonDaily,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspRptCommonDaily> queryWrapper = QueryGenerator.initQueryWrapper(dspRptCommonDaily, req.getParameterMap());
        Page<DspRptCommonDaily> page = new Page<DspRptCommonDaily>(pageNo, pageSize);
        IPage<DspRptCommonDaily> pageList = dspRptCommonDailyService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspRptCommonDaily
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-添加")
    @ApiOperation(value = "dsp_rpt_common_daily-添加", notes = "dsp_rpt_common_daily-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspRptCommonDaily dspRptCommonDaily) {
        dspRptCommonDailyService.save(dspRptCommonDaily);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspRptCommonDaily
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-编辑")
    @ApiOperation(value = "dsp_rpt_common_daily-编辑", notes = "dsp_rpt_common_daily-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspRptCommonDaily dspRptCommonDaily) {
        dspRptCommonDailyService.updateById(dspRptCommonDaily);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-通过id删除")
    @ApiOperation(value = "dsp_rpt_common_daily-通过id删除", notes = "dsp_rpt_common_daily-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspRptCommonDailyService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-批量删除")
    @ApiOperation(value = "dsp_rpt_common_daily-批量删除", notes = "dsp_rpt_common_daily-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspRptCommonDailyService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_rpt_common_daily-通过id查询")
    @ApiOperation(value = "dsp_rpt_common_daily-通过id查询", notes = "dsp_rpt_common_daily-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspRptCommonDaily dspRptCommonDaily = dspRptCommonDailyService.getById(id);
        if (dspRptCommonDaily == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspRptCommonDaily);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dspRptCommonDaily
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspRptCommonDaily dspRptCommonDaily) {
        return super.exportXls(request, dspRptCommonDaily, DspRptCommonDaily.class, "dsp_rpt_common_daily");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DspRptCommonDaily.class);
    }

}
