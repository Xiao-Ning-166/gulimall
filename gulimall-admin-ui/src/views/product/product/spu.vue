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
        <template slot-scope="{row}">
          <el-tag v-show="row.publishStatus === 0" type="info">新建</el-tag>
          <el-tag v-show="row.publishStatus === 1" type="success">上架</el-tag>
          <el-tag v-show="row.publishStatus === 2" type="danger">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" align="center" />
      <el-table-column prop="updateTime" label="修改时间" width="180" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-upload2"
            size="mini"
            type="primary"
            title="上架"
            @click="handlePutaway(scope.row)"
          />
          <el-button
            icon="el-icon-cpu"
            size="mini"
            title="规格"
            @click="handleAttr(scope.row)"
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

    <el-dialog :title="attrTitle" :visible.sync="attrVisible">
      <el-tabs tab-position="left" style="width:98%">
        <el-tab-pane
          v-for="(group,gidx) in dataResp.attrGroups"
          :key="group.attrGroupId"
          :label="group.attrGroupName"
        >
          <!-- 遍历属性,每个tab-pane对应一个表单，每个属性是一个表单项  spu.baseAttrs[0] = [{attrId:xx,val:}]-->
          <el-form ref="form" :model="dataResp">
            <el-form-item
              v-for="(attr,aidx) in group.attrs"
              :key="attr.attrId"
              :label="attr.attrName"
            >
              <el-input
                v-show="false"
                v-model="dataResp.baseAttrs[gidx][aidx].attrId"
                type="hidden"
              />
              <el-select
                v-model="dataResp.baseAttrs[gidx][aidx].attrValues"
                :multiple="attr.isMultiple === 1"
                filterable
                allow-create
                default-first-option
                placeholder="请选择或输入值"
              >
                <el-option
                  v-for="(val,vidx) in attr.valueSelect.split(',')"
                  :key="vidx"
                  :label="val"
                  :value="val"
                />
              </el-select>
              <el-checkbox
                v-model="dataResp.baseAttrs[gidx][aidx].showDesc"
                :true-label="1"
                :false-label="0"
              >快速展示</el-checkbox>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button @click="attrVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="handleAttrOk()">
          确认
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {
  getSpuList,
  spuPutaway
} from '@/api/product/spu.js'
import { getAttrGroupsWithAttr } from '@/api/product/attributeGroup'
import { getAttrValuesBySpuId, updateAttrValuesBySpuId } from '@/api/product/productAttrValue'
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
      },
      attrTitle: '规格维护',
      attrVisible: false,
      spuId: '',
      catalogId: '',
      dataResp: {
        // 后台返回的所有数据
        attrGroups: [],
        baseAttrs: []
      },
      spuAttrsMap: {}
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
    // 获取规格信息
    handleAttr(row) {
      console.log('fdsfds', row)
      this.attrVisible = true
      this.spuId = row.id
      this.catalogId = row.catalogId
      this.clearData()
      if (this.spuId && this.catalogId) {
        this.showBaseAttrs()
        this.getSpuBaseAttrs()
      }
    },
    clearData() {
      this.dataResp.attrGroups = []
      this.dataResp.baseAttrs = []
      this.spuAttrsMap = {}
    },
    showBaseAttrs() {
      const _this = this
      getAttrGroupsWithAttr(this.catalogId).then((res) => {
        if (res.success) {
          // 对表单的baseAttrs进行初始化
          res.data.forEach(item => {
            const attrArray = []
            item.attrs.forEach(attr => {
              let v = ''
              if (_this.spuAttrsMap['' + attr.attrId]) {
                v = _this.spuAttrsMap['' + attr.attrId].attrValue.split(',')
                if (v.length === 1) {
                  v = v[0] + ''
                }
              }
              attrArray.push({
                attrId: attr.attrId,
                attrValues: v,
                showDesc: attr.showDesc
              })
            })
            this.dataResp.baseAttrs.push(attrArray)
          })
          this.dataResp.attrGroups = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    getSpuBaseAttrs() {
      getAttrValuesBySpuId(this.spuId).then((res) => {
        if (res.success) {
          res.data.forEach(item => {
            this.spuAttrsMap['' + item.attrId] = item
          })
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 确认修改规格信息
    handleAttrOk() {
      console.log('fdsfds', this.dataResp.baseAttrs)
      // spu_id  attr_id  attr_name             attr_value             attr_sort  quick_show
      const submitData = []
      this.dataResp.baseAttrs.forEach(item => {
        item.forEach(attr => {
          let val = ''
          if (attr.attrValues instanceof Array) {
            val = attr.attrValues.join(',')
          } else {
            val = attr.attrValues
          }

          if (val !== '') {
            submitData.push({
              attrId: attr.attrId,
              attrName: attr.attrName,
              attrValue: val,
              quickShow: attr.showDesc
            })
          }
        })
      })
      this.$confirm('修改商品规格信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateAttrValuesBySpuId(this.spuId, submitData).then((res) => {
          if (res.success) {
            this.attrVisible = false
          } else {
            this.$error(res.message)
          }
        })
      }).catch((e) => {
        this.$message.error('修改失败：' + e)
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
    // 商品上架
    handlePutaway(row) {
      console.log(row)
      this.$confirm(`确定要上架商品【${row.spuName}】吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        spuPutaway(row.id).then((res) => {
          if (res.isSuccess) {

          } else {
            this.$message.error(res.message)
          }
        })
      })
    }
  }
}
</script>

<style>
</style>
