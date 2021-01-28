package org.jeecg.modules.dsp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonSerialize
public class DspRptResult implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 维度
     */
    @JSONField
    private String dim;
    /**
     * 展示名称
     */
    @JSONField
    private String name;
    /**
     * 展示数
     */
    @JSONField
    private java.lang.Long pv = 0l;
    /**
     * 点击数
     */
    @JSONField
    private java.lang.Long click = 0l;
    /**
     * 点击率
     */
    @JSONField(format = "#.0000")
    private Double ctr = 0.0;
    /**
     * ECPM
     */
    @JSONField(format = "#.00")
    private float ecpm = 0.0f;
    /**
     * ECPC
     */
    @JSONField(format = "#.00")
    private float ecpc = 0.0f;
    /**
     * 消耗
     */
    @JSONField(format = "#.00")
    private java.math.BigDecimal customerCost = new BigDecimal(0.0);
    /**
     * 转化次数
     */
    private java.lang.Long cv = 0L;
    /**
     * 转化率
     */
    @JSONField(format = "#.0000")
    private float cvr = 0.0f;
    /**
     * 单次转化成本
     */
    @NumberFormat(pattern = "#.00")
    private float ecv = 0.0f;
    /**
     * 深度转化数
     */
    private java.lang.Long deepCv = 0l;
    /**
     * 单次深度转化成本
     */
    @NumberFormat(pattern = "#.00")
    private float eDeepCv = 0.0f;
    /**
     * 深度转化率
     */
    @JSONField(format = "#.0000")
    private float deepCvr = 0.0f;

    public DspRptResult(DspRptCommonDaily common) {
        if (common != null) {
            this.dim = common.getDim();
            this.name = common.getDimName();
            this.pv = common.getPv();
            this.click = common.getClick();
            this.ctr = common.getPv() > 0 ? common.getClick() * 1.0d / common.getPv() : 0.0f;
            this.ecpm = common.getPv() > 0 ? common.getCustomerCost().floatValue() * 1000f / common.getPv() : 0.0f;
            this.ecpc = common.getClick() > 0 ? common.getCustomerCost().floatValue() / common.getClick() : 0.0f;
            this.customerCost = common.getCustomerCost();
            this.cv = common.getCv();
            this.ecv = common.getCv() > 0 ? common.getCustomerCost().floatValue() / common.getCv() : 0.0f;
            this.cvr = common.getClick() > 0 ? common.getCv() * 1.0f / common.getClick() : 0.0f;
            this.deepCv = common.getDeepCv();
            this.eDeepCv = common.getDeepCv() > 0 ? common.getCustomerCost().floatValue() * 1.0f / common.getDeepCv() : 0.0f;
            this.deepCvr = common.getCv() > 0 ? common.getDeepCv() * 1.0f / common.getCv() : 0.0f;
        }
    }
}

