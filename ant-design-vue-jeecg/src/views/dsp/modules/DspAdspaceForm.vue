<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="媒体名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name']" placeholder="请输入媒体名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="媒体编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mediaId']" placeholder="请输入媒体编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <dsp-ad-format
                @change="changeAdformat"
                :datasource="adFormatDatasource"
                :placeholder="'请选择广告形式'"
              ></dsp-ad-format>
              <a-input v-decorator="['adFormatId']" placeholder="请输入广告形式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="广告尺寸" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['adRadioId']" placeholder="请输入广告尺寸"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="售卖方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sellType', validatorRules.sellType]" placeholder="请输入售卖方式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="交互方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['interaction', validatorRules.interaction]" rows="4" placeholder="请输入交互方式"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="图片格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['imageType']" rows="4" placeholder="请输入图片格式"/>
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
    import DspAdFormat from "../components/DspAdFormat";

    export default {
        name: 'DspAdspaceForm',
        components: {
            JFormContainer,
            JDate,
            DspAdFormat
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
              adFormatDatasource:[],
                validatorRules: {
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
                },
                url: {
                    add: "/dsp/dspAdspace/add",
                    edit: "/dsp/dspAdspace/edit",
                    queryById: "/dsp/dspAdspace/queryById",
                    listAdFormat:"/dsp/dspAdFormat/list"
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
            let that = this;
            getAction(this.url.listAdFormat,null).then((res)=>{
              that.adformatDatasource = [];
              let treeList = res.result.treeList
              for (let a = 0; a < treeList.length; a++) {
                let temp = treeList[a];
                temp.isLeaf = temp.leaf;
                that.adformatDatasource.push(temp);
              }
          })
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
                    this.form.setFieldsValue(pick(this.model,  'name', 'platformId', 'mediaId', 'adFormatId', 'adRadioId', 'comment', 'sellType', 'interaction', 'imageType'))
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
                this.form.setFieldsValue(pick(row, 'name', 'platformId', 'mediaId', 'adFormatId', 'adRadioId', 'comment', 'sellType', 'interaction', 'imageType'))
            },
            changeAdformat(ids,names){

            }
        }
    }
</script>