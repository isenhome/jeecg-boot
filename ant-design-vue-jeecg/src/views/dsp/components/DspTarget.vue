<template>
  <a-card :title="getTitle">
    <a-form :form="form">
      <a-col :span="24">
        <a-form-item label="小时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-checkbox-group
            v-model="model.hour"
            :options="hourOption"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="星期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-checkbox-group
            v-model="model.week"
            :options="weekOption"
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="地域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-tree-select
            v-model="model.geo"
            placeholder="请选择地域"
            dict="dsp_geo,name,id"
            pidField="parent_id"
            pidValue=""
            multiple
          />
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.gender" title="性别" dictCode="dsp_gender" placeholder="请选择性别"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.age" title="年龄" dictCode="dsp_age" placeholder="请选择年龄"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.education" title="学历" dictCode="dsp_education" placeholder="请选择学历"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="婚恋" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.marriage" title="婚恋" dictCode="dsp_marriage" placeholder="请选择婚恋"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="收入" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.income" title="收入" dictCode="dsp_income" placeholder="请选择收入"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="操作系统" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.os" title="操作系统" dictCode="dsp_os" placeholder="请选择操作系统"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="上网方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.conn" title="上网方式" dictCode="dsp_conn" placeholder="请选择上网方式"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="运营商" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-model="model.operator" title="运营商" dictCode="dsp_operator" placeholder="请选择运营商"/>
        </a-form-item>
      </a-col>
      <a-col :span="24">
        <a-form-item label="商业兴趣" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-tree-select
            v-model="model.interest"
            placeholder="请选择菜单"
            dict="dsp_interest,name,id"
            pidField="parent_id"
            pidValue=""
            multiple
          />
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
    import JDictSelectTag from "../../../components/dict/JDictSelectTag";
    import JTreeSelect from "../../../components/jeecg/JTreeSelect";
    import JMultiSelectTag from "../../../components/dict/JMultiSelectTag";
    import JCategorySelect from "../../../components/jeecg/JCategorySelect";
    import pick from 'lodash.pick'
    import {httpAction} from '@/api/manage'


    export default {
        name: "DspCreative",
        comments: {
            JDictSelectTag,
            JTreeSelect,
            JMultiSelectTag,
            JCategorySelect
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
                hourOption: [
                    {label: '00', value: '0'},
                    {label: '01', value: '1'},
                    {label: '02', value: '2'},
                    {label: '03', value: '3'},
                    {label: '04', value: '4'},
                    {label: '05', value: '5'},
                    {label: '06', value: '6'},
                    {label: '07', value: '7'},
                    {label: '08', value: '8'},
                    {label: '09', value: '9'},
                    {label: '10', value: '10'},
                    {label: '11', value: '11'},
                    {label: '12', value: '12'},
                    {label: '13', value: '13'},
                    {label: '14', value: '14'},
                    {label: '15', value: '15'},
                    {label: '16', value: '16'},
                    {label: '17', value: '17'},
                    {label: '18', value: '18'},
                    {label: '19', value: '19'},
                    {label: '20', value: '20'},
                    {label: '21', value: '21'},
                    {label: '22', value: '22'},
                    {label: '23', value: '23'}
                ],
                weekOption: [
                    {label: '星期一', value: 'mon'},
                    {label: '星期二', value: 'tues'},
                    {label: '星期三', value: 'wed'},
                    {label: '星期四', value: 'thur'},
                    {label: '星期五', value: 'fri'},
                    {label: '星期六', value: 'sta'},
                    {label: '星期日', value: 'sun'}
                ]
            }
        },
        methods: {
            closeForm() {
                this.$emit('close');
            },
            submitForm() {
                const that = this;
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true;
                        let httpurl = '/dsp/dspStrategy/target';
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
                                that.$emit("change",formData.data)
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
            this.form.resetFields();
            this.model = Object.assign({}, this.formData);
            this.$nextTick(() => {
                this.form.setFieldsValue(pick(this.model, 'hour', 'week', 'geo', 'gender','age','education','marriage','income','os','conn','operator','interest'))
            })
        },
        computed: {
            getTitle() {
                return "定向（" + this.strategy.name + ")"
            }
        }

    }
</script>

<style scoped>

</style>