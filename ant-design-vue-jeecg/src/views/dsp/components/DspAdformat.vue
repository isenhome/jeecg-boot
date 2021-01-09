<template>
  <a-select
    mode="multiple"
    :placeholder="placeholder"
    :value="getNames"
    style="width: 100%"
    @change="handleChange"
  >
    <a-select-option v-for="item in filteredOptions" :key="item.key" :value="item.value">
      {{ item.value }}
    </a-select-option>
  </a-select>
</template>

<script>

    export default {
        name: "DspAdformat",
        props: {
            placeholder: {
                type: String,
                default: "请输入",
                required: true
            },
            selectedIds: {
                type: Array,
                required: false
            },
            datasource: {
                type: Array,
                required: true
            }
        },
        data() {
            return {};
        },
        computed: {
            getNames() {
                let names = []
                if (this.selectedIds) {
                    for (let o in this.datasource) {
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
            handleChange(selectedItems) {
                this.$emit("change", selectedItems)
            }
        }
    }
</script>

<style scoped>

</style>