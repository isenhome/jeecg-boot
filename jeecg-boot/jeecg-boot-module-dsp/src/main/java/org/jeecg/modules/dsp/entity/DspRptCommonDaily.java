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
 * @Date:   2021-01-22
 * @Version: V1.0
 */
@Data
@TableName("dsp_rpt_common_daily")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_rpt_common_daily对象", description="dsp_rpt_common_daily")
public class DspRptCommonDaily implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**reportDate*/
	@Excel(name = "reportDate", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "reportDate")
    private java.util.Date reportDate;
	/**campaignId*/
	@Excel(name = "campaignId", width = 15)
    @ApiModelProperty(value = "campaignId")
    private java.lang.String campaignId;
	/**strategyId*/
	@Excel(name = "strategyId", width = 15)
    @ApiModelProperty(value = "strategyId")
    private java.lang.String strategyId;
	/**creativeId*/
	@Excel(name = "creativeId", width = 15)
    @ApiModelProperty(value = "creativeId")
    private java.lang.String creativeId;
	/**platformId*/
	@Excel(name = "platformId", width = 15)
    @ApiModelProperty(value = "platformId")
    private java.lang.String platformId;
	/**adspaceId*/
	@Excel(name = "adspaceId", width = 15)
    @ApiModelProperty(value = "adspaceId")
    private java.lang.String adspaceId;
	/**pv*/
	@Excel(name = "pv", width = 15)
    @ApiModelProperty(value = "pv")
    private java.lang.Integer pv;
	/**click*/
	@Excel(name = "click", width = 15)
    @ApiModelProperty(value = "click")
    private java.lang.Integer click;
	/**转化*/
	@Excel(name = "转化", width = 15)
    @ApiModelProperty(value = "转化")
    private java.lang.Integer cv;
	/**深度转化*/
	@Excel(name = "深度转化", width = 15)
    @ApiModelProperty(value = "深度转化")
    private java.lang.Integer deepCv;
	/**消耗*/
	@Excel(name = "消耗", width = 15)
    @ApiModelProperty(value = "消耗")
    private java.math.BigDecimal customerCost;
	/**成本*/
	@Excel(name = "成本", width = 15)
    @ApiModelProperty(value = "成本")
    private java.math.BigDecimal platformCost;
}
