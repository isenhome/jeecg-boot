package org.jeecg.modules.dsp.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.dsp.entity.DspTree;
import org.jeecg.modules.dsp.entity.DspInterest;
import org.jeecg.modules.dsp.service.IDspInterestService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: dsp_interest
 * @Author: jeecg-boot
 * @Date:   2021-02-04
 * @Version: V1.0
 */
@Api(tags="dsp_interest")
@RestController
@RequestMapping("/dsp/dspInterest")
@Slf4j
public class DspInterestController extends JeecgController<DspInterest, IDspInterestService> {
	@Autowired
	private IDspInterestService dspInterestService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dspInterest
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dsp_interest-分页列表查询")
	@ApiOperation(value="dsp_interest-分页列表查询", notes="dsp_interest-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DspInterest dspInterest,
								   HttpServletRequest req) {
		QueryWrapper<DspInterest> queryWrapper = QueryGenerator.initQueryWrapper(dspInterest, req.getParameterMap());
		List<DspInterest> list = dspInterestService.list(queryWrapper);
		Map<Object, DspTree> map = getTreeModelList(list);
		return Result.OK(map.values());
	}
	
	/**
	 *   添加
	 *
	 * @param dspInterest
	 * @return
	 */
	@AutoLog(value = "dsp_interest-添加")
	@ApiOperation(value="dsp_interest-添加", notes="dsp_interest-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DspInterest dspInterest) {
		dspInterestService.save(dspInterest);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dspInterest
	 * @return
	 */
	@AutoLog(value = "dsp_interest-编辑")
	@ApiOperation(value="dsp_interest-编辑", notes="dsp_interest-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DspInterest dspInterest) {
		dspInterestService.updateById(dspInterest);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_interest-通过id删除")
	@ApiOperation(value="dsp_interest-通过id删除", notes="dsp_interest-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dspInterestService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dsp_interest-批量删除")
	@ApiOperation(value="dsp_interest-批量删除", notes="dsp_interest-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dspInterestService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dsp_interest-通过id查询")
	@ApiOperation(value="dsp_interest-通过id查询", notes="dsp_interest-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DspInterest dspInterest = dspInterestService.getById(id);
		if(dspInterest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dspInterest);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dspInterest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DspInterest dspInterest) {
        return super.exportXls(request, dspInterest, DspInterest.class, "dsp_interest");
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
        return super.importExcel(request, response, DspInterest.class);
    }

	 private Map<Object, DspTree> getTreeModelList(List<DspInterest> metaList) {
		 Map<Object, DspTree> map = new HashMap<>();
		 for (DspInterest interest : metaList) {
			 map.put(interest.getId(), new DspTree(interest));
		 }

		 for (DspInterest interest : metaList) {
			 Object id = interest.getId();
			 Object pid = interest.getParentId();
			 if (map.containsKey(pid)) {
				 if(map.get(pid).getChildren() == null){
					 map.get(pid).setChildren(new ArrayList<>());
				 }
				 map.get(pid).getChildren().add(map.get(id));
				 map.get(pid).setLeaf(false);
			 }
		 }

		 for (DspInterest interest : metaList) {
			 if(map.containsKey(interest.getParentId())){
				 map.remove(interest.getId());
			 }
		 }

		 return map;
	 }

}
