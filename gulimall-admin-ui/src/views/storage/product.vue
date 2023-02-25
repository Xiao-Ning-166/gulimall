<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <storage-select class="filter-item" :storage-id="queryForm.StorageId" />
      <el-input
        v-model="queryForm.skuId"
        placeholder="请输入商品编号"
        style="width: 200px; margin-left: 10px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleSearch">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-refresh" @click="handleReset">
        重置
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增
      </el-button>

      <!-- 下拉菜单 -->
      <el-dropdown
        v-show="multipleSelection.length > 0"
        class="filter-item"
        style="margin-left: 10px;"
        trigger="click"
      >
        <el-button>
          更多菜单<i class="el-icon-arrow-down el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-delete-solid" @click.native="handleDeleteBatch">
            批量删除
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="dataLoading"
      :data="stockData"
      stripe
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      fit
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="80" align="center" />
      <el-table-column prop="skuId" label="商品编号" align="center" />
      <el-table-column prop="wareName" label="仓库名称" align="center" />
      <el-table-column prop="stock" label="库存数" align="center" />
      <el-table-column prop="skuName" label="商品名称" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            size="mini"
            title="编辑"
            @click="handleEdit(scope.row)"
          />
          <el-button
            icon="el-icon-delete-solid"
            size="mini"
            type="danger"
            title="删除"
            @click="handleDelete(scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <pagination :pagination="pagination" @loadData="loadData" />

  </div>
</template>

<script>

import {
  getProductStockList
} from '@/api/storage/product.js'
import { upload } from '@/utils/oss'
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'
import StorageSelect from '@/components/StorageSelect'

export default {
  name: 'ProductStorage',
  description: '商品库存信息',
  components: {
    Pagination,
    StorageSelect
  },
  data() {
    return {
      // 表格数据
      stockData: [],
      // 加载
      dataLoading: false,
      // 多选
      multipleSelection: [],
      // 分页参数
      pagination: {
        // 当前页码
        current: 1,
        // 每页可选大小
        pageSizes: [10, 20, 30],
        // 每页大小
        size: 10,
        layout: 'total, sizes, prev, pager, next, jumper',
        // 总记录数
        total: 20
      },
      queryForm: {},
      storageForm: {
      },
      // 弹框标题
      storageFormTitle: '',
      // 弹框是否显示
      storageFormVisible: false,
      // 表单校验规则
      rules: {
        name: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }],
        areaCode: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    // 加载数据
    loadData(current) {
      this.dataLoading = true
      // 封装参数
      var params = {
        ...this.queryForm,
        current: current === 1 ? current : this.pagination.current,
        size: this.pagination.size
      }
      console.log('请求参数', params)
      getProductStockList(params).then((res) => {
        if (res.success) {
          this.stockData = res.data.records
          // 更新分页条信息
          this.$set(this.pagination, 'current', res.data.current)
          this.$set(this.pagination, 'size', res.data.size)
          this.$set(this.pagination, 'total', res.data.total)

          this.dataLoading = false
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 条件搜索
    handleSearch() {
      this.loadData(1)
    },
    // 重置
    handleReset() {
      this.queryForm = {}
      this.loadData(1)
    },
    // 新增
    handleCreate(row) {
      // 重置表单
      this.resetForm('dataForm')
      this.storageFormTitle = '新增'
      this.storageFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 修改
    handleEdit(row) {
      console.log('row', row)
      // 重置表单
      this.resetForm('dataForm')
      this.storageForm = { ...row }
      console.log('storageForm', this.storageForm)
      this.storageFormTitle = '编辑'
      this.storageFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 删除
    handleDelete(row) {
      this.$confirm(`确定要删除仓库【${row.name}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [row.id]
        // 删除操作
        this.deleteById(ids)
      })
    },
    // 批量删除
    handleDeleteBatch() {
      if (this.multipleSelection.length < 0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      const ids = []
      for (let i = 0; i < this.multipleSelection.length; i++) {
        var record = this.multipleSelection[i]
        ids.push(record.id)
      }
      console.log('批量删除', this.multipleSelection, ids)
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除操作
        this.deleteById(ids)
      })
    },
    // 调用删除方法
    deleteById(ids) {
    },
    // 重置表单
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields()
      }
    },
    // 多选变化触发
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 上传文件之前调用
    beforeUpload(file) {
      const isPicture = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg'
      const isLt5M = file.size / 1024 / 1024 < 5

      if (!isPicture) {
        this.$message.error('上传图片只能是 jpg、jpeg、png 格式!')
      }
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过 5MB!')
      }
      return isPicture && isLt5M
    },
    // 文件上传
    uploadFile(file) {
      const _this = this
      upload(file.file).then((res) => {
        if (res.code === 200) {
          _this.$set(_this.brandForm, 'logo', res.data)
        } else {
          _this.$message.error(res.message)
        }
      })
      console.log('_this.brandForm', _this.brandForm)
    },
    handleRelation(row) {
      this.categoryRelationForm.brandId = row.brandId
      this.getBrandCategoryList(row.brandId)
    }
  }
}
</script>

<style>
</style>
