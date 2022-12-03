<template>
  <div>
    <el-cascader
      v-model="catelogId"
      :options="categoryData"
      :props="cascaderProps"
      clearable
      filterable
      style="width:100%"
      @change="handleChange"
    />
  </div>
</template>

<script>
import { getCategoryTreeData } from '@/api/product/category'

export default {
  name: 'CategoryCascader',
  description: '分类级联选择',
  props: {
    categoryId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      catelogId: undefined,
      categoryData: [],
      cascaderProps: {
        emitPath: false,
        label: 'name',
        value: 'catId'
      }
    }
  },
  created() {
    // 加载数据
    this.loadData()
  },
  methods: {
    // 加载数据
    loadData() {
      getCategoryTreeData().then((res) => {
        if (res.code === 200) {
          this.categoryData = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 选中结点改变事件
    handleChange(data) {
      console.log('结点变化', data)
      this.$emit('update:categoryId', data)
    }
  }
}
</script>

<style>

</style>
