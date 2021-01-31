<template>
  <page-layout title="订单名称">
    <a-layout>
      <a-layout-sider :span="6" style="background-color: #ffffff">
        <a-menu
          :default-selected-keys="['report_date']"
          :open-keys.sync="menu.openKeys"
          mode="inline"
          @click="handleClick"
        >
          <a-sub-menu key="common" @titleClick="titleClick">
            <span slot="title"><span>基本报告</span></span>
            <a-menu-item key="report_date" title="日报">
              日报
            </a-menu-item>
            <a-menu-item key="strategy_id" title="策略">
              策略
            </a-menu-item>
            <a-menu-item key="creative_id" title="创意">
              创意
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="resource" @titleClick="titleClick">
            <span slot="title"><span>资源报告</span></span>
            <a-menu-item key="platform_id" title="平台">
              平台
            </a-menu-item>
            <a-menu-item key="media_id" title="媒体">
              媒体
            </a-menu-item>
            <a-menu-item key="adspace_id" title="广告位">
              广告位
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout :span="16" style="margin-left: 10px">
        <a-layout-content>
          <a-card :title="menu.currentMenu">
            <a-row>
              <a-col :span="12">
                <a-select v-model="selector.bar" :options="selector.options" style="width: 45%"
                          @change="selectorBarChange"/>
                <span>  对比  </span>
                <a-select v-model="selector.line" :options="selector.options" style="width: 45%"
                          @change="selectorLineChange"/>
              </a-col>
              <a-col :span="12">
                <a-range-picker v-model="range" style="float: right" @change="changeRange"/>
              </a-col>
            </a-row>
            <a-row style="margin-top: 24px">
              <a-col>
                <!--            <bar-and-line :height="chart.height" :data-source="chartDataSource" :span="16"/>-->
                <ve-histogram ref="chart" :data="chart.chartData" :settings="chart.chartSettings"></ve-histogram>
              </a-col>
            </a-row>
            <a-row>
              <a-col>
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
              </a-col>
            </a-row>
          </a-card>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </page-layout>
</template>

<script>
  import 'v-charts'
  import VeHistogram from 'v-charts/lib/histogram.common'
  import PageLayout from '@/components/page/PageLayout'
  import BarAndLine from '@/components/chart/BarAndLine'
  import {mixinDevice} from '@/utils/mixin'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import moment from 'dayjs'
  import ARow from "ant-design-vue/es/grid/Row";

  const range_start = moment().subtract(8, 'days').format('YYYY-MM-DD')
  const range_end = moment().subtract(1, 'days').format('YYYY-MM-DD')
  const showLine = ['click']
  const columns = ['name', 'pv', 'click']
  export default {
    name: "DspRptCampaign",
    mixins: [JeecgListMixin, mixinDevice],
    components: {
      ARow,
      BarAndLine,
      PageLayout,
      VeHistogram
    },
    data() {
      return {
        menu: {
          openKeys: ['common', 'resource'],
          currentMenu: "日报",
        },
        chart: {
          chartSettings: {
            labelMap: {
              'dim': '维度编号',
              'name': '维度名称',
              'pv': '展示数',
              'click': '点击数',
              'ctr': '点击率',
              'ecpm': 'ECPM',
              'ecpc': 'ECPC',
              'customerCost': '消耗',
              'cv': '转化次数',
              'cvr': '转化率',
              'ecv': '单次转化成本',
              'deepCv': '深度转化数',
              'edeepCv': '单次深度转化成本',
              'deepCvr': '深度转化率'
            },
            showLine: showLine
          },
          chartData: {
            columns: columns,
            rows: []
          }
        },
        columns: [
          {
            title: '维度编号',
            align: "center",
            dataIndex: 'dim'
          },
          {
            title: '日报',
            align: "center",
            dataIndex: 'name'
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
            title: '单次转化成本',
            align: "center",
            dataIndex: 'ecv'
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
            title: '单次深度转化成本',
            align: "center",
            dataIndex: 'edeepCv'
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
        range: [range_start, range_end],
        filters: {
          campaignId: this.$route.params.campaignId,
          dim: "report_date",
          start: range_start,
          end: range_end
        },
        selector: {
          bar: 'pv',
          line: 'click',
          options: [
            {value: 'pv', label: '展示数'},
            {value: 'click', label: '点击数'},
            {value: 'ctr', label: '展示率'},
            {value: 'ecpm', label: 'ECPM'},
            {value: 'ecpc', label: 'ECPC'},
            {value: 'customerCost', label: '消耗'},
            {value: 'cv', label: '转化次数'},
            {value: 'cvr', label: '转化率'},
            {value: 'ecv', label: '单次转化成本'},
            {value: 'deepCv', label: '深度转化数'},
            {value: 'edeepCv', label: '单次深度转化成本'},
            {value: 'deepCvr', label: '深度转化率'}
          ]
        },
        chartDataSource: []
      }
    },
    watch: {
      dataSource(newVal, oldVal) {
        console.log('newVal', newVal)
        console.log('oldVal', oldVal)
        this.chartDataSource = []
        this.chart.chartData.rows = []
        for (let i in newVal) {
          let item = newVal[i]
          this.chart.chartData.rows.push(item)
        }
        this.$refs.chart.echarts.resize()
        console.log(this.$refs.chart)
      }
    },
    computed: {},
    methods: {
      handleClick(e) {
        console.log('click', e);
        let text = e.item.title
        this.menu.currentMenu = text;
        this.columns[0].title = text;
        this.filters.dim = e.key;
        this.loadData()
      },
      titleClick(e) {
        console.log('titleClick', e);
      },
      selectorBarChange(value) {
        this.selector.bar = value
        this.$set(this.chart.chartData.columns, 1, value)
      },
      selectorLineChange(value, option) {
        this.selector.line = value
        this.$set(this.chart.chartData.columns, 2, value)
        this.$set(this.chart.chartSettings.showLine, 0, value)
      },
      changeRange(moments, strDate) {
        this.filters.start = strDate[0]
        this.filters.end = strDate[1]
        this.loadData()
      }
    },
    created() {

    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less';
</style>