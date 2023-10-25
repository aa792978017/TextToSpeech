<template>
  <el-container class="main">
    <el-row class="content">
        <el-col class="left" >
          <el-row class="left-textarea">
            <el-input
              type="textarea"
              :rows="17"
              placeholder="你可将此文本替换为所需的任何文本。你可在此文本框中编写或在此处粘贴你自己的文本。"
              v-model="textarea"
              maxlength="2000"
              show-word-limit
            class="input_count">
            </el-input>
          </el-row>
          <el-row class="left-function-1">
            <el-col :span="3" class="left-function-content">
              <el-button type="primary" @click="clear()" class="fuction-button">清空文本</el-button>
            </el-col>
            <el-col :span="2"><div class="grid-content"></div></el-col>
            <el-col :span="3" class="left-function-content">
              <el-button type="primary" @click="arrange()" class="fuction-button">整理文本</el-button>
            </el-col>
          </el-row>
        </el-col>
        <el-col class="right" >
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">语言</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-select v-model="language" placeholder="请选择" class="right-function-selector" @change="chooseLanguage">
                <el-option
                  v-for="item in languageOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.label">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1">
              <span class="right-function-text">语音</span>
            </el-col>
            <el-col :span="20" class="right-function-2">
              <el-select v-model="voiceName" placeholder="请选择" class="right-function-selector" @change="chooseVoiceName">
                <el-option
                  v-for="item in voiceNameOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">质量</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-select v-model="voiceQuality" placeholder="请选择" class="right-function-selector" @change="chooseVoiceQuality">
                <el-option
                  v-for="item in voiceQualityOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">模仿</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-select v-model="role" placeholder="请选择" class="right-function-selector" @change="chooseRole">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">感情</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-select v-model="style" placeholder="请选择" class="right-function-selector" filterable @change="chooseStyle">
                <el-option
                  v-for="item in styleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">语速</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-slider
                class="slider"
                v-model="rate"
                :min="0.5"
                :max="2"
                :step="0.1"
                show-input
                @change="chooseRate">
              </el-slider>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="right-function-1"><span class="right-function-text">音调</span></el-col>
            <el-col :span="20" class="right-function-2">
              <el-slider
                v-model="pitch"
                :min="-50"
                :max="50"
                :step="1"
                show-input
                @change="choosePitch">
              </el-slider>
            </el-col>
          </el-row>
          <el-row class="right-function-row">
            <el-col :span="2" class="grid-content"></el-col>
            <el-col :span="22" class="grid-content right-function-3">
              <audio src="" controls="controls" controlsList="nodownload" class="audio">
                Your browser does not support the audio element.
              </audio>
            </el-col>
            <el-col :span="1" class="grid-content"></el-col>
          </el-row>
          <el-row class="right-function-button-row">
            <el-col :span="5" class="right-function-3 grid-content">
            </el-col>
            <el-col :span="4" class="right-function-3 grid-content">
              <el-button type="primary" @click="audition()" class="fuction-button">试听<i class="el-icon-video-play el-icon--right"></i></el-button>
            </el-col>
            <el-col :span="1" class="right-function-3 grid-content"></el-col>
            <el-col :span="4" class="right-function-3 grid-content">
<!--              <el-button type="primary" :loading="making" @click="makeVoice()" class="fuction-button">{{audioStatus}}<i class="el-icon-edit el-icon&#45;&#45;right"></i></el-button>-->
            </el-col>
            <el-col :span="1" class="right-function-3 grid-content"></el-col>
            <el-col :span="4" class="right-function-3 grid-content">
              <el-button type="primary" @click="download()" class="fuction-button">下载<i class="el-icon-download el-icon--right"></i></el-button>
            </el-col>
            <el-col :span="5" class="right-function-3 grid-content">
            </el-col>
          </el-row>
        </el-col>
    </el-row>
  </el-container>

</template>

<script>
import {
  AUDIO_STATUS_MAKING,
  AUDIO_STATUS_MAKE,
  TEXT_INPUT_TIPS,
  TEXTAREA_MAX_LENGTH,
  LANGUAGE_LIST,
  LANGUAGE_VOICE_MAP,
  VOICE_QUALITY,
  STYLE_OPTIONS,
  ROLE_OPTIONS,
  ROLE_DEFAULT,
  STYLE_DEFAULT,
  VOICE_QUALITY_DEFAULT,
  PITCH_DEFAULT,
  RATE_DEFAULT, UNEXPECTED_DOWNLOAD_ERROR, UNEXPECTED_AUDITION_ERROR, UNEXPECTED_MAKEVOICE_ERROR
} from '../config/const'
import {downLoadVoiceFile} from '../config/utils'

export default {
  name: 'TextToSpeech',
  data () {
    return {
      textAreaLength: 0,
      texAreaMaxLength: TEXTAREA_MAX_LENGTH,
      audioStatus: AUDIO_STATUS_MAKE,
      making: false,
      textarea: '你可将此文本替换为所需的任何文本。你可在此文本框中编写或在此处粘贴你自己的文本',
      voice_data: [], // 生成二进制语音,
      voice_url: '',
      fileName: '',
      textInputTips: TEXT_INPUT_TIPS,
      language: LANGUAGE_LIST[0].label,
      languageOptions: LANGUAGE_LIST,
      voiceName: LANGUAGE_VOICE_MAP[LANGUAGE_LIST[0].label][0].label,
      voiceNameOptions: LANGUAGE_VOICE_MAP[LANGUAGE_LIST[0].label],
      voiceQuality: VOICE_QUALITY[0].label,
      voiceQualityOptions: VOICE_QUALITY,
      role: ROLE_DEFAULT.label,
      roleOptions: ROLE_OPTIONS,
      style: STYLE_DEFAULT.label,
      styleOptions: STYLE_OPTIONS,
      rate: 1,
      pitch: 0,
      speechConfig: {
        'textArea': '',
        'language': LANGUAGE_LIST[0].value,
        'voiceName': LANGUAGE_VOICE_MAP[LANGUAGE_LIST[0].label][0].value,
        'voiceQuality': VOICE_QUALITY_DEFAULT.value,
        'role': ROLE_DEFAULT.value,
        'style': STYLE_DEFAULT.value,
        'rate': RATE_DEFAULT,
        'pitch': PITCH_DEFAULT
      }

    }
  },
  methods: {
    // 清空文本框
    clear () {
      this.textarea = '你可将此文本替换为所需的任何文本。你可在此文本框中编写或在此处粘贴你自己的文本'
    },
    // 整理文本框中的内容
    arrange () {
      // 清除文本中所有空格、换行符号
      this.textarea = this.textarea.replace(/\s*/g, '').replace(/[\r\n]/g, '')
    },
    // 试听音频
    audition () {
      // 获取音频流，保存到audio的url中
      let that = this
      that.speechConfig.textArea = that.textarea
      that.$axios.post('/text-to-speech/audition', that.speechConfig, {headers: {'Content-Type': 'application/x-www-form-urlencode; charset=UTF-8'}, responseType: 'blob'}).then(function (response) {
        const audioUrl = window.URL.createObjectURL(response.data)
        const audio = document.querySelector('audio')
        audio.src = audioUrl
        audio.play()
      }).catch(function (error) {
        // todo 提示用户试听音频生成失败，需要重试或联系管理员
        alert(UNEXPECTED_AUDITION_ERROR)
        console.log(error)
        console.log(UNEXPECTED_AUDITION_ERROR)
      })
    },
    // 生成语音
    makeVoice () {
      let that = this
      that.speechConfig.textArea = that.textarea
      that.making = true
      that.audioStatus = AUDIO_STATUS_MAKING
      // 调用后端，生成语音
      // 生成语音文件
      this.$axios.post('/text-to-speech/make-job-voice', that.speechConfig).then(function (response) {
        if (response.data['code'] === 200) {
          that.fileName = response.data['fileName']
          // todo 提示用户生成成功
          alert(response.data['msg'])
          that.making = false
          that.audioStatus = AUDIO_STATUS_MAKE
        } else {
          // todo 提示用户生成失败，需要重试
          alert(response.data['msg'])
          that.making = false
          that.audioStatus = AUDIO_STATUS_MAKE
        }
      }).catch(function (error) {
        // todo 提示用户生成失败，需要重试
        alert(UNEXPECTED_MAKEVOICE_ERROR)
        console.log(error)
        console.log(UNEXPECTED_MAKEVOICE_ERROR)
        that.making = false
        that.audioStatus = AUDIO_STATUS_MAKE
      })
    },
    // 导出音频文件
    download () {
      let that = this
      let param = {
        'fileName': that.fileName
      }
      this.$axios.post('/text-to-speech/vaild-voice-file', param).then(function (response) {
        if (response.data['code'] === 200) {
          that.$axios.get('/text-to-speech/download-file?fileName=' + that.fileName, {headers: {'Content-Type': 'application/x-www-form-urlencode; charset=UTF-8'}, responseType: 'blob'}).then(function (response) {
            downLoadVoiceFile(that.fileName, response.data)
          }).catch(function (error) {
            // todo 下载异常，提醒重新尝试下载，或者重新生成
            alert(UNEXPECTED_DOWNLOAD_ERROR)
            console.log(error)
            console.log(UNEXPECTED_DOWNLOAD_ERROR)
          })
        } else if (response.data['code'] === 201) {
          // todo 提示音频文件已经过期
          alert(response.data['msg'])
        } else if (response.data['code'] === 400) {
          // todo
          alert(response.data['msg'])
        }
      }).catch(function (error) {
        // todo 提示 下载出现未知异常，请联系管理员
        alert(UNEXPECTED_DOWNLOAD_ERROR)
        console.log(error)
        console.log(UNEXPECTED_DOWNLOAD_ERROR)
      })
    },
    // 勾选语言,调整支持的语音包
    chooseLanguage (language) {
      // 设置语言支持的语音包
      this.voiceNameOptions = LANGUAGE_VOICE_MAP[language]
      // 根据language的key，找出value
      let languageItem = {}
      languageItem = LANGUAGE_LIST.find((item) => {
        return item.label === language
      })
      this.speechConfig.language = languageItem.value
    },
    chooseVoiceName (value) {
      this.speechConfig.voiceName = value
    },
    chooseVoiceQuality (voiceQuality) {
      this.speechConfig.voiceQuality = voiceQuality
    },
    chooseRole (role) {
      this.speechConfig.role = role
    },
    chooseStyle (style) {
      this.speechConfig.style = style
    },
    chooseRate (rate) {
      this.speechConfig.rate = rate
    },
    choosePitch (pitch) {
      this.speechConfig.pitch = pitch + '%'
    }
  }

}
</script>

<style scoped>
.main{
  background-color: #ffffff;
  width: 100%;
  height: 100%;
}

.header{
  background-color: #E9EEF3;
  color: #000000;
  text-align: left;
  line-height: 60px;
  font-size: 25px;
  width: 100%;
}
.content{
  height: 100%;
  width: 98% !important;
}
.left, .right{
  border-radius: 4px;
  /*border: 1px solid #333333;*/
  margin: 1%;
  width: 48% !important;
  height: auto !important;
}
.left-textarea{
  margin-bottom: 5px;
}
.input_count{
  line-height: normal;
  font-size: 17px;
  color: #333333;
}
.left-function-content{
  line-height: normal;
}
.left-function-1,left-function-2{
  margin-bottom: 5px;
}
.grid-content {
  min-height: 36px;
  text-align: left;
}
.right-function-1,.right-function-2,.right-function-3,.right-function-selector{
  border-radius: 4px;
  line-height: normal;
  height: 50px;

}
.right-function-2{
  text-align: left;
}
.right-function-text{
  font-size: 18px;
  text-align: center;
  padding-top: 5px;
  display: inline-block;
  vertical-align: middle;
}
.right-function-selector{
  width: 100%;
}
.right-function-row{
  width: 100%;
}
.right-function-button-row{
  margin-top: 20px;
}
.audio{
  width: 100%;
  line-height: normal;
}

.fuction-button,.fuction-button:focus:not(.fuction-button:hover) {
  //background: #0f0f13;
  //border-color: #0f0f13;
  //color: #eee;
  background: #eee;
  border-color: #0f0f13;
  color: #0f0f13;
}

.fuction-button:focus,.fuction-button:hover{
  background: #b0b0b0;
  border-color: #0f0f13;
  color: #0f0f13;
}
.fuction-button:active {
  background: #24272d;
  border-color: #24272d;
  color: #eee;
}
</style>
