<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name',validatorRules.name]" placeholder="请输入媒体名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="媒体" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-selector
                :multi="false"
                :ids="model.mediaId"
                :names="model.mediaName"
                :title="'请选择媒体'"
                :query-url="url.mediaList"
                v-decorator="['mediaId',validatorRules.mediaId]"
                @change="mediaChange"
              ></dsp-selector>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-tags
                @change="changeAdFormat"
                :datasource="adFormatDatasource"
                :selected-ids="adFormatSelectIds"
                :placeholder="'请选择广告形式'"
                v-decorator="['adFormatId',validatorRules.adFormatId]"
              ></dsp-tags>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告尺寸" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-tags
                @change="changeAdRadio"
                :datasource="adRadioDatasource"
                :selected-ids="adRadioSelectIds"
                :placeholder="'请选择广告尺寸'"
                v-decorator="['adRadioId',validatorRules.adRadioId]"
              ></dsp-tags>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-card title="设置成本">
              <a-form-item label="计费方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group
                  v-model="sellTypeBox.values"
                  :options="sellTypeBox.options"
                  v-decorator="['sellType',validatorRules.sellTypeBox]"
                />
              </a-form-item>
              <a-form-item label="底价" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number :step="0.1" v-decorator="['basePrice',validatorRules.basePrice]" placeholder="请输入底价"></a-input-number>
              </a-form-item>
            </a-card>
          </a-col>
          <a-col :span="24">
            <a-card title="高级设置">
              <a-form-item label="交互方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-checkbox
                  v-model="interactionBox.values"
                  :options="interactionBox.options"
                  v-decorator="['interaction', validatorRules.interaction]"
                />
              </a-form-item>
              <a-form-item label="图片格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-checkbox
                  v-model="imageTypeBox.values"
                  :options="imageTypeBox.options"
                  v-decorator="['imageType',validatorRules.imageType]"
                />
              </a-form-item>
            </a-card>
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
    import DspTags from "../components/DspTags";
    import {load} from "../utils/api";
    import DspSelector from "../components/DspSelector";
    import JCheckbox from '@/components/jeecg/JCheckbox'

    export default {
        name: 'DspAdspaceForm',
        components: {
            JFormContainer,
            JDate,
            DspTags,
            DspSelector,
            JCheckbox
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
                adFormatDatasource: [],
                adFormatSelectIds: [],
                adRadioDatasource: [],
                adRadioSelectIds: [],
                sellTypeBox: {
                    values: "",
                    options: [
                        {label: 'CPM', value: 'CPM'},
                        {label: 'CPC', value: 'CPC'},
                        {label: 'OCPA', value: 'OCPA'},
                        {label: 'OCPM', value: 'OCPM'}
                    ]
                },
                imageTypeBox: {
                    values: "JPG,PNG,GIF",
                    options: [
                        {label: 'JPG', value: 'JPG'},
                        {label: 'PNG', value: 'PNG'},
                        {label: 'GIF', value: 'GIF'}
                    ]
                },
                interactionBox: {
                    values: '链接,下载',
                    options: [
                        {label: '链接', value: '链接'},
                        {label: '下载', value: '下载'}
                    ]
                },
                validatorRules: {
                    name: {
                        rules: [
                            {required: true, message: '请输入名称!'},
                        ]
                    },
                    sellType: {
                        rules: [
                            {required: true, message: '请输入售卖方式!'},
                        ]
                    },
                    interaction: {
                        rules: [
                            {required: true, message: '请输入交互方式!'},
                        ]
                    },
                    adFormatId: {
                        rules: [
                            {required: true, message: '请选择广告形式!'},
                        ]
                    },
                    mediaId: {
                        rules: [
                            {required: true, message: '请选择媒体!'},
                        ]
                    },
                    adRadioId: {
                        rules: [
                            {required: true, message: '请选广告位支持尺寸!'},
                        ]
                    },
                    imageType: {
                        rules: [
                            {required: true, message: '请选广告位支持图片格式!'},
                        ]
                    },
                    sellTypeBox: {
                        rules: [
                            {required: true, message: '请选择计费方式!'},
                        ]
                    },
                    basePrice: {
                        rules: [
                            {required: true, message: '请输入底价!'},
                        ]
                    }
                },
                url: {
                    add: "/dsp/dspAdspace/add",
                    edit: "/dsp/dspAdspace/edit",
                    queryById: "/dsp/dspAdspace/queryById",
                    mediaList: "/dsp/dspMedia/list"
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
            this.adFormatDatasource = load("/dsp/dspAdFormat/list")
            this.adRadioDatasource = load("/dsp/dspAdRadio/list")
            console.log(this.adFormatDatasource)
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
                    this.form.setFieldsValue(pick(this.model, 'name', 'platformId', 'mediaId', 'mediaName', 'adFormatId', 'adRadioId', 'comment', 'sellType', 'basePrice', 'interaction', 'imageType'))
                    this.adFormatSelectIds = this.model.adFormatId.split(",")
                    this.adRadioSelectIds = this.model.adRadioId.split(",")
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
                this.form.setFieldsValue(pick(row, 'name', 'platformId', 'mediaId', 'mediaName', 'adFormatId', 'adRadioId', 'comment', 'sellType','basePrice', 'interaction', 'imageType'))
            },
            mediaChange(ids, names) {
                this.model.mediaId = ids;
                this.model.mediaName = names;
            },
            changeAdFormat(ids) {
                this.adFormatSelectIds = ids.split(",");
                this.model.adFormatId = ids;
            },
            changeAdRadio(ids) {
                this.adRadioSelectIds = ids.split(",");
                this.model.adRadioId = ids;
            }
        }
    }
</script>