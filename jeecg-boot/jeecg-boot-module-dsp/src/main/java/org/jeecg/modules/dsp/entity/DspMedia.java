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
 * @Description: dsp_media
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName(value="dsp_media",excludeProperty = {"platformName"})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_media对象", description="dsp_media")
public class DspMedia extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**平台编号*/
	@Excel(name = "平台编号", width = 15)
    @ApiModelProperty(value = "平台编号")
    private java.lang.String platformId;
    private java.lang.String platformName;
	/**简介*/
	@Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private java.lang.String intro;
}
