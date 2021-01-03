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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <!--<a-table-->
        <!--ref="table"-->
        <!--size="middle"-->
        <!--:scroll="{x:true}"-->
        <!--bordered-->
        <!--rowKey="id"-->
        <!--:columns="columns"-->
        <!--:dataSource="dataSource"-->
        <!--:pagination="ipagination"-->
        <!--:loading="loading"-->
        <!--:rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"-->
        <!--class="j-table-force-nowrap"-->
        <!--@change="handleTableChange">-->

        <!--<template slot="htmlSlot" slot-scope="text">-->
          <!--<div v-html="text"></div>-->
        <!--</template>-->
        <!--<template slot="imgSlot" slot-scope="text">-->
          <!--<span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>-->
          <!--<img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>-->
        <!--</template>-->
        <!--<template slot="fileSlot" slot-scope="text">-->
          <!--<span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>-->
          <!--<a-button-->
            <!--v-else-->
            <!--:ghost="true"-->
            <!--type="primary"-->
            <!--icon="download"-->
            <!--size="small"-->
            <!--@click="downloadFile(text)">-->
            <!--下载-->
          <!--</a-button>-->
        <!--</template>-->

        <!--<span slot="action" slot-scope="text, record">-->
          <!--<a @click="handleEdit(record)">编辑</a>-->

          <!--<a-divider type="vertical" />-->
          <!--<a-dropdown>-->
            <!--<a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
            <!--<a-menu slot="overlay">-->
              <!--<a-menu-item>-->
                <!--<a @click="handleDetail(record)">详情</a>-->
              <!--</a-menu-item>-->
              <!--<a-menu-item>-->
                <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                  <!--<a>删除</a>-->
                <!--</a-popconfirm>-->
              <!--</a-menu-item>-->
            <!--</a-menu>-->
          <!--</a-dropdown>-->
        <!--</span>-->

      <!--</a-table>-->

      <j-tree-table
        :url="url.queryTreeList"
        topValue="0"
        queryKey="id"
        :columns="columns"
        :tableProps="tableProps">

        <template v-slot:action="props">
          <!-- 可使用的参数： -->
          <!-- props.text -->
          <!-- props.record -->
          <!-- props.index -->
          <a @click="()=>handleEdit(props.record)">编辑</a>
        </template>

      </j-tree-table>

    </div>

    <dsp-industry-modal ref="modalForm" @ok="modalFormOk"></dsp-industry-modal>

  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DspIndustryModal from './modules/DspIndustryModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'
  import JTreeTable from '@/components/jeecg/JTreeTable'

  export default {
    name: 'DspIndustryList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      DspIndustryModal,
      JSuperQuery,
      JTreeTable
    },
    data () {
      return {
        description: 'dsp_industry管理页面',
        url: '/mock/api/asynTreeList',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'父级',
            align:"center",
            dataIndex: 'parentId'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/dsp/dspIndustry/list",
          delete: "/dsp/dspIndustry/delete",
          deleteBatch: "/dsp/dspIndustry/deleteBatch",
          queryTreeList:"/dsp/dspIndustry/queryTreeList",
          mock:'/mock/api/asynTreeList'
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
      tableProps() {
        let _this = this
        return {
          // 列表项是否可选择
          // https://vue.ant.design/components/table-cn/#rowSelection
          rowSelection: {
            selectedRowKeys: _this.selectedRowKeys,
            onChange: (selectedRowKeys) => _this.selectedRowKeys = selectedRowKeys
          }
        }
      }
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'name',text:'名称'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>