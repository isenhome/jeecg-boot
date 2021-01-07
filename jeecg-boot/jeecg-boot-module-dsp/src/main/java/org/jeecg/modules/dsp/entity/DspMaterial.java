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
 * @Description: dsp_material
 * @Author: jeecg-boot
 * @Date:   2021-01-07
 * @Version: V1.0
 */
@Data
@TableName(value = "dsp_material",excludeProperty = {"advertiserName"})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_material对象", description="dsp_material")
public class DspMaterial extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告主编号*/
	@Excel(name = "广告主编号", width = 15)
    @ApiModelProperty(value = "广告主编号")
    private java.lang.String advertiserId;
	private java.lang.String advertiserName;
	/**素材图片地址*/
	@Excel(name = "素材图片地址", width = 15)
    @ApiModelProperty(value = "素材图片地址")
    private java.lang.String imgUrl;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
}
