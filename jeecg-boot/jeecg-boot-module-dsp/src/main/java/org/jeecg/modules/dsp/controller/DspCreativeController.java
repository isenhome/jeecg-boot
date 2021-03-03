package org.jeecg.modules.dsp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.dsp.entity.DspAdFormat;
import org.jeecg.modules.dsp.entity.DspCreative;
import org.jeecg.modules.dsp.entity.DspMaterial;
import org.jeecg.modules.dsp.service.IDspAdFormatService;
import org.jeecg.modules.dsp.service.IDspCreativeService;
import org.jeecg.modules.dsp.service.IDspMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: dsp_creative
 * @Author: jeecg-boot
 * @Date: 2020-12-30
 * @Version: V1.0
 */
@Api(tags = "dsp_creative")
@RestController
@RequestMapping("/dsp/dspCreative")
@Slf4j
public class DspCreativeController extends JeecgController<DspCreative, IDspCreativeService> {
    @Autowired
    private IDspCreativeService dspCreativeService;

    @Autowired
    private IDspMaterialService dspMaterialService;

    @Autowired
    private IDspAdFormatService dspAdFormatService;

    Map<String, String> material;
    Map<String, String> format;

    private void fixCreative(DspCreative dspCreative) {
        if (material == null) {
            material = dspMaterialService.getNameMap();
        }
        List<String> names = new ArrayList<>();
        for (String item : dspCreative.getMaterialIds().split(",")) {
            if (material.containsKey(item)) {
                names.add(material.get(item));
            }
        }
        dspCreative.setMaterialNames(StringUtils.join(names, ","));
        if (format == null) {
            format = dspAdFormatService.getNameMap();
        }
        if (format.containsKey(dspCreative.getAdFormatId())) {
            dspCreative.setAdFormatName(format.get(dspCreative.getAdFormatId()));
        }
    }

    /**
     * 分页列表查询
     *
     * @param dspCreative
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_creative-分页列表查询")
    @ApiOperation(value = "dsp_creative-分页列表查询", notes = "dsp_creative-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspCreative dspCreative,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspCreative> queryWrapper = QueryGenerator.initQueryWrapper(dspCreative, req.getParameterMap());
        Page<DspCreative> page = new Page<DspCreative>(pageNo, pageSize);
        IPage<DspCreative> pageList = dspCreativeService.page(page, queryWrapper);
        for (DspCreative item : pageList.getRecords()) {
            fixCreative(item);
        }
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspCreative
     * @return
     */
    @AutoLog(value = "dsp_creative-添加")
    @ApiOperation(value = "dsp_creative-添加", notes = "dsp_creative-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspCreative dspCreative) {
        dspCreativeService.save(dspCreative);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspCreative
     * @return
     */
    @AutoLog(value = "dsp_creative-编辑")
    @ApiOperation(value = "dsp_creative-编辑", notes = "dsp_creative-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspCreative dspCreative) {
        dspCreativeService.updateById(dspCreative);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_creative-通过id删除")
    @ApiOperation(value = "dsp_creative-通过id删除", notes = "dsp_creative-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspCreativeService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_creative-批量删除")
    @ApiOperation(value = "dsp_creative-批量删除", notes = "dsp_creative-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspCreativeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_creative-通过id查询")
    @ApiOperation(value = "dsp_creative-通过id查询", notes = "dsp_creative-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspCreative dspCreative = dspCreativeService.getById(id);
        if (dspCreative == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspCreative);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dspCreative
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspCreative dspCreative) {
        return super.exportXls(request, dspCreative, DspCreative.class, "dsp_creative");
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
        return super.importExcel(request, response, DspCreative.class);
    }

}
