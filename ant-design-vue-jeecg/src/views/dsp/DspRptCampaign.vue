<template>
  <page-layout title="订单名称">
    <a-layout>
      <a-layout-sider :span="6" style="background-color: #ffffff">
        <a-menu
          :default-selected-keys="['日报']"
          :open-keys.sync="menu.openKeys"
          mode="inline"
          @click="handleClick"
        >
          <a-sub-menu key="common" @titleClick="titleClick">
            <span slot="title"><span>基本报告</span></span>
            <a-menu-item key="日报">
              日报
            </a-menu-item>
            <a-menu-item key="策略">
              策略
            </a-menu-item>
            <a-menu-item key="创意">
              创意
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="resource" @titleClick="titleClick">
            <span slot="title"><span>资源报告</span></span>
            <a-menu-item key="平台">
              平台
            </a-menu-item>
            <a-menu-item key="媒体">
              媒体
            </a-menu-item>
            <a-menu-item key="广告位">
              广告位
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout :span="16" style="margin-left: 10px">
        <a-layout-content>
          <a-card :title="menu.currentMenu">
            <a-row>
              <a-col :span="6">
                <a-select v-model="selector.bar" :options="selector.options" style="width: 45%"
                          @change="selectorBarChange"/>
                <span>  对比  </span>
                <a-select v-model="selector.line" :options="selector.options" style="width: 45%"
                          @change="selectorLineChange"/>
              </a-col>
              <a-col :span="18">
                <a-range-picker v-model="filters.dateRange"  style="float: right" @change="changeRange"/>
              </a-col>
            </a-row>
            <bar-and-line :height="chart.height" :span="16"/>
            <a-table
              ref="table"
              size="middle"
              :scroll="{x:true}"
              bordered
              rowKey="id"
              :columns="columns"
              :dataSource="dataSource"
              :pagination="ipagination"
              :loading="loading"
              class="j-table-force-nowrap"
              @change="handleTableChange">
            </a-table>
          </a-card>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </page-layout>
</template>

<script>
    import PageLayout from '@/components/page/PageLayout';
    import BarAndLine from '@/components/chart/BarAndLine'
    import {mixinDevice} from '@/utils/mixin'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import moment from 'dayjs'

    export default {
        name: "DspRptCampaign",
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            BarAndLine,
            PageLayout,
        },
        data() {
            return {
                menu: {
                    openKeys: ['common', 'resource'],
                    currentMenu: "日报",
                },
                chart: {
                    height: 500,
                },
                columns: [
                    {
                        title: '日报',
                        align: "center",
                        dataIndex: 'dim'
                    },
                    {
                        title: '展示数',
                        align: "center",
                        dataIndex: 'pv'
                    },
                    {
                        title: '点击数',
                        align: "center",
                        dataIndex: 'click'
                    },
                    {
                        title: '点击率',
                        align: "center",
                        dataIndex: 'ctr'
                    },
                    {
                        title: 'ECPM',
                        align: "center",
                        dataIndex: 'ecpm'
                    },
                    {
                        title: 'ECPC',
                        align: "center",
                        dataIndex: 'ecpc'
                    },
                    {
                        title: '消耗',
                        align: "center",
                        dataIndex: 'customerCost'
                    },
                    {
                        title: '转化数',
                        align: "center",
                        dataIndex: 'cv'
                    },
                    {
                        title: '转化成本',
                        align: "center",
                        dataIndex: 'platformCost'
                    },
                    {
                        title: '转化率',
                        align: "center",
                        dataIndex: 'cvr'
                    },
                    {
                        title: '深度转化数',
                        align: "center",
                        dataIndex: 'deepCv'
                    },
                    {
                        title: '深度转化成本',
                        align: "center",
                        dataIndex: 'deepCvPlatformCost'
                    },
                    {
                        title: '深度转化率',
                        align: "center",
                        dataIndex: 'deepCvr'
                    }
                ],
                url: {
                    list: '/dsp/dspRptCommonDaily/report'
                },
                filters: {
                    campaignId: this.$route.params.campaignId,
                    type: "日报",
                    range: [moment().subtract(8, 'days').format('YYYY-MM-DD'), moment().subtract(1, 'days').format('YYYY-MM-DD')]
                },
                selector: {
                    bar: 'pv',
                    line:
                        'click',
                    options:
                        [
                            {value: 'pv', label: "展示"},
                            {value: 'click', label: "点击"},
                            {value: 'cv', label: "转化"},
                            {value: 'deep_cv', label: "深度转化"},
                            {value: 'ctr', label: "点击率"},
                            {value: 'cvr', label: "转化率"},
                            {value: 'deep_cvr', label: "深度转化率"}
                        ]
                }
            }
        },
        watch: {
            openKeys(val) {
                console.log('openKeys', val);
            },
        },
        computed: {},
        methods: {
            handleClick(e) {
                console.log('click', e);
                this.menu.currentMenu = e.key;
                this.table.columns[1].title = e.key;
                this.table.columns[1].dataIndex = e.key;
                this.selector.bar = 'pv';
                this.selector.line = 'click';
            },
            titleClick(e) {
                console.log('titleClick', e);
            },
            selectorBarChange(value, option) {

            },
            selectorLineChange(value, option) {

            },
            changeRange(moments, strDate) {

            }
        },

    }
</script>

<style scoped>
  @import '~@assets/less/common.less';
</style>