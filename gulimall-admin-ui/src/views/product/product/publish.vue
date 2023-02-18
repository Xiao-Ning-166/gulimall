<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24">
        <el-steps :active="step" finish-status="success">
          <el-step title="基本信息" />
          <el-step title="规格参数" />
          <el-step title="销售属性" />
          <el-step title="SKU信息" />
          <el-step title="保存完成" />
        </el-steps>
      </el-col>

      <!-- 基本信息 begin -->
      <el-col v-show="step === 0" :span="24">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-form ref="spuBaseForm" :model="spu" label-width="120px" :rules="spuBaseInfoRules">
            <el-form-item label="商品名称" prop="spuName">
              <el-input v-model="spu.spuName" />
            </el-form-item>
            <el-form-item label="商品描述" prop="spuDescription">
              <el-input v-model="spu.spuDescription" />
            </el-form-item>
            <el-form-item label="选择分类" prop="catelogId">
              <category-cascader :catelog-id.sync="spu.catelogId" />
            </el-form-item>
            <el-form-item label="选择品牌" prop="brandId">
              <BrandSelect :catelog-id.sync="spu.catelogId" :brand-id.sync="spu.brandId" />
            </el-form-item>
            <el-form-item label="商品重量(Kg)" prop="weight">
              <el-input-number v-model.number="spu.weight" :min="0" :precision="3" :step="0.1" />
            </el-form-item>
            <el-form-item label="设置积分" prop="bounds">
              <label>金币 </label>
              <el-input-number
                v-model="spu.bounds.buyBounds"
                style="width:130px"
                placeholder="金币"
                :min="0"
                controls-position="right"
              />
              <label style="margin-left:15px">成长值 </label>
              <el-input-number
                v-model="spu.bounds.growBounds"
                style="width:130px"
                placeholder="成长值"
                :min="0"
                controls-position="right"
              >
                <template slot="prepend">成长值</template>
              </el-input-number>
            </el-form-item>
            <el-form-item label="商品介绍" prop="decript">
              <multi-upload v-model="spu.decript" />
            </el-form-item>

            <el-form-item label="商品图集" prop="images">
              <multi-upload v-model="spu.images" />
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="step0Finished">下一步：设置基本参数</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <!-- 基本信息 end -->

      <!-- 规格参数 begin -->
      <el-col v-show="step === 1" :span="24">
        <el-card class="box-card" style="width:80%; margin:20px auto">
          <el-tabs tab-position="left" style="width:98%">
            <el-tab-pane
              v-for="(group,gidx) in dataResp.attrGroups"
              :key="group.attrGroupId"
              :label="group.attrGroupName"
            >
              <!-- 遍历属性,每个tab-pane对应一个表单，每个属性是一个表单项  spu.baseAttrs[0] = [{attrId:xx,val:}]-->
              <el-form ref="form" :model="spu" label-width="80px">
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
                    style="margin-left: 10px"
                  >快速展示</el-checkbox>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
          <div style="margin:auto">
            <el-button type="primary" @click="step = 0">上一步</el-button>
            <el-button type="success" @click="step1Finished">下一步：设置销售属性</el-button>
          </div>
        </el-card>
      </el-col>
      <!-- 规格参数 end -->

      <!-- 销售属性 begin -->
      <el-col v-show="step == 2" :span="24">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>选择销售属性</span>
              <el-form ref="saleform" :model="spu">
                <el-form-item
                  v-for="(attr,aidx) in dataResp.saleAttrs"
                  :key="attr.attrId"
                  :label="attr.attrName"
                >
                  <el-input
                    v-show="false"
                    v-model="dataResp.tempSaleAttrs[aidx].attrId"
                    type="hidden"
                  />
                  <el-checkbox-group v-model="dataResp.tempSaleAttrs[aidx].attrValues">
                    <el-checkbox
                      v-for="val in dataResp.saleAttrs[aidx].valueSelect.split(',')"
                      v-show="dataResp.saleAttrs[aidx].valueSelect != ''"
                      :key="val"
                      :label="val"
                    />
                    <div style="margin-left:20px;display:inline">
                      <el-button
                        v-show="!inputVisible[aidx].view"
                        class="button-new-tag"
                        size="mini"
                        @click="showInput(aidx)"
                      >+自定义</el-button>
                      <el-input
                        v-show="inputVisible[aidx].view"
                        :ref="'saveTagInput'+aidx"
                        v-model="inputValue[aidx].val"
                        size="mini"
                        style="width:150px"
                        @keyup.enter.native="handleInputConfirm(aidx)"
                        @blur="handleInputConfirm(aidx)"
                      />
                    </div>
                  </el-checkbox-group>
                </el-form-item>
              </el-form>
            </div>
            <el-button type="primary" @click="step = 1">上一步</el-button>
            <el-button type="success" @click="step2Finished">下一步：设置SKU信息</el-button>
          </el-card>
        </el-card>
      </el-col>
      <!-- 销售属性 end -->

      <!-- 设置sku信息 -->
      <el-col v-show="step == 3" :span="24">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-table :data="spu.skus" style="width: 100%">
            <el-table-column label="属性组合">
              <el-table-column
                v-for="(item,index) in dataResp.tableAttrColumn"
                :key="item.attrId"
                :label="item.attrName"
              >
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.attr[index].attrValue }}</span>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="商品名称" prop="skuName">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuName" />
              </template>
            </el-table-column>
            <el-table-column label="标题" prop="skuTitle">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuTitle" />
              </template>
            </el-table-column>
            <el-table-column label="副标题" prop="skuSubtitle">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuSubtitle" />
              </template>
            </el-table-column>
            <el-table-column label="价格" prop="price">
              <template slot-scope="scope">
                <el-input v-model="scope.row.price" />
              </template>
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="scope">
                <el-row>
                  <el-col :span="24">
                    <label style="display:block;float:left">选择图集 或</label>
                    <multi-upload v-model="uploadImages" :show-file="false" :list-type="'text'" style="float:left;margin-left:10px;" />
                  </el-col>
                  <el-col :span="24">
                    <el-divider />
                  </el-col>
                  <el-col :span="24">
                    <el-card
                      v-for="(img,index) in spu.images"
                      :key="index"
                      style="width:170px;float:left;margin-left:15px;margin-top:15px;"
                      :body-style="{ padding: '0px' }"
                    >
                      <img :src="img" style="width:160px;height:120px">
                      <div style="padding: 14px;">
                        <el-row>
                          <el-col :span="12">
                            <el-checkbox v-model="scope.row.images[index].imgUrl" :true-label="img" false-label />
                          </el-col>
                          <el-col :span="12">
                            <el-tag v-if="scope.row.images[index].defaultImg == 1">
                              <input type="radio" checked :name="scope.row.skuName" @change="setDefaultImg(scope.row,index,img)">设为默认
                            </el-tag>
                            <el-tag v-else>
                              <input type="radio" :name="scope.row.skuName" @change="setDefaultImg(scope.row,index,img)">设为默认
                            </el-tag>
                          </el-col>
                        </el-row>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
                <!-- 折扣，满减，会员价 -->
                <el-form :model="scope.row">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="设置折扣">
                        <label>满</label>
                        <el-input-number v-model="scope.row.fullCount" style="width:160px" :min="0" controls-position="right" />
                        <label>件</label>

                        <label style="margin-left:15px;">打</label>
                        <el-input-number
                          v-model="scope.row.discount"
                          style="width:160px"
                          :precision="2"
                          :max="1"
                          :min="0"
                          :step="0.01"
                          controls-position="right"
                        />
                        <label>折</label>
                        <el-checkbox v-model="scope.row.countStatus" :true-label="1" :false-label="0">可叠加优惠</el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="设置满减">
                        <label>满</label>
                        <el-input-number v-model="scope.row.fullPrice" style="width:160px" :step="100" :min="0" controls-position="right" />
                        <label>元</label>
                        <label style="margin-left:15px;">减</label>
                        <el-input-number v-model="scope.row.reducePrice" style="width:160px" :step="10" :min="0" controls-position="right" />
                        <label>元</label>
                        <el-checkbox v-model="scope.row.priceStatus" :true-label="1" :false-label="0">可叠加优惠</el-checkbox>
                      </el-form-item>
                    </el-col>

                    <el-col :span="24">
                      <el-form-item v-if="scope.row.memberPrice.length>0" label="设置会员价">
                        <br>
                        <!--   @change="handlePriceChange(scope,mpidx,$event)" -->
                        <el-form-item v-for="(mp,mpidx) in scope.row.memberPrice" :key="mp.id">
                          {{ mp.name }}
                          <el-input-number v-model="scope.row.memberPrice[mpidx].price" style="width:160px" :precision="2" :min="0" controls-position="right" />
                        </el-form-item>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="step = 2">上一步</el-button>
          <el-button type="success" @click="saveSpu">下一步：保存商品信息</el-button>
        </el-card>
      </el-col>

      <el-col v-show="step == 4" :span="24" />
    </el-row>

  </div>
</template>

<script>
import CategoryCascader from '@/components/CategoryCascader'
import BrandSelect from '@/components/BrandSelect'
import MultiUpload from '@/components/Upload/MultiUpload'
import { getMemberLevelList } from '@/api/member/memberLevel'
import { getAttrGroupsWithAttr } from '@/api/product/attributeGroup'
import { searchSaleAttributes } from '@/api/product/attribute'
import { saveSpu } from '@/api/product/spu'

export default {
  name: 'Publish',
  description: '发布商品',
  components: {
    CategoryCascader,
    BrandSelect,
    MultiUpload
  },
  data() {
    return {
      uploadDialogVisible: false,
      uploadImages: [],
      step: 0,
      // 要提交的数据
      spu: {
        spuName: '',
        spuDescription: '',
        catelogId: 0,
        brandId: undefined,
        weight: '',
        publishStatus: 0,
        // 商品详情
        decript: [],
        // 商品图集，最后sku也可以新增
        images: [],
        // 积分
        bounds: {
          buyBounds: 0,
          growBounds: 0
        },
        // 基本属性
        baseAttrs: [],
        // 所有sku信息
        skus: []
      },
      spuBaseInfoRules: {
        spuName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        spuDescription: [
          { required: true, message: '请编写一个简单描述', trigger: 'blur' }
        ],
        catelogId: [
          { required: true, message: '请选择一个分类', trigger: 'blur' }
        ],
        brandId: [
          { required: true, message: '请选择一个品牌', trigger: 'blur' }
        ],
        decript: [
          { required: false, message: '请上传商品详情图集', trigger: 'blur' }
        ],
        images: [
          { required: false, message: '请上传商品图片集', trigger: 'blur' }
        ]
      },
      // 后台返回的所有数据
      dataResp: {
        attrGroups: [],
        baseAttrs: [],
        saleAttrs: [],
        tempSaleAttrs: [],
        tableAttrColumn: [],
        memberLevels: [],
        steped: [false, false, false, false, false]
      },
      inputVisible: [],
      inputValue: []
    }
  },
  // 监听data中数据的变化
  watch: {
    uploadImages(val) {
      // 扩展sku中imgs选项
      const imgArr = Array.from(new Set(this.spu.images.concat(val)))
      // {imgUrl:"",defaultImg:0} 由于concat每次迭代上次，有很多重复。所以我们必须得到上次+这次的总长
      this.spu.skus.forEach((item, index) => {
        // 还差这么多
        const len = imgArr.length - this.spu.skus[index].images.length
        if (len > 0) {
          let imgs = new Array(len)
          imgs = imgs.fill({ imgUrl: '', defaultImg: 0 })
          this.spu.skus[index].images = item.images.concat(imgs)
        }
      })
      // 去重
      this.spu.images = imgArr
      console.log('this.spu.skus', this.spu.skus)
    }
  },
  created() {
  },
  mounted() {
    // 加载会员等级数据
    this.loadMemberLevelData()
  },
  methods: {
    // 加载会员等级数据
    loadMemberLevelData() {
      getMemberLevelList({ current: 1, size: 500 }).then((res) => {
        if (res.code === 200) {
          this.dataResp.memberLevels = res.data.records
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 基本信息填写完成并点击的下一步
    step0Finished() {
      this.$refs.spuBaseForm.validate(valid => {
        if (valid) {
          this.step = 1
          this.showBaseAttrs()
        } else {
          return false
        }
      })
    },
    showBaseAttrs() {
      if (!this.dataResp.steped[0]) {
        console.log('step 0 ==> spu', this.spu)
        getAttrGroupsWithAttr(this.spu.catelogId).then((res) => {
          if (res.code === 200) {
            // 对表单的baseAttrs进行初始化
            res.data.forEach(item => {
              const attrArray = []
              item.attrs.forEach(attr => {
                attrArray.push({
                  attrId: attr.attrId,
                  attrValues: [],
                  showDesc: attr.showDesc
                })
              })
              this.dataResp.baseAttrs.push(attrArray)
            })
            this.dataResp.steped[0] = 0
            this.dataResp.attrGroups = res.data

            console.log('step 0 Finished ==> this.dataResp', this.dataResp)
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    // 规格参数填写完成
    step1Finished() {
      // 把页面绑定的所有attr处理到spu里面
      this.spu.baseAttrs = []
      this.dataResp.baseAttrs.forEach(item => {
        item.forEach(attr => {
          var { attrId, attrValues, showDesc } = attr
          // 跳过没有录入值的属性
          if (attrValues !== '') {
            if (attrValues instanceof Array) {
              // 多个值用,隔开
              attrValues = attrValues.join(',')
            }
            this.spu.baseAttrs.push({ attrId, attrValues, showDesc })
          }
        })
      })
      console.log('baseAttrs', this.spu.baseAttrs)
      this.step = 2
      this.initShowSaleAttr()
    },
    // 初始化显示销售属性
    initShowSaleAttr() {
      const _this = this
      // 获取当前分类可以使用的销售属性
      if (!_this.dataResp.steped[1]) {
        searchSaleAttributes(_this.spu.catelogId).then((res) => {
          if (res.code === 200) {
            debugger
            _this.dataResp.saleAttrs = res.data
            res.data.forEach(attr => {
              _this.dataResp.tempSaleAttrs.push({
                attrId: attr.attrId,
                attrValues: [],
                attrName: attr.attrName
              })
              _this.inputVisible.push({ view: false })
              _this.inputValue.push({ val: '' })
            })
            _this.dataResp.steped[1] = true
          } else {
            _this.$message.error(res.message)
          }
        })
      }
    },
    showInput(idx) {
      console.log('显示输入框', this.view)
      this.inputVisible[idx].view = true
    },
    handleInputConfirm(idx) {
      const inputValue = this.inputValue[idx].val
      if (inputValue) {
        if (this.dataResp.saleAttrs[idx].valueSelect === '') {
          this.dataResp.saleAttrs[idx].valueSelect = inputValue
        } else {
          this.dataResp.saleAttrs[idx].valueSelect += ',' + inputValue
        }
      }
      this.inputVisible[idx].view = false
      this.inputValue[idx].val = ''
    },
    // 销售属性填写完成
    step2Finished() {
      this.step = 3
      // 生成sku
      const selectValues = []
      this.dataResp.tableAttrColumn = []
      this.dataResp.tempSaleAttrs.forEach((attr) => {
        if (attr.attrValues.length > 0) {
          selectValues.push(attr.attrValues)
          this.dataResp.tableAttrColumn.push(attr)
        }
      })
      // 计算属性列表
      const descartes = this.getSkus(selectValues)

      console.log('生成的组合', JSON.stringify(descartes))
      // 有多少descartes就有多少sku
      const skus = []

      descartes.forEach((descar, descaridx) => {
        // sku属性组
        const attrArray = []
        descar.forEach((de, index) => {
          // 构造saleAttr信息
          const saleAttrItem = {
            attrId: this.dataResp.tableAttrColumn[index].attrId,
            attrName: this.dataResp.tableAttrColumn[index].attrName,
            attrValue: de
          }
          attrArray.push(saleAttrItem)
        })
        // 先初始化几个images，后面的上传还要加
        const imgs = []
        this.spu.images.forEach((img, idx) => {
          imgs.push({ imgUrl: '', defaultImg: 0 })
        })

        // 会员价，也必须在循环里面生成，否则会导致数据绑定问题
        const memberPrices = []
        if (this.dataResp.memberLevels.length > 0) {
          for (let i = 0; i < this.dataResp.memberLevels.length; i++) {
            if (this.dataResp.memberLevels[i].priviledgeMemberPrice === 1) {
              memberPrices.push({
                id: this.dataResp.memberLevels[i].id,
                name: this.dataResp.memberLevels[i].name,
                price: 0
              })
            }
          }
        }
        // descaridx，判断如果之前有就用之前的值;
        const res = this.hasAndReturnSku(this.spu.skus, descar)
        if (res === null) {
          skus.push({
            attr: attrArray,
            skuName: this.spu.spuName + ' ' + descar.join(' '),
            price: 0,
            skuTitle: this.spu.spuName + ' ' + descar.join(' '),
            skuSubtitle: '',
            images: imgs,
            descar: descar,
            fullCount: 0,
            discount: 0,
            countStatus: 0,
            fullPrice: 0.0,
            reducePrice: 0.0,
            priceStatus: 0,
            memberPrice: new Array().concat(memberPrices)
          })
        } else {
          skus.push(res)
        }
      })
      this.spu.skus = skus
      console.log('结果!!!', this.spu.skus, this.dataResp.tableAttrColumn)
    },
    getSkus(list) {
      // parent上一级索引;count指针计数
      var point = {}

      var result = []
      var pIndex = null
      var tempCount = 0
      var temp = []

      // 根据参数列生成指针对象
      for (var index in list) {
        if (typeof list[index] === 'object') {
          point[index] = { parent: pIndex, count: 0 }
          pIndex = index
        }
      }

      // 单维度数据结构直接返回
      if (pIndex == null) {
        return list
      }

      // 动态生成笛卡尔积
      while (true) {
        for (var index in list) {
          tempCount = point[index]['count']
          temp.push(list[index][tempCount])
        }

        // 压入结果数组
        result.push(temp)
        temp = []

        // 检查指针最大值问题
        while (true) {
          if (point[index]['count'] + 1 >= list[index].length) {
            point[index]['count'] = 0
            pIndex = point[index]['parent']
            if (pIndex == null) {
              return result
            }
            // 赋值parent进行再次检查
            index = pIndex
          } else {
            point[index]['count']++
            break
          }
        }
      }
    },
    // 判断如果包含之前的sku的descar组合，就返回这个sku的详细信息；
    hasAndReturnSku(skus, descar) {
      let res = null
      if (skus.length > 0) {
        for (let i = 0; i < skus.length; i++) {
          if (skus[i].descar.join(' ') === descar.join(' ')) {
            res = skus[i]
          }
        }
      }
      return res
    },
    // 设置默认图片
    setDefaultImg(row, index, img) {
      console.log('默认图片', row, index)
      // 默认选中
      row.images[index].imgUrl = img
      // 修改标志位
      row.images[index].defaultImg = 1
      // 修改其他人的标志位
      row.images.forEach((item, idx) => {
        if (idx !== index) {
          row.images[idx].defaultImg = 0
        }
      })
    },
    saveSpu() {
      console.log('保存sku信息', JSON.stringify(this.spu))
      this.$confirm('将要提交商品数据，需要一小段时间，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        saveSpu(this.spu).then((res) => {
          if (res.success) {
            // 跳转第4步
            this.step = 4
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>

<style>

</style>
