<template>
  <page-layout title="订单名称">
    <a-layout>
      <a-layout-sider :span="6" style="background-color: #ffffff">
        <a-menu
          :default-selected-keys="['日报']"
          :open-keys.sync="menu.openKeys"
          mode="inline"
          @click="handleClick"
        >
          <a-sub-menu key="common" @titleClick="titleClick">
            <span slot="title"><span>基本报告</span></span>
            <a-menu-item key="日报">
              日报
            </a-menu-item>
            <a-menu-item key="策略">
              策略
            </a-menu-item>
            <a-menu-item key="创意">
              创意
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="resource" @titleClick="titleClick">
            <span slot="title"><span>资源报告</span></span>
            <a-menu-item key="平台">
              平台
            </a-menu-item>
            <a-menu-item key="媒体">
              媒体
            </a-menu-item>
            <a-menu-item key="广告位">
              广告位
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout :span="16" style="margin-left: 10px">
        <a-layout-content>
          <a-card :title="menu.currentMenu">
            <a-range-picker name="buildTime" style="float: right" @change="changeRange"/>
            <bar-and-line :height="chart.height" style="width:100%" :span="16"/>
            <p style="text-align: center">
              <a-checkbox-group v-model="checkBox.checkedList" :options="checkBox.options" @change="onChange"/>
            </p>
          </a-card>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </page-layout>
</template>

<script>
  import PageLayout from '@/components/page/PageLayout';
  import BarAndLine from '@/components/chart/BarAndLine'


  export default {
    name: "DspRptCampaign",
    components: {
      BarAndLine,
      PageLayout,
    },
    data() {
      return {
        menu:{
          openKeys: ['common', 'resource'],
          currentMenu: "日报",
        },
        chart:{
          height: 500,
        },
        checkBox: {
          checkedList: ['pv', 'click'],
          options: [
            {value: 'pv', label: "展示"},
            {value: 'click', label: "点击"},
            {value: 'cv', label: "转化"},
            {value: 'deep_cv', label: "深度转化"},
            {value: 'ctr', label: "点击率"},
            {value: 'cvr', label: "转化率"},
            {value: 'deep_cvr', label: "深度转化率"}
          ],
        },
      };
    },
    watch: {
      openKeys(val) {
        console.log('openKeys', val);
      },
    },
    methods: {
      handleClick(e) {
        console.log('click', e);
        this.menu.currentMenu = e.key
      },
      titleClick(e) {
        console.log('titleClick', e);
      },
      onChange(checkedList) {
        if (checkedList) {
          if (checkedList.length > 2) {
            this.checkBox.checkedList = checkedList.slice(-2)
            this.$message.error("只能选择两个");
          } else {
            this.checkBox.checkedList = checkedList
          }
        }
      },
      changeRange(moments,strDate){

      }
    },

  }
</script>

<style scoped>
  @import '~@assets/less/common.less';
</style>