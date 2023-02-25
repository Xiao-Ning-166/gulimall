<template>
  <div>
    <el-select
      v-model="selectedStorageId"
      filterable
      clearable
      placeholder="请选择仓库"
      style="width:100%"
      @change="handleChange"
    >
      <el-option
        v-for="item in storages"
        :key="item.id"
        :label="item.name"
        :value="item.id"
      />
    </el-select>
  </div>
</template>

<script>
import { getStorageInfoList } from '@/api/storage/info'

export default {
  name: 'StorageSelect',
  description: '仓库选择',
  props: {
    storageId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      selectedStorageId: this.storageId,
      storages: []
    }
  },
  created() {
    // 加载品牌列表
    this.loadData()
  },
  methods: {
    // 加载品牌列表
    loadData() {
      const params = {
        'size': 500
      }
      getStorageInfoList(params).then((res) => {
        if (res.success) {
          this.storages = res.data.records
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 选中值变化的回调
    handleChange(newStorageId) {
      this.$emit('update:storageId', newStorageId)
    }
  }
}
</script>

<style>

</style>
