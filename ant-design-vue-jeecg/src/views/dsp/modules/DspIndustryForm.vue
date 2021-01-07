<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-form-item label="行业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group @change="onChangeMenuType" v-decorator="['menuType',{'initialValue':localMenuType}]">
              <a-radio :value="0">一级行业</a-radio>
              <a-radio :value="1">子行业</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name',validatorRules.name]" placeholder="请输入名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item
              v-show="localMenuType!=0"
              label="父级"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :validate-status="validateStatus"
              :hasFeedback="true"
              :required="true">
              <span slot="help">{{ validateStatus=='error'?'请选择父级菜单':'&nbsp;&nbsp;' }}</span>
              <a-tree-select
                style="width:100%"
                :dropdownStyle="{ maxHeight: '200px', overflow: 'auto' }"
                :treeData="treeData"
                v-model="model.parentId"
                placeholder="请选择父级菜单"
                :disabled="disableSubmit"
                @change="handleParentIdChange">
              </a-tree-select>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import {httpAction, getAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import {validateDuplicateValue} from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'
  import ARow from "ant-design-vue/es/grid/Row";

  export default {
    name: 'DspIndustryForm',
    components: {
      ARow,
      JFormContainer,
      JDate,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: () => {
        },
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data() {
      return {
        form: this.$form.createForm(this),
        model: {},
        localMenuType: 0,
        show: false,
        treeData:[],
        disableSubmit:false,
        validateStatus:"",
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        url: {
          add: "/dsp/dspIndustry/add",
          edit: "/dsp/dspIndustry/edit",
          queryById: "/dsp/dspIndustry/queryById",
          queryTreeList:"/dsp/dspIndustry/queryTreeList"
        }
      }
    },
    computed: {
      formDisabled() {
        if (this.formBpm === true) {
          if (this.formData.disabled === false) {
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton() {
        if (this.formBpm === true) {
          if (this.formData.disabled === false) {
            return true
          }
        }
        return false
      },
      validatorRules:function() {
        return {
          name: {
            rules: [
              {required: true, message: "请输入名称！"},
            ]
          },
          parentId: {
            rules: [
              {required: this.show, message: "请输入上级编号！"},
            ]
          }
        }
      }
    },
    created() {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      loadTree(){
        var that = this;
        getAction(this.url.queryTreeList,null).then((res)=>{
          if(res.success){
            console.log(res)
            that.treeData = [];
            let treeList = res.result.treeList
            for(let a=0;a<treeList.length;a++){
              let temp = treeList[a];
              temp.isLeaf = temp.leaf;
              that.treeData.push(temp);
            }
          }
        });
      },
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.loadTree();
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'parentId'))
        })
      },
      //渲染流程表单数据
      showFlowData() {
        if (this.formBpm === true) {
          let params = {id: this.formData.dataId};
          getAction(this.url.queryById, params).then((res) => {
            if (res.success) {
              this.edit(res.result);
            }
          });
        }
      },
      submitForm() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据", formData)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      popupCallback(row) {
        this.form.setFieldsValue(pick(row, 'name', 'parentId'))
      },
      handleParentIdChange(value){
        if(!value){
          this.validateStatus="error"
        }else{
          this.validateStatus="success"
        }
      },
      onChangeMenuType(e) {
        console.log('localMenuType checked', e.target.value)
        this.localMenuType = e.target.value
        if (e.target.value == 1) {
          this.show = true;
        } else {
          this.show = false;
        }
        this.$nextTick(() => {
          this.form.validateFields(['name'], { force: true });
        });
      }
    }
  }
</script>