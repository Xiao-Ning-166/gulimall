<template>
  <div>
    <el-select
      v-model="selectedBrandId"
      filterable
      clearable
      placeholder="请选择"
      style="width:100%"
      @change="handleChange"
    >
      <el-option
        v-for="item in brands"
        :key="item.brandId"
        :label="item.name"
        :value="item.brandId"
      />
    </el-select>
  </div>
</template>

<script>
import { getBrandsByCategory } from '@/api/product/brand'

export default {
  name: 'BrandSelect',
  description: '品牌选择',
  props: {
    catelogId: {
      type: Number,
      default: 0
    },
    brandId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      selectedBrandId: undefined,
      brands: []
    }
  },
  watch: {
    catelogId(newCatelogId, oldCatelogId) {
      if (newCatelogId !== oldCatelogId) {
        console.log('品牌分类变化')
        this.loadData()
      }
    }
  },
  created() {
    // 加载品牌列表
    this.loadData()
  },
  methods: {
    // 加载品牌列表
    loadData() {
      getBrandsByCategory(this.catelogId).then((res) => {
        if (res.code === 200) {
          this.brands = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 选中值变化的回调
    handleChange(newBrandId) {
      this.$emit('update:brandId', newBrandId)
    }
  }
}
</script>

<style>

</style>
