<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24">
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
            <el-form-item label="连接地址">
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
          <el-button type="primary" icon="el-icon-receiving" @click="dialogBatchVisible = true">批量導入</el-button>
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
              prop="type"
              label="类别"
              width="180"
              sortable
            />
            <el-table-column
              prop="hostname"
              label="主機名稱"
              width="180"
              sortable
            />
            <el-table-column
              prop="address"
              label="連接地址"
              width="180"
              sortable
            />
            <el-table-column
              prop="port"
              label="端口"
              width="180"
            />
            <el-table-column
              prop="remark"
              label="備註信息"
            />
            <el-table-column
              prop="action"
              label="操作"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="editVm(scope.row.path)">編輯</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteVm(scope.row.path)">刪除</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="openConsole(scope.row.path)">控制台</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>
    <el-dialog title="新建主机" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item required label="主机类别" :label-width="formLabelWidth">
          <el-form :inline="true">
            <el-form-item>
              <el-select v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button>添加類別</el-button>
            </el-form-item>
            <el-form-item>
              <el-button>編輯類別</el-button>
            </el-form-item>
          </el-form>

        </el-form-item>
        <el-form-item required label="主机别名" :label-width="formLabelWidth">
          <el-input v-model="input" placeholder="请输入" />
        </el-form-item>
        <el-form-item required label="连接地址" :label-width="formLabelWidth">
          <el-input v-model="input" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="独立密钥" :label-width="formLabelWidth">
          <el-button icon="el-icon-plus">點擊上傳</el-button><br>
          <span>默认使用全局密钥，如果上传了独立密钥则优先使用该密钥。</span>
        </el-form-item>
        <el-form-item label="备注信息" :label-width="formLabelWidth">
          <el-input
            v-model="textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入模板备注信息"
          /><br>

          <span>首次验证时需要输入登录用户名对应的密码，但不会存储该密码。</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">驗 證</el-button>
      </div>
    </el-dialog>

    <el-dialog title="批量导入" :visible.sync="dialogBatchVisible">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-alert
            title=""
            type="warning"
            description="导入或输入的密码仅作首次验证使用，并不会存储密码。"
            show-icon
          />
        </el-col>
      </el-row>
      <el-form>
        <el-form-item label="模板下载" :label-width="formLabelWidth">
          <el-input v-model="input" placeholder="请输入" /><br>
          <span>请下载使用该模板填充数据后导入</span>
        </el-form-item>
        <el-form-item label="默认密码" :label-width="formLabelWidth">
          <el-input v-model="input" placeholder="请输入默认主機密码" /><br>
          <span>如果excel中密码为空则使用该密码</span>
        </el-form-item>
        <el-form-item required label="导入数据" :label-width="formLabelWidth">
          <el-button icon="el-icon-plus">點擊上傳</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBatchVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogBatchVisible = false">导 入</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogBatchVisible: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      input: '',
      options: [{
        value: '1',
        label: 'DBSRV2'
      }, {
        value: '2',
        label: 'WEB'
      }],
      value: '',

      tableData: [{
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
      }]
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
