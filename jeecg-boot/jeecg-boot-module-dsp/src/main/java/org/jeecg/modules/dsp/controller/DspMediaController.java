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
import org.jeecg.modules.dsp.entity.DspCampaign;
import org.jeecg.modules.dsp.entity.DspMedia;
import org.jeecg.modules.dsp.entity.DspPlatform;
import org.jeecg.modules.dsp.service.IDspMediaService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.dsp.service.IDspPlatformService;
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
 * @Description: dsp_media
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Api(tags="dsp_media")
@RestController
@RequestMapping("/dsp/dspMedia")
@Slf4j
public class DspMediaController extends JeecgController<DspMedia, IDspMediaService> {
	@Autowired
	private IDspMediaService dspMediaService;

	 @Autowired
	 private IDspPlatformService dspPlatformService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dspMedia
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dsp_media-分页列表查询")
	@ApiOperation(value="dsp_media-分页列表查询", notes="dsp_media-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DspMedia dspMedia,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DspMedia> queryWrapper = QueryGenerator.initQueryWrapper(dspMedia, req.getParameterMap());
		Page<DspMedia> page = new Page<DspMedia>(pageNo, pageSize);
		IPage<DspMedia> pageList = dspMediaService.page(page, queryWrapper);
		Map<String, String> platforms = dspPlatformService.getNameMap();

		for (DspMedia item : pageList.getRecords()) {
			if(platforms.containsKey(item.getPlatformId())){
				item.setPlatformName(platforms.get(item.getPlatformId()));
			}
		}
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dspMedia
	 * @return
	 */
	@AutoLog(value = "dsp_media-添加")
	@ApiOperation(value="dsp_media-添加", notes="dsp_media-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DspMedia dspMedia) {
		dspMediaService.save(dspMedia);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dspMedia
	 * @return
	 */
	@AutoLog(value = "dsp_media-编辑")
	@ApiOperation(value="dsp_media-编辑", notes="dsp_media-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DspMedia dspMedia) {
		dspMediaService.updateById(dspMedia);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_media-通过id删除")
	@ApiOperation(value="dsp_media-通过id删除", notes="dsp_media-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dspMediaService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dsp_media-批量删除")
	@ApiOperation(value="dsp_media-批量删除", notes="dsp_media-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dspMediaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_media-通过id查询")
	@ApiOperation(value="dsp_media-通过id查询", notes="dsp_media-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DspMedia dspMedia = dspMediaService.getById(id);
		if(dspMedia==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dspMedia);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dspMedia
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspMedia dspMedia) {
        return super.exportXls(request, dspMedia, DspMedia.class, "dsp_media");
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
        return super.importExcel(request, response, DspMedia.class);
    }

}
