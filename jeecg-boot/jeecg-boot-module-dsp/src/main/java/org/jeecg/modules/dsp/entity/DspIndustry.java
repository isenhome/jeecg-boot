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
 * @Description: dsp_industry
 * @Author: jeecg-boot
 * @Date:   2021-01-01
 * @Version: V1.0
 */
@Data
@TableName("dsp_industry")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dsp_industry对象", description="dsp_industry")
public class DspIndustry extends DspEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**父级*/
	@Excel(name = "父级", width = 15)
    @ApiModelProperty(value = "父级")
    private java.lang.String parentId;

}
