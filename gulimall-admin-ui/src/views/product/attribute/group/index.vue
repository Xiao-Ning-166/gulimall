<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <CategoryTree @tree-node-click="handleTreeNodeClick" />
    </el-col>
    <el-col :span="18">
      <!-- 搜索框 -->
      <div class="filter-container">
        <el-input
          v-model="queryForm.attrGroupName"
          placeholder="请输入分组名称"
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
        :data="attrGroupData"
        stripe
        style="width: 100%; margin-bottom: 20px"
        row-key="attrGroupId"
        border
        fit
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" label="序号" width="50" align="center" />
        <el-table-column prop="attrGroupName" label="分组名称" width="100" align="center" />
        <el-table-column prop="descript" label="分组介绍" width="300" align="center" />
        <el-table-column prop="sort" label="排序" width="80" align="center" />
        <el-table-column prop="" label="所属分类" width="100" align="center" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-connection"
              size="mini"
              title="关联规格参数"
              @click="handleRelation(scope.row)"
            />
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
      <el-dialog :title="attrGroupFormTitle" :visible.sync="attrGroupFormVisible" @close="dialogClose">
        <el-form
          ref="dataForm"
          :rules="rules"
          :model="attrGroupForm"
          label-position="left"
          label-width="100px"
          style="width: 500px; margin-left:50px;"
        >
          <el-form-item label="分组名称" prop="attrGroupName">
            <el-input v-model="attrGroupForm.attrGroupName" placeholder="请输入分组名称" />
          </el-form-item>
          <el-form-item label="所属分类">
            <el-cascader
              v-model="attrGroupForm.catelogId"
              :options="categoryData"
              :show-all-levels="false"
              :props="cascaderProps"
              clearable
              filterable
            />
          </el-form-item>
          <el-form-item label="属性分组介绍" prop="descript">
            <el-input
              v-model="attrGroupForm.descript"
              type="textarea"
              placeholder="请输入属性分组介绍"
              maxlength="200"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="序号">
            <el-input-number v-model="attrGroupForm.sort" :step-strictly="true" controls-position="right" placeholder="请输入序号" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="attrGroupFormVisible = false">
            关闭
          </el-button>
          <el-button type="primary" @click="attrGroupFormTitle==='新增'?createData():updateData()">
            确认
          </el-button>
        </div>
      </el-dialog>
      <!-- 新增、编辑弹框 end -->

      <attr-drawer
        ref="attrDrawer"
        :attr-drawer-visible.sync="relationAttrDrawerVisible"
        :attr-group="attrGroup"
      />
    </el-col>
  </el-row>
</template>

<script>
import CategoryTree from '@/components/CategoryTree/index.vue'
import Pagination from '@/components/Pagination'
import AttrDrawer from './AttrDrawer.vue'
// 防止重复提交
import debounce from 'lodash/debounce'
import { getCategoryTreeData } from '@/api/product/category'
import {
  getAttrGroupList,
  deleteById,
  addAttrGroup,
  updateById
} from '@/api/product/attributeGroup'

export default {
  name: 'AttributeGroup',
  description: '属性分组',
  components: {
    CategoryTree,
    Pagination,
    AttrDrawer
  },
  data() {
    return {
      // 查询参数
      queryForm: {
        // attrGroupName: '',
        // catelogId: 0
      },
      attrGroupData: [],
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
      attrGroupForm: {
        attrGroupId: '',
        attrGroupName: '',
        descript: '',
        sort: ''
      },
      // 弹框标题
      attrGroupFormTitle: '',
      // 弹框是否显示
      attrGroupFormVisible: false,
      // 表单校验规则
      rules: {
        attrGroupName: [{ required: true, message: '请输入分组名称', trigger: 'change' }],
        type: [{ required: true, message: '请选择菜单类型', trigger: 'change' }]
      },
      // 分类数据
      categoryData: [],
      cascaderProps: {
        emitPath: false,
        label: 'name',
        value: 'catId'
      },
      relationAttrDrawerVisible: false,
      attrGroup: undefined
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
      getAttrGroupList(params).then((res) => {
        if (res.code === 200) {
          this.attrGroupData = res.data.records
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
    // 条件搜索
    handleSearch() {
      this.loadData()
    },
    // 新增
    handleCreate() {
      // 重置表单
      this.resetForm('dataForm')
      if (this.queryForm.hasOwnProperty('catelogId')) {
        this.attrGroupForm.catelogId = this.queryForm['catelogId']
      }
      // 获取分类数据
      this.getCategoryData()
      this.attrGroupFormTitle = '新增'
      this.attrGroupFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
          addAttrGroup(this.attrGroupForm).then((res) => {
            if (res.code === 200) {
              this.attrGroupFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '属性分组添加成功!',
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
      this.$confirm(`确定要删除属性分组【${row.attrGroupName}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [row.attrGroupId]
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
        ids.push(record.attrGroupId)
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
            message: '属性分组删除成功!',
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
      // 获取分类数据
      this.getCategoryData()
      // 重置表单
      this.resetForm('dataForm')
      this.attrGroupForm = { ...row }
      console.log('attrGroupForm', this.attrGroupForm)
      this.attrGroupFormTitle = '编辑'
      this.attrGroupFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
          updateById(this.attrGroupForm).then((res) => {
            if (res.code === 200) {
              this.attrGroupFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '属性分组信息修改成功!',
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
      }
    },
    // 重置搜索框
    handleReset() {
      this.queryForm = {}
      this.loadData(1)
    },
    // 获取分类数据
    getCategoryData() {
      getCategoryTreeData().then((res) => {
        if (res.code === 200) {
          this.categoryData = res.data
        } else {
          this.$message.error(res.message)
        }
      })
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
    },
    handleRelation(row) {
      this.attrGroup = row
      this.relationAttrDrawerVisible = true
      this.$refs.attrDrawer.loadData(row.attrGroupId)
    }
  }
}
</script>

<style>

</style>
