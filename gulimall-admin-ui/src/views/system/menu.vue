<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <el-input v-model="queryForm.name" placeholder="请输入菜单名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleSearch" />
      <el-select v-model="queryForm.type" placeholder="请选择菜单类型" clearable style="width: 200px; margin-left: 10px;" class="filter-item">
        <el-option style="margin-left: 10px;" label="目录" value="0" />
        <el-option style="margin-left: 10px;" label="菜单" value="1" />
        <el-option style="margin-left: 10px;" label="按钮" value="2" />
      </el-select>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleSearch">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增
      </el-button>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="dataLoading"
      :data="menuList"
      :fit="true"
      style="width: 100%; margin-bottom: 20px"
      row-key="menuId"
      border
      :default-expand-all="false"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column prop="menuId" label="编号" width="55" /> -->
      <el-table-column prop="name" label="菜单名称" width="180" align="center" />
      <el-table-column prop="path" label="路由地址" width="180" align="center" />
      <el-table-column prop="component" label="前端组件" width="180" align="center" />
      <el-table-column prop="perms" label="权限标识" width="180" align="center" />
      <el-table-column label="类型" class-name="status-col" width="180" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.type === 0">目录</el-tag>
          <el-tag v-if="row.type === 1" type="success">菜单</el-tag>
          <el-tag v-if="row.type === 2" type="warning">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="图标" class-name="status-col" width="100" align="center">
        <template slot-scope="{row}">
          <span v-if="!row.icon">无</span>
          <i v-else-if="row.icon.includes('el-icon')" :class="row.icon" />
          <svg-icon v-else :icon-class="row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="排序" width="80" align="center" />
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
            @click="edit(scope.row)"
          />
          <el-button
            v-show="scope.row.children.length === 0"
            icon="el-icon-delete-solid"
            size="mini"
            type="danger"
            title="删除"
            @click="handleDelete(scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增、编辑弹框 begin -->
    <el-dialog :title="menuFormTitle" :visible.sync="menuFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="menuForm"
        label-position="left"
        label-width="80px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="menuForm.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="menuForm.type">
            <el-radio :label="0">目录</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="menuForm.type && menuForm.type !== 0" label="上级菜单">
          <el-select v-model="menuForm.parentId" placeholder="请选择上级菜单，不选则为一级菜单" style="width: 100%;">
            <el-option :value="menuForm.parentId" :label="menuForm.parentName">
              <el-tree
                ref="selectTree"
                :data="treeData"
                accordion
                node-key="menuId"
                :props="treeProps"
                :default-expanded-keys="defaultExpandedKey"
                @node-click="handleNodeClick"
              />
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="menuForm.type !== 2" label="菜单路径" prop="path">
          <el-input v-model="menuForm.path" placeholder="请输入菜单路径" />
        </el-form-item>
        <el-form-item v-if="menuForm.type !== 2" label="前端组件" prop="component">
          <el-input v-model="menuForm.component" placeholder="请输入前端组件" />
        </el-form-item>
        <el-form-item v-if="menuForm.type !== 2" label="序号">
          <el-input v-model="menuForm.orderNum" placeholder="请输入序号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="menuFormTitle==='新增'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 新增、编辑弹框 end -->

  </div>
</template>

<script>

import { getMenuList, getTreeData, deleteMenuById, addMenu, editMenu } from '@/api/system/menu'
// 防止重复提交
import debounce from 'lodash/debounce'

export default {
  name: 'MenuManage',
  data() {
    // 校验path、component非按钮时必填
    var validatePathAndComponent = (rule, value, callback) => {
      if (this.menuForm.type !== 2 && value === '') {
        callback(new Error('菜单不是按钮时，此属性必填!'))
      } else {
        callback()
      }
    }
    return {
      // 列表数据
      menuList: [],
      multipleSelection: [],
      // 数据加载loading
      dataLoading: false,
      // 查询条件
      queryForm: {},
      titleMap: {
        update: '编辑',
        create: '新增'
      },
      // 菜单表单
      menuForm: {
        parentId: '0',
        parentName: '一级菜单'
      },
      // 弹框标题
      menuFormTitle: '',
      // 表单校验规则
      rules: {
        name: [{ required: true, message: '请输入菜单名称', trigger: 'change' }],
        type: [{ required: true, message: '请选择菜单类型', trigger: 'change' }],
        path: [{ trigger: 'blur', validator: validatePathAndComponent }],
        component: [{ trigger: 'blur', validator: validatePathAndComponent }]
      },
      menuFormVisible: false,
      treeData: [],
      treeProps: {
        label: 'name',
        children: 'children'
      },
      parentMenuId: '',
      parentMenuName: '',
      defaultExpandedKey: []
    }
  },
  created() {
    // 加载数数据
    this.loadData()
  },
  methods: {
    loadData() {
      this.dataLoading = true
      getMenuList().then((res) => {
        if (res.code === 200) {
          this.menuList = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).finally(() => {
        this.dataLoading = false
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 条件搜索
    handleSearch() {
      this.dataLoading = true
      getMenuList(this.queryForm).then((res) => {
        if (res.code === 200) {
          this.menuList = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).finally(() => {
        this.dataLoading = false
      })
    },
    // 重置表单
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields()
        if (formName === 'menuForm') {
          this.menuForm.parentId = '0'
          this.menuForm.parentName = '一级菜单'
        }
      }
    },
    // 新增
    handleCreate(row) {
      // 得到树形选择框的数据
      this.getTreeData()
      // 重置表单
      this.resetForm('dataForm')
      if (row.menuId !== undefined) {
        this.menuForm.type = row.type + 1
        this.menuForm.parentId = row.menuId
        this.menuForm.parentName = row.name
      }
      this.menuFormTitle = '新增'
      this.menuFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
          addMenu(this.menuForm).then((res) => {
            if (res.code === 200) {
              this.menuFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '菜单添加成功!',
                type: 'success'
              })
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
          editMenu(this.menuForm).then((res) => {
            if (res.code === 200) {
              this.menuFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '菜单修改成功!',
                type: 'success'
              })
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 新增/修改
    edit(row) {
      // 得到树形选择框的数据
      this.getTreeData()
      // 重置表单
      this.resetForm('dataForm')
      this.menuForm = row
      this.parentMenuId = row.parentId
      this.parentMenuName = row.parentName
      this.menuFormTitle = '编辑'
      this.menuFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 删除菜单
    handleDelete(menu) {
      this.$confirm(`确定要删除菜单【${menu.name}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMenuById(menu.menuId).then((res) => {
          if (res.code === 200) {
            // 重新加载数据
            this.loadData()
            this.$notify({
              title: '成功',
              message: '菜单删除成功!',
              type: 'success'
            })
          } else {
            this.$message.error(res.msg)
          }
        })
      })
    },
    // 获取树形选择框数据
    getTreeData() {
      getTreeData().then((res) => {
        if (res.code === 200) {
          this.treeData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    // 树形选择框切换选项
    handleNodeClick(node) {
      console.log('node', node)
      this.menuForm.parentId = node.menuId
      this.menuForm.parentName = node.name
      this.defaultExpandedKey = []
    }
  }
}
</script>

<style scoped>
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
