<template>
  <div>
    <a-input-search
      v-model="getNames"
      :placeholder="title"
      readOnly
      unselectable="on"
      @search="onSearch">
      <a-button slot="enterButton" :disabled="disabled">选择</a-button>
    </a-input-search>
    <dsp-selector-model ref="selectModal" :query-params="queryParams" :title="title" :query-url="queryUrl" :modal-width="modalWidth" :multi="multi"
                        @ok="selectOK"
                        :ids="ids" @initComp="initComp">

    </dsp-selector-model>
  </div>
</template>

<script>
    import DspSelectorModel from "./DspSelectorModel";

    export default {
        name: "DspSelector",
        components: {DspSelectorModel},
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
                required: false,
                default: ""
            },
            title: {
                type: String,
                required: false,
                default: ""
            },
            queryUrl: {
                type: String,
                required: false,
                default: ""
            },
            queryParams: {
                type: Object,
                required: false,
                default: () => {
                }
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
            getNames: function () {
                return this.names;
            }
        },
        methods: {
            initComp(advertiserNames) {
                this.advertiserNames = advertiserNames
            },
            onSearch() {
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