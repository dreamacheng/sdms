<template>
  <div class="account-settings-info-view">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">
        <a-form
          :form="form"
          @submit="handleSubmit"
        >
          <a-form-item
            label="学号"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.accountNo }}
          </a-form-item>
          <a-form-item
            label="姓名"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.username }}
          </a-form-item>
          <a-form-item
            label="入学时间"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.enrollment }}
          </a-form-item>
          <a-form-item
            label="当前学年"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.enrollment }}
          </a-form-item>
          <a-form-item
            label="专业科系"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.department }}
          </a-form-item>
          <a-form-item>
            <a-row>
              <a-col span="4"></a-col>
              <a-col span="12">
                <a-button
                  size="large"
                  type="primary"
                  htmlType="submit"
                  class="register-button"
                  :loading="submitBtn"
                  @click.stop.prevent="handleSubmit"
                  :disabled="submitBtn">保存
                </a-button>
              </a-col>
            </a-row>
          </a-form-item>
        </a-form>

      </a-col>
      <a-col :md="24" :lg="8" :style="{ minHeight: '180px' }">
        <div class="ant-upload-preview" @click="$refs.modal.edit(1)" >
          <a-icon type="cloud-upload-o" class="upload-icon"/>
          <div class="mask">
            <a-icon type="plus" />
          </div>
          <img :src="option.img"/>
        </div>
      </a-col>

    </a-row>

    <avatar-modal ref="modal" @ok="setavatar"/>

  </div>
</template>

<script>
import { updateAccountApi, currentUserInfo } from '@/api/login'

export default {
  data () {
    return {
      // cropper
      form: this.$form.createForm(this),
      preview: {},
      account: {
        id: '',
        accountNo: '',
        username: '',
        role: '',
        department: '',
        avatar: ''
      },
      submitBtn: false,
      editShow: {
        accountNo: false,
        username: false,
        lodgingHouse: false,
        politicsStatus: false,
        department: false,
        birthday: false
      },
      option: {
        img: '',
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: false,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 180,
        autoCropHeight: 180,
        fixedBox: true,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [1, 1]
      }
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        0: '正常',
        1: '锁定'
      }
      return statusMap[status]
    },
    roleFilter (role) {
      const statusMap = {
        'STUDENT': '学生',
        'MANAGER': '管理员'
      }
      return statusMap[role]
    },
    politicsStatusFilter (status) {
      const statusMap = {
        'PART_MEMBER': '党员',
        'LEAGUE_MEMBER': '团员',
        'MASSES': '群众'
      }
      return statusMap[status]
    },
    genderFileter (gender) {
      const statusMap = {
        'MALE': '男',
        'FEMALE': '女'
      }
      return statusMap[gender]
    }
  },
  created () {
    this.loadCurrent()
  },
  methods: {
    setavatar (url) {
      this.option.img = url
    },
    loadCurrent () {
      currentUserInfo()
        .then(res => {
          this.account.id = res.info.id
          this.account.accountNo = res.info.accountNo
          this.account.age = res.info.age
          this.account.gender = res.info.gender
          this.account.username = res.info.username
          this.account.identityCard = res.info.identityCard
          this.account.tel = res.info.tel
          this.account.politicsStatus = res.info.politicsStatus
          this.account.role = res.info.role
          this.account.birthday = res.info.birthday
          this.account.lodgingHouse = res.info.lodgingHouse
          this.account.department = res.info.department
          this.option.img = res.info.avatar
          this.form.setFieldsValue({
            username: this.account.username,
            politicsStatus: this.account.politicsStatus,
            lodgingHouse: this.account.lodgingHouse,
            department: this.account.department
          })
        })
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          var updateAccount = values
          updateAccount['id'] = this.account.id
          updateAccountApi(updateAccount)
            .then(res => {
              if (res.code === 0) {
                this.$message.info('更新个人资料成功')
              } else {
                this.$message.info('更新个人资料失败')
              }
              location.reload()
            })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-wrapper {
    height: 200px;
    width: 100%;
  }

  .ant-upload-preview {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;

    .upload-icon {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.4rem;
      padding: 0.5rem;
      background: rgba(222, 221, 221, 0.7);
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.2);
    }
    .mask {
      opacity: 0;
      position: absolute;
      background: rgba(0,0,0,0.4);
      cursor: pointer;
      transition: opacity 0.4s;

      &:hover {
        opacity: 1;
      }

      i {
        font-size: 2rem;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -1rem;
        margin-top: -1rem;
        color: #d6d6d6;
      }
    }

    img, .mask {
      width: 100%;
      max-width: 180px;
      height: 100%;
      border-radius: 50%;
      overflow: hidden;
    }
  }
</style>
