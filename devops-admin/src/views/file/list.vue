<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-button type="primary" icon="el-icon-remove">移動</el-button>
        <el-button type="primary" icon="el-icon-document-copy">複製</el-button>
        <el-button type="primary" icon="el-icon-s-comment">壓縮</el-button>
        <el-button type="primary" icon="el-icon-share">傳送選取項目</el-button>
        <el-button type="primary" icon="el-icon-download">下載選取項目</el-button>
        <el-button type="danger" icon="el-icon-delete">刪除</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item>
                <el-button type="primary" icon="el-icon-refresh">更新</el-button>
            </el-form-item>
            <el-form-item>
                <el-select v-model="value" clearable placeholder="新增">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-input
                    placeholder="請輸入內容"
                    prefix-icon="el-icon-search"
                    v-model="input">
                </el-input>
            </el-form-item>
        </el-form>


      </el-col>
    </el-row>

    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>活动管理</el-breadcrumb-item>
        <el-breadcrumb-item>活动列表</el-breadcrumb-item>
        <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
        type="selection"
        width="55">
        </el-table-column>
        <el-table-column
        prop="name"
        label="名稱"
        sortable
        width="120">
        </el-table-column>
        <el-table-column
        prop="size"
        label="大小"
        sortable
        width="120">
        </el-table-column>
        <el-table-column
        label="修改日期"
        sortable
        width="120">
            <template slot-scope="scope">{{ scope.row.modified }}</template>
        </el-table-column>
        <el-table-column
        prop="privilege"
        label="權限"
        sortable
        width="120">
        </el-table-column>
        <el-table-column
        prop="owner"
        label="擁有者"
        width="120">
        </el-table-column>
        <el-table-column
        prop="action"
        label="操作"
        show-overflow-tooltip>
        </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
        <el-button @click="toggleSelection()">全選</el-button>
        <el-button @click="toggleSelection()">反向選擇</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [{
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }, {
          modified: '2016-05-03',
          name: '資料夾',
          privilege: '系統管理員',
          owner: '系統管理員'
        }],
        options: [{
          value: '選項1',
          label: '資料夾'
        }, {
          value: '選項2',
          label: '檔案'
        }],
        value: '',
        input: '',
        multipleSelection: []
      }
    },
    methods: {
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      }
    }
  }
</script>

<style>
  .el-row {
    margin-bottom: 20px;
  }
</style>