<template>
  <a-card :title="getTitle">
    <a-form :form="form">
      <a-col :span="24">
        <a-form-item label="投放速度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group
            v-model="radioExcuteType.value"
            :options="radioExcuteType.options"
            v-decorator="['excuteType',validatorRules.excuteType]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="消耗日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['buyDailyLimit', validatorRules.buyDailyLimit]"
                          placeholder="请输入消耗日限"
                          style="width: 50%"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="展示日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['pvLimitDaily', validatorRules.pvLimitDaily]"
                          placeholder="请输入展示日限"
                          style="width: 50%"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="点击日限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['clickLimitDaily', validatorRules.clickLimitDaily]"
                          placeholder="请输入点击日限"
                          style="width: 50%"/>
        </a-form-item>
      </a-col>
      <a-col :span="24" style="text-align: center">
        <a-button v-has="'strategy:edit'" @click="submitForm" type="primary">提 交</a-button>
        <a-button @click="closeForm" style="margin-left: 8px;">关 闭</a-button>
      </a-col>
    </a-form>
  </a-card>
</template>

<script>
    import {validateCheckRule} from '@/utils/util'
    import {httpAction} from '@/api/manage'
    import pick from 'lodash.pick'

    export default {
        name: "DspDailyLimit",
        props: {
            formData: {
                type: Object,
                default: () => {
                },
                required: false
            },
            strategy: {
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
                url: {},
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 16},
                },
                validatorRules: {
                    buyDailyLimit: {
                        rules: [
                            {required: true, message: '请输入消耗日限!'},
                        ]
                    },
                    pvLimitDaily: {
                        rules: [
                            {required: true, message: '请输入展示日限!'},
                        ]
                    },
                    clickLimitDaily: {
                        rules: [
                            {required: true, message: '请输入点击日限!'},
                        ]
                    },
                    excuteType: {
                        rules: [
                            {required: true, message: '请输入投放速度!'},
                        ]
                    }
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
        methods: {
            submitForm() {
                const that = this;
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true;
                        let httpurl = '/dsp/dspStrategy/dailyLimit';
                        let method = 'post';
                        let tmpData = Object.assign(this.model, values)
                        let formData = {
                            strategyId: that.strategy.id,
                            data: JSON.stringify(tmpData)
                        };
                        console.log("表单提交数据", formData);
                        httpAction(httpurl, formData, method).then((res) => {
                            if (res.success) {
                                that.$message.success(res.message);
                                that.$emit("change",formData.data)
                            } else {
                                that.$message.warning(res.message);
                            }
                        }).finally(() => {
                            that.confirmLoading = false;
                        })
                    }
                })
            },
            closeForm() {
                this.$emit('close');
            }
        },
        created() {
            this.form.resetFields();
            this.model = Object.assign({}, this.formData);
            this.$nextTick(() => {
                this.form.setFieldsValue(pick(this.model, 'excuteType', 'buyDailyLimit', 'pvLimitDaily', 'clickLimitDaily'))
            })
        },
        computed: {
            getTitle() {
                return "日限（" + this.strategy.name + ")"
            }
        }

    }
</script>

<style scoped>

</style>