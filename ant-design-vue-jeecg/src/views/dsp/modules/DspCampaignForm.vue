<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name']" placeholder="请输入媒体名称"></a-input>
            </a-form-item>
          </a-col>
          <!--  通过部门选择用户控件 -->
          <a-col :span="24">
            <a-form-item label="广告主" :labelCol="labelCol" :wrapperCol="wrapperCol" :required="true">
              <dsp-selector
                :multi="false"
                :ids="model.advertiserId"
                :names="model.advertiserName"
                :title="'选择广告主'"
                :query-url="'/dsp/dspAdvertiser/list'"
                v-decorator="['advertiserId',validatorRules.advertiserId]"
                @change="advertiserChange"
              ></dsp-selector>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="行业编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-tree-select
                v-decorator="['industryId', validatorRules.industryId]"
                placeholder="请选择行业编号"
                :dropdownStyle="{ maxHeight: '200px', overflow: 'auto' }"
                :treeData="industryTreeData"
                v-model="model.industryId"
                :disabled="false"
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开始时间" v-decorator="['start', validatorRules.start]" :trigger-change="true"
                      style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结束时间" v-decorator="['end']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="预算" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['limitCost', validatorRules.limitCost]" placeholder="请输入预算"
                              style="width: 100%"/>
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
    import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

    export default {
        name: 'DspCampaignForm',
        components: {
            JFormContainer,
            JDate,
            JSelectUserByDep,
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
            }
        },
        data() {
            return {
                form: this.$form.createForm(this),
                model: {},
                industryTreeData: [],
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
                    industryId: {
                        rules: [
                            {required: true, message: '请输入行业编号!'},
                        ]
                    },
                    start: {
                        rules: [
                            {required: true, message: '请输入开始时间!'},
                        ]
                    },
                    limitCost: {
                        rules: [
                            {required: true, message: '请输入预算!'},
                        ]
                    },
                    advertiserId: {
                        rules: [
                            {required: true, message: '请选择广告主!'},
                        ]
                    }
                },
                url: {
                    add: "/dsp/dspCampaign/add",
                    edit: "/dsp/dspCampaign/edit",
                    queryById: "/dsp/dspCampaign/queryById",
                    industryTree: "/dsp/dspIndustry/queryTreeList"
                }
            }
        },
        created() {
            let that = this
            getAction(this.url.industryTree, null).then((res) => {
                that.industryTreeData = [];
                let treeList = res.result.treeList
                for (let a = 0; a < treeList.length; a++) {
                    let temp = treeList[a];
                    temp.isLeaf = temp.leaf;
                    that.industryTreeData.push(temp);
                }
            })
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
        methods: {
            add() {
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.visible = true;
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model, 'name', 'advertiserId', 'industryId', 'industryName', 'start', 'end', 'comment', 'limitCost'))
                })
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
                this.form.setFieldsValue(pick(row, 'name', 'advertiserId', 'industryId', 'industryName', 'start', 'end', 'comment', 'limitCost'))
            },
            advertiserChange(ids, names) {
                this.model.advertiserId = ids;
                this.model.advertiserName = names;
            }
        }
    }
</script>