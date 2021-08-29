import request from '@/utils/request'

export default {
  // 檔案列表（条件查询分页）
  // current当前页 limit每页记录数 fileQuery条件对象
  getRemoteFilesPage(current, limit, path) {
    return request({
    //   url: `/file/listRemoteFiles/${current}/${limit}`,
      url: `/file/listRemoteFiles`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 刪除遠端檔案
  deleteRemoteFile(path) {
    return request({
      url: `/file/deleteRemoteFile`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 刪除遠端目錄
  deleteRemoteDir(path) {
    return request({
      url: `/file/deleteRemoteDir`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 回显数据
  listRemoteFiles() {
    return request({
      url: `/file/listRemoteFiles`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: '' }
    })
  },
  // 下載遠端檔案
  downloadRemoteFile(path) {
    return request({
      url: `/file/downloadRemoteFile`,
      method: 'get',
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json'
      },
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 壓縮遠端檔案並下載
  downloadZipRemoteFile(path) {
    return request({
      url: `/file/downloadZipRemoteFile`,
      method: 'get',
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json'
      },
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 壓縮遠端多個檔案及目錄並下載
  downloadZipRemoteMultiFile(path, smbEntities) {
    return request({
      url: `/file/downloadZipRemoteMultiFile`,
      method: 'post',
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json'
      },
      data: { hostname: 'localhost', account: 'Bryan', path: path, smbEntities: smbEntities }
    })
  },
  // 創建檔案
  createRemoteFile(path) {
    return request({
      url: `/file/createRemoteFile`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 創建目錄
  createRemoteDir(path) {
    return request({
      url: `/file/createRemoteDir`,
      method: 'get',
      params: { hostname: 'localhost', account: 'Bryan', path: path }
    })
  },
  // 上傳檔案
  uploadRemoteFile(form) {
    return request({
      url: `/file/uploadRemoteFile`,
      method: 'post',
      data: form
    })
  }
}
