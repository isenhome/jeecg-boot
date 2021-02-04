<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="创意名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name',validatorRules.name]" placeholder="请输入创意名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创意形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag
                placeholder="请选择创意形式"
                dictCode="dsp_ad_format,name,id"
                v-decorator="['adFormatId',validatorRules.adFormatId]"
                triggerChange
                @change="changeAdFormat"
              />
<!--              <dsp-tags-->
<!--                @change="changeAdFormat"-->
<!--                :datasource="adFormatDatasource"-->
<!--                :selected-ids="getModelAdFormat"-->
<!--                :placeholder="'请选择创意形式'"-->
<!--                v-decorator="['adFormatId',validatorRules.adFormatId]"-->
<!--              ></dsp-tags>-->
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="选择素材" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-selector
                :multi="true"
                :ids="model.materialIds"
                :names="model.materialNames"
                :title="'选择素材'"
                :query-url="'/dsp/dspMaterial/list'"
                :query-params="getMaterialQueryParams"
                v-decorator="['materialIds',validatorRules.materialIds]"
                @change="materialChange"
              ></dsp-selector>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="交互方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                v-model="interactionBox.value"
                :options="interactionBox.options"
                v-decorator="['interactionType', validatorRules.interactionType]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="目标地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['targetUrl',validatorRules.targetUrl]" placeholder="请输入链接跳转地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="DeepLink" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deepLink']" placeholder="请输入链接跳转地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="展示监测" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['pvMonitor']" placeholder="请输入展示监测"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点击监测" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['clickMonitor']" placeholder="请输入点击监测"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
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
    import {load} from "../utils/api";
    import DspTags from "../components/DspTags";
    import DspSelector from "../components/DspSelector";
    import JDictSelectTag from "../../../components/dict/JDictSelectTag";

    export default {
        name: 'DspCreativeForm',
        components: {
            JFormContainer,
            JDate,
            DspTags,
            DspSelector
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
                adFormatDatasource: [],
                adFormatSelectIds: [],
                interactionBox: {
                    value: '链接',
                    options: [
                        {label: '链接', value: '链接'},
                        {label: '下载', value: '下载'}
                    ]
                },
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
                        rules: [
                            {required: true, message: '请输入名称!'},
                        ]
                    },
                    adFormatId: {
                        rules: [
                            {required: true, message: '请输入创意形式!'},
                        ]
                    },
                    interactionType: {
                        rules: [
                            {required: true, message: '请输入交互方式!'},
                        ]
                    },
                    targetUrl: {
                        rules: [
                            {required: true, message: '请输入目标地址!'},
                        ]
                    },
                    materialIds: {
                        rules: [
                            {required: true, message: '请选择素材!'},
                        ]
                    }
                },
                url: {
                    add: "/dsp/dspCreative/add",
                    edit: "/dsp/dspCreative/edit",
                    queryById: "/dsp/dspCreative/queryById"
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
            getMaterialQueryParams() {
                return {advertiserId: this.campaign.advertiserId}
            },
            getModelAdFormat() {
                let adFormatSelectIds = []
                if (this.model.adFormatId) {
                    adFormatSelectIds = this.model.adFormatId.split(',')
                }
                return adFormatSelectIds
            }
        },
        created() {
            //如果是流程中表单，则需要加载流程表单data
            this.showFlowData();
            this.adFormatDatasource = load("/dsp/dspAdFormat/list")
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
                    this.form.setFieldsValue(pick(this.model, 'name', 'advertiserId', 'campaignId', 'adFormatId', 'materialIds','materialNames', 'comment', 'interactionType', 'targetUrl', 'deepLink', 'pvMonitor', 'clickMonitor'))
                })
                console.log(this.model)
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
                let extFormData = {campaignId: that.campaign.id, advertiserId: that.campaign.advertiserId}
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
                        let formData = Object.assign(this.model, values, extFormData);
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
                this.form.setFieldsValue(pick(row, 'name', 'advertiserId', 'campaignId', 'adFormatId', 'materialIds','materialNames', 'comment', 'interactionType', 'targetUrl', 'deepLink', 'pvMonitor', 'clickMonitor'))
            },
            changeAdFormat(ids) {
                this.model.adFormatId = ids;
            },
            materialChange(ids, names) {
                this.model.materialIds = ids;
                this.model.materialNames = names;
            }
        }
    }
</script>