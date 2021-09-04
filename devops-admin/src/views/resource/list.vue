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
          <el-button type="primary" icon="el-icon-plus">新建</el-button>
          <el-button type="primary" icon="el-icon-receiving">批量導入</el-button>
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
                <el-button size="mini" type="primary" icon="el-icon-view" @click="editVm(scope.row.path)" />
                <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteVm(scope.row.path)" />
                <el-button size="mini" type="primary" icon="el-icon-monitor" @click="openConsole(scope.row.path)" />
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
</style>
