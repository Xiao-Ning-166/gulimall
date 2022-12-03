<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <el-input
        v-model="queryForm.name"
        placeholder="请输入分类名称"
        style="width: 200px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleSearch">
        搜索
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
      :data="categoryList"
      :fit="true"
      style="width: 100%; margin-bottom: 20px"
      row-key="catId"
      border
      :default-expand-all="false"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="分类名称" width="200" header-align="center" />
      <el-table-column prop="catLevel" label="分类层级" width="180" align="center">
        <template slot-scope="{row}">
          <el-tag v-show="row.catLevel == 1">一级分类</el-tag>
          <el-tag v-show="row.catLevel == 2" type="success">二级分类</el-tag>
          <el-tag v-show="row.catLevel == 3" type="warning">三级分类</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否显示" width="150" align="center">
        <template slot-scope="{row}">
          <el-switch
            v-model="row.showStatus"
            :active-value="1"
            :inactive-value="0"
            @change="statusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="180" align="center" />
      <el-table-column prop="productUnit" label="计量单位" width="180" align="center" />
      <el-table-column prop="productCount" label="商品数量" width="180" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.type !== 2"
            type="success"
            icon="el-icon-plus"
            size="mini"
            title="添加下级节点"
            @click="handleCreate(scope.row)"
          />
          <el-button
            icon="el-icon-edit"
            size="mini"
            title="编辑"
            @click="handleEdit(scope.row)"
          />
          <el-button
            v-show="!scope.row.children || scope.row.children.length === 0"
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
    <!-- <el-pagination
      :current-page="pagination.current"
      :page-sizes.sync="pagination.pageSizes"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    /> -->
    <pagination :pagination="pagination" @loadData="loadData" />

    <!-- 新增、编辑弹框 begin -->
    <el-dialog :title="categoryFormTitle" :visible.sync="categoryFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="categoryForm"
        label-position="left"
        label-width="100px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryForm.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="是否显示">
          <el-switch
            v-model="categoryForm.showStatus"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
        <el-form-item label="父级分类">
          <el-select v-model="categoryForm.parentCid" placeholder="请选择上级菜单，不选则为一级分类" style="width: 100%;">
            <el-option :value="categoryForm.parentCid" :label="categoryForm.parentName">
              <el-tree
                ref="selectTree"
                :data="selectTreeData"
                accordion
                node-key="catId"
                :props="selectTreeProps"
                :default-expanded-keys="defaultExpandedKey"
                @node-click="handleNodeClick"
              />
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="序号">
          <el-input-number v-model="categoryForm.sort" :step-strictly="true" controls-position="right" placeholder="请输入序号" />
        </el-form-item>
        <el-form-item label="分类计量单位">
          <el-input v-model="categoryForm.productUnit" placeholder="请输入分类计量单位" />
        </el-form-item>
        <el-form-item label="分类商品数量">
          <el-input v-model="categoryForm.productCount" placeholder="请输入分类商品数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="categoryFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="categoryFormTitle==='新增'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 新增、编辑弹框 end -->

  </div>
</template>

<script>

import {
  getCategoryList,
  updateById,
  deleteById,
  getSelectTreeData,
  addCategory
} from '@/api/product/category.js'
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'

export default {
  name: 'ProductManage',
  components: {
    Pagination
  },
  data() {
    return {
      // 数据加载动画
      dataLoading: false,
      // 列表数据
      categoryList: [],
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
      // 查询参数
      queryForm: {},
      // 新增/修改数据
      categoryForm: {},
      // 表单校验规则
      rules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'change' }],
        type: [{ required: true, message: '请选择菜单类型', trigger: 'change' }]
      },
      // 新增/修改弹框标题
      categoryFormTitle: '',
      // 弹框是否显示
      categoryFormVisible: false,
      // 树形选择框数据
      selectTreeData: [],
      selectTreeProps: {
        label: 'name',
        children: 'children'
      },
      // 树形选择框默认展开
      defaultExpandedKey: []
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
      getCategoryList(params).then((res) => {
        if (res.code === 200) {
          this.categoryList = res.data.records
          this.$set(this.pagination, 'current', res.data.current)
          this.$set(this.pagination, 'size', res.data.size)
          this.$set(this.pagination, 'total', res.data.total)
          this.dataLoading = false
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 状态改变触发
    statusChange(row) {
      console.log('statusChange', row)
      updateById(row).then((res) => {
        if (res.code === 200) {
          this.$message.success('修改成功!')
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 条件搜索
    handleSearch() {
      this.loadData(1)
    },
    // 删除
    handleDelete(row) {
      this.$confirm(`确定要删除分类【${row.name}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById([row.catId]).then((res) => {
          if (res.code === 200) {
            // 重新加载数据
            this.loadData()
            this.$notify({
              title: '成功',
              message: '菜单删除成功!',
              type: 'success'
            })
          } else {
            this.$message.error(res.message)
          }
        })
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
        ids.push(record.catId)
      }
      console.log('批量删除', this.multipleSelection, ids)
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除操作
        deleteById(ids).then((res) => {
          if (res.code === 200) {
            // 重新加载数据
            this.loadData()
            this.$notify({
              title: '成功',
              message: '菜单删除成功!',
              type: 'success'
            })
          } else {
            this.$message.error(res.message)
          }
        })
      })
    },
    // 新增
    handleCreate(row) {
      // 得到树形选择框的数据
      this.getTreeData()
      // 重置表单
      this.resetForm('dataForm')
      if (row.catId !== undefined) {
        this.categoryForm.catLevel = row.catLevel + 1
        this.categoryForm.parentCid = row.catId
        this.categoryForm.parentName = row.name
      }
      this.categoryFormTitle = '新增'
      this.categoryFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
          addCategory(this.categoryForm).then((res) => {
            if (res.code === 200) {
              this.categoryFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '分类添加成功!',
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
    // 重置表单
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields()
        if (formName === 'categoryForm') {
          this.categoryForm.parentCid = 0
          this.categoryForm.parentName = '一级菜单'
        }
      }
    },
    // 修改
    handleEdit(row) {
      console.log('row', row)
      // 得到树形选择框数据
      this.getTreeData()
      // 重置表单
      this.resetForm('dataForm')
      this.categoryForm = row
      console.log('categoryForm', this.categoryForm)
      if (row.parentCid === 0 && row.parentName == null) {
        this.categoryForm.parentName = '一级菜单'
      }
      this.categoryFormTitle = '编辑'
      this.categoryFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
          updateById(this.categoryForm).then((res) => {
            if (res.code === 200) {
              this.categoryFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '分类修改成功!',
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
    // 获取树形选择框数据
    getTreeData() {
      getSelectTreeData().then((res) => {
        if (res.code === 200) {
          this.selectTreeData = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 树形选择框切换选项
    handleNodeClick(node) {
      console.log('node', node)
      this.categoryForm.parentCid = node.parentCid
      this.categoryForm.parentName = node.name
      this.defaultExpandedKey = []
    },
    // 多选变化触发
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 每页记录数改变时触发
    handleSizeChange(size) {
      this.pagination.size = size
      // 重新加载数据
      this.loadData()
    },
    // 页码改变时触发
    handleCurrentChange(current) {
      this.pagination.current = current
      // 重新加载数据
      this.loadData()
    }
  }
}
</script>

<style>
  .el-scrollbar .el-scrollbar__view .el-select-dropdown__item{
    height: auto;
    max-height: 274px;
    padding: 0;
    /* overflow: hidden;
    overflow-y: auto; */
  }
  .el-select-dropdown__item.selected{
    font-weight: normal;
  }
  ul li >>>.el-tree .el-tree-node__content{
    height: auto;
    padding: 0 20px;
  }
  .el-tree-node__label{
    font-weight: normal;
  }
  .el-tree >>>.is-current .el-tree-node__label{
    color: #409EFF;
    font-weight: 700;
  }
  .el-tree >>>.is-current .el-tree-node__children .el-tree-node__label{
    color:#606266;
    font-weight: normal;
  }
</style>
