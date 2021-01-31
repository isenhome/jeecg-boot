<template>
  <div>
    <a-row :gutter="24">

      <a-col>
        <a-card>
          <h1 style="float: left">广告数据概况</h1>
          <a-range-picker v-model="range" style="float: right" @change="changeRange"/>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="24" :style="{ marginTop: '24px' }">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总消耗" :total="'￥'+headerData.customerCost">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o"/>
          </a-tooltip>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总客户数" :total="headerData.customer">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o"/>
          </a-tooltip>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总转化数" :total="headerData.cv">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o"/>
          </a-tooltip>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="平均转化成本" :total="'￥'+ headerData.ecv">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o"/>
          </a-tooltip>
        </chart-card>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col>
        <a-card :loading="loading" :bordered="false">
          <a-row>
            <a-col :span="12">
              <a-select v-model="selector.bar" :options="selector.options" style="width: 45%"
                        @change="selectorBarChange"/>
              <span>  对比  </span>
              <a-select v-model="selector.line" :options="selector.options" style="width: 45%"
                        @change="selectorLineChange"/>
            </a-col>
            <a-col :span="12">
            </a-col>
          </a-row>
          <a-row>
            <a-col>
              <ve-histogram ref="chart" :data="chart.chartData" :settings="chart.chartSettings"></ve-histogram>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>

  </div>
</template>

<script>

  import 'v-charts'
  import VeHistogram from 'v-charts/lib/histogram.common'
  import moment from 'dayjs'
  import {getAction} from "../../api/manage";
  import ChartCard from '@/components/ChartCard'
  import Trend from '@/components/Trend'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'

  const range_start = moment().subtract(8, 'days').format('YYYY-MM-DD')
  const range_end = moment().subtract(1, 'days').format('YYYY-MM-DD')
  const showLine = ['click']
  const columns = ['name', 'pv', 'click']

  export default {
    name: "Analysis",
    components: {
      VeHistogram,
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      Trend
    },
    data() {
      return {
        loading: true,
        range: [range_start, range_end],
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
        headerData: {
          customerCost: 0.00,
          cv: 0,
          customer: 0,
          ecv: 0.0
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
        url: {
          list: '/dsp/dspRptCommonDaily/allReport',
          total: '/dsp/dspRptCommonDaily/totalReport'
        },
        filters: {
          campaignId: this.$route.params.campaignId,
          dim: "report_date",
          start: range_start,
          end: range_end
        },
        range: [range_start, range_end],
      }
    },
    methods: {
      selectorBarChange(value) {
        this.selector.bar = value
        this.$set(this.chart.chartData.columns, 1, value)
      },
      selectorLineChange(value) {
        this.selector.line = value
        this.$set(this.chart.chartData.columns, 2, value)
        this.$set(this.chart.chartSettings.showLine, 0, value)
      },
      changeRange(moments, strDate) {
        this.filters.start = strDate[0]
        this.filters.end = strDate[1]
        this.loadData()
        this.loadHeader()
      },
      loadData() {
        let that = this
        getAction(this.url.list, this.filters).then((res) => {
          if (res.success) {
            that.chart.chartData.rows = res.result
          }
        })
      },
      loadHeader() {
        let that = this
        getAction(this.url.total, this.filters).then((res) => {
          if (res.success) {
            that.headerData = res.result
          }
        })
      },
    },
    created() {
      this.loadData()
      this.loadHeader()
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
    }
  }
</script>

<style scoped lang="less">

  .circle-cust {
    position: relative;
    top: 28px;
    left: -100%;
  }

  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }

    p {
      line-height: 42px;
      margin: 0;

      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }

</style>