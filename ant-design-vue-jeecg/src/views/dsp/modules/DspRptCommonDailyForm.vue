<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="报告日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择reportDate" v-decorator="['reportDate']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['campaignId']" placeholder="请输入campaignId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="策略编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['strategyId']" placeholder="请输入strategyId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创意编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['creativeId']" placeholder="请输入creativeId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="平台编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['platformId']" placeholder="请输入platformId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告位编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['adspaceId']" placeholder="请输入adspaceId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="展示" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['pv']" placeholder="请输入pv" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点击" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['click']" placeholder="请输入click" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="转化" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cv']" placeholder="请输入转化" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="深度转化" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['deepCv']" placeholder="请输入深度转化" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="消耗" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['customerCost']" placeholder="请输入消耗" style="width: 100%" />
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

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'DspRptCommonDailyForm',
    components: {
      JFormContainer,
      JDate,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
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
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/dsp/dspRptCommonDaily/add",
          edit: "/dsp/dspRptCommonDaily/edit",
          queryById: "/dsp/dspRptCommonDaily/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'reportDate','campaignId','strategyId','creativeId','platformId','adspaceId','pv','click','cv','deepCv','customerCost','platformCost'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'reportDate','campaignId','strategyId','creativeId','platformId','adspaceId','pv','click','cv','deepCv','customerCost','platformCost'))
      },
    }
  }
</script>