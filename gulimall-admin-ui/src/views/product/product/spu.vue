<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <el-form :inline="true" :model="queryForm" class="demo-form-inline">
        <el-form-item label="分类">
          <category-cascader :catelog-id.sync="queryForm.catalogId" />
        </el-form-item>
        <el-form-item label="品牌">
          <brand-select :catelog-id.sync="queryForm.catelogId" :brand-id.sync="queryForm.brandId" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.publishStatus">
            <el-option label="新建" value="0" />
            <el-option label="上架" value="1" />
            <el-option label="下架" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="请输入关键词" />
        </el-form-item>
        <el-form-item>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleSearch">
            搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-refresh" @click="handleReset">
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增
      </el-button> -->

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
      :data="spuData"
      stripe
      style="width: 100%; margin-bottom: 20px"
      row-key="brandId"
      border
      fit
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="55" align="center" />
      <el-table-column prop="spuName" label="名称" width="150" align="center" />
      <el-table-column prop="spuDescription" label="描述" :show-overflow-tooltip="true" width="300" align="center" />
      <el-table-column prop="catalogName" label="分类" width="100" align="center" />
      <el-table-column prop="brandName" label="品牌" width="100" align="center" />
      <el-table-column prop="publishStatus" label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.publishStatus === 0" type="info">新建</el-tag>
          <el-tag v-show="scope.publishStatus === 1" type="success">上架</el-tag>
          <el-tag v-show="scope.publishStatus === 2" type="danger">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" align="center" />
      <el-table-column prop="updateTime" label="修改时间" width="180" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-connection"
            size="mini"
            type="primary"
            title="上架"
            @click="handleRelation(scope.row)"
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
    <el-dialog :title="brandFormTitle" :visible.sync="brandFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="brandForm"
        label-position="left"
        label-width="100px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="品牌名称" prop="name">
          <el-input v-model="brandForm.name" placeholder="请输入品牌名称" />
        </el-form-item>
        <el-form-item label="是否显示">
          <el-switch
            v-model="brandForm.showStatus"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
        <el-form-item label="品牌logo" prop="logo">
          <el-upload
            action="none"
            list-type="picture-card"
            :show-file-list="false"
            :before-upload="beforeUpload"
            :http-request="uploadFile"
            :multiple="false"
          >
            <img v-if="brandForm.logo" width="100%" :src="brandForm.logo">
            <i v-else class="el-icon-plus" />
          </el-upload>
        </el-form-item>
        <el-form-item label="检索首字母" prop="firstLetter">
          <el-input v-model="brandForm.firstLetter" placeholder="请输入检索首字母" />
        </el-form-item>
        <el-form-item label="品牌介绍" prop="descript">
          <el-input
            v-model="brandForm.descript"
            type="textarea"
            placeholder="请输入品牌介绍"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="序号">
          <el-input-number v-model="brandForm.sort" :step-strictly="true" controls-position="right" placeholder="请输入序号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="brandFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="brandFormTitle==='新增'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 新增、编辑弹框 end -->

    <!-- 关联分类弹框 begin -->
    <el-dialog title="关联分类" :visible.sync="relationCategoryVisible" width="23%">
      <el-popover
        v-model="popoverVisible"
        placement="right-end"
        title="选择分类"
        width="350"
        trigger="click"
      >
        <category-cascader :catelog-id.sync="categoryRelationForm.catelogId" />
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="popoverVisible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="handleSaveRelation">确定</el-button>
        </div>
        <el-button slot="reference">进行关联</el-button>
      </el-popover>
      <el-table :data="relationCategoryList">
        <el-table-column type="index" label="序号" width="50" align="center" />
        <!-- <el-table-column property="catelogId" label="分类id" width="100" /> -->
        <el-table-column property="catelogName" label="分类名称" width="200" align="center" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-delete-solid"
              size="mini"
              type="danger"
              title="删除"
              @click="handleRemoveRelation(scope.row)"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 关联分类弹框 end -->
  </div>
</template>

<script>

import {
  getSpuList
} from '@/api/product/spu.js'
import { upload } from '@/utils/oss'
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'
import CategoryCascader from '@/components/CategoryCascader'
import BrandSelect from '@/components/BrandSelect'

export default {
  name: 'Spu',
  components: {
    Pagination,
    CategoryCascader,
    BrandSelect
  },
  data() {
    return {
      // 表格数据
      spuData: [],
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
      brandForm: {
        // 默认显示
        showStatus: 1,
        logo: '',
        sort: 0
      },
      // 弹框标题
      brandFormTitle: '',
      // 弹框是否显示
      brandFormVisible: false,
      // 表单校验规则
      rules: {
        name: [{ required: true, message: '请输入品牌名称', trigger: 'blur' }],
        logo: [{ required: true, message: '请上传品牌logo', trigger: 'blur' }],
        firstLetter: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('请输入检索首字母!'))
              } else if (!/^[a-zA-Z]$/.test(value)) {
                callback(new Error('检索首字母必须在a-z或A-Z范围!'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      },
      relationCategoryList: [],
      relationCategoryVisible: false,
      popoverVisible: false,
      categoryRelationForm: {
        brandId: undefined,
        catelogId: 0
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
      getSpuList(params).then((res) => {
        if (res.code === 200) {
          this.spuData = res.data.records
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
      this.brandFormTitle = '新增'
      this.brandFormVisible = true
    },
    // 新增确认
    createData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 新增
          addBrand(this.brandForm).then((res) => {
            if (res.code === 200) {
              this.brandFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '品牌添加成功!',
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
      this.brandForm = { ...row }
      console.log('brandForm', this.brandForm)
      this.brandFormTitle = '编辑'
      this.brandFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 修改确认
    updateData: debounce(function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 修改
          updateById(this.brandForm).then((res) => {
            if (res.code === 200) {
              this.brandFormVisible = false
              // 重新加载数据
              this.loadData()
              this.$notify({
                title: '成功',
                message: '品牌信息修改成功!',
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
      this.$confirm(`确定要删除品牌【${row.name}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [row.brandId]
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
        ids.push(record.brandId)
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
            message: '品牌删除成功!',
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
    // // 每页记录数改变时触发
    // handleSizeChange(size) {
    //   this.pagination.size = size
    //   // 重新加载数据
    //   this.loadData()
    // },
    // // 页码改变时触发
    // handleCurrentChange(current) {
    //   this.pagination.current = current
    //   // 重新加载数据
    //   this.loadData()
    // },
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
    },
    // 得到品牌关联的分类列表
    getBrandCategoryList(brandId) {
      // 查询当前品牌已经关联的分类列表
      getRelationCategoryList(brandId).then((res) => {
        if (res.code === 200) {
          this.relationCategoryList = res.data
          this.relationCategoryVisible = true
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 保存关系
    handleSaveRelation() {
      console.log('关系', this.categoryRelationForm)
      // 保存关系
      saveBrandCategoryRelation(this.categoryRelationForm).then((res) => {
        if (res.code === 200) {
          this.getBrandCategoryList(this.categoryRelationForm.brandId)
          this.popoverVisible = false
        } else {
          res.$message.error(res.message)
        }
      })
    },
    // 删除关系
    handleRemoveRelation(row) {
      this.$confirm(`确定要解除和分类【${row.catelogName}】的关联吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [row.id]
        // 删除操作
        removeRelation(ids).then((res) => {
          if (res.code === 200) {
            // 重新加载数据
            this.getBrandCategoryList(this.categoryRelationForm.brandId)
            this.$notify({
              title: '成功',
              message: '关联解除成功!',
              type: 'success'
            })
          }
        })
      })
    }
  }
}
</script>

<style>
</style>
