<template>
  <div>
    <a-input-search
      v-model="industryNames"
      placeholder="请先选择行业"
      readOnly
      unselectable="on"
      @search="onSearchIndustry">
      <a-button slot="enterButton" :disabled="disabled">选择行业</a-button>
    </a-input-search>
    <dsp-advertiser-selector-model ref="selectModal" :modal-width="modalWidth" :multi="multi" @ok="selectOK"
                                   :ids="value" @initComp="initComp">

    </dsp-advertiser-selector-model>
  </div>
</template>

<script>
    export default {
        name: "DspIndustrySelector"
    }
</script>

<style scoped>

</style>