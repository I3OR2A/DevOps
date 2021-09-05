<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form :inline="true">
            <el-form-item label="模板类型">
              <el-select v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="模版名称">
              <el-input v-model="input" placeholder="请输入" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-refresh">更新</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-button type="primary" icon="el-icon-plus" @click="dialogFormVisible = true">新建</el-button>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-table
            :data="tableData"
            border
            resizable
            tooltip-effect="dark"
            style="width: 100%"
          >
            <el-table-column
              prop="name"
              label="模版名称"
              width="180"
            />
            <el-table-column
              prop="type"
              label="模版类型"
              width="180"
            />
            <el-table-column
              prop="content"
              label="模版内容"
              width="180"
            />
            <el-table-column
              prop="description"
              label="描述信息"
              width="180"
            />
            <el-table-column
              prop="action"
              label="操作"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="editTemplate(scope.row.path)">編輯</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteTemplate(scope.row.path)">刪除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog title="新建模板" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="模板类型" required placeholder="请选择模板类型" :label-width="formLabelWidth">
          <el-input autocomplete="off" />
        </el-form-item>
        <el-form-item label="模板名稱" required placeholder="请輸入模板名稱" :label-width="formLabelWidth">
          <el-input autocomplete="off" />
        </el-form-item>
        <el-form-item label="模板内容" required :label-width="formLabelWidth">
          <editor
            v-model="content"
            lang="json"
            :options="editorOptions"
            theme="chrome"
            height="300"
            @init="editorInit"
          />
        </el-form-item>
        <el-form-item label="备注信息" :label-width="formLabelWidth">
          <el-input
            v-model="textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入模板备注信息"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  components: {
    editor: require('vue2-ace-editor')
  },
  data() {
    return {
      input: '',
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
        label: '测试'
      }],
      value: '',
      dialogFormVisible: false,
      formLabelWidth: '120px',
      tableData: [{
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
</style>
