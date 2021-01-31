package org.jeecg.modules.dsp.service;

import org.jeecg.modules.dsp.entity.DspRptCommonDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: dsp_rpt_common_daily
 * @Author: jeecg-boot
 * @Date: 2021-01-22
 * @Version: V1.0
 */
public interface IDspRptCommonDailyService extends IService<DspRptCommonDaily> {

    List<DspRptCommonDaily> getReport(String campaignId, String dim, Date start, Date end);

    List<DspRptCommonDaily> getReport(Date start, Date end, String sysOrgCode);

    DspRptCommonDaily getTotalReport(Date start, Date end, String sysOrgCode);

    int getAdvertiserCount(Date start, Date end, String sysOrgCode);

}
