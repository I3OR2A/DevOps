<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24" style="text-align: right">
          <el-button type="primary" icon="el-icon-remove">移動</el-button>
          <el-button type="primary" icon="el-icon-document-copy">複製</el-button>
          <el-button type="primary" icon="el-icon-s-comment" @click="downloadZipFile()">壓縮</el-button>
          <el-button type="primary" icon="el-icon-share">傳送選取項目</el-button>
          <el-button type="primary" icon="el-icon-upload" @click="dialogUploadFormVisible = true">上傳</el-button>
          <el-button
            type="primary"
            icon="el-icon-download"
            @click="downloadSelectedFile()"
          >下載選取項目</el-button>
          <el-button type="danger" icon="el-icon-delete">刪除</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form :inline="true">
            <el-form-item>
              <el-button type="primary" icon="el-icon-refresh" @click="refresh()">更新</el-button>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="search"
                placeholder="請輸入搜尋檔案名稱"
                prefix-icon="el-icon-search"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-plus" @click="dialogCreateFormVisible = true">新增項目
              </el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-breadcrumb separator=">">
            <template v-for="(item, index) in breadcrumb">
              <el-breadcrumb-item v-if="index < (breadcrumb.length - 1)" :key="index" :to="{ path: '/file/list', query: { path: setQueryPath(index) } }">{{ item }}</el-breadcrumb-item>
              <el-breadcrumb-item v-else :key="index">{{ item }}</el-breadcrumb-item>
            </template>
            <el-breadcrumb-item v-if="breadcrumb.length === 1" />
          </el-breadcrumb>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24" style="text-align: left">
          <el-table
            ref="multipleTable"
            :data="list.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase())).slice((currentPage-1)*pagesize,currentPage*pagesize)"
            border
            resizable
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="name" label="名稱" sortable width="240">
              <template slot-scope="scope">
                <el-button v-if="scope.row.directory" icon="el-icon-folder" type="text" @click="rowClick(scope.row)">{{ scope.row.name }}</el-button>
                <el-button v-else icon="el-icon-document" type="text" @click="rowClick(scope.row)">{{ scope.row.name }}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="size" label="大小" sortable width="120" />
            <el-table-column label="修改日期" sortable width="120">
              <template slot-scope="scope">{{ scope.row.lastModified }}</template>
            </el-table-column>
            <el-table-column prop="privilege" label="權限" sortable width="120" />
            <el-table-column prop="owner" label="擁有者" width="120" />
            <el-table-column prop="action" label="操作" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="showDetail(scope.row.path)">查看詳情</el-button>
                <el-button v-if="!scope.row.directory" size="mini" type="primary" icon="el-icon-view" @click="viewFile(scope.row.path)" />
                <el-button v-if="scope.row.directory" size="mini" type="danger" icon="el-icon-delete" @click="deleteDir(scope.row.path)" />
                <el-button v-else size="mini" type="danger" icon="el-icon-delete" @click="deleteFile(scope.row.path)" />
                <el-button size="mini" type="primary" icon="el-icon-edit" @click="editFile(scope.row.path)" />
                <el-button size="mini" type="primary" icon="el-icon-document-copy" @click="copyFile(scope.row.path)" />
                <el-button size="mini" type="primary" icon="el-icon-link" @click="shareFile(scope.row.path)" />
                <el-button v-if="!scope.row.directory" size="mini" type="primary" icon="el-icon-download" @click="downloadFile(scope.row.path)" />
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24" style="text-align: left">
          <el-button @click="toggleSelection()">全選</el-button>
          <el-button @click="toggleSelection()">取消全選</el-button>
          <el-button @click="toggleSelection()">反向選擇</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24" style="text-align: center">
          <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            @current-change="current_change"
          />
        </el-col>
      </el-row>

      <el-dialog title="新增新的項目" :visible.sync="dialogCreateFormVisible">
        <el-form>
          <el-form-item label="項目類型" :label-width="formLabelWidth">
            <el-select v-model="value" placeholder="請選擇">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="項目名稱" :label-width="formLabelWidth">
            <el-input v-model="newFileName" placeholder="項目名稱" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogCreateFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="createNewItem()">確 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="上傳檔案" :visible.sync="dialogUploadFormVisible" @close="cancelUpload()">
        <el-form>
          <el-upload
            ref="upload"
            class="upload-demo"
            drag
            action="#"
            :limit="1"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false"
            :http-request="uploadHttpRequest"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelUpload()">取 消</el-button>
          <el-button type="primary" @click="uploadFile()">確 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import file from '@/api/file/file'

export default {
  data() {
    return {
      fileList: [],
      BASE_API: process.env.VUE_APP_BASE_API,
      dialogCreateFormVisible: false,
      dialogUploadFormVisible: false,
      parentPath: '',
      breadcrumb: [],
      list: [], // 查询之后接口返回集合
      currentPage: 1, // 当前页
      pagesize: 10, // 每页记录数
      total: 0, // 总记录数
      teacherQuery: {}, // 条件封装对象,
      options: [
        {
          value: '1',
          label: '資料夾'
        },
        {
          value: '2',
          label: '檔案'
        }
      ],
      value: '',
      search: '',
      multipleSelection: [],
      newFileName: '',
      formLabelWidth: '120px'
    }
  },
  watch: {
    $route(val) { // 监听路由参数变化
      console.log('watch' + val.query.path)
      if (val.query.path === undefined || val.query.path === '/') {
        // this.list.filters.path = '/'
        this.breadcrumb = ['我的資源']
        this.getList()
      } else {
        // this.list.filters.path = val.query.path
        this.breadcrumb = [...['我的資源'], ...val.query.path.split('/').slice(1)]
        this.getList(val.query.path)
      }
    }
  },
  created() { // 在页面渲染之前执行，一般调用methods定义的方法
    // 调用
    if (this.$route.query.path === undefined || this.$route.query.path === '/') {
      this.breadcrumb = ['我的資源']
      this.getList()
    } else {
      this.breadcrumb = [...['我的資源'], ...this.$route.query.path.split('/').slice(1)]
      this.getList(this.$route.query.path)
    }
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    current_change: function(currentPage) {
      this.currentPage = currentPage
    },
    setQueryPath(index) { // 设置面包屑的query参数
      const tmp = JSON.parse(JSON.stringify(this.breadcrumb))
      if (index === 0) {
        return '/'
      } else {
        return '/' + tmp.slice(1).slice(0, index).join('/')
        // return tmp.slice(0, index + 1).join('/')
      }
    },
    refresh() {
      console.log('refresh' + this.$route.query.path)
      if (this.$route.query.path === undefined || this.$route.query.path === '/') {
        // this.list.filters.path = '/'
        this.breadcrumb = ['我的資源']
        this.getList()
      } else {
        // this.list.filters.path = val.query.path
        this.breadcrumb = [...['我的資源'], ...this.$route.query.path.split('/').slice(1)]
        this.getList(this.$route.query.path)
      }
    },
    rowClick(row) { // 表格行被点击
      this.$router.push({ path: '/file/list', query: { path: '/' + row.path }})
    },
    // 檔案列表的方法
    getList(path = '') {
      if (path.length > 0 && path.charAt(0) === '/') {
        path = path.substring(1)
      }
      file.getRemoteFilesPage(this.page, this.limit, path)
        .then(response => {
          // response接口返回的数据
          // console.log(response)
          this.list = response.data.items
          this.total = response.data.total
          console.log(this.list)
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    },
    // 刪除檔案方法
    deleteFile(path) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          file.deleteRemoteFile(path)
            .then(response => {
              // 删除成功
              // 提示信息
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              // 回到查询列表
              this.refresh()
            }) // 请求成功
            .catch(error => {
              console.log(error)
            }) // 请求失败
        })
        .catch(() => {
          this.$message({ type: 'info', message: '已取消删除' })
        })
    },
    // 刪除目錄方法
    deleteDir(path) {
      this.$confirm('此操作将永久删除该目錄, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          file.deleteRemoteDir(path)
            .then(response => {
              // 删除成功
              // 提示信息
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              // 回到查询列表
              this.refresh()
            }) // 请求成功
            .catch(error => {
              console.log(error)
            }) // 请求失败
        })
        .catch(() => {
          this.$message({ type: 'info', message: '已取消删除' })
        })
    },
    // 檔案列表的方法
    downloadFile(path) {
      file.downloadRemoteFile(path)
        .then(response => {
          // response接口返回的数据
          console.log(response)
          console.info('文件下载')
          const contentDisposition = response.headers['content-disposition']
          console.log('contentDisposition=' + contentDisposition)
          let fileName = window.decodeURI(
            contentDisposition.substring(contentDisposition.indexOf('=') + 1)
          )
          // 正则获取filename的值
          //   let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
          //   let matches = filenameRegex.exec(contentDisposition)
          //   if (matches != null && matches[1]) {
          //     fileName = matches[1].replace(/['"]/g, '')
          //   }
          fileName = window.decodeURI(fileName)
          console.log('fileName=' + fileName)

          console.info('文件下载')
          var blob = new Blob([response.data], {
            type: 'application/file'
          })
          var url = window.URL.createObjectURL(blob)
          var a = document.createElement('a')
          a.href = url
          a.download = fileName // 文件名称需要自己定义。如果需要知道也可以在header中自定义一个文件名称返回 也可以随机生成一个 我这里是已经提前知道了文件名
          a.click()
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    },
    // 檔案列表的方法
    downloadZipFile() {
      console.log('dir=' + this.$route.query.path)
      file.downloadZipRemoteFile(this.$route.query.path)
        .then(response => {
          // response接口返回的数据
          console.log(response)
          console.info('文件下载')
          const contentDisposition = response.headers['content-disposition']
          console.log('contentDisposition=' + contentDisposition)
          let fileName = window.decodeURI(
            contentDisposition.substring(contentDisposition.indexOf('=') + 1)
          )
          // 正则获取filename的值
          //   let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
          //   let matches = filenameRegex.exec(contentDisposition)
          //   if (matches != null && matches[1]) {
          //     fileName = matches[1].replace(/['"]/g, '')
          //   }
          fileName = window.decodeURI(fileName)
          console.log('fileName=' + fileName)

          console.info('文件下载')
          var blob = new Blob([response.data], {
            type: 'application/file'
          })
          var url = window.URL.createObjectURL(blob)
          var a = document.createElement('a')
          a.href = url
          a.download = fileName // 文件名称需要自己定义。如果需要知道也可以在header中自定义一个文件名称返回 也可以随机生成一个 我这里是已经提前知道了文件名
          a.click()
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    },
    downloadSelectedFile() {
      console.log('dir=' + this.$route.query.path)
      const currentSelect = this.$refs.multipleTable.selection
      console.log(currentSelect)

      file.downloadZipRemoteMultiFile(this.$route.query.path, currentSelect)
        .then(response => {
          // response接口返回的数据
          console.log(response)
          console.info('文件下载')
          const contentDisposition = response.headers['content-disposition']
          console.log('contentDisposition=' + contentDisposition)
          let fileName = window.decodeURI(
            contentDisposition.substring(contentDisposition.indexOf('=') + 1)
          )
          // 正则获取filename的值
          //   let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
          //   let matches = filenameRegex.exec(contentDisposition)
          //   if (matches != null && matches[1]) {
          //     fileName = matches[1].replace(/['"]/g, '')
          //   }
          fileName = window.decodeURI(fileName)
          console.log('fileName=' + fileName)

          console.info('文件下载')
          var blob = new Blob([response.data], {
            type: 'application/file'
          })
          var url = window.URL.createObjectURL(blob)
          var a = document.createElement('a')
          a.href = url
          a.download = fileName // 文件名称需要自己定义。如果需要知道也可以在header中自定义一个文件名称返回 也可以随机生成一个 我这里是已经提前知道了文件名
          a.click()
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    }, createNewItem() {
      this.dialogCreateFormVisible = false
      let path = this.$route.query.path
      if (path.length > 0 && path.charAt(0) === '/') {
        path = path.substring(1)
      } else if (path === undefined || path === '/') {
        return
      }

      if (this.value === '1') {
        file.createRemoteDir(path + '/' + this.newFileName)
          .then(response => {
            // response接口返回的数据
            // console.log(response)
            this.$message({ type: 'info', message: '項目創建成功' })
          }) // 请求成功
          .catch(error => {
            this.$message({ type: 'info', message: '項目創建失敗' })
            console.log(error)
          }) // 请求失败
      } else if (this.value === '2') {
        file.createRemoteFile(path + '/' + this.newFileName)
          .then(response => {
            // response接口返回的数据
            // console.log(response)
            this.$message({ type: 'info', message: '項目創建成功' })
          }) // 请求成功
          .catch(error => {
            this.$message({ type: 'info', message: '項目創建失敗' })
            console.log(error)
          }) // 请求失败
      } else {
        this.$message({ type: 'info', message: '項目類型不存在' })
      }

      this.value = ''
      this.newFileName = ''
    }, uploadFile() {
      this.$refs.upload.submit()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    cancelUpload() {
      this.$refs.upload.clearFiles() // 清除上傳檔案物件
      this.fileList = [] // 清空選擇的檔案列表
      this.dialogUploadFormVisible = false
    }, uploadHttpRequest(param) {
      let path = this.$route.query.path
      if (path.length > 0 && path.charAt(0) === '/') {
        path = path.substring(1)
      } else if (path === undefined || path === '/') {
        return
      }

      const form = new FormData()
      form.append('file', param.file)
      form.append('path', path)
      form.append('hostname', 'localhost')
      form.append('account', 'Bryan')

      file.uploadRemoteFile(form)
        .then(response => {
          // response接口返回的数据
          // console.log(response)
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败

      this.dialogUploadFormVisible = false
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
.upload-demo {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
