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

import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.*;
import org.jeecg.modules.dsp.service.IDspAdFormatService;
import org.jeecg.modules.dsp.service.IDspAdRadioService;
import org.jeecg.modules.dsp.service.IDspAdspaceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.dsp.service.IDspMediaService;
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
 * @Description: dsp_adspace
 * @Author: jeecg-boot
 * @Date: 2020-12-30
 * @Version: V1.0
 */
@Api(tags = "dsp_adspace")
@RestController
@RequestMapping("/dsp/dspAdspace")
@Slf4j
public class DspAdspaceController extends JeecgController<DspAdspace, IDspAdspaceService> {
    @Autowired
    private IDspAdspaceService dspAdspaceService;

    @Autowired
    private IDspMediaService dspMediaService;

    @Autowired
    private IDspAdFormatService dspAdFormatService;

    @Autowired
    private IDspAdRadioService dspAdRadioService;

    /**
     * 分页列表查询
     *
     * @param dspAdspace
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_adspace-分页列表查询")
    @ApiOperation(value = "dsp_adspace-分页列表查询", notes = "dsp_adspace-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspAdspace dspAdspace,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspAdspace> queryWrapper = QueryGenerator.initQueryWrapper(dspAdspace, req.getParameterMap());
        Page<DspAdspace> page = new Page<DspAdspace>(pageNo, pageSize);
        IPage<DspAdspace> pageList = dspAdspaceService.page(page, queryWrapper);
        Map<String, String> mediaMap = dspMediaService.getNameMap(QueryGenerator.initQueryWrapper(new DspMedia() {{
            setStatus(1);
        }}, null));
        Map<String, String> adFormatMap = dspAdFormatService.getNameMap(QueryGenerator.initQueryWrapper(new DspAdFormat() {{
            setStatus(1);
        }}, null));
        Map<String, String> adRadioMap = dspAdRadioService.getNameMap(QueryGenerator.initQueryWrapper(new DspAdRadio() {{
            setStatus(1);
        }}, null));

        for (DspAdspace item : pageList.getRecords()) {
            if (mediaMap.containsKey(item.getMediaId())) {
                item.setMediaName(mediaMap.get(item.getMediaId()));
            }
            if (adFormatMap.containsKey(item.getAdFormatId())) {
                item.setAdFormatName(adFormatMap.get(item.getAdFormatId()));
            }
            if (adRadioMap.containsKey(item.getAdRadioId())) {
                item.setAdRadioName(adRadioMap.get(item.getAdRadioId()));
            }
        }
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspAdspace
     * @return
     */
    @AutoLog(value = "dsp_adspace-添加")
    @ApiOperation(value = "dsp_adspace-添加", notes = "dsp_adspace-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspAdspace dspAdspace) {
        dspAdspaceService.save(dspAdspace);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspAdspace
     * @return
     */
    @AutoLog(value = "dsp_adspace-编辑")
    @ApiOperation(value = "dsp_adspace-编辑", notes = "dsp_adspace-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspAdspace dspAdspace) {
        dspAdspaceService.updateById(dspAdspace);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_adspace-通过id删除")
    @ApiOperation(value = "dsp_adspace-通过id删除", notes = "dsp_adspace-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspAdspaceService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_adspace-批量删除")
    @ApiOperation(value = "dsp_adspace-批量删除", notes = "dsp_adspace-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspAdspaceService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_adspace-通过id查询")
    @ApiOperation(value = "dsp_adspace-通过id查询", notes = "dsp_adspace-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspAdspace dspAdspace = dspAdspaceService.getById(id);
        if (dspAdspace == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspAdspace);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dspAdspace
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspAdspace dspAdspace) {
        return super.exportXls(request, dspAdspace, DspAdspace.class, "dsp_adspace");
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
        return super.importExcel(request, response, DspAdspace.class);
    }

}
