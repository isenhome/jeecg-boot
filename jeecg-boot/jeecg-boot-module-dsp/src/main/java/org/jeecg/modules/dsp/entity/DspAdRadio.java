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
 * @Description: dsp_ad_radio
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName("dsp_ad_radio")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_ad_radio对象", description="dsp_ad_radio")
public class DspAdRadio extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**宽度*/
	@Excel(name = "宽度", width = 15)
    @ApiModelProperty(value = "宽度")
    private java.lang.Integer width;
	/**高度*/
	@Excel(name = "高度", width = 15)
    @ApiModelProperty(value = "高度")
    private java.lang.Integer height;
}
