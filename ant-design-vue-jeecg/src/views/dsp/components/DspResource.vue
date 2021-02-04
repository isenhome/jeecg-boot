<template>
  <a-card :title="getTitle">
    <a-form :form="form">
      <a-col :span="24">
        <a-form-item label="资源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group
            :default-value="'ADX'"
            :options="resourceType.options"
            v-decorator="['resourceType',validatorRules.resourceType]"
            @change="changeResourceType"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24" v-if="resourceType.value === 'ADX'">
        <a-form-item label="平台" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-tags
            @change="changePlatforms"
            :mode="'multiple'"
            :datasource="platformDatasource"
            :selected-ids="model.platformIds"
            :placeholder="'选择平台'"
            v-decorator="['platformIds',validatorRules.platformIds]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24" v-if="resourceType.value === 'ADX'">
        <a-form-item label="广告形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-tags
            @change="changeAdFormats"
            :mode="'multiple'"
            :datasource="adformatDatasource"
            :selected-ids="model.adFormatIds"
            :placeholder="'选择广告形式'"
            v-decorator="['adFormatIds',validatorRules.adFormatIds]"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24" v-if="resourceType.value === 'ADN'">
        <a-form-item label="广告位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-selector
            :multi="true"
            :ids="model.adspaceIds"
            :names="model.adspaceNames"
            :title="'选择广告位'"
            :query-url="'/dsp/dspAdspace/list'"
            v-decorator="['adspaceIds',validatorRules.adspaceIds]"
            @change="adspaceChange"
          ></dsp-selector>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="创意" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dsp-selector
            :multi="true"
            :ids="model.creativeIds"
            :names="model.creativeNames"
            :title="'选择创意'"
            :query-url="'/dsp/dspCreative/list'"
            v-decorator="['creativeIds',validatorRules.creativeIds]"
            @change="creativeChange"
          ></dsp-selector>
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
    import {validateCheckRule} from '@/utils/util';
    import DspSelector from "./DspSelector";
    import {httpAction, getAction} from '@/api/manage';
    import {load} from "../utils/api";
    import DspTags from "./DspTags";
    import pick from 'lodash.pick'

    export default {
        name: "DspResrouce",
        components: {
            DspSelector,
            DspTags
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
                    adFormatIds: {
                        rules: [
                            {required: true, message: '请选择广告形式!'},
                        ]
                    },
                    adspaceIds: {
                        rules: [
                            {required: true, message: '请选择广告位!'},
                        ]
                    },
                    creativeIds: {
                        rules: [
                            {required: true, message: '请选择创意!'},
                        ]
                    }
                },
                resourceType: {
                    value: 'ADX',
                    options: [
                        {label: 'ADX', value: 'ADX'},
                        {label: 'ADN', value: 'ADN'}
                    ]
                },
                platformDatasource: {},
                adformatDatasource: {}
            }
        },
        methods: {
            changePlatforms(ids) {
                this.model.platformIds = ids.split(",");
            },
            changeAdFormats(ids) {
                this.model.adFormatIds = ids.split(",");
            },
            adspaceChange(ids, names) {
                this.model.adspaceIds = ids;
                this.model.adspaceNames = names;
            },
            creativeChange(ids, names) {
                this.model.creativeIds = ids;
                this.model.creativeNames = names;
            },
            changeResourceType(e) {
                this.model.resourceType = e.target.value;
                this.resourceType.value = e.target.value;
            },
            loadData() {
                this.platformDatasource = load("/dsp/dspPlatform/list")
                this.adformatDatasource = load("/dsp/dspAdFormat/list")
            },
            submitForm() {
                const that = this;
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true;
                        let httpurl = '/dsp/dspStrategy/resource';
                        let method = 'post';
                        let tmpData = Object.assign(this.model, values)
                        let formData = {
                            strategyId: that.strategy.id,
                            data: JSON.stringify(tmpData)
                        };
                        console.log("表单提交数据", formData)
                        httpAction(httpurl, formData, method).then((res) => {
                            if (res.success) {
                                that.$message.success(res.message);
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
            this.loadData()
            this.form.resetFields();
            if (this.formData) {
                this.model = Object.assign({}, this.formData);
                if (this.model.resourceType) {
                    this.resourceType.value = this.model.resourceType
                }
            }
            this.$nextTick(() => {
                this.form.setFieldsValue(pick(this.model, 'resourceType', 'platformIds', 'adFormatIds', 'adspaceIds', 'creativeIds'))
            })
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