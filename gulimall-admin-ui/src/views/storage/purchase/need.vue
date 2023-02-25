<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <storage-select class="filter-item" :storage-id="queryForm.storageId" />
      <el-select v-model="queryForm.status" class="filter-item" style="margin-left: 10px;" placeholder="请选择状态">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-input
        v-model="queryForm.keyword"
        placeholder="请输入搜索关键词"
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
          <el-dropdown-item icon="el-icon-s-order" @click.native="handleMergeNeed">
            合并到采购单
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="dataLoading"
      :data="needData"
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
      <el-table-column prop="skuNum" label="采购数量" align="center" />
      <el-table-column prop="skuPrice" label="采购金额" align="center" />
      <el-table-column prop="wareName" label="仓库名称" align="center" />
      <el-table-column label="状态" align="center">
        <template slot-scope="{row}">
          <el-tag v-show="row.status === 0">新建</el-tag>
          <el-tag v-show="row.status === 1" type="info">已分配</el-tag>
          <el-tag v-show="row.status === 2" type="warning">正在采购</el-tag>
          <el-tag v-show="row.status === 3" type="success">已完成</el-tag>
          <el-tag v-show="row.status === 4" type="danger">采购失败</el-tag>
        </template>
      </el-table-column>
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

    <!-- 新增、编辑弹框 begin -->
    <el-dialog :title="needFormTitle" :visible.sync="needFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="needForm"
        label-position="left"
        label-width="120px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="采购商品编号" prop="skuId">
          <el-input v-model="needForm.skuId" placeholder="请输入采购商品编号" />
        </el-form-item>
        <el-form-item label="采购数量" prop="skuNum">
          <el-input-number v-model="needForm.skuNum" :min="1" />
        </el-form-item>
        <el-form-item label="采购金额" prop="skuPrice">
          <el-input-number v-model="needForm.skuPrice" :precision="2" />
        </el-form-item>
        <el-form-item label="仓库名称" prop="wareId">
          <storage-select class="filter-item" :storage-id="needForm.wareId" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="needFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="needFormTitle==='新增'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 新增、编辑弹框 end -->

    <!-- 选择采购单弹框 begin -->
    <el-dialog :title="orderFormTitle" :visible.sync="orderFormVisible">
      <el-form
        ref="mergeFormData"
        :rules="mergeFormRules"
        :model="mergeForm"
        label-position="left"
        label-width="120px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="采购单" prop="orderId">
          <el-select v-model="mergeForm.orderId" class="filter-item" style="margin-left: 10px;" placeholder="请选择采购单">
            <el-option
              v-for="item in orders"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="mergeConfirm()">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 选择采购单弹框 end -->

  </div>
</template>

<script>

import {
  getPurchaseNeedList,
  addPurchaseNeed,
  updatePurchaseNeedById,
  deletePurchaseNeedById,
  mergePurchaseNeed
} from '@/api/storage/purchaseNeed.js'
import { getPurchaseOrders } from '@/api/storage/purchaseOrder.js'
import { upload } from '@/utils/oss'
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'
import StorageSelect from '@/components/StorageSelect'

export default {
  name: 'PurchaseNeed',
  description: '采购需求',
  components: {
    Pagination,
    StorageSelect
  },
  data() {
    return {
      // 表格数据
      needData: [],
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
      // 0新建，1已分配，2正在采购，3已完成，4采购失败
      options: [
        {
          value: 0,
          label: '新建'
        },
        {
          value: 1,
          label: '已分配'
        },
        {
          value: 2,
          label: '正在采购'
        },
        {
          value: 3,
          label: '已完成'
        },
        {
          value: 4,
          label: '采购失败'
        }
      ],
      needForm: {
      },
      // 弹框标题
      needFormTitle: '',
      // 弹框是否显示
      needFormVisible: false,
      // 表单校验规则
      rules: {
        skuId: [{ required: true, message: '请输入采购商品编号', trigger: 'blur' }],
        skuNum: [{ required: true, message: '请输入采购数量', trigger: 'blur' }],
        skuPrice: [{ required: true, message: '请输入采购金额', trigger: 'blur' }],
        wareId: [{ required: true, message: '请选择仓库', trigger: 'blur' }]
      },
      orders: [],
      orderFormTitle: '选择采购单',
      orderFormVisible: false,
      mergeFormRules: {
        orderId: [{ required: true, message: '请选择采购单', trigger: 'blur' }]
      },
      mergeForm: {}
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
      getPurchaseNeedList(params).then((res) => {
        if (res.success) {
          this.needData = res.data.records
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
      this.needFormTitle = '新增'
      this.needFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
          addPurchaseNeed(this.needForm).then((res) => {
            if (res.success) {
              this.needFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '采购需求添加成功!',
                type: 'success'
              })
            } else {
              this.$message.error(res.message)
            }
          })
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
      this.needForm = { ...row }
      console.log('needForm', this.needForm)
      this.needFormTitle = '编辑'
      this.needFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
          updatePurchaseNeedById(this.needForm).then((res) => {
            if (res.success) {
              this.needFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '采购需求信息修改成功!',
                type: 'success'
              })
            } else {
              this.$message.error(res.message)
            }
          })
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 删除
    handleDelete(row) {
      this.$confirm(`确定要删除采购需求吗？`, '提示', {
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
      deletePurchaseNeedById(ids).then((res) => {
        if (res.success) {
          // 重新加载数据
          this.loadData()
          this.$notify({
            title: '成功',
            message: '采购需求删除成功!',
            type: 'success'
          })
        } else {
          this.$message.error(res.message)
        }
      })
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
    // 打开选择采购单窗口
    handleMergeNeed() {
      // 获取可用的采购单列表
      getPurchaseOrders().then((res) => {
        if (res.success) {
          this.orders = res.data
          this.mergeForm = {}
          this.orderFormVisible = true
        }
      })
    },
    // 合并确认
    mergeConfirm: debounce(function() {
      this.$refs['mergeFormData'].validate((valid) => {
        if (valid) {
          if (this.multipleSelection.length < 0) {
            this.$message.waring('请先选择要合并的采购需求')
          }
          const ids = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            var record = this.multipleSelection[i]
            ids.push(record.id)
          }
          this.mergeForm.needIds = ids
          // 合并
          mergePurchaseNeed(this.mergeForm).then((res) => {
            if (res.success) {
              this.orderFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '采购需求合并成功!',
                type: 'success'
              })
            } else {
              this.$message.error(res.message)
            }
          })
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
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
    }
  }
}
</script>

<style>
</style>