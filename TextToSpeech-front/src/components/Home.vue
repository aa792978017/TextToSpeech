<template>
  <el-container class="main-container">
    <el-header class="header">AI 工具箱</el-header>
    <el-container>
      <el-aside class="aside" width="12%" height="100%">
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          background-color="#FFFFFF"
          text-color="#252b3a"
          active-text-color="#464c59">
          <el-menu-item index="1">
            <i class="el-icon-document"></i>
            <span slot="title" class="menu-title">文本转语音工具</span>
          </el-menu-item>
          <el-menu-item index="2" disabled>
            <i class="el-icon-document"></i>
            <span slot="title" class="menu-title">AI 绘画工具</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main">
        <router-view/>
      </el-main>
    </el-container>
    <el-footer class="footer">
      Copyright © 2022 Xiao Wang No Bug - All rights reserved 粤ICP备xxxx号
    </el-footer>
  </el-container>
</template>
<style scoped>
.main-container{
  height: 100%;
  width: 100%;
}

.header, .footer {
  background: #464c59;
  border-color: #464c59;
  color: #ffffff;
  text-align: center;
  line-height: 60px;
  height: 8% !important;
  width: 100%;
}
.header{
  font-size: 30px;
}
.aside {
  background-color: #545c64;
  color: #333;
  text-align: left;
}

.main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 150px;
  height: 100%;
}
.menu-title{
  font-size: 13px;
}
</style>

<script>
export default {
  name: 'Home',
  data () {
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
