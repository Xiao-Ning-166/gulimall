<template>
  <div>
    <el-input
      v-model="filterText"
      placeholder="输入分类名称进行查找"
      class="filter-container"
    />
    <el-tree
      ref="tree"
      :data="categoryData"
      :props="defaultProps"
      accordion
      :filter-node-method="filterNode"
      :highlight-current="true"
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script>
import { getCategoryTreeData } from '@/api/product/category'

export default {
  name: 'CategoryTree',
  description: '分类树',
  data() {
    return {
      categoryData: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      filterText: ''
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
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
    // 过滤结点
    filterNode(value, data) {
      if (!value) {
        return true
      }
      return data.name.indexOf(value) !== -1
    },
    handleNodeClick(data) {
      if (!data.children || data.children.length === 0) {
        console.log('点击了', data)
        // 传给父组件
        this.$emit('tree-node-click', data)
      }
    }
  }
}
</script>

<style>

</style>
