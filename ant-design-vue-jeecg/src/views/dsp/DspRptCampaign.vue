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
            <bar-and-line :height="chart.height" :data-source="chartDataSource" :span="16"/>
<!--            <v-chart :data="chart.chartData" :settings="chart.chartSettings"></v-chart>-->
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

    const range_start = moment().subtract(8, 'days').format('YYYY-MM-DD')
    const range_end = moment().subtract(1, 'days').format('YYYY-MM-DD')
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
                    chartSettings: {
                        showLine: ['下单用户']
                    },
                    chartData: {
                        columns: ['日期', '访问用户', '下单用户', '下单率'],
                        rows: [
                            {'日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32},
                            {'日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26},
                            {'日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76},
                            {'日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49},
                            {'日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323},
                            {'日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78}
                        ]
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
                },
                chartDataSource: [
                    {type: '10:10', bar: 200, line: 1000},
                    {type: '10:15', bar: 600, line: 1000},
                    {type: '10:20', bar: 200, line: 1000},
                    {type: '10:25', bar: 900, line: 1000},
                    {type: '10:30', bar: 200, line: 1000},
                    {type: '10:35', bar: 200, line: 1000},
                    {type: '10:40', bar: 100, line: 1000}
                ]
            }
        },
        watch: {
            dataSource(newVal, oldVal) {
                console.log('newVal', newVal)
                console.log('oldVal', oldVal)
                this.chartDataSource = []
                for (let i in newVal) {
                    let item = newVal[i]
                    this.chartDataSource.push({type: item.name, bar: item["pv"], line: item["click"]})
                }
                console.log("chartDataSource", this.chartDataSource)

            }
        },
        computed: {},
        methods: {
            handleClick(e) {
                console.log('click', e);
                let text = e.item.title
                this.menu.currentMenu = text;
                this.columns[0].title = text;
                this.selector.bar = 'pv';
                this.selector.line = 'click';
                this.filters.dim = e.key;
                this.loadData()
            },
            titleClick(e) {
                console.log('titleClick', e);
            },
            selectorBarChange(value) {
                this.selector.bar = value
                this.chartDataSource = []
                for (let i in this.dataSource) {
                    let item = this.dataSource[i]
                    this.chartDataSource.push({
                        type: item.name,
                        bar: item[this.selector.bar],
                        line: item[this.selector.line]
                    })
                }
            },
            selectorLineChange(value, option) {
                this.selector.line = value
                this.chartDataSource = []
                for (let i in this.dataSource) {
                    let item = this.dataSource[i]
                    this.chartDataSource.push({
                        type: item.name,
                        bar: item[this.selector.bar],
                        line: item[this.selector.line]
                    })
                }
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