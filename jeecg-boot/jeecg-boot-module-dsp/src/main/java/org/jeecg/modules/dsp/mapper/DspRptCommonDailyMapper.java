package org.jeecg.modules.dsp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.dsp.entity.DspRptCommonDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * @Description: dsp_rpt_common_daily
 * @Author: jeecg-boot
 * @Date: 2021-01-22
 * @Version: V1.0
 */
public interface DspRptCommonDailyMapper extends BaseMapper<DspRptCommonDaily> {

    @Select("SELECT a.report_date AS dim,\n" +
            "       a.report_date AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY a.report_date")
    List<DspRptCommonDaily> queryReportDimByDate(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    @Select("SELECT a.strategy_id AS dim,\n" +
            "       b.name AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "LEFT JOIN dsp_strategy b ON a.strategy_id = b.id\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY a.strategy_id,\n" +
            "         b.name")
    List<DspRptCommonDaily> queryReportDimByStrategy(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    @Select("SELECT a.creative_id AS dim,\n" +
            "       b.name AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "LEFT JOIN dsp_creative b ON a.creative_id = b.id\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY a.creative_id,\n" +
            "         b.name")
    List<DspRptCommonDaily> queryReportDimByCreative(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    @Select("SELECT a.platform_id AS dim,\n" +
            "       b.name AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "LEFT JOIN dsp_platform b ON a.platform_id = b.id\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY a.platform_id,\n" +
            "         b.name")
    List<DspRptCommonDaily> queryReportDimByPlatform(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    @Select("SELECT b.id AS dim,\n" +
            "       b.name AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "LEFT JOIN dsp_adspace c ON a.adspace_id = c.id\n" +
            "LEFT JOIN dsp_media b ON c.media_id = b.id\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY b.id,\n" +
            "         b.name")
    List<DspRptCommonDaily> queryReportDimByMedia(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    @Select("SELECT a.adspace_id AS dim,\n" +
            "       b.name AS dim_name,\n" +
            "       sum(a.pv) AS pv,\n" +
            "       sum(a.click) AS click,\n" +
            "       sum(a.cv) AS cv,\n" +
            "       sum(a.deep_cv) AS deep_cv,\n" +
            "       sum(a.customer_cost) AS customer_cost\n" +
            "FROM dsp_rpt_common_daily a\n" +
            "LEFT JOIN dsp_adspace b ON a.adspace_id = b.id\n" +
            "WHERE a.campaign_id = #{campaignId}\n" +
            "  AND a.report_date >= #{start}\n" +
            "  AND a.report_date <= #{end}\n" +
            "GROUP BY a.adspace_id,\n" +
            "         b.name")
    List<DspRptCommonDaily> queryReportDimByAdspace(
            @Param("campaignId") String campaignId,
            @Param("start") Date start,
            @Param("end") Date end
    );

}
