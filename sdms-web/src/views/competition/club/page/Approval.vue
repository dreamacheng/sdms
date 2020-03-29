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
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          listType="picture"
          class="upload-list-inline"
          :fileList="fileList"
          @change="handleChange"
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
      fileList: []
    }
  },
  methods: {
    submit () {
      const { form: { validateFields } } = this
      // 先校验，通过表单校验后，才进入下一步
      validateFields((err, values) => {
        if (!err) {
          const clubInfo = values
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
    handleChange (info) {
      let fileList = [...info.fileList]
      // 1. Limit the number of uploaded files
      //    Only to show two recent uploaded files, and old ones will be replaced by the new
      fileList = fileList.slice(-1)
      // 2. read from response and show file link
      fileList = fileList.map(file => {
        if (file.response) {
          // Component will show file.url as link
          file.url = file.response.url
        }
        return file
      })
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
