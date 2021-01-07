<template>
  <j-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    switchFullscreen
    @ok="handleSubmit"
    @cancel="close"
    style="top:50px"
    cancelText="关闭">
    行业:
    <a-input-search
      :style="{width:'450px',marginBottom:'15px'}"
      placeholder="请输入行业名称"
      v-model="queryParam.name"
      @search="onSearch"
    ></a-input-search>
  </j-modal>
</template>

<script>
    export default {
        name: "DspIndustrySelectorModel"
    }
</script>

<style scoped>

</style>