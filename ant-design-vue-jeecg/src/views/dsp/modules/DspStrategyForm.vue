<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-card title="基础信息">
            <a-col :span="24">
              <a-form-item label="策略名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['name']" placeholder="请输入策略名称"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-date placeholder="请选择开始时间" v-decorator="['start', validatorRules.start]" :trigger-change="true"
                        :span="12"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-date placeholder="请选择结束时间" v-decorator="['end']" :trigger-change="true" :span="12"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
              </a-form-item>
            </a-col>
          </a-card>
        </a-row>
        <a-row>
          <a-card title="价格">
            <a-col :span="24">
              <a-form-item label="出价方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group
                  v-model="radioBuyType.value"
                  :options="radioBuyType.options"
                  v-decorator="['buyType',validatorRules.buyType]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="最低价" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number v-decorator="['buyMinBidprice', validatorRules.buyMinBidprice]" placeholder="最低价"
                                style="width: 50%" :step="0.1"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="最高价" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number v-decorator="['buyMaxBidprice', validatorRules.buyMaxBidprice]" placeholder="最高价"
                                style="width: 50%" :step="0.1"/>
              </a-form-item>
            </a-col>
          </a-card>
        </a-row>
        <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
          <a-button @click="submitForm">提 交</a-button>
        </a-col>
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
            },
            campaign: {
                type: Object,
                default: () => {
                },
                required: true
            }

        },
        data() {
            return {
                form: this.$form.createForm(this),
                model: {},
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 16},
                },
                confirmLoading: false,
                validatorRules: {
                    start: {
                        rules: [
                            {required: true, message: '请输入开始时间!'},
                        ]
                    },
                    buyType: {
                        rules: [
                            {required: true, message: '请输入出价方式!'},
                        ]
                    },
                    buyMaxBidprice: {
                        rules: [
                            {required: true, message: '请输入最高价格!'},
                        ]
                    },
                    buyMinBidprice: {
                        rules: [
                            {required: false, message: '请输入最低价格!'},
                        ]
                    }
                },
                url: {
                    add: "/dsp/dspStrategy/add",
                    edit: "/dsp/dspStrategy/edit",
                    queryById: "/dsp/dspStrategy/queryById"
                },
                radioBuyType: {
                    value: "CPM",
                    options: [
                        {label: 'CPM', value: 'CPM'},
                        {label: 'CPC', value: 'CPC'},
                        {label: 'OCPA', value: 'OCPA'},
                        {label: 'OCPM', value: 'OCPM'}
                    ]
                },
                radioExcuteType: {
                    value: "fast",
                    options: [
                        {label: '越快越好', value: 'fast'},
                        {label: '平均投放', value: 'average'}
                    ]
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
            }
        },
        created() {
            //如果是流程中表单，则需要加载流程表单data
            this.showFlowData();
        },
        methods: {
            add() {
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.visible = true;
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'name', 'advertiserId', 'start', 'end', 'comment'))
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
                        formData.campaignId = that.campaign.id;
                        formData.advertiserId = that.campaign.advertiserId;
                        console.log("表单提交数据", formData);
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
                this.form.setFieldsValue(pick(row, 'name', 'advertiserId', 'start', 'end', 'comment'))
            },
        }
    }
</script>