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
 * @Description: dsp_strategy
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName("dsp_strategy")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_strategy对象", description="dsp_strategy")
public class DspStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**状态 1:有效,-1:删除,*/
	@Excel(name = "状态 1:有效,-1:删除,", width = 15)
    @ApiModelProperty(value = "状态 1:有效,-1:删除,")
    private java.lang.Integer status;
	/**策略名称*/
	@Excel(name = "策略名称", width = 15)
    @ApiModelProperty(value = "策略名称")
    private java.lang.String name;
    /**广告主编号*/
    @Excel(name = "广告主编号", width = 15)
    @ApiModelProperty(value = "广告主编号")
    private java.lang.String advertiserId;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String campaignId;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date start;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date end;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
	/**出价方式*/
	@Excel(name = "出价方式", width = 15)
    @ApiModelProperty(value = "出价方式")
    private java.lang.String buyType;
	/**最高价格*/
	@Excel(name = "最高价格", width = 15)
    @ApiModelProperty(value = "最高价格")
    private java.math.BigDecimal buyMaxBidprice;
	/**最低价格*/
	@Excel(name = "最低价格", width = 15)
    @ApiModelProperty(value = "最低价格")
    private java.math.BigDecimal buyMinBidprice;

    /**日限*/
    @Excel(name = "日限", width = 15)
    @ApiModelProperty(value = "日限")
    private java.lang.String dailyLimit;

    /**资源*/
    @Excel(name = "资源", width = 15)
    @ApiModelProperty(value = "资源")
    private java.lang.String resource;

    /**定向*/
    @Excel(name = "定向", width = 15)
    @ApiModelProperty(value = "定向")
    private java.lang.String target;
}
