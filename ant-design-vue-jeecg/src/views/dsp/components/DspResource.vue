<template>
  <a-card :title="getTitle">
    <a-form :form="form">
      <a-col :span="24">
        <a-form-item label="资源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group
            v-model="resourceType.value"
            :options="resourceType.options"
            v-decorator="['resourceType',validatorRules.resourceType]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="平台" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-tags
            @change="changePlatform"
            :datasource="platformDatasource"
            :selected-ids="platformIds"
            :placeholder="'选择平台'"
            v-decorator="['platformIds',validatorRules.platformIds]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="广告形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-tags
            @change="changeAdformat"
            :datasource="adformatDatasource"
            :selected-ids="adformatIds"
            :placeholder="'选择广告形式'"
            v-decorator="['adformatIds',validatorRules.adformatIds]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="广告位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-selector
            :multi="false"
            :ids="model.adspaceIds"
            :names="model.adspaceNames"
            :title="'选择广告位'"
            :query-url="'/dsp/dspAdspace/list'"
            v-decorator="['adspaceIds',validatorRules.adspaceIds]"
            @change="advertiserChange"
          ></dsp-selector>
        </a-form-item>
      </a-col>
      <a-col :span="24" style="text-align: center">
        <a-button @click="submitForm">提 交</a-button>
      </a-col>
    </a-form>
  </a-card>
</template>

<script>
    import {validateCheckRule} from '@/utils/util'
    import DspSelector from "./DspSelector";

    export default {
        name: "DspResrouce",
        components:{
            DspSelector
        },
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
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 16},
                },
                validatorRules: {
                    resourceType: {
                        rules: [
                            {required: true, message: '请选择资源类型!'},
                        ]
                    },
                    platformIds: {
                        rules: [
                            {required: true, message: '请选择平台!'},
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
                resourceType: {
                    value: "ADX",
                    options: [
                        {label: 'ADX', value: 'ADX'},
                        {label: 'ADN', value: 'ADN'}
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
            }
        },
        created() {
            console.log(this.strategy)
        },
        computed: {
            getTitle() {
                return "资源（" + this.strategy.name + ")"
            }
        }

    }
</script>

<style scoped>

</style>