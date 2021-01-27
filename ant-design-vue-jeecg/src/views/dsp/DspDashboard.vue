<template>
  <a-card>
    <a-row>
      <a-col :span="6">
        <a-select v-model="selector.bar" :options="selector.options" style="width: 45%"
                  @change="selectorBarChange"/>
        <span>  对比  </span>
        <a-select v-model="selector.line" :options="selector.options" style="width: 45%"
                  @change="selectorLineChange"/>
      </a-col>
      <a-col :span="18">
        <a-range-picker v-model="range" style="float: right" @change="changeRange"/>
      </a-col>
    </a-row>
    <ve-histogram ref="chart" :data="chart.chartData" :settings="chart.chartSettings"></ve-histogram>
  </a-card>
</template>

<script>
    import 'v-charts'
    import VeHistogram from 'v-charts/lib/histogram.common'
    import moment from 'dayjs'
    import {getAction} from "../../api/manage";

    const range_start = moment().subtract(8, 'days').format('YYYY-MM-DD')
    const range_end = moment().subtract(1, 'days').format('YYYY-MM-DD')
    const showLine = ['click']
    const columns = ['name', 'pv', 'click']

    export default {
        name: "DspDashboard",
        components: {
            VeHistogram
        },
        data() {
            return {
                range: [range_start, range_end],
                chart: {
                    chartSettings: {
                        labelMap: {
                            'dim': '维度编号',
                            'name': '维度名称',
                            'pv': '展示数',
                            'click': '点击数',
                            'ctr': '展示率',
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
                    list: '/dsp/dspRptCommonDaily/allReport'
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
            },
            loadData() {
                let that = this
                getAction(this.url.list, this.filters).then((res) => {
                    if (res.success) {
                        that.chart.chartData.rows = res.result
                    }
                })
            }
        },
        created() {
            this.loadData()
        }
    }
</script>

<style scoped>

</style>