package org.jeecg.modules.dsp.service.impl;

import org.jeecg.modules.dsp.entity.DspRptCommonDaily;
import org.jeecg.modules.dsp.mapper.DspRptCommonDailyMapper;
import org.jeecg.modules.dsp.service.IDspRptCommonDailyService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: dsp_rpt_common_daily
 * @Author: jeecg-boot
 * @Date: 2021-01-22
 * @Version: V1.0
 */
@Service
public class DspRptCommonDailyServiceImpl extends ServiceImpl<DspRptCommonDailyMapper, DspRptCommonDaily> implements IDspRptCommonDailyService {


    @Override
    public List<DspRptCommonDaily> getReport(String campaignId, String dim, Date start, Date end) {
        switch (dim) {
            case "report_date":
                return this.baseMapper.queryReportDimByDate(campaignId, start, end);
            case "strategy_id":
                return this.baseMapper.queryReportDimByStrategy(campaignId, start, end);
            case "creative_id":
                return this.baseMapper.queryReportDimByCreative(campaignId, start, end);
            case "platform_id":
                return this.baseMapper.queryReportDimByPlatform(campaignId, start, end);
            case "media_id":
                return this.baseMapper.queryReportDimByMedia(campaignId, start, end);
            case "adspace_id":
                return this.baseMapper.queryReportDimByAdspace(campaignId, start, end);
        }
        return new ArrayList<>();
    }

    @Override
    public List<DspRptCommonDaily> getReport(Date start, Date end) {
        return this.baseMapper.queryAllReportDimByDate(start, end);
    }
}
