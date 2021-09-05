<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-form>
        <el-form-item label="属性方式：" />
        <el-form-item>
          <el-button icon="el-icon-plus" @click="dialogHostVisible = true">从主机列表中选择</el-button>
        </el-form-item>
        <el-form-item label="执行命令：" />
        <el-form-item>
          <editor
            v-model="content"
            lang="json"
            :options="editorOptions"
            theme="chrome"
            height="300"
            @init="editorInit"
          />
        </el-form-item>
        <el-form-item>

          <el-button icon="el-icon-plus" @click="dialogTemplateVisible = true">从执行模版中选择</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-caret-right" @click="dialogConsoleVisible = true">开始执行</el-button>
        </el-form-item>
      </el-form>

      <el-dialog title="选择执行主机" :visible.sync="dialogHostVisible">
        <el-form :inline="true">

          <el-form-item label="主机类别">
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="主机别名">
            <el-input v-model="input" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="已选">
            {{ count }} 台
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-refresh">更新</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableHostData">
          <el-table-column
            type="selection"
            width="55"
          />
          <el-table-column property="type" label="类别" width="150" />
          <el-table-column property="hostname" label="主机名称" width="200" />
          <el-table-column property="address" label="连接地址" />
          <el-table-column property="port" label="端口" />
          <el-table-column property="remark" label="备注" />
        </el-table>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogHostVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogHostVisible = false">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="选择执行模板" :visible.sync="dialogTemplateVisible">
        <el-form :inline="true">

          <el-form-item label="模板类别">
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="模板名称">
            <el-input v-model="input" placeholder="请输入" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-refresh">更新</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableTemplateData">
          <el-table-column
            type="selection"
            width="55"
          />
          <el-table-column property="name" label="类型" width="150" />
          <el-table-column property="type" label="名称" width="200" />
          <el-table-column property="content" label="内容" />
          <el-table-column property="remark" label="备注" />
        </el-table>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTemplateVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogTemplateVisible = false">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="执行控制台" :visible.sync="dialogConsoleVisible" />
    </el-card>
  </div>
</template>

<script>
export default {
  components: {
    editor: require('vue2-ace-editor')
  },
  data() {
    return {
      count: 0,
      dialogConsoleVisible: false,
      dialogHostVisible: false,
      dialogTemplateVisible: false,
      content: '',
      editorOptions: {
        // 设置代码编辑器的样式
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true,
        tabSize: 2,
        fontSize: 12,
        showPrintMargin: false // 去除编辑器里的竖线
      },
      options: [{
        value: '1',
        label: 'DBSRV2'
      }, {
        value: '2',
        label: 'WEB'
      }],
      value: '',

      tableHostData: [{
        type: 'Web服务',
        hostname: 'web-01',
        address: '10.7.117.181',
        port: '2201',
        remark: ''
      }, {
        type: 'Web服务',
        hostname: 'web-03',
        address: '10.7.117.182',
        port: '2201',
        remark: ''
      }, {
        type: 'Web服务',
        hostname: 'web-02',
        address: '10.7.117.183',
        port: '2201',
        remark: ''
      }, {
        type: 'Web服务',
        hostname: 'web-04',
        address: '10.7.117.184',
        port: '2201',
        remark: ''
      }],

      tableTemplateData: [{
        name: '测试',
        type: '测试',
        content: 'uname -a',
        description: ''
      }, {
        name: '测试',
        type: '测试',
        content: 'uname -a',
        description: ''
      }, {
        name: '测试',
        type: '测试',
        content: 'uname -a',
        description: ''
      }, {
        name: '测试',
        type: '测试',
        content: 'uname -a',
        description: ''
      }]
    }
  },
  methods: {
    editorInit: function(editor) {
      require('brace/theme/chrome')
      require('brace/ext/language_tools') // language extension prerequsite...
      require('brace/mode/yaml')
      require('brace/mode/json')
      require('brace/mode/less')
      require('brace/snippets/json')
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
.el-dialog__header{
  border-bottom: 1px solid #e8eaec;
}
.el-dialog__footer {
  border-top: 1px solid #e8eaec;
}
</style>
