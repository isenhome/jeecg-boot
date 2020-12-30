<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建日期" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入更新人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新日期" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sysOrgCode']" placeholder="请输入所属部门"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态 1:有效,-1:删除," :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['status']" placeholder="请输入状态 1:有效,-1:删除," style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="策略名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name']" placeholder="请输入策略名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告主编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['advertiserId']" placeholder="请输入广告主编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开始时间" v-decorator="['start', validatorRules.start]" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结束时间" v-decorator="['end']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="出价方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['buyType', validatorRules.buyType]" placeholder="请输入出价方式"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="最高价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['buyMaxBidprice', validatorRules.buyMaxBidprice]" placeholder="请输入最高价格" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="最低价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['buyMinBidprice', validatorRules.buyMinBidprice]" placeholder="请输入最低价格" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="消耗日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['buyDailyLimit', validatorRules.buyDailyLimit]" placeholder="请输入消耗日限" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="展示日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['pvLimitDaily', validatorRules.pvLimitDaily]" placeholder="请输入展示日限" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点击日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['clickLimitDaily', validatorRules.clickLimitDaily]" placeholder="请输入点击日限" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="投放速度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['excuteType', validatorRules.excuteType]" placeholder="请输入投放速度"  ></a-input>
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
    name: 'DspStrategyForm',
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
          start: {
            rules: [
              { required: true, message: '请输入开始时间!'},
            ]
          },
          buyType: {
            rules: [
              { required: true, message: '请输入出价方式!'},
            ]
          },
          buyMaxBidprice: {
            rules: [
              { required: true, message: '请输入最高价格!'},
            ]
          },
          buyMinBidprice: {
            rules: [
              { required: true, message: '请输入最低价格!'},
            ]
          },
          buyDailyLimit: {
            rules: [
              { required: true, message: '请输入消耗日限!'},
            ]
          },
          pvLimitDaily: {
            rules: [
              { required: true, message: '请输入展示日限!'},
            ]
          },
          clickLimitDaily: {
            rules: [
              { required: true, message: '请输入点击日限!'},
            ]
          },
          excuteType: {
            rules: [
              { required: true, message: '请输入投放速度!'},
            ]
          },
        },
        url: {
          add: "/dsp/dspStrategy/add",
          edit: "/dsp/dspStrategy/edit",
          queryById: "/dsp/dspStrategy/queryById"
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','status','name','advertiserId','start','end','comment','buyType','buyMaxBidprice','buyMinBidprice','buyDailyLimit','pvLimitDaily','clickLimitDaily','excuteType'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','status','name','advertiserId','start','end','comment','buyType','buyMaxBidprice','buyMinBidprice','buyDailyLimit','pvLimitDaily','clickLimitDaily','excuteType'))
      },
    }
  }
</script>