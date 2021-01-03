package org.jeecg.modules.dsp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dsp.entity.DspIndustry;
import org.jeecg.modules.dsp.entity.DspIndustryTree;
import org.jeecg.modules.dsp.service.IDspIndustryService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.online.cgform.model.TreeModel;
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
 * @Description: dsp_industry
 * @Author: jeecg-boot
 * @Date: 2021-01-01
 * @Version: V1.0
 */
@Api(tags = "dsp_industry")
@RestController
@RequestMapping("/dsp/dspIndustry")
@Slf4j
public class DspIndustryController extends JeecgController<DspIndustry, IDspIndustryService> {
    @Autowired
    private IDspIndustryService dspIndustryService;

    /**
     * 分页列表查询
     *
     * @param dspIndustry
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "dsp_industry-分页列表查询")
    @ApiOperation(value = "dsp_industry-分页列表查询", notes = "dsp_industry-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DspIndustry dspIndustry,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DspIndustry> queryWrapper = QueryGenerator.initQueryWrapper(dspIndustry, req.getParameterMap());
        Page<DspIndustry> page = new Page<DspIndustry>(pageNo, pageSize);
        IPage<DspIndustry> pageList = dspIndustryService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param dspIndustry
     * @return
     */
    @AutoLog(value = "dsp_industry-添加")
    @ApiOperation(value = "dsp_industry-添加", notes = "dsp_industry-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DspIndustry dspIndustry) {
        dspIndustryService.save(dspIndustry);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param dspIndustry
     * @return
     */
    @AutoLog(value = "dsp_industry-编辑")
    @ApiOperation(value = "dsp_industry-编辑", notes = "dsp_industry-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DspIndustry dspIndustry) {
        dspIndustryService.updateById(dspIndustry);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_industry-通过id删除")
    @ApiOperation(value = "dsp_industry-通过id删除", notes = "dsp_industry-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        dspIndustryService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "dsp_industry-批量删除")
    @ApiOperation(value = "dsp_industry-批量删除", notes = "dsp_industry-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.dspIndustryService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "dsp_industry-通过id查询")
    @ApiOperation(value = "dsp_industry-通过id查询", notes = "dsp_industry-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DspIndustry dspIndustry = dspIndustryService.getById(id);
        if (dspIndustry == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(dspIndustry);
    }


    /**
     * 获取全部的权限树
     *
     * @return
     */
    @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
    public Result<Map<String, Object>> queryTreeList() {
        Result<Map<String, Object>> result = new Result<>();
        // 全部ids
        List<String> ids = new ArrayList<>();
        try {
            LambdaQueryWrapper<DspIndustry> query = new LambdaQueryWrapper<DspIndustry>();
            query.eq(DspIndustry::getStatus, CommonConstant.ACT_SYNC_1);
            List<DspIndustry> list = dspIndustryService.list(query);
            for (DspIndustry industry : list) {
                ids.add(industry.getId());
            }
            Map<String, DspIndustryTree> map = getTreeModelList(list);

            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("treeList", map.values()); // 全部树节点数据
            resMap.put("ids", ids);// 全部树ids
            result.setResult(resMap);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    private Map<String, DspIndustryTree> getTreeModelList(List<DspIndustry> metaList) {
        Map<String, DspIndustryTree> map = new HashMap<>();
        for (DspIndustry industry : metaList) {
            map.put(industry.getId(), new DspIndustryTree(industry));
        }

        for (DspIndustry industry : metaList) {
            String id = industry.getId();
            String pid = industry.getParentId();
            if (map.containsKey(pid)) {
                map.get(pid).getChildren().add(map.get(id));
                map.get(pid).setLeaf(false);
            }
        }

        for (DspIndustry industry : metaList) {
            if(map.containsKey(map.get(industry.getParentId()))){
                map.remove(industry.getId());
            }
        }

        return map;
    }

}
