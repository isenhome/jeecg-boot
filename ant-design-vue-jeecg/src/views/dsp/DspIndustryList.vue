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
      <j-super-query :fieldList="superFieldList" ref="superQueryModal"
                     @handleSuperQuery="handleSuperQuery"></j-super-query>
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
        :columns="columns"
        :scroll="{x: 1500}"
        size="middle"
        :pagination="false"
        :dataSource="dataSource"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @expandedRowsChange="handleExpandedRowsChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" @click="handleAddSub(record)">添加下级</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" @click="handleDataRule(record)">数据规则</a>
              </a-menu-item>

              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.key)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
        <!-- 字符串超长截取省略号显示 -->
        <span slot="url" slot-scope="text">
          <j-ellipsis :value="text" :length="25"/>
        </span>
        <!-- 字符串超长截取省略号显示-->
        <span slot="component" slot-scope="text">
          <j-ellipsis :value="text"/>
        </span>
      </a-table>

    </div>

    <dsp-industry-modal ref="modalForm" @ok="modalFormOk"></dsp-industry-modal>

  </a-card>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import {mixinDevice} from '@/utils/mixin'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import DspIndustryModal from './modules/DspIndustryModal'
    import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'
    import JTreeTable from '@/components/jeecg/JTreeTable'

    export default {
        name: 'DspIndustryList',
        mixins: [JeecgListMixin, mixinDevice],
        components: {
            DspIndustryModal,
            JSuperQuery,
            JTreeTable
        },
        data() {
            return {
                description: 'dsp_industry管理页面',
                // 展开的行，受控属性
                expandedRowKeys: [],
                // 表头
                columns: [
                    {
                        title: '编号',
                        dataIndex: 'id',
                        key: 'rowIndex',
                        align: "left"
                    },
                    {
                        title: '名称',
                        align: "left",
                        dataIndex: 'title'
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
                    list: "/dsp/dspIndustry/list",
                    delete: "/dsp/dspIndustry/delete",
                    deleteBatch: "/dsp/dspIndustry/deleteBatch",
                    queryTreeList: "/dsp/dspIndustry/queryTreeList"
                },
                dictOptions: {},
                superFieldList: [],
                // dataSource:
            }
        },
        created() {
            this.getSuperFieldList();
        },
        computed: {
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
            initDictConfig() {
            },
            getSuperFieldList() {
                let fieldList = [];
                fieldList.push({type: 'string', value: 'title', text: '名称'})
                this.superFieldList = fieldList
            },
            handleAddSub(record) {
                this.$refs.modalForm.title = "添加子菜单";
                this.$refs.modalForm.localMenuType = 1;
                this.$refs.modalForm.disableSubmit = false;
                this.$refs.modalForm.edit({'parentId':record.key});
            },
            handleExpandedRowsChange(expandedRows) {
                this.expandedRowKeys = expandedRows
            }
        }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>