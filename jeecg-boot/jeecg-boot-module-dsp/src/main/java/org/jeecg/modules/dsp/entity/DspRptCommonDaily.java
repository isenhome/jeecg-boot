package org.jeecg.modules.dsp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: dsp_rpt_common_daily
 * @Author: jeecg-boot
 * @Date: 2021-01-22
 * @Version: V1.0
 */
@Data
@TableName(value = "dsp_rpt_common_daily", excludeProperty = {"dim", "dimName"})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "dsp_rpt_common_daily对象", description = "dsp_rpt_common_daily")
public class DspRptCommonDaily implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 状态 1:有效,-1:删除,
     */
//    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态 1:有效,-1:删除,")
    private java.lang.Integer status;

    /**
     * 维度
     */
    @ApiModelProperty(value = "维度")
    private java.lang.String dim;

    /**
     * 维度名称
     */
    @ApiModelProperty(value = "维度名称")
    private java.lang.String dimName;

    /**
     * reportDate
     */
    @Excel(name = "报告日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "reportDate")
    private java.util.Date reportDate;
    /**
     * campaignId
     */
    @Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "campaignId")
    private java.lang.String campaignId;
    /**
     * strategyId
     */
    @Excel(name = "策略编号", width = 15)
    @ApiModelProperty(value = "strategyId")
    private java.lang.String strategyId;
    /**
     * creativeId
     */
    @Excel(name = "创意编号", width = 15)
    @ApiModelProperty(value = "creativeId")
    private java.lang.String creativeId;
    /**
     * platformId
     */
    @Excel(name = "平台编号", width = 15)
    @ApiModelProperty(value = "platformId")
    private java.lang.String platformId;
    /**
     * adspaceId
     */
    @Excel(name = "广告位编号", width = 15)
    @ApiModelProperty(value = "adspaceId")
    private java.lang.String adspaceId;
    /**
     * pv
     */
    @Excel(name = "展示", width = 15)
    @ApiModelProperty(value = "pv")
    private java.lang.Long pv;
    /**
     * click
     */
    @Excel(name = "点击", width = 15)
    @ApiModelProperty(value = "click")
    private java.lang.Long click;
    /**
     * 转化
     */
    @Excel(name = "转化", width = 15)
    @ApiModelProperty(value = "转化")
    private java.lang.Long cv;
    /**
     * 深度转化
     */
    @Excel(name = "深度转化", width = 15)
    @ApiModelProperty(value = "深度转化")
    private java.lang.Long deepCv;
    /**
     * 消耗
     */
    @Excel(name = "消耗", width = 15)
    @ApiModelProperty(value = "消耗")
    private java.math.BigDecimal customerCost;
}
