package org.jeecg.modules.dsp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.DspAdvertiser;
import org.jeecg.modules.dsp.entity.DspCampaign;
import org.jeecg.modules.dsp.entity.DspIndustry;
import org.jeecg.modules.dsp.service.IDspAdvertiserService;
import org.jeecg.modules.dsp.service.IDspCampaignService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.dsp.service.IDspIndustryService;
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
 * @Description: dsp_campaign
 * @Author: jeecg-boot
 * @Date: 2020-12-30
 * @Version: V1.0
 */
@Api(tags = "dsp_campaign")
@RestController
@RequestMapping("/dsp/dspCampaign")
@Slf4j
public class DspCampaignController extends JeecgController<DspCampaign, IDspCampaignService> {
    @Autowired
    private IDspCampaignService dspCampaignService;
    @Autowired
    private IDspIndustryService dspIndustryService;
    @Autowired
    private IDspAdvertiserService dspAdvertiserService;

    /**
     * 分页列表查询
     *
     * @param dspCampaign
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_campaign-分页列表查询")
    @ApiOperation(value = "dsp_campaign-分页列表查询", notes = "dsp_campaign-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspCampaign dspCampaign,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspCampaign> queryWrapper = QueryGenerator.initQueryWrapper(dspCampaign, req.getParameterMap());
        Page<DspCampaign> page = new Page<DspCampaign>(pageNo, pageSize);
        IPage<DspCampaign> pageList = dspCampaignService.page(page, queryWrapper);
        Map<String, String> industries = dspIndustryService.getNameMap(QueryGenerator.initQueryWrapper(new DspIndustry(){{setStatus(1);}},null));
        Map<String, String> advertisers = dspAdvertiserService.getNameMap(QueryGenerator.initQueryWrapper(new DspAdvertiser(){{setStatus(1);}},null));

        for (DspCampaign item : pageList.getRecords()) {
            if(industries.containsKey(item.getIndustryId())){
                item.setIndustryName(industries.get(item.getIndustryId()));
            }
			if(advertisers.containsKey(item.getAdvertiserId())){
                item.setAdvertiserName(advertisers.get(item.getAdvertiserId()));
            }
        }
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspCampaign
     * @return
     */
    @AutoLog(value = "dsp_campaign-添加")
    @ApiOperation(value = "dsp_campaign-添加", notes = "dsp_campaign-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspCampaign dspCampaign) {
        dspCampaignService.save(dspCampaign);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspCampaign
     * @return
     */
    @AutoLog(value = "dsp_campaign-编辑")
    @ApiOperation(value = "dsp_campaign-编辑", notes = "dsp_campaign-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspCampaign dspCampaign) {
        dspCampaignService.updateById(dspCampaign);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_campaign-通过id删除")
    @ApiOperation(value = "dsp_campaign-通过id删除", notes = "dsp_campaign-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspCampaignService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_campaign-批量删除")
    @ApiOperation(value = "dsp_campaign-批量删除", notes = "dsp_campaign-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspCampaignService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_campaign-通过id查询")
    @ApiOperation(value = "dsp_campaign-通过id查询", notes = "dsp_campaign-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspCampaign dspCampaign = dspCampaignService.getById(id);
        if (dspCampaign == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspCampaign);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dspCampaign
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspCampaign dspCampaign) {
        return super.exportXls(request, dspCampaign, DspCampaign.class, "dsp_campaign");
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
        return super.importExcel(request, response, DspCampaign.class);
    }

}
