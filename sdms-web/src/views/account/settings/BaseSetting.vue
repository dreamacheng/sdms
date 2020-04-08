<template>
  <div class="account-settings-info-view">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">
        <a-form
          :form="form"
          @submit="handleSubmit"
        >
          <a-form-item
            label="学号/职工号"
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
            <div v-show="!editShow.username">{{ account.username }}</div>
            <div v-show="editShow.username">
              <a-input style="width:250px" v-show="editShow.username" placeholder="姓名" v-decorator="['username', {rules: [{ required: true, message: '请输入姓名' }], validateTrigger: ['blur'] }]"/>
            </div>
            <a-icon type="edit" style="margin-top:-25px;float:right;" @click="editShow.username = !editShow.username"/>
          </a-form-item>
          <a-form-item
            label="性别"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.gender | genderFileter }}
          </a-form-item>
          <a-form-item
            label="政治面貌"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            <div v-show="!editShow.politicsStatus">{{ account.politicsStatus | politicsStatusFilter }}</div>
            <div v-show="editShow.politicsStatus">
              <a-radio-group style="width:250px" v-show="editShow.politicsStatus" v-decorator="['politicsStatus', {rules: [{ required: true, message: '请选择政治面貌'}], validateTrigger: ['blur'] }]">
                <a-radio value="LEAGUE_MEMBER" defaultChecked>团员</a-radio>
                <a-radio value="PART_MEMBER">党员</a-radio>
                <a-radio value="MASSES">群众</a-radio>
              </a-radio-group>
            </div>
          </a-form-item>
          <div v-show="account.role == 'STUDENT'">
            <a-form-item
              label="入学时间"
              :label-col="{ span: 4 }"
              :wrapper-col="{ span: 12, offset:3 }"
            >
              {{ account.enrollment }}
            </a-form-item>
          </div>
          <a-form-item
            label="身份证号"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.identityCard }}
          </a-form-item>
          <a-form-item
            label="联系电话"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.tel }}
          </a-form-item>
          <div v-show="account.role == 'STUDENT'">
            <a-form-item
              label="在校公寓"
              :label-col="{ span: 4 }"
              :wrapper-col="{ span: 12, offset:3 }"
            >
              <div v-show="!editShow.lodgingHouse">{{ account.lodgingHouse | nullFilter }}</div>
              <div v-show="editShow.lodgingHouse">
                <a-input style="width:250px" v-show="editShow.lodgingHouse" placeholder="在校公寓" v-decorator="['lodgingHouse', {rules: [{ required: false, message: '请输入在校公寓' }], validateTrigger: ['blur'] }]"/>
              </div>
              <a-icon type="edit" style="margin-top:-25px;float:right" @click="editShow.lodgingHouse = !editShow.lodgingHouse"/>
            </a-form-item>
          </div>
          <a-form-item
            label="学院"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            <div v-show="!editShow.college">{{ account.college | nullFilter }}</div>
            <div v-show="editShow.college">
              <a-input style="width:250px" v-show="editShow.college" placeholder="学院" v-decorator="['college', {rules: [{ required: true, message: '请输入学院' }], validateTrigger: ['blur'] }]"/>
            </div>
            <a-icon type="edit" style="margin-top:-25px;float:right" @click="editShow.college = !editShow.college"/>
          </a-form-item>
          <div v-show="account.role == 'STUDENT'">
            <a-form-item
              label="专业科系"
              :label-col="{ span: 4 }"
              :wrapper-col="{ span: 12, offset:3 }"
            >
              <div v-show="!editShow.major">{{ account.major | nullFilter }}</div>
              <div v-show="editShow.major">
                <a-input style="width:250px" v-show="editShow.major" placeholder="专业科系" v-decorator="['major', {rules: [{ required: true, message: '请输入专业科系' }], validateTrigger: ['blur'] }]"/>
              </div>
              <a-icon type="edit" style="margin-top:-25px;float:right" @click="editShow.major = !editShow.major"/>
            </a-form-item>
          </div>
          <a-form-item
            label="年龄"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.age }}
          </a-form-item>
          <a-form-item
            label="出生年月"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.birthday }}
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
        <div class="ant-upload-preview" @click="$refs.modal.edit(account.id)" >
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
import AvatarModal from './AvatarModal'
import { updateAccountApi, currentUserInfo } from '@/api/login'

export default {
  components: {
    AvatarModal
  },
  data () {
    return {
      // cropper
      form: this.$form.createForm(this),
      preview: {},
      account: {
        id: '',
        accountNo: '',
        age: '',
        gender: '',
        username: '',
        identityCard: '',
        tel: '',
        politicsStatus: '',
        role: '',
        birthday: '',
        lodgingHouse: '',
        major: '',
        college: '',
        enrollment: '',
        avatar: ''
      },
      submitBtn: false,
      editShow: {
        accountNo: false,
        username: false,
        lodgingHouse: false,
        politicsStatus: false,
        major: false,
        birthday: false,
        college: false
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
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
    },
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
          this.account.major = res.info.major
          this.account.college = res.info.college
          this.account.enrollment = res.info.enrollment
          this.option.img = res.info.avatar
          this.form.setFieldsValue({
            username: this.account.username,
            politicsStatus: this.account.politicsStatus,
            lodgingHouse: this.account.lodgingHouse,
            major: this.account.major,
            college: this.account.college
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
