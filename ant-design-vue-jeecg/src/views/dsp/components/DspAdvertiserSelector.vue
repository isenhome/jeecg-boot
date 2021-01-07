<template>
  <div>
    <a-input-search
      v-model="getAdvertiserNames"
      placeholder="请先选择用户"
      readOnly
      unselectable="on"
      @search="onSearchAdvertiser">
      <a-button slot="enterButton" :disabled="disabled">选择广告主</a-button>
    </a-input-search>
    <dsp-advertiser-selector-model ref="selectModal" :modal-width="modalWidth" :multi="multi" @ok="selectOK"
                                   :ids="ids" @initComp="initComp">

    </dsp-advertiser-selector-model>
  </div>
</template>

<script>
    import DspAdvertiserSelectorModel from "./DspAdvertiserSelectorModel";

    export default {
        name: "DspAdvertiserSelector",
        components: {DspAdvertiserSelectorModel},
        props: {
            modalWidth: {
                type: Number,
                default: 1250,
                required: false
            },
            ids: {
                type: String,
                required: false
            },
            names: {
                type: String,
                required: false
            },
            disabled: {
                type: Boolean,
                required: false,
                default: false
            },
            multi: {
                type: Boolean,
                default: true,
                required: false
            },

        },
        data() {
            return {
                advertiserNames: "",
                advertiserIds: ""
            }
        },
        computed: {
            getAdvertiserNames: function () {
                return this.names;
            }
        },
        methods: {
            initComp(advertiserNames) {
                this.advertiserNames = advertiserNames
            },
            onSearchAdvertiser() {
                this.$refs.selectModal.showModal()
            },
            selectOK(rows, idstr) {
                if (!rows) {
                    this.advertiserNames = ''
                    this.advertiserIds = ''
                } else {
                    let temp = ''
                    for (let item of rows) {
                        temp += ',' + item.name
                    }
                    this.advertiserNames = temp.substring(1)
                    this.advertiserIds = idstr
                }
                this.$emit("change", this.advertiserIds, this.advertiserNames)
            }
        }
    }
</script>

<style scoped>

</style>