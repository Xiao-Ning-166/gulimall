<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <CategoryTree @tree-node-click="handleTreeNodeClick" />
    </el-col>
    <el-col :span="18">
      <!-- 搜索框 -->
      <div class="filter-container">
        <el-input
          v-model="queryForm.attrName"
          placeholder="请输入参数名称"
          style="width: 200px;"
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
        :data="attrData"
        stripe
        style="width: 100%; margin-bottom: 20px"
        row-key="attrId"
        border
        fit
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" fixed="left" align="center" />
        <el-table-column type="index" label="序号" width="50" fixed="left" align="center" />
        <el-table-column prop="attrName" label="参数名称" width="100" align="center" />
        <el-table-column prop="valueSelect" label="可选值列表" width="300" align="center" />
        <el-table-column prop="isMultiple" label="值类型" width="80" align="center" >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isMultiple == 1">多选</el-tag>
            <el-tag v-else type="warning">单选</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类" width="100" align="center" />
        <el-table-column label="可检索" width="70" align="center">
          <template slot-scope="scope">
            <i v-if="scope.row.searchType" class="el-icon-success" />
            <i v-else class="el-icon-error" />
          </template>
        </el-table-column>
        <el-table-column label="快速显示" width="80" align="center">
          <template slot-scope="scope">
            <i v-if="scope.row.showDesc" class="el-icon-success" />
            <i v-else class="el-icon-error" />
          </template>
        </el-table-column>
        <el-table-column label="启用状态" width="80" align="center">
          <template slot-scope="scope">
            <i v-if="scope.row.showDesc" class="el-icon-success" />
            <i v-else class="el-icon-error" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right" align="center">
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
      <el-dialog :title="attrFormTitle" :visible.sync="attrFormVisible" @close="dialogClose">
        <el-form
          ref="dataForm"
          :rules="rules"
          :model="attrForm"
          label-position="left"
          label-width="100px"
          style="width: 500px; margin-left:50px;"
        >
          <el-form-item label="参数名称" prop="attrName">
            <el-input v-model="attrForm.attrName" placeholder="请输入参数名称" />
          </el-form-item>
          <el-form-item label="参数类型">
            <el-select v-model="attrForm.attrType" disabled style="width: 400px">
              <el-option :value="0" label="销售属性" />
              <el-option :value="1" label="规格参数" />
            </el-select>
          </el-form-item>
          <el-form-item label="可选值列表">
            <el-select
              v-model="attrForm.valueSelect"
              multiple
              filterable
              allow-create
              default-first-option
              placeholder="请输入内容"
              style="width: 400px"
            />
          </el-form-item>
          <el-form-item label="能否多选">
            <el-switch
              v-model="attrForm.isMultiple"
              active-text="多选"
              inactive-text="单选"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
          <el-form-item label="所属分类" prop="catelogId">
            <category-cascader :catelog-id.sync="attrForm.catelogId" />
          </el-form-item>
          <el-form-item label="是否可检索">
            <el-switch
              v-model="attrForm.searchType"
              active-text="是"
              inactive-text="否"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
          <el-form-item label="是否快速展示">
            <el-switch
              v-model="attrForm.showDesc"
              active-text="是"
              inactive-text="否"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
          <el-form-item label="启用状态">
            <el-switch
              v-model="attrForm.enable"
              active-text="启用"
              inactive-text="禁用"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="attrFormVisible = false">
            关闭
          </el-button>
          <el-button type="primary" @click="attrFormTitle==='新增'?createData():updateData()">
            确认
          </el-button>
        </div>
      </el-dialog>
      <!-- 新增、编辑弹框 end -->
    </el-col>
  </el-row>
</template>

<script>
import CategoryTree from '@/components/CategoryTree/index.vue'
import Pagination from '@/components/Pagination'
import CategoryCascader from '@/components/CategoryCascader'
// 防止重复提交
import debounce from 'lodash/debounce'
import { getAttrGroups } from '@/api/product/attributeGroup'
import {
  getSpecificationList,
  addAttr,
  updateById,
  deleteById
} from '@/api/product/attribute'

export default {
  name: 'SaleAttribute',
  description: '销售属性',
  components: {
    CategoryTree,
    CategoryCascader,
    Pagination
  },
  data() {
    return {
      // 查询参数
      queryForm: {
        attrType: 0
      },
      attrData: [],
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
        total: 10
      },
      // 弹框标题
      attrFormTitle: '',
      // 弹框是否显示
      attrFormVisible: false,
      // 表单校验规则
      rules: {
        attrName: [{ required: true, message: '请输入分组名称', trigger: 'change' }],
        catelogId: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
      },
      attrForm: {
        attrType: 0
      },
      attrGroupList: []
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
      getSpecificationList(params).then((res) => {
        if (res.code === 200) {
          this.attrData = res.data.records
          // 更新分页条信息
          this.$set(this.pagination, 'current', res.data.current)
          this.$set(this.pagination, 'size', res.data.size)
          this.$set(this.pagination, 'total', res.data.total)

          this.dataLoading = false
        } else {
          this.$message.error(res.message)
        }
      }).finally(() => {
        this.dataLoading = false
      })
    },
    // 新增
    handleCreate() {
      // 重置表单
      this.resetForm('dataForm')
      if (this.queryForm.hasOwnProperty('catelogId')) {
        this.attrForm.catelogId = this.queryForm['catelogId']
      }
      this.attrFormTitle = '新增'
      this.attrFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 处理参数
          console.log(this.attrForm['valueSelect'])
          if (this.attrForm['valueSelect'] !== undefined) {
            this.$set(this.attrForm, 'valueSelect', this.attrForm['valueSelect'].join(','))
          }
          console.log('新增', this.attrForm)
          // 新增
          addAttr(this.attrForm).then((res) => {
            if (res.code === 200) {
              this.attrFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '属性添加成功!',
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
      this.$confirm(`确定要删除属性【${row.attrName}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [row.attrId]
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
        ids.push(record.attrId)
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
      deleteById(ids).then((res) => {
        if (res.code === 200) {
          // 重新加载数据
          this.loadData()
          this.$notify({
            title: '成功',
            message: '属性删除成功!',
            type: 'success'
          })
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 修改
    handleEdit(row) {
      console.log('row', row)
      // 重置表单
      this.resetForm('dataForm')
      this.attrForm = { ...row }
      if (row['valueSelect']) {
        this.$set(this.attrForm, 'valueSelect', row['valueSelect'].split(','))
      }
      console.log('attrGroupIds', row['attrGroupIds'])
      if (row['attrGroupIds']) {
        this.$set(this.attrForm, 'attrGroupIds', row['attrGroupIds'])
        console.log('attrGroupIds', this.attrForm.attrGroupIds)
      }
      console.log('attrForm', this.attrForm)
      this.attrFormTitle = '编辑'
      this.attrFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 处理参数
          if (this.attrForm['valueSelect'] !== undefined) {
            this.$set(this.attrForm, 'valueSelect', this.attrForm['valueSelect'].join(','))
          }
          // 修改
          updateById(this.attrForm).then((res) => {
            if (res.code === 200) {
              this.attrFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '属性信息修改成功!',
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
    // 条件搜索
    handleSearch() {
      this.loadData(1)
    },
    // 搜索框重置
    handleReset() {
      this.queryForm = {
        attrType: 0
      }
      this.loadData(1)
    },
    // 重置表单
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields()
      }
    },
    // 树节点点击事件
    handleTreeNodeClick(treeNode) {
      this.$set(this.queryForm, 'catelogId', treeNode.catId)
      // 重新加载数据
      this.loadData()
    },
    // 多选变化触发
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 对话框关闭
    dialogClose() {
      // 重置表单
      this.resetForm('dataForm')
    }
  }
}
</script>

<style>
</style>
