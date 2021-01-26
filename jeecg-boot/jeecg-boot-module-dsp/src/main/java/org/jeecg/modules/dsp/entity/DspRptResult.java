package org.jeecg.modules.dsp.entity;

import lombok.Data;

@Data
public class DspRptResult {
    /**
     * 维度
     */
    private String dim;
    /**
     * 展示名称
     */
    private String name;
    /**
     * 展示数
     */
    private java.lang.Long pv;
    /**
     * 点击数
     */
    private java.lang.Long click;
    /**
     * 展示率
     */
    private float ctr;
    /**
     * ECPM
     */
    private float ecpm;
    /**
     * ECPC
     */
    private float ecpc;
    /**
     * 消耗
     */
    private java.math.BigDecimal customerCost;
    /**
     * 转化次数
     */
    private java.lang.Long cv;
    /**
     * 转化率
     */
    private float cvr;
    /**
     * 单次转化成本
     */
    private float ecv;
    /**
     * 深度转化数
     */
    private java.lang.Long deepCv;
    /**
     * 单次深度转化成本
     */
    private float eDeepCv;
    /**
     * 深度转化率
     */
    private float deepCvr;

    public DspRptResult(DspRptCommonDaily common) {
        this.dim = common.getDim();
        this.name = common.getDimName();
        this.pv = common.getPv();
        this.click = common.getClick();
        this.ctr = common.getPv() > 0 ? common.getClick() * 1.0f / common.getPv() : 0.0f;
        this.ecpm = common.getPv() > 0 ? common.getCustomerCost().floatValue() * 1000f / common.getPv() : 0.0f;
        this.ecpc = common.getClick() > 0 ? common.getCustomerCost().floatValue() / common.getClick() : 0.0f;
        this.customerCost = common.getCustomerCost();
        this.cv = common.getCv();
        this.ecv = common.getCustomerCost().floatValue() > 0 ? common.getCv() * 1.0f / common.getCustomerCost().floatValue() : 0.0f;
        this.cvr = common.getClick() > 0 ? common.getCv() * 1.0f / common.getClick() : 0.0f;
        this.deepCv = common.getDeepCv();
        this.eDeepCv = common.getCustomerCost().floatValue() > 0 ? common.getDeepCv() * 1.0f / common.getCustomerCost().floatValue() : 0.0f;
        this.deepCvr = common.getCv() > 0 ? common.getDeepCv() * 1.0f / common.getCv() : 0.0f;
    }
}

