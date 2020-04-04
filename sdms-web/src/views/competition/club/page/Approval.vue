<template>
  <div class="app-list">
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">
      <a-form-item
        label="社团名称"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-input
          placeholder="社团名称"
          v-decorator="['name', { rules: [{required: true, message: '社团名称必须填写'}] }]"/>
      </a-form-item>
      <a-form-item
        label="社团类型"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-input
          placeholder="社团类型"
          v-decorator="['type', { rules: [{required: true, message: '社团类型号必须填写'}] }]"/>
      </a-form-item>
      <a-form-item
        label="社团logo"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-upload
          listType="picture"
          class="upload-list-inline"
          :fileList="fileList"
          @change="handleChange"
          :customRequest="customRequest"
        >
          <a-button> <a-icon type="upload" /> 上传logo </a-button>
        </a-upload>
      </a-form-item>
      <a-form-item
        label="社团简介"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-textarea
          :rows="10"
          placeholder="请键入100字以内的描述"
          maxLength="100"
          v-decorator="['introduction', { rules: [{required: true, message: '社团简介必须填写'}] }]"/>
      </a-form-item>
      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button type="primary" @click="submit">创建</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { clubAdd } from '@/api/club'
import { upload } from '@/api/fileUpload'

export default {
  name: 'Approval',
  components: {},
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
      pagination: {
        pageSize: 3
      },
      clubList: [],
      fileList: [],
      logoUrl: ''
    }
  },
  methods: {
    submit () {
      const self = this
      const { form: { validateFields } } = this
      // 先校验，通过表单校验后，才进入下一步
      validateFields((err, values) => {
        if (!self.logoUrl) {
          this.$message.error('请上传社团logo')
          return
        }
        if (!err) {
          const clubInfo = values
          clubInfo.logoUrl = self.logoUrl
          clubAdd(clubInfo)
            .then(res => {
              if (res.code === 0) {
                this.$message.info('创建社团成功')
                this.$router.go(0)
              } else {
                this.$message.error('提交申请失败')
              }
            })
        }
      })
    },
    customRequest (data) {
      const self = this
      var param = new FormData() // 创建form对象
      param.append('file', data.file)
      param.append('objectType', 'CLUB')
      upload(param)
        .then(res => {
          if (res.code === 0 && res.info) {
            self.logoUrl = res.info
            self.fileList = self.fileList.map(file => {
              file.url = res.info
              return file
            })
            this.$message.info('社团logo上传成功')
          } else {
            this.$message.error('社团logo上传失败')
          }
        })
    },
    handleChange (info) {
      let fileList = [...info.fileList]
      // 1. Limit the number of uploaded files
      //    Only to show two recent uploaded files, and old ones will be replaced by the new
      fileList = fileList.slice(-1)
      this.fileList = fileList
    }
  }
}
</script>

<style lang="less" scoped>

  .app-list {

    .meta-cardInfo {
      zoom: 1;
      margin-top: 16px;

      > div {
        position: relative;
        text-align: left;
        float: left;
        width: 50%;

        p {
          line-height: 32px;
          font-size: 24px;
          margin: 0;

          &:first-child {
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }

      }
    }
  }
</style>
