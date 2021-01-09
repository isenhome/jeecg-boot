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
 * @Description: dsp_adspace
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName(value = "dsp_adspace",excludeProperty = {"mediaName"})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_adspace对象", description="dsp_adspace")
public class DspAdspace extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**媒体编号*/
	@Excel(name = "媒体编号", width = 15)
    @ApiModelProperty(value = "媒体编号")
    private java.lang.String mediaId;
    private java.lang.String mediaName;
	/**广告形式*/
	@Excel(name = "广告形式", width = 15)
    @ApiModelProperty(value = "广告形式")
    private java.lang.String adFormatId;
	/**广告尺寸*/
	@Excel(name = "广告尺寸", width = 15)
    @ApiModelProperty(value = "广告尺寸")
    private java.lang.String adRadioId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
	/**售卖方式*/
	@Excel(name = "售卖方式", width = 15)
    @ApiModelProperty(value = "售卖方式")
    private java.lang.String sellType;
	/**交互方式*/
	@Excel(name = "交互方式", width = 15)
    @ApiModelProperty(value = "交互方式")
    private java.lang.String interaction;
	/**图片格式*/
	@Excel(name = "图片格式", width = 15)
    @ApiModelProperty(value = "图片格式")
    private java.lang.String imageType;
}
