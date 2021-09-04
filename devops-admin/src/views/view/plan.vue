<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form :inline="true">
            <el-form-item label="状态">
              <el-select v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="类型">
              <el-select v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="名称">
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
              label="任务名称"
              width="180"
            />
            <el-table-column
              prop="type"
              label="任务类型"
              width="180"
              sortable
            />
            <el-table-column
              prop="status"
              label="最新状态"
              width="180"
              sortable
            >
              <template slot-scope="scope">
                <el-button v-if="scope.row.status === 'success'" size="mini" type="success">開啟</el-button>
                <el-button v-else size="mini" type="danger">關閉</el-button>
              </template>
            </el-table-column>
            <el-table-column
              prop="update_time"
              label="更新於"
              width="180"
            />
            <el-table-column
              prop="description"
              label="描述信息"
            />
            <el-table-column
              prop="action"
              label="操作"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="newItem(scope.row.path)">詳情</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="cloneItem(scope.row.path)">編輯</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="editItem(scope.row.path)">執行測試</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteItem(scope.row.path)">禁用任務</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteItem(scope.row.path)">歷史紀錄</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteItem(scope.row.path)">刪除</el-button>
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
        name: '检查支付状态',
        type: '订单服务',
        status: 'fail',
        update_time: '9 分钟前',
        description: ''
      }, {
        name: '关闭超时订单',
        type: '订单服务',
        status: 'fail',
        update_time: '7 分钟前',
        description: ''
      }, {
        name: '检查支付状态',
        type: '订单服务',
        status: 'success',
        update_time: '3 分钟前',
        description: ''
      }, {
        name: '检查支付状态',
        type: '订单服务',
        status: 'fail',
        update_time: '20 分钟前',
        description: ''
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
