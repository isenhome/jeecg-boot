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
import org.jeecg.modules.dsp.entity.DspAdvertiser;
import org.jeecg.modules.dsp.service.IDspAdvertiserService;

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
 * @Description: dsp_advertiser
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Api(tags="dsp_advertiser")
@RestController
@RequestMapping("/dsp/dspAdvertiser")
@Slf4j
public class DspAdvertiserController extends JeecgController<DspAdvertiser, IDspAdvertiserService> {
	@Autowired
	private IDspAdvertiserService dspAdvertiserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dspAdvertiser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-分页列表查询")
	@ApiOperation(value="dsp_advertiser-分页列表查询", notes="dsp_advertiser-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DspAdvertiser dspAdvertiser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DspAdvertiser> queryWrapper = QueryGenerator.initQueryWrapper(dspAdvertiser, req.getParameterMap());
		Page<DspAdvertiser> page = new Page<DspAdvertiser>(pageNo, pageSize);
		IPage<DspAdvertiser> pageList = dspAdvertiserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dspAdvertiser
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-添加")
	@ApiOperation(value="dsp_advertiser-添加", notes="dsp_advertiser-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DspAdvertiser dspAdvertiser) {
		dspAdvertiserService.save(dspAdvertiser);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dspAdvertiser
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-编辑")
	@ApiOperation(value="dsp_advertiser-编辑", notes="dsp_advertiser-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DspAdvertiser dspAdvertiser) {
		dspAdvertiserService.updateById(dspAdvertiser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-通过id删除")
	@ApiOperation(value="dsp_advertiser-通过id删除", notes="dsp_advertiser-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dspAdvertiserService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-批量删除")
	@ApiOperation(value="dsp_advertiser-批量删除", notes="dsp_advertiser-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dspAdvertiserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_advertiser-通过id查询")
	@ApiOperation(value="dsp_advertiser-通过id查询", notes="dsp_advertiser-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DspAdvertiser dspAdvertiser = dspAdvertiserService.getById(id);
		if(dspAdvertiser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dspAdvertiser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dspAdvertiser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspAdvertiser dspAdvertiser) {
        return super.exportXls(request, dspAdvertiser, DspAdvertiser.class, "dsp_advertiser");
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
        return super.importExcel(request, response, DspAdvertiser.class);
    }

}
