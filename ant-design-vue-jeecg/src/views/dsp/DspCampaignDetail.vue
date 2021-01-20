<template>
  <page-layout :title="campaign.name">
    <a-card :bordered="false">
      <detail-list>
        <detail-list-item term="广告主">{{campaign.advertiserName}}</detail-list-item>
        <detail-list-item term="行业">{{campaign.industryName}}</detail-list-item>
        <detail-list-item term="开始时间">{{campaign.start}}</detail-list-item>
        <detail-list-item v-if="campaign.end!=null" term="结束时间">{{campaign.end}}</detail-list-item>
        <detail-list-item term="预算">{{campaign.limitCost}}</detail-list-item>
        <detail-list-item v-if="campaign.comment!=null" term="备注">{{campaign.comment}}</detail-list-item>
      </detail-list>
      <a-divider style="margin-bottom: 32px"/>
    </a-card>
    <a-card
      style="margin-top: 24px"
      :bordered="false"
      :tabList="tab.tabList"
      :activeTabKey="tab.activeTabKey"
      @tabChange="(key) => {this.tab.activeTabKey = key}"
      :bodyStyle="{background:'#f6f6f6'}"
    >
      <dsp-strategy-list
        :campaign="campaign"
        v-if="tab.activeTabKey === '1' && campaign.id"
      />
      <dsp-creative-list
        :campaign="campaign"
        v-if="tab.activeTabKey === '2' && campaign.id"
      />
    </a-card>
  </page-layout>
</template>

<script>
    import PageLayout from '@/components/page/PageLayout'
    import STable from '@/components/table/'
    import DetailList from '@/components/tools/DetailList'
    import ABadge from "ant-design-vue/es/badge/Badge"
    import DspStrategyList from "./DspStrategyList";
    import DspCreativeList from "./DspCreativeList";
    import {getAction} from "../../api/manage";

    const DetailListItem = DetailList.Item

    export default {
        name: "DspCampaignDetail",
        components: {
            PageLayout,
            ABadge,
            DetailList,
            DetailListItem,
            STable,
            DspStrategyList,
            DspCreativeList
        },
        data() {
            return {
                tab: {
                    activeTabKey: '1',
                    tabList: [
                        {
                            key: '1',
                            tab: '策略管理'
                        },
                        {
                            key: '2',
                            tab: '创意管理'
                        }
                    ]
                },
                campaign: {},
                url: {
                    getCampaignById: "/dsp/dspCampaign/queryById"
                },
            }
        },
        computed: {
            title() {
                return this.$route.meta.title
            }
        },
        mounted() {
            if (this.$route.params.campaignId) {
                let that = this;
                let params = {id: this.$route.params.campaignId}
                getAction(this.url.getCampaignById, params).then((res) => {
                    if (res.success) {
                        that.campaign = res.result
                    } else {
                        that.$message.error(res.message);
                        that.$router.push({name: 'dsp-DspCampaignList'})
                    }
                })
            } else {
                this.$router.push({name: 'exception-404'})
            }
        }
    }
</script>

<style lang="less" scoped>
  .title {
    color: rgba(0, 0, 0, .85);
    font-size: 16px;
    font-weight: 500;
    margin-bottom: 16px;
  }
</style>