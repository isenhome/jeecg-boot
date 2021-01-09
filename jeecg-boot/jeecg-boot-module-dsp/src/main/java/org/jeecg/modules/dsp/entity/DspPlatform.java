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
 * @Description: dsp_platform
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName("dsp_platform")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_platform对象", description="dsp_platform")
public class DspPlatform extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**icon*/
	@Excel(name = "icon", width = 15)
    @ApiModelProperty(value = "icon")
    private java.lang.String iconUrl;
	/**简介*/
	@Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private java.lang.String intro;
}
