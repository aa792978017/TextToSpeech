<template>
  <div id="app" >
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/*清除浏览器默认样式，使其贴紧浏览器边缘，全屏显示*/
html,body{
  margin: 0px;
  height: 100%;
  width: 100%;
}
#app{
  margin: 0px;
  height: 100%;
  width: 100%;
}
.el-container.is-vertical {
  overflow: auto;
}
</style>
