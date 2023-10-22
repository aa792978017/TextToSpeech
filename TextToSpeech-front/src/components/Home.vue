<template>
  <el-container class="main-container">
    <el-header class="header" >
      <el-row>
        <el-col :span="3" class="header-title"><span>AI 工具箱</span></el-col>
        <el-col :span="6" :offset="15" class="header-msg"><span>注：工具问题或建议联系管理员，微信：Cang5271</span></el-col>
      </el-row>
    </el-header>
    <el-container class="main-body">
      <el-aside class="aside">
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
  position: relative;
}
.header, .footer {
  background: #464c59;
  border-color: #464c59;
  color: #ffffff;
  text-align: center;
  width: 100%;
}
.footer {
  position: absolute;
  top: 100%;
  transform: translateY(-102%);
  height: 3% !important;
}
.header{
  padding-top: 0.5%;
  height: 7% !important;
}
.header-title{
  font-size: 30px;
  text-align: left;
}
.header-msg{
  padding-top: 2%;
  font-size: 10px;
}
.main-body{
  height: 90%;
}
.aside {
  width: 12% !important;
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
  width: 100%;
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
