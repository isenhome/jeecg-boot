<template>
  <j-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    switchFullscreen
    @ok="handleSubmit"
    @cancel="close"
    style="top:50px"
    cancelText="关闭"
  >
    广告主:
    <a-input-search
      :style="{width:'450px',marginBottom:'15px'}"
      placeholder="请输入广告主名称"
      v-model="queryParam.name"
      @search="onSearch"
    ></a-input-search>
    <a-button @click="searchReset(1)" style="margin-left: 20px" icon="redo">重置</a-button>
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
      :rowSelection="{selectedRowKeys: selectedRowKeys,type:getType, onChange: onSelectChange}"
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
    </a-table>
  </j-modal>
</template>

<script>
    import {filterObj} from '@/utils/util'
    import {getAction} from "../../../api/manage";

    export default {
        name: "DspAdvertiserSelectorModel",
        props: ['modalWidth', 'multi', 'ids'],
        data() {
            return {
                visible: false,
                title: "选择广告主",
                queryParam: {
                    advertiserName: "",
                },
                columns: [
                    {
                        title: '广告主',
                        align: "center",
                        dataIndex: 'name'
                    },
                    {
                        title: '公司名称',
                        align: "center",
                        dataIndex: 'company'
                    }
                ],
                selectedRowKeys: [],
                selectAdvertiserIds: [],
                selectAdvertiserRows: [],
                dataSource: [],
                ipagination: {
                    current: 1,
                    pageSize: 10,
                    pageSizeOptions: ['10', '20', '30'],
                    showTotal: (total, range) => {
                        return range[0] + '-' + range[1] + ' 共' + total + '条'
                    },
                    showQuickJumper: true,
                    showSizeChanger: true,
                    total: 0
                },
                loading: false,
                url:{
                    queryList:"/dsp/dspAdvertiser/list"
                }
            }
        },
        created() {
            this.getSuperFieldList();
        },
        computed: {
            // 计算属性的 getter
            getType: function () {
                return this.multi == true ? 'checkbox' : 'radio';
            }
        },
        methods: {
            initSelectRow() {
                if (this.ids) {
                    // 这里最后加一个 , 的原因是因为无论如何都要使用 in 查询，防止后台进行了模糊匹配，导致查询结果不准确
                    let values = this.ids.split(',') + ','
                    getAction(this.url.queryList,{
                        id: values,
                        pageNo: 1,
                        pageSize: values.length
                    }).then((res) => {
                        if (res.success) {
                            let selectedRowKeys = []
                            let names = []
                            res.result.records.forEach(user => {
                                names.push(user['name'])
                                selectedRowKeys.push(user['id'])
                            })
                            this.selectedRowKeys = selectedRowKeys
                            this.$emit('initComp', names.join(','))
                        }
                    })
                } else {
                    this.$emit('initComp', '')
                }
            },
            async loadData(arg) {
                if (arg === 1) {
                    this.ipagination.current = 1;
                }
                this.loading = true
                let params = this.getQueryParams()//查询条件
                await getAction(this.url.queryList, params).then((res) => {
                    if (res.success) {
                        this.dataSource = res.result.records
                        this.ipagination.total = res.result.total
                    }
                }).finally(() => {
                    this.loading = false
                })
            },
            onSearch() {
                this.loadData(1);
            },
            close() {
                this.searchReset(0);
                this.visible = false;
            },
            handleTableChange(pagination, filters, sorter) {
                //TODO 筛选
                if (Object.keys(sorter).length > 0) {
                    this.isorter.column = sorter.field;
                    this.isorter.order = 'ascend' === sorter.order ? 'asc' : 'desc';
                }
                this.ipagination = pagination;
                this.loadData();
            },
            handleSubmit() {
                let that = this;
                this.getSelectUserRows();
                that.$emit('ok', that.selectAdvertiserRows, that.selectAdvertiserIds);
                that.searchReset(0)
                that.close();
            },
            searchReset(num) {
                let that = this;
                if (num !== 0) {
                    that.queryParam = {};
                    that.loadData(1);
                }
                that.selectAdvertiserRows = [];
                that.selectAdvertiserIds = [];
            },
            //获取选择用户信息
            getSelectUserRows(rowId) {
                let dataSource = this.dataSource;
                let advertiserIds = "";
                this.selectAdvertiserRows = [];
                for (let i = 0, len = dataSource.length; i < len; i++) {
                    if (this.selectedRowKeys.includes(dataSource[i].id)) {
                        this.selectAdvertiserRows.push(dataSource[i]);
                        advertiserIds = advertiserIds + "," + dataSource[i].id
                    }
                }
                this.selectAdvertiserIds = advertiserIds.substring(1);
            },
            getSuperFieldList() {
                let fieldList = [];
                fieldList.push({type: 'string', value: 'name', text: '广告主'})
                fieldList.push({type: 'string', value: 'company', text: '公司名称'})
                this.superFieldList = fieldList
            },
            // 触发屏幕自适应
            resetScreenSize() {
                let screenWidth = document.body.clientWidth;
                if (screenWidth < 500) {
                    this.scrollTrigger = {x: 800};
                } else {
                    this.scrollTrigger = {};
                }
            },
            showModal() {
                this.visible = true;
                this.initSelectRow()
                this.loadData();
            },
            getQueryParams() {
                let param = Object.assign({}, this.queryParam, this.isorter);
                param.field = this.getQueryField();
                param.pageNo = this.ipagination.current;
                param.pageSize = this.ipagination.pageSize;
                return filterObj(param);
            },
            getQueryField() {
                let str = 'id,';
                for (let a = 0; a < this.columns.length; a++) {
                    str += ',' + this.columns[a].dataIndex;
                }
                return str;
            },
            searchReset(num) {
                let that = this;
                if (num !== 0) {
                    that.queryParam = {};
                    that.loadData(1);
                }
                that.selectedRowKeys = [];
                that.selectAdvertiserIds = [];
            },
            onSelectChange(selectedRowKeys, selectionRows) {
                this.selectedRowKeys = selectedRowKeys;
                this.selectionRows = selectionRows;
            },
        }
    }
</script>

<style scoped>

</style>