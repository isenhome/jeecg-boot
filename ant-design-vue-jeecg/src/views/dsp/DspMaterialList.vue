<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button v-has="'material:add'" @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal"
                     @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-has="'material:delete'" v-if="selectedRowKeys.length > 0">
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

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a v-has="'material:edit'" @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm v-has="'material:delete'" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <dsp-material-modal ref="modalForm" @ok="modalFormOk"></dsp-material-modal>
  </a-card>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import {mixinDevice} from '@/utils/mixin'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import DspMaterialModal from './modules/DspMaterialModal'
    import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

    export default {
        name: 'DspMaterialList',
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            DspMaterialModal,
            JSuperQuery,
        },
        data() {
            return {
                description: 'dsp_material管理页面',
                // 表头
                columns: [
                    {
                        title: '编号',
                        dataIndex: 'id',
                        key: 'rowIndex',
                        align: "center"
                    },
                    {
                        title: '素材名称',
                        align: "center",
                        dataIndex: 'name'
                    },
                    {
                        title: '广告主',
                        align: "center",
                        dataIndex: 'advertiserName'
                    },
                    {
                        title: '素材图片地址',
                        align: "center",
                        dataIndex: 'imgUrl'
                    },
                    {
                        title: '备注',
                        align: "center",
                        dataIndex: 'comment'
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align: "center",
                        fixed: "right",
                        width: 147,
                        scopedSlots: {customRender: 'action'}
                    }
                ],
                url: {
                    list: "/dsp/dspMaterial/list",
                    delete: "/dsp/dspMaterial/delete",
                    deleteBatch: "/dsp/dspMaterial/deleteBatch"
                },
                dictOptions: {},
                superFieldList: [],
            }
        },
        created() {
            this.getSuperFieldList();
        },
        computed: {
            importExcelUrl: function () {
                return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
            },
        },
        methods: {
            initDictConfig() {
            },
            getSuperFieldList() {
                let fieldList = [];
                fieldList.push({type: 'string', value: 'name', text: '素材名称'})
                fieldList.push({type: 'string', value: 'advertiserId', text: '广告主编号'})
                fieldList.push({type: 'string', value: 'imgUrl', text: '素材图片地址'})
                fieldList.push({type: 'string', value: 'comment', text: '备注'})
                this.superFieldList = fieldList
            }
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>