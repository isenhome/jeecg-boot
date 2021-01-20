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
 * @Description: dsp_creative
 * @Author: jeecg-boot
 * @Date: 2020-12-30
 * @Version: V1.0
 */
@Data
@TableName(value = "dsp_creative", excludeProperty = {"materialNames","adFormatName"})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "dsp_creative对象", description = "dsp_creative")
public class DspCreative extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 广告主编号
     */
    @Excel(name = "广告主编号", width = 15)
    @ApiModelProperty(value = "广告主编号")
    private java.lang.String advertiserId;
    /**
     * 订单编号
     */
    @Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String campaignId;
    /**
     * 创意形式
     */
    @Excel(name = "创意形式", width = 15)
    @ApiModelProperty(value = "创意形式")
    private java.lang.String adFormatId;
    private java.lang.String adFormatName;
    /**
     * 素材
     */
    @Excel(name = "素材", width = 15)
    @ApiModelProperty(value = "素材")
    private java.lang.String materialIds;
    private java.lang.String materialNames;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
    /**
     * 交互方式
     */
    @Excel(name = "交互方式", width = 15)
    @ApiModelProperty(value = "交互方式")
    private java.lang.String interactionType;
    /**
     * 目标地址
     */
    @Excel(name = "目标地址", width = 15)
    @ApiModelProperty(value = "目标地址")
    private java.lang.String targetUrl;
    /**
     * DeepLink
     */
    @Excel(name = "DeepLink", width = 15)
    @ApiModelProperty(value = "DeepLink")
    private java.lang.String deepLink;
    /**
     * 展示监测
     */
    @Excel(name = "展示监测", width = 15)
    @ApiModelProperty(value = "展示监测")
    private java.lang.String pvMonitor;
    /**
     * 点击监测
     */
    @Excel(name = "点击监测", width = 15)
    @ApiModelProperty(value = "点击监测")
    private java.lang.String clickMonitor;
}
