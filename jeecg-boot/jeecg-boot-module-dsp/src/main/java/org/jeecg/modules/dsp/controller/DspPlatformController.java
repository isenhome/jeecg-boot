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
import org.jeecg.modules.dsp.entity.DspPlatform;
import org.jeecg.modules.dsp.service.IDspPlatformService;

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
 * @Description: dsp_platform
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Api(tags="dsp_platform")
@RestController
@RequestMapping("/dsp/dspPlatform")
@Slf4j
public class DspPlatformController extends JeecgController<DspPlatform, IDspPlatformService> {
	@Autowired
	private IDspPlatformService dspPlatformService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dspPlatform
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dsp_platform-分页列表查询")
	@ApiOperation(value="dsp_platform-分页列表查询", notes="dsp_platform-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DspPlatform dspPlatform,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DspPlatform> queryWrapper = QueryGenerator.initQueryWrapper(dspPlatform, req.getParameterMap());
		Page<DspPlatform> page = new Page<DspPlatform>(pageNo, pageSize);
		IPage<DspPlatform> pageList = dspPlatformService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dspPlatform
	 * @return
	 */
	@AutoLog(value = "dsp_platform-添加")
	@ApiOperation(value="dsp_platform-添加", notes="dsp_platform-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DspPlatform dspPlatform) {
		dspPlatformService.save(dspPlatform);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dspPlatform
	 * @return
	 */
	@AutoLog(value = "dsp_platform-编辑")
	@ApiOperation(value="dsp_platform-编辑", notes="dsp_platform-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DspPlatform dspPlatform) {
		dspPlatformService.updateById(dspPlatform);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_platform-通过id删除")
	@ApiOperation(value="dsp_platform-通过id删除", notes="dsp_platform-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dspPlatformService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dsp_platform-批量删除")
	@ApiOperation(value="dsp_platform-批量删除", notes="dsp_platform-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dspPlatformService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_platform-通过id查询")
	@ApiOperation(value="dsp_platform-通过id查询", notes="dsp_platform-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DspPlatform dspPlatform = dspPlatformService.getById(id);
		if(dspPlatform==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dspPlatform);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dspPlatform
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspPlatform dspPlatform) {
        return super.exportXls(request, dspPlatform, DspPlatform.class, "dsp_platform");
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
        return super.importExcel(request, response, DspPlatform.class);
    }

}
