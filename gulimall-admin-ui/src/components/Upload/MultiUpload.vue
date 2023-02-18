<template>
  <div>
    <el-upload
      action="#"
      list-type="picture-card"
      :file-list="fileList"
      :on-remove="handleRemove"
      :on-preview="handlePreview"
      :limit="maxCount"
      :on-exceed="handleExceed"
      :http-request="uploadFile"
    >
      <i class="el-icon-plus" />
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>

<script>
import { upload } from '@/utils/oss'

export default {
  name: 'MultiUpload',
  description: '多文件上传',
  props: {
    // 图片属性数组
    files: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 最大上传图片数量
    maxCount: {
      type: Number,
      default: 30
    }
  },
  data() {
    return {
      dialogVisible: false,
      dialogImageUrl: null
    }
  },
  computed: {
    fileList() {
      const fileList = []
      for (let i = 0; i < this.files.length; i++) {
        fileList.push({ url: this.files[i] })
      }

      return fileList
    }
  },
  methods: {
    // 向父组件同步数据
    syncData(fileList) {
      const fileUrls = []
      for (let i = 0; i < fileList.length; i++) {
        fileUrls.push(fileList[i].url)
      }
      this.$emit('input', fileUrls)
    },
    // 删除图片
    handleRemove(file, fileList) {
      this.emitInput(fileList)
    },
    // 预览图片
    handlePreview(file) {
      this.dialogVisible = true
      this.dialogImageUrl = file.url
    },
    // 文件上传
    uploadFile(file) {
      const _this = this
      upload(file.file).then((res) => {
        if (res.code === 200) {
          file.url = res.data
          this.fileList.push({
            name: file.name,
            url: res.data
          })
          this.syncData(this.fileList)
          console.log('文件上传===>', file)
        } else {
          _this.$message.error(res.message)
        }
      })
    },
    // 上传数量超出限制
    handleExceed(files, fileList) {
      this.$message({
        message: '最多只能上传' + this.maxCount + '张图片',
        type: 'warning',
        duration: 1000
      })
    }
  }
}
</script>

<style>

</style>
