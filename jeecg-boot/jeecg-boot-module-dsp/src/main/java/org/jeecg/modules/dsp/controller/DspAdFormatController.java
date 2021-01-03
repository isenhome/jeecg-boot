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

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.DspAdFormat;
import org.jeecg.modules.dsp.service.IDspAdFormatService;

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
 * @Description: dsp_ad_format
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Api(tags="dsp_ad_format")
@RestController
@RequestMapping("/dsp/dspAdFormat")
@Slf4j
public class DspAdFormatController extends JeecgController<DspAdFormat, IDspAdFormatService> {
	@Autowired
	private IDspAdFormatService dspAdFormatService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dspAdFormat
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-分页列表查询")
	@ApiOperation(value="dsp_ad_format-分页列表查询", notes="dsp_ad_format-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DspAdFormat dspAdFormat,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DspAdFormat> queryWrapper = QueryGenerator.initQueryWrapper(dspAdFormat, req.getParameterMap());
		Page<DspAdFormat> page = new Page<DspAdFormat>(pageNo, pageSize);
		IPage<DspAdFormat> pageList = dspAdFormatService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dspAdFormat
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-添加")
	@ApiOperation(value="dsp_ad_format-添加", notes="dsp_ad_format-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DspAdFormat dspAdFormat) {
		dspAdFormatService.save(dspAdFormat);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dspAdFormat
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-编辑")
	@ApiOperation(value="dsp_ad_format-编辑", notes="dsp_ad_format-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DspAdFormat dspAdFormat) {
		dspAdFormatService.updateById(dspAdFormat);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-通过id删除")
	@ApiOperation(value="dsp_ad_format-通过id删除", notes="dsp_ad_format-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dspAdFormatService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-批量删除")
	@ApiOperation(value="dsp_ad_format-批量删除", notes="dsp_ad_format-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dspAdFormatService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_ad_format-通过id查询")
	@ApiOperation(value="dsp_ad_format-通过id查询", notes="dsp_ad_format-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DspAdFormat dspAdFormat = dspAdFormatService.getById(id);
		if(dspAdFormat==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dspAdFormat);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dspAdFormat
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspAdFormat dspAdFormat) {
        return super.exportXls(request, dspAdFormat, DspAdFormat.class, "dsp_ad_format");
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
        return super.importExcel(request, response, DspAdFormat.class);
    }

}
