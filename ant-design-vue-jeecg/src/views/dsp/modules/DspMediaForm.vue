<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="媒体名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name',validatorRules.name]" placeholder="请输入媒体名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="平台编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-selector
                :multi="false"
                :ids="model.platformId"
                :names="model.platformName"
                :title="'选择平台'"
                :query-url="'/dsp/dspPlatform/list'"
                v-decorator="['platformId',validatorRules.platformId]"
                @change="platformChange"
              ></dsp-selector>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="简介" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['intro']" rows="4" placeholder="请输入简介"/>
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
    import DspSelector from "../components/DspSelector";

    export default {
        name: 'DspMediaForm',
        components: {
            DspSelector,
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
                    name: {
                        rules: [{required: true, message: "请输入名称"}]
                    },
                    platformId: {
                        rules: [{required: true, message: "请选择平台"}]
                    }
                },
                url: {
                    add: "/dsp/dspMedia/add",
                    edit: "/dsp/dspMedia/edit",
                    queryById: "/dsp/dspMedia/queryById"
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
                    this.form.setFieldsValue(pick(this.model,'name', 'platformId', 'intro'))
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
                this.form.setFieldsValue(pick(row, 'name', 'platformId', 'intro'))
            },
            platformChange(ids, names) {
              this.model.platformId = ids;
              this.model.platformName = names;
            }
        }
    }
</script>