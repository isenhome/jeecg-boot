<template>
  <div>
  <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit">
    <a-form-item label="客户名称">
      <a-input
        v-decorator="['name', { rules: [{ required: true}] }]"
      />
    </a-form-item>
    <a-form-item label="供应商名称">
      <a-input
        v-decorator="['applier', { rules: [{ required: true}] }]"
      />
    </a-form-item>
    <a-form-item label="联系人姓名">
      <a-input
        v-decorator="['contact_name', { rules: [{ required: true}] }]"
      />
    </a-form-item>
    <a-form-item label="联系人手机">
      <a-input
        v-decorator="['contact_phone', { rules: [{ required: false}] }]"
      />
    </a-form-item>
    <a-form-item label="联系人邮箱">
      <a-input
        v-decorator="['contact_mail', { rules: [{ required: false}] }]"
      />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
      <a-button type="primary" html-type="submit">
        提交
      </a-button>
    </a-form-item>
  </a-form>
  </div>
</template>

<script>

  import {getAction} from "../../api/manage";

  export default {
        data() {
            return {
                title:"",
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            };
        },
        methods: {
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        console.log('Received values of form: ', values);
                    }
                });
            },
            handleSelectChange(value) {
                console.log(value);
                this.form.setFieldsValue({
                    note: `Hi, ${value === 'male' ? 'man' : 'lady'}!`,
                });
            },
        },
        created() {
            getAction("/dsp/customer/edit").then((res)=>{
                console.log(res)
                this.title = res.result
            })
        }
    };
</script>