<template>
  <div class="right di main-wrap" v-loading="audio.waiting">
    <!-- 此处的ref属性，可以很方便的在vue组件中通过 this.$refs.audio获取该dom元素 -->
    <audio ref="audio" class="dn"
           :src="url" :preload="audio.preload"
           @play="onPlay"
           @error="onError"
           @waiting="onWaiting"
           @pause="onPause"
           @timeupdate="onTimeupdate"
           @loadedmetadata="onLoadedmetadata"
    ></audio>

    <div class="w-full">
      <div class="flex items-center w-10/12 mx-auto">
        <!-- 当前时间 -->
        <el-tag type="info">{{ audio.currentTime | formatSecond}}</el-tag>

        <!-- 滚动条 -->
        <el-slider v-show="!controlList.noProcess" v-model="sliderTime" :format-tooltip="formatProcessToolTip" @change="changeCurrentTime" class="slider_time"></el-slider>
        <!-- 总时长 -->
        <el-tag type="info">{{ audio.maxTime | formatSecond }}</el-tag>
      </div>
      <div class="mt-3 flex items-center w-1/2 mx-auto justify-around">
        <!-- 播放/暂停 -->
        <el-button type="text" @click="startPlayOrPause">{{audio.playing | transPlayPause}}</el-button>
        <!-- 快进 -->
        <el-button v-show="!controlList.noSpeed" type="text" @click="changeSpeed">{{audio.speed | transSpeed}}</el-button>
        <!-- 静音 -->
        <el-button v-show="!controlList.noMuted" type="text" @click="startMutedOrNot">{{audio.muted | transMutedOrNot}}</el-button>
        <!-- 音量 -->
        <div class="flex items-center">
          <span class="mr-2 text-sm">音量</span>
          <el-slider v-show="!controlList.noVolume" v-model="volume" :format-tooltip="formatVolumeToolTip" @change="changeVolume" class="slider_voice"></el-slider>
        </div>

        <!-- 下载 -->
        <a :href="url" rel="external nofollow"  v-show="!controlList.noDownload" target="_blank" class="download text-sm" download>下载</a>
      </div>
    </div>
  </div>
</template>

<script>
// 将整数转换成 时：分：秒的格式
function realFormatSecond(second) {
  var secondType = typeof second

  if (secondType === 'number' || secondType === 'string') {
    second = parseInt(second)

    var hours = Math.floor(second / 3600)
    second = second - hours * 3600
    var mimute = Math.floor(second / 60)
    second = second - mimute * 60

    return hours + ':' + ('0' + mimute).slice(-2) + ':' + ('0' + second).slice(-2)
  } else {
    return '0:00:00'
  }
}

export default {
  name: 'Audio',
  props: {
    theSpeeds: {
      type: Array,
      default () {
        return [1, 1.5, 2]
      }
    },
    theControlList: {
      type: String,
      default: ''
    }
  },
  data(){
    return{
      url: 'https://wdd.js.org/element-audio/static/falling-star.mp3',
      audio: {
        currentTime: 0,
        maxTime: 0,
        playing: false,
        muted: false,
        speed: 1,
        waiting: true,
        preload: 'auto'
      },

      sliderTime: 0,
      volume: 100,
      speeds: this.theSpeeds,

      controlList: {
        // 不显示下载
        noDownload: false,
        // 不显示静音
        noMuted: false,
        // 不显示音量条
        noVolume: false,
        // 不显示进度条
        noProcess: false,
        // 只能播放一个
        onlyOnePlaying: false,
        // 不要快进按钮
        noSpeed: false
      }
    }
  },
  methods:{
    setControlList () {
      let controlList = this.theControlList.split(' ')
      controlList.forEach((item) => {
        if(this.controlList[item] !== undefined){
          this.controlList[item] = true
        }
      })
    },
    changeSpeed() {
      let index = this.speeds.indexOf(this.audio.speed) + 1
      this.audio.speed = this.speeds[index % this.speeds.length]
      this.$refs.audio.playbackRate = this.audio.speed
    },
    startMutedOrNot() {
      this.$refs.audio.muted = !this.$refs.audio.muted
      this.audio.muted = this.$refs.audio.muted
    },
    // 音量条toolTip
    formatVolumeToolTip(index) {
      return '音量条: ' + index
    },
    // 进度条toolTip
    formatProcessToolTip(index = 0) {
      index = parseInt(this.audio.maxTime / 100 * index)
      return '进度条: ' + realFormatSecond(index)
    },
    // 音量改变
    changeVolume(index = 0) {
      this.$refs.audio.volume = index / 100
      this.volume = index
    },
    // 播放跳转
    changeCurrentTime(index) {
      this.pausePlay()
      this.$refs.audio.currentTime = parseInt(index / 100 * this.audio.maxTime)
    },
    startPlayOrPause() {
      return this.audio.playing ? this.pausePlay() : this.startPlay()
    },
    // 开始播放
    startPlay() {
      this.$refs.audio.play()
    },
    // 暂停
    pausePlay() {
      this.$refs.audio.pause()
    },
    // 当音频暂停
    onPause () {
      this.audio.playing = false
    },
    // 当发生错误, 就出现loading状态
    onError () {
      this.audio.waiting = true
    },
    // 当音频开始等待
    onWaiting (res) {
      console.log(res)
    },
    // 当音频开始播放
    onPlay (res) {
      console.log(res)
      this.audio.playing = true
      this.audio.loading = false

      if(!this.controlList.onlyOnePlaying){
        return
      }

      let target = res.target

      let audios = document.getElementsByTagName('audio');

      [...audios].forEach((item) => {
        if(item !== target){
          item.pause()
        }
      })
    },
    // 当timeupdate事件大概每秒一次，用来更新音频流的当前播放时间
    onTimeupdate(res) {
      // console.log('timeupdate')
      // console.log(res)
      this.audio.currentTime = res.target.currentTime
      this.sliderTime = parseInt(this.audio.currentTime / this.audio.maxTime * 100)
    },
    // 当加载语音流元数据完成后，会触发该事件的回调函数
    // 语音元数据主要是语音的长度之类的数据
    onLoadedmetadata(res) {
      this.audio.waiting = false
      this.audio.maxTime = parseInt(res.target.duration)
    }
  },
  filters: {
    formatSecond(second = 0) {
      return realFormatSecond(second)
    },
    transPlayPause(value) {
      return value ? '暂停' : '播放'
    },
    transMutedOrNot(value) {
      return value ? '放音' : '静音'
    },
    transSpeed(value) {
      return '快进: x' + value
    }
  },
  created() {
    this.setControlList()
  }
}
</script>
<style scoped lang="scss">
.right{
  width: 100%;
  padding: 10px 15px;
  display: inline-block;
  .slider {
    display: inline-block;
    position: relative;
    top: 14px;
    margin-left: 15px;
  }
  .slider_time{
    width: 550px;
    margin: 0 10px;
  }
  .slider_voice{
    width: 80px;
  }
  .download {
    color: #409EFF;
    margin-left: 15px;
  }

  .dn{
    display: none;
  }
}

</style>
