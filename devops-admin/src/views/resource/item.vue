<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form :inline="true">
            <el-form-item label="应用名称">
              <el-input v-model="input" placeholder="请输入" />
            </el-form-item>
            <el-form-item label="描述信息">
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

            <el-table-column type="expand">
              <!-- 子table START-->
              <template slot-scope="props">
                <el-table
                  ref="tableDataChild"
                  :data="props.row.children"
                  tooltip-effect="dark"
                  style="width: 100%"
                >
                  <el-table-column prop="mode" label="模式" width="120">
                    <template slot-scope="scope">
                      <el-button v-if="scope.row.mode === 'sequence'" size="mini" icon="el-icon-s-order" type="primary " />
                      <el-button v-else size="mini" icon="el-icon-s-unfold" type="primary " />
                    </template>
                  </el-table-column>
                  <el-table-column prop="environment" label="发布环境" width="120" />
                  <el-table-column prop="associate" label="关联主机" show-overflow-tooltip />
                  <el-table-column prop="audit" label="发布审核" show-overflow-tooltip>
                    <template slot-scope="scope">
                      <el-button v-if="scope.row.audit === 'open'" size="mini" type="success">開啟</el-button>
                      <el-button v-else size="mini" type="danger">關閉</el-button>
                    </template>
                  </el-table-column>
                  <el-table-column prop="action" label="操作" show-overflow-tooltip>
                    <el-button size="mini" type="text" @click="viewChild(scope.row.path)">查看</el-button>
                    <el-divider direction="vertical" />
                    <el-button size="mini" type="text" @click="editChild(scope.row.path)">編輯</el-button>
                    <el-divider direction="vertical" />
                    <el-button size="mini" type="text" @click="deleteChild(scope.row.path)">刪除</el-button>
                  </el-table-column>
                </el-table>
              </template>
              <!-- 子table END-->
            </el-table-column>

            <el-table-column
              prop="sort"
              label="排序"
              width="180"
              align="center"
            >
              <template slot-scope="scope">
                <el-button icon="el-icon-caret-bottom" @click="moveDown(scope.$index,scope.row)" />
                <el-divider direction="vertical" />
                <el-button icon="el-icon-caret-top" @click="moveUp(scope.$index,scope.row)" />
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="应用名称"
              width="180"
            />
            <el-table-column
              prop="tag"
              label="标识符"
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
                <el-button size="mini" type="text" @click="newItem(scope.row.path)">新建發布</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="cloneItem(scope.row.path)">克隆發布</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="editItem(scope.row.path)">編輯</el-button>
                <el-divider direction="vertical" />
                <el-button size="mini" type="text" @click="deleteItem(scope.row.path)">刪除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog title="新建应用" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="应用名稱" required placeholder="请輸入应用名稱，例如:訂單服務" :label-width="formLabelWidth">
          <el-input autocomplete="off" />
        </el-form-item>
        <el-form-item label="唯一標識符" required placeholder="请輸入唯一標識符，例如:api_order" :label-width="formLabelWidth">
          <el-input autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注信息" :label-width="formLabelWidth">
          <el-input
            v-model="textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入备注信息"
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
      dialogFormVisible: false,
      formLabelWidth: '120px',
      tableData: [{
        id: 1,
        sort: '',
        name: '订单服务',
        tag: 'order',
        description: '订单创建支付等订单相关功能',
        children: [{
          id: 11,
          mode: 'sequence',
          environment: '测试环境',
          associate: '1 台',
          audit: 'close'
        }, {
          id: 12,
          mode: 'sequence',
          environment: '线上环境',
          associate: '1 台',
          audit: 'open'
        }]
      }, {
        id: 2,
        sort: '',
        name: '簡訊服务',
        tag: 'order',
        description: '订单创建支付等订单相关功能',
        children: [{
          id: 21,
          mode: 'parallel',
          environment: '测试环境',
          associate: '1 台',
          audit: 'close'
        }, {
          id: 22,
          mode: 'parallel',
          environment: '线上环境',
          associate: '1 台',
          audit: 'open'
        }]
      }, {
        id: 3,
        sort: '',
        name: '庫存服务',
        tag: 'order',
        description: '订单创建支付等订单相关功能',
        children: [{
          id: 31,
          mode: 'sequence',
          environment: '测试环境',
          associate: '1 台',
          audit: 'close'
        }, {
          id: 32,
          mode: 'parallel',
          environment: '线上环境',
          associate: '1 台',
          audit: 'open'
        }]
      }, {
        id: 4,
        sort: '',
        name: '商品服务',
        tag: 'order',
        description: '订单创建支付等订单相关功能',
        children: [{
          id: 41,
          mode: 'parallel',
          environment: '测试环境',
          associate: '1 台',
          audit: 'close'
        }, {
          id: 42,
          mode: 'sequence',
          environment: '线上环境',
          associate: '1 台',
          audit: 'open'
        }]
      }]
    }
  },
  methods: {
    // 上移
    moveUp(index, row) {
      // console.log('上移', index, row)
      if (index > 0) {
        const upDate = this.tableData[index - 1]
        this.tableData.splice(index - 1, 1)
        this.tableData.splice(index, 0, upDate)
      } else {
        alert('已经是第一条，不可上移')
      }
    },
    // 下移
    moveDown(index, row) {
      // console.log('下移', index, row)
      if ((index + 1) === this.tableData.length) {
        alert('已经是最后一条，不可下移')
      } else {
        const downDate = this.tableData[index + 1]
        this.tableData.splice(index + 1, 1)
        this.tableData.splice(index, 0, downDate)
      }
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
</style>
