<template>
  <a-row :gutter="10">
    <a-col :md="leftColMd" :sm="24">
      <a-card :bordered="false">

        <!-- 操作按钮区域 -->
        <div class="table-operator">
          <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>

          <a-dropdown v-if="selectedRowKeys.length > 0">
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="batchDel">
                <a-icon type="delete"/>
                删除
              </a-menu-item>
            </a-menu>
            <a-button style="margin-left: 8px"> 批量操作
              <a-icon type="down"/>
            </a-button>
          </a-dropdown>
        </div>

        <!-- table区域-begin -->
        <div>
          <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
            <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
            selectedRowKeys.length }}</a>项
            <a style="margin-left: 24px" @click="onClearSelected">清空</a>
          </div>

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
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            class="j-table-force-nowrap"
            @change="handleTableChange">

            <template slot="dateRange" slot-scope="text, record">
              {{record.start}}~{{record.end}}
            </template>

            <template slot="price" slot-scope="text, record">
              <b>{{record.buyType}}</b>（￥）{{record.buyMinBidprice}}~{{record.buyMaxBidprice}}
            </template>

            <template slot="limit" slot-scope="text, record">
              <a @click="handleSetting(record,'limit')">{{record.dailyLimit?'修改':'未设置'}}</a>
            </template>

            <template slot="resource" slot-scope="text, record">
              <a @click="handleSetting(record,'resource')">{{record.resource?'修改':'未设置'}}</a>
            </template>

            <template slot="target" slot-scope="text, record">
              <a @click="handleSetting(record,'target')">{{record.target?'修改':'未设置'}}</a>
            </template>

            <span slot="action" slot-scope="text, record">
            <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>

          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

          </a-table>
        </div>

        <dsp-strategy-modal :campaign="campaign" ref="modalForm" @ok="modalFormOk"></dsp-strategy-modal>
      </a-card>
    </a-col>
    <a-col :md="rightColMd" :sm="24" v-if="this.setting.rightColVal == 1">
      <a-card :bordered="false">
        <a-card :bordered="false">
          <div style="text-align: right;">
            <a-icon type="close-circle" @click="hideSetting"/>
          </div>
        </a-card>
        <dsp-daily-limit v-if="setting.tab==='limit'" :form-data="getDailyLimitFormData" :strategy="getCurrentStrategy"
                         @close="closeRight"/>
        <dsp-resource v-if="setting.tab==='resource'" :form-data="getResourceFormData" :strategy="getCurrentStrategy"
                      @close="closeRight"/>
        <dsp-target v-if="setting.tab==='target'" :strategy="getCurrentStrategy" @close="closeRight"/>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import {mixinDevice} from '@/utils/mixin'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import DspStrategyModal from './modules/DspStrategyModal'
    import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'
    import DspDailyLimit from "./components/DspDailyLimit";
    import DspResource from "./components/DspResource";
    import DspCreative from "./components/DspCreative";
    import DspTarget from "./components/DspTarget";
    import Template4 from "../jeecg/JVxeDemo/layout-demo/Template4";
    import DetailList from '@/components/tools/DetailList'

    const DetailListItem = DetailList.Item

    export default {
        name: 'DspStrategyList',
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            Template4,
            DspStrategyModal,
            JSuperQuery,
            DspDailyLimit,
            DspResource,
            DspCreative,
            DspTarget,
            DetailList,
            DetailListItem
        },
        props: {
            campaign: {
                type: Object,
                default: () => {
                },
                required: true
            }
        },
        data() {
            let that = this;
            return {
                description: 'dsp_strategy管理页面',
                // 表头
                columns: [
                    {
                        title: '#',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 60,
                        align: "center",
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1;
                        }
                    },
                    {
                        title: '策略名称',
                        align: "center",
                        dataIndex: 'name'
                    },
                    {
                        title: '投放周期',
                        align: "center",
                        scopedSlots: {customRender: 'dateRange'}
                    },
                    {
                        title: '价格',
                        align: "center",
                        scopedSlots: {customRender: 'price'}
                    },
                    {
                        title: '备注',
                        dataIndex: 'comment',
                        align: "center"
                    },
                    {
                        title: '日限',
                        align: "center",
                        scopedSlots: {customRender: 'limit'}
                    },
                    {
                        title: '资源',
                        align: "center",
                        scopedSlots: {customRender: 'resource'}
                    },
                    {
                        title: '定向',
                        align: "center",
                        scopedSlots: {customRender: 'target'}
                    },
                    {
                        title: '操作',
                        align: "center",
                        fixed: "right",
                        width: 147,
                        scopedSlots: {customRender: 'action'}
                    }
                ],
                url: {
                    list: "/dsp/dspStrategy/list",
                    delete: "/dsp/dspStrategy/delete",
                    deleteBatch: "/dsp/dspStrategy/deleteBatch",
                    exportXlsUrl: "/dsp/dspStrategy/exportXls",
                    importExcelUrl: "dsp/dspStrategy/importExcel"
                },
                filters: {campaignId: this.campaign.id},
                dictOptions: {},
                superFieldList: [],
                settingTab: 1,
                currentStrategy: {},
                setting: {
                    rightColVal: 0,
                    selectedRowKeys1: [],
                    currentStrategy: {},
                    tab: "",
                    radioBuyType: {
                        value: "CPM",
                        options: [
                            {label: 'CPM', value: 'CPM'},
                            {label: 'CPC', value: 'CPC'}
                        ]
                    },
                    resource: {
                        value: "ADX",
                        options: [
                            {label: 'ADX', value: 'ADX'},
                            {label: 'ADN', value: 'ADN'}
                        ]
                    },
                },
            }
        },
        created() {
            this.getSuperFieldList();
        },
        computed: {
            leftColMd() {
                if (this.setting.currentStrategy.id) {
                    return 12
                } else {
                    return 24
                }
            },
            rightColMd() {
                if (this.setting.currentStrategy.id) {
                    return 12
                } else {
                    return 0
                }
            },
            getCurrentStrategy() {
                return this.setting.currentStrategy
            },
            getDailyLimitFormData() {
                let formData = {}
                let jsonStr = this.setting.currentStrategy.dailyLimit
                if (jsonStr) {
                    formData = JSON.parse(jsonStr)
                }
                return formData
            },
            getResourceFormData() {
                let formData = {}
                let jsonStr = this.setting.currentStrategy.resource
                if (jsonStr) {
                    formData = JSON.parse(jsonStr)
                }
                return formData
            }
        },
        methods: {
            initDictConfig() {
            },
            getSuperFieldList() {
                let fieldList = [];
                fieldList.push({type: 'string', value: 'name', text: '策略名称'})
                this.superFieldList = fieldList
            },
            handleSetting(record, tab) {
                this.setting.rightColVal = 1
                this.setting.currentStrategy = record
                this.setting.tab = tab
            },
            hideSetting() {
                this.setting.rightColVal = 0
                this.setting.currentStrategy = {}
            },
            closeRight() {
                this.setting.rightColVal = 0
                this.setting.currentStrategy = {}
            }
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
