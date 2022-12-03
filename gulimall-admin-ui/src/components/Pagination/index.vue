<template>
  <div>
    <!-- 分页条 -->
    <el-pagination
      :current-page="pagination.current"
      :page-sizes="pagination.pageSizes"
      :page-size="pagination.size"
      :layout="pagination.layout"
      :total="pagination.total"
      :hide-on-single-page="true"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  description: '分页条组件',
  props: {
    pagination: {
      type: Object,
      default: () => {
        return {
          // 当前页码
          current: 1,
          // 每页可选大小
          pageSizes: [10, 20, 30],
          // 每页大小
          size: 10,
          layout: 'total, sizes, prev, pager, next, jumper',
          // 总记录数
          total: 1
        }
      }
    }
  },
  methods: {
    // 每页记录数改变时触发
    handleSizeChange(size) {
      this.pagination.size = size
      // 更新父组件数据
      this.$emit('pagination.size', size)
      // 重新加载数据
      this.$emit('loadData')
    },
    // 页码改变时触发
    handleCurrentChange(current) {
      this.pagination.current = current
      // 更新父组件数据
      this.$emit('pagination.size', current)
      // 重新加载数据
      this.$emit('loadData')
    }
  }
}
</script>

<style scoped>
  .pagination-container {
    background: #fff;
    padding: 32px 16px;
  }
</style>
