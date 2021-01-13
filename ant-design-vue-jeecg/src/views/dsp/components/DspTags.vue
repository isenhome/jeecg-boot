<template>
  <a-select
    mode="mode"
    :placeholder="placeholder"
    :value="getNames"
    style="width: 100%"
    @change="handleChange"
  >
    <a-select-option v-for="item in filteredOptions" :key="item.key" :title="item.value" :value="item.value">
      {{
      item.value
      }}
    </a-select-option>
  </a-select>
</template>

<script>

    export default {
        name: "DspTags",
        props: {
            placeholder: {
                type: String,
                default: "请输入",
                required: true
            },
            mode: {
                type: String,
                default: "default",
                required: false
            },
            selectedIds: {
                type: Array,
                required: false
            },
            datasource: {
                type: Array,
                required: true,
                default: () => {
                    return []
                }
            }
        },
        data() {
            return {};
        },
        computed: {
            getNames() {
                let names = []
                if (this.selectedIds) {
                    for (let i in this.datasource) {
                        let o = this.datasource[i]
                        if (this.selectedIds.includes(o.key)) {
                            names.push(o.value)
                        }
                    }
                }
                return names
            },
            filteredOptions() {
                if (this.selectedIds) {
                    return this.datasource.filter(o => !this.selectedIds.includes(o))
                } else {
                    return this.datasource
                }
            },
        },
        methods: {
            handleChange(values, selectedItems) {
                let ids = []
                if (selectedItems.key) {
                    ids.push(selectedItems.key)
                } else {
                    for (let i in selectedItems) {
                        ids.push(selectedItems[i].key)
                    }
                }
                this.$emit("change", ids.join(","))
            }
        }
    }
</script>

<style scoped>

</style>