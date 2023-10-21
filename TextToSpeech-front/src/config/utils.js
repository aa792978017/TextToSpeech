// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

export function uploadFile (blob, filename) {
  const a = document.createElement('a')
  let href
  if (typeof blob === 'string') {
    href = blob
  } else {
    a.download = filename
    href = URL.createObjectURL(blob)
  }
  a.href = href
  a.click()
  URL.revokeObjectURL(href)
}

// 按当前时间生成文件名
export function makeVoiceName () {
  return Date.parse(new Date()) + '.wav'
}

// 下载文件
export function downLoadVoiceFile (fileName, data) {
  // TODO
  let downloadLink = document.createElement('a')
  downloadLink.display = 'none'
  downloadLink.href = window.URL.createObjectURL(new Blob([data]))
  downloadLink.download = fileName
  document.body.appendChild(downloadLink)
  downloadLink.click()
}
// 进程休眠
export function sleep (n) {
  let start = new Date().getTime()
  while (true) {
    if (new Date().getTime() - start > n) {
      break
    }
  }
}
