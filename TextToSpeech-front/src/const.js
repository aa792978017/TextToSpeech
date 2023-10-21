// 用户提示
export const JOB_MSG_AUDITION_FAILURE = '试听音频获取失败，请重试或联系管理员'
export const JOB_MSG_SUCCESS = '文本转语音成功，已生成语音文件'
export const JOB_MSG_FAILURE = '文本转语音失败，请联系管理员'
export const TEXT_INPUT_TIPS = '你可将此文本替换为所需的任何文本。你可在此文本框中编写或在此处粘贴你自己的文本。\n' +
  '试用不同的语言和声音。改变语速和音调。\n' +
  '部分语音无法使用模仿与感情功能，可使用晓墨体验感情功能，使用晓晓体验模仿功能。\n' +
  '请尽情使用文本转语音功能！\n' +
  '实测10000字可生成，偶尔会因为网络原因生成失败，失败重新生成即可。'

// 未知异常提醒
export const UNEXPECTED_AUDITION_ERROR = '试听音频出现未知异常，请重试或联系管理员'
export const UNEXPECTED_MAKEVOICE_ERROR = '生成音频出现未知异常，请重试或联系管理员'
export const UNEXPECTED_DOWNLOAD_ERROR = '下载音频出现未知异常，请重试或联系管理员'

// 单次文本转语音最大文本长度
export const TEXTAREA_MAX_LENGTH = 5000
// 语音状态
export const AUDIO_STATUS_MAKING = '生成中'
export const AUDIO_STATUS_MAKE = '生成'
// 语音文件获取
export const AUDIO_FILE_GET_FAILURE = '获取音频文件失败'

// 功能区常量结构体
export const LANGUAGE_LIST = [
  {
    label: '中文-普通话（简体）',
    value: 'zh-CN'
  },
  {
    label: '中文-粤语（简体）',
    value: 'yue-CN'
  },
  {
    label: '中文-东北话（简体）',
    value: 'zh-CN-liaoning'
  },
  {
    label: '中文-河南话（简体）',
    value: 'zh-CN-henan'
  },
  {
    label: '中文-四川话（简体）',
    value: 'zh-CN-sichuan'
  },
  {
    label: '中文-山东话（简体）',
    value: 'zh-CN-shandong'
  },
  {
    label: '中文-陕西话（简体）',
    value: 'zh-CN-shaanxi'
  },
  {
    label: '中文-台湾话（简体）',
    value: 'zh-TW'
  },
  {
    label: '中文-粤语（繁体）',
    value: 'zh-HK'
  }]
export const LANGUAGE_VOICE_MAP = {
  '中文-普通话（简体）': [
    {
      value: 'zh-CN-XiaochenNeural',
      label: '普通话-晓晨-女'
    },
    {
      value: 'zh-CN-XiaonanNeural',
      label: '普通话-晓楠-女'
    },
    {
      value: 'zh-CN-XiaohanNeural',
      label: '普通话-晓梦-女'
    },
    {
      value: 'zh-CN-XiaomoNeural',
      label: '普通话-小莫-女'
    },
    {
      value: 'zh-CN-XiaoqiuNeural',
      label: '普通话-小邱-女'
    },
    {
      value: 'zh-CN-XiaoruiNeural',
      label: '普通话-小瑞-女'
    },
    {
      value: 'zh-CN-XiaoshuangNeural',
      label: '普通话-小霜-女'
    }
  ],
  '中文-粤语（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-东北话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-河南话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-四川话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-山东话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-陕西话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-台湾话（简体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ],
  '中文-粤语（繁体）': [
    {
      value: 'yue-CN-XiaoMinNeural',
      label: '粤语-晓敏-女'
    },
    {
      value: 'yue-CN-YunSongNeural',
      label: '粤语-云松-男'
    }
  ]

}
export const VOICE_QUALITY = [
  {
    label: '48khz-192kbitrate(mp3)',
    value: 0
  },
  {
    label: '16khz-32kbitrate(mp3)',
    value: 1
  },
  {
    label: '16khz-128kbitrate(mp3)',
    value: 2
  },
  {
    label: '24khz-160kbitrate(mp3)',
    value: 3
  },
  {
    label: '16khz-16bit-mono-pcm(wav)',
    value: 4
  },
  {
    label: '24khz-16bit-mono-pcm(wav)',
    value: 5
  },
  {
    label: '48khz-16bit-mono-pcm(wav)',
    value: 6
  }
]
export const VOICE_QUALITY_DEFAULT = VOICE_QUALITY[0]
export const ROLE_DEFAULT = {
  label: '请选择模仿（非必选）',
  value: 'default'
}
export const ROLE_OPTIONS = [
  {
    label: '模仿女孩',
    value: 'Girl'
  },
  {
    label: '模仿男孩',
    value: 'Boy'
  },
  {
    label: '模仿年轻的成年女性',
    value: 'YoungAdultFemale'
  },
  {
    label: '模仿年轻的成年男性',
    value: 'YoungAdultMale'
  },
  {
    label: '模仿年长的成年女性',
    value: 'OlderAdultFemale'
  },
  {
    label: '模仿年长的成年男性',
    value: 'OlderAdultMale'
  },
  {
    label: '模仿年老女性',
    value: 'SeniorFemale'
  },
  {
    label: '模仿年老男性',
    value: 'SeniorMale'
  }
]
export const STYLE_DEFAULT = {
  label: '请选择感情（非必选）',
  value: 'default'
}
export const STYLE_OPTIONS = [
  {
    label: '兴奋和精力充沛',
    value: 'advertisement_upbeat'
  },
  {
    label: '较高的音调和音量表达温暖而亲切',
    value: 'affectionate'
  },
  {
    label: '生气和厌恶',
    value: 'angry'
  },
  {
    label: '热情而轻松',
    value: 'assistant'
  },
  {
    label: '沉着冷静',
    value: 'calm'
  },
  {
    label: '轻松随意',
    value: 'chat'
  },
  {
    label: '积极愉快',
    value: 'cheerful'
  },
  {
    label: '友好热情',
    value: 'customerservice'
  },
  {
    label: '忧郁、沮丧',
    value: 'depressed'
  },
  {
    label: '轻蔑和抱怨',
    value: 'disgruntled'
  },
  {
    label: '用一种轻松、感兴趣和信息丰富的风格讲述纪录片，适合配音纪录片、专家评论和类似内容',
    value: 'documentary-narration'
  },
  {
    label: '在说话者感到不舒适时表达不确定、犹豫的语气',
    value: 'embarrassed'
  },
  {
    label: '表达关心和理解',
    value: 'empathetic'
  },
  {
    label: '当你渴望别人拥有的东西时，表达一种钦佩的语气',
    value: 'envious'
  },
  {
    label: '表达乐观和充满希望的语气。 似乎发生了一些美好的事情，说话人对此非常满意。',
    value: 'excited'
  },
  {
    label: '以较高的音调、较高的音量和较快的语速来表达恐惧、紧张的语气。 说话人处于紧张和不安的状态。',
    value: 'fearful'
  },
  {
    label: '表达一种愉快、怡人且温暖的语气。 听起来很真诚且满怀关切。',
    value: 'friendly'
  },
  {
    label: '以较低的音调和音量表达温和、礼貌和愉快的语气。',
    value: 'gentle'
  },
  {
    label: '表达一种温暖且渴望的语气。 听起来像是会有好事发生在说话人身上。',
    value: 'hopeful'
  },
  {
    label: '以优美又带感伤的方式表达情感。',
    value: 'lyrical'
  },
  {
    label: '以专业、客观的语气朗读内容。',
    value: 'narration-professional'
  },
  {
    label: '以正式专业的语气叙述新闻。。',
    value: 'newscast'
  },
  {
    label: '以通用、随意的语气发布一般新闻。',
    value: 'newscast-casual'
  },
  {
    label: '以正式、自信和权威的语气发布新闻。',
    value: 'newscast-formal'
  },
  {
    label: '在读诗时表达出带情感和节奏的语气。',
    value: 'poetry-reading'
  },
  {
    label: '表达悲伤语气。',
    value: 'sad'
  },
  {
    label: '表达严肃和命令的语气。 说话者的声音通常比较僵硬，节奏也不那么轻松',
    value: 'serious'
  },
  {
    label: '就像从遥远的地方说话或在外面说话，但能让自己清楚地听到',
    value: 'shouting'
  },
  {
    label: '用轻松有趣的语气播报体育赛事',
    value: 'sports_commentary'
  },
  {
    label: '用快速且充满活力的语气播报体育赛事精彩瞬间。',
    value: 'sports_commentary_excited'
  },
  {
    label: '说话非常柔和，发出的声音小且温柔',
    value: 'whispering'
  },
  {
    label: '表达一种非常害怕的语气，语速快且声音颤抖。 听起来说话人处于不稳定的疯狂状态',
    value: 'terrified'
  },
  {
    label: '冷淡无情的语气',
    value: 'unfriendly'
  }
]
export const RATE_DEFAULT = 1
export const PITCH_DEFAULT = '0%'
