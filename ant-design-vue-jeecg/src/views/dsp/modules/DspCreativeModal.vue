<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <dsp-creative-form ref="realForm" :campaign="campaign" @ok="submitCallback" :disabled="disableSubmit"></dsp-creative-form>
  </j-modal>
</template>

<script>

    import DspCreativeForm from './DspCreativeForm'

    export default {
        name: 'DspCreativeModal',
        components: {
            DspCreativeForm
        },
        props: {
            campaign: {
                type: Object,
                default: () => {
                },
                required: true
            }
        },
        data() {
            return {
                title: '',
                width: 800,
                visible: false,
                disableSubmit: false
            }
        },
        methods: {
            add() {
                this.visible = true
                this.$nextTick(() => {
                    this.$refs.realForm.add();
                })
            },
            edit(record) {
                this.visible = true
                this.$nextTick(() => {
                    this.$refs.realForm.edit(record);
                })
            },
            close() {
                this.$emit('close');
                this.visible = false;
            },
            handleOk() {
                this.$refs.realForm.submitForm();
            },
            submitCallback() {
                this.$emit('ok');
                this.visible = false;
            },
            handleCancel() {
                this.close()
            }
        }
    }
</script>