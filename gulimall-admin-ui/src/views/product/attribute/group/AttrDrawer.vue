<template>
  <div class="app-container">
    <el-drawer
      title="关联规格参数"
      :visible.sync="drawerVisible"
      size="40%"
      @close="drawerClose"
    >
      <!-- 搜索框 -->
      <div class="filter-container">
        <el-button class="filter-item" style="margin-left: 10px;" size="small" type="primary" icon="el-icon-plus" @click="handleAdd">
          批量添加
        </el-button>
        <el-button class="filter-item" style="margin-left: 10px;" size="small" type="danger" icon="el-icon-delete-solid" @click="handleDeleteBatch">
          批量删除
        </el-button>
      </div>

      <el-table
        v-loading="dataLoading"
        :data="attrData"
        stripe
        @selection-change="handleDrawerSelectionChange"
      >
        <el-table-column type="selection" align="center" />
        <el-table-column type="index" label="序号" align="center" />
        <el-table-column prop="attrName" label="参数名称" align="center" />
        <el-table-column prop="valueSelect" label="可选值列表" align="center" />
        <el-table-column label="操作" width="120" fixed="right" align="center">
          <template slot-scope="scope">
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
      <pagination :pagination="drawerPagination" @loadData="loadData" />
    </el-drawer>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-table
        v-loading="dialogTableLoading"
        :data="noAttrData"
        @selection-change="handleDialogSelectionChange"
      >
        <el-table-column type="selection" align="center" />
        <el-table-column type="index" label="序号" align="center" />
        <el-table-column prop="attrName" label="参数名称" align="center" />
        <el-table-column prop="valueSelect" label="可选值列表" align="center" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'
import {
  getAttrsByAttrGroupId,
  getAttrsNoAttrGroup,
  saveRelationBatch,
  deleteBatchById
} from '@/api/product/attributeGroup'
export default {
  name: 'AttrDrawer',
  components: {
    Pagination
  },
  props: {
    attrDrawerVisible: {
      type: Boolean,
      default: false
    },
    attrGroup: {
      type: Object,
      default: () => {
        return {
          // 分组id
          attrGroupId: undefined,
          attrGroupName: '',
          // 所属分类id
          catelogId: 0
        }
      }
    }
  },
  data() {
    return {
      attrData: [],
      // 分页参数
      drawerPagination: {
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
      dataLoading: false,
      dialogVisible: false,
      dialogTitle: '',
      // 抽屉多选
      drawerMultipleSelection: [],
      noAttrData: [],
      // 弹框多选
      dialogMultipleSelection: [],
      // 分页参数
      dialogPagination: {
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
      dialogTableLoading: false,
    }
  },
  computed: {
    drawerVisible: {
      get: function() {
        return this.attrDrawerVisible
      },
      set: function(value) {
        this.$emit('update:attrDrawerVisible', value)
      }
    }
  },
  watch: {
    attrGroupId(newValue) {
      this.loadData(newValue)
    }
  },
  methods: {
    loadData(attrGroupId) {
      this.dataLoading = true
      const params = {
        attrGroupId: attrGroupId,
        current: this.drawerPagination.current,
        size: this.drawerPagination.size
      }
      getAttrsByAttrGroupId(params).then((res) => {
        if (res.code === 200) {
          this.attrData = res.data.records
          // 更新分页条信息
          this.$set(this.drawerPagination, 'current', res.data.current)
          this.$set(this.drawerPagination, 'size', res.data.size)
          this.$set(this.drawerPagination, 'total', res.data.total)

          this.dataLoading = false
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 查询分类下属性分组未关联的属性列表
    loadNoAttrGroupData() {
      const params = {
        catelogId: this.attrGroup.catelogId,
        current: this.dialogPagination.current,
        size: this.dialogPagination.size
      }
      this.dialogTableLoading = true
      getAttrsNoAttrGroup(params).then((res) => {
        if (res.code === 200) {
          this.noAttrData = res.data.records
          // 更新分页条信息
          this.$set(this.drawerPagination, 'current', res.data.current)
          this.$set(this.drawerPagination, 'size', res.data.size)
          this.$set(this.drawerPagination, 'total', res.data.total)

          this.dialogTableLoading = false
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleAdd() {
      // 查询当前分组所属分类下未绑定的属性列表
      console.log('分组', this.attrGroup)

      // 查询分类下属性分组未关联的属性列表
      this.loadNoAttrGroupData()
      this.dialogTitle = '批量添加'
      this.dialogVisible = true
    },
    // 确认添加
    handleAddConfirm: debounce(function() {
      if (this.dialogMultipleSelection.length < 0) {
        this.$message.warning('请选择要批量添加的属性！')
        return
      }
      const attrIds = []
      for (let i = 0; i < this.dialogMultipleSelection.length; i++) {
        var record = this.dialogMultipleSelection[i]
        attrIds.push(record.attrId)
      }
      console.log('批量删除', this.dialogMultipleSelection, attrIds)
      this.$confirm('是否选择批量添加所选属性?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 批量添加
        const data = {
          attrGroupId: this.attrGroup.attrGroupId,
          attrIds: attrIds
        }
        saveRelationBatch(data).then((res) => {
          if (res.code === 200) {
            this.$message.success('添加成功!')
            this.dialogVisible = false
            // 重新加载数据
            this.loadData(this.attrGroup.attrGroupId)
          } else {
            this.$message.error(res.message)
          }
        })
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    // 删除关联
    handleDelete(row) {
      this.$confirm(`确定要从属性分组中移除属性【${row.attrName}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const attrIds = [row.attrId]
        // 删除操作
        this.deleteBatchById(attrIds)
      })
    },
    handleDeleteBatch() {
      if (this.drawerMultipleSelection.length < 0) {
        this.$message.warning('请选择要解除关联的属性！')
        return
      }
      const attrIds = []
      for (let i = 0; i < this.drawerMultipleSelection.length; i++) {
        var record = this.drawerMultipleSelection[i]
        attrIds.push(record.attrId)
      }
      console.log('批量删除', this.drawerMultipleSelection, attrIds)
      this.$confirm('此操作将从属性分组中移除所选属性, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除操作
        this.deleteBatchById(attrIds)
      })
    },
    // 批量删除
    deleteBatchById(attrIds) {
      const data = {
        attrGroupId: this.attrGroup.attrGroupId,
        attrIds: attrIds
      }
      deleteBatchById(data).then((res) => {
        if (res.code === 200) {
          this.$message.success('删除关联成功!')
          this.loadData(this.attrGroup.attrGroupId)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 抽屉关闭
    drawerClose() {
      this.$emit('update:attrDrawerVisible', false)
    },
    // 抽屉表格多选
    handleDrawerSelectionChange(val) {
      this.drawerMultipleSelection = val
    },
    // 弹框表格多选
    handleDialogSelectionChange(val) {
      this.dialogMultipleSelection = val
    },
  }
}
</script>

<style>

</style>
