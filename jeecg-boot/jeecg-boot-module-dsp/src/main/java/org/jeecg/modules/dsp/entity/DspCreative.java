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
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName("dsp_creative")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_creative对象", description="dsp_creative")
public class DspCreative implements Serializable {
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
	/**创意名称*/
	@Excel(name = "创意名称", width = 15)
    @ApiModelProperty(value = "创意名称")
    private java.lang.String name;
	/**广告主编号*/
	@Excel(name = "广告主编号", width = 15)
    @ApiModelProperty(value = "广告主编号")
    private java.lang.String advertiserId;
	/**创意形式*/
	@Excel(name = "创意形式", width = 15)
    @ApiModelProperty(value = "创意形式")
    private java.lang.String adFormatId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
	/**交互方式*/
	@Excel(name = "交互方式", width = 15)
    @ApiModelProperty(value = "交互方式")
    private java.lang.String interactionType;
	/**交互内容*/
	@Excel(name = "交互内容", width = 15)
    @ApiModelProperty(value = "交互内容")
    private java.lang.String interactionContent;
	/**展示监测*/
	@Excel(name = "展示监测", width = 15)
    @ApiModelProperty(value = "展示监测")
    private java.lang.String pvMonitor;
	/**点击监测*/
	@Excel(name = "点击监测", width = 15)
    @ApiModelProperty(value = "点击监测")
    private java.lang.String clickMonitor;
}
