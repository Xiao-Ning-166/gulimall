<template>
  <div class="app-container">

    <!-- 搜索框 -->
    <div class="filter-container">
      <el-form :inline="true" :model="queryForm" size="small" class="demo-form-inline">
        <el-form-item label="分类">
          <category-cascader :catelog-id.sync="queryForm.catalogId" />
        </el-form-item>
        <el-form-item label="品牌">
          <brand-select :catelog-id.sync="queryForm.catelogId" :brand-id.sync="queryForm.brandId" />
        </el-form-item>
        <el-form-item label="价格区间">
          <el-col :span="11">
            <el-input v-model="queryForm.minPrice" type="number" style="width: 100%;" />
          </el-col>
          <el-col class="line" :span="2" style="text-align: center;">-</el-col>
          <el-col :span="11">
            <el-input v-model="queryForm.maxPrice" type="number" style="width: 100%;" />
          </el-col>
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
      row-key="skuId"
      border
      fit
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="商品标题">
              <span>{{ props.row.skuTitle }}</span>
            </el-form-item>
            <el-form-item label="商品副标题">
              <span>{{ props.row.skuSubtitle }}</span>
            </el-form-item>
            <el-form-item label="商品分类">
              <span>{{ props.row.catalogName }}</span>
            </el-form-item>
            <el-form-item label="商品品牌">
              <span>{{ props.row.brandName }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="55" align="center" />
      <el-table-column prop="skuName" label="名称" width="200" align="center" />
      <el-table-column label="默认图片" :show-overflow-tooltip="true" width="300" align="center">
        <template slot-scope="{row}">
          <el-image
            style="width: 100px; height: 100px"
            :src="row.skuDefaultImg"
            fit="fill"
          />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="价格" width="150" align="center" />
      <el-table-column prop="saleCount" label="销量" width="150" align="center" />
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

  </div>
</template>

<script>

import {
  getSkuList
} from '@/api/product/sku.js'
import { upload } from '@/utils/oss'
// 防止重复提交
import debounce from 'lodash/debounce'
import Pagination from '@/components/Pagination'
import CategoryCascader from '@/components/CategoryCascader'
import BrandSelect from '@/components/BrandSelect'

export default {
  name: 'Sku',
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
      getSkuList(params).then((res) => {
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
