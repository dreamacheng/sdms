<template>
  <div class="account-settings-info-view">
    <a-row :gutter="24">
      <a-col :md="24" :lg="16">
        <a-form>
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
            {{ account.currentTerm | termFilter }}
          </a-form-item>
          <a-form-item
            label="学院"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.college }}
          </a-form-item>
          <a-form-item
            label="专业科系"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 12, offset:3 }"
          >
            {{ account.major }}
          </a-form-item>
          <a-form-item
            label="学生自评"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 17, offset:3 }"
          >
            <div v-show="!editShow.selfEval">{{ evaluation.selfEval | nullFilter }}</div>
            <div v-show="editShow.selfEval">
              <a-textarea v-model="evaluation.selfEval" style="width:500px" :rows="10" v-show="editShow.selfEval" placeholder="学生自评"/>
            </div>
            <a-icon type="edit" v-show="account.role === 'STUDENT'" style="margin-top:-25px;float:right" @click="editShow.selfEval = !editShow.selfEval"/>
          </a-form-item>
          <a-form-item
            label="老师评价"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 17, offset:3 }"
          >
            <div v-show="!editShow.teacherEval">{{ evaluation.teacherEval | nullFilter }}</div>
            <div v-show="editShow.teacherEval">
              <a-textarea v-model="evaluation.teacherEval" style="width:500px" :rows="10" v-show="editShow.teacherEval" placeholder="老师评价"/>
            </div>
            <a-icon type="edit" v-show="account.role === 'MANAGER'" style="margin-top:-25px;float:right" @click="editShow.teacherEval = !editShow.teacherEval"/>
          </a-form-item>
          <a-form-item
            label="评定等级"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 17, offset:3 }"
          >
            <div v-show="!editShow.grade">{{ evaluation.grade | nullFilter }}</div>
            <div v-show="editShow.grade">
              <a-select v-model="evaluation.grade" style="width: 200px">
                <a-select-option value="A">A</a-select-option>
                <a-select-option value="B">B</a-select-option>
                <a-select-option value="C">C</a-select-option>
                <a-select-option value="D">D</a-select-option>
                <a-select-option value="E">E</a-select-option>
              </a-select>
            </div>
            <a-icon type="edit" v-show="account.role === 'MANAGER'" style="margin-top:-25px;float:right" @click="editShow.grade = !editShow.grade"/>
          </a-form-item>
          <a-form-item>
            <a-row>
              <a-col span="4"></a-col>
              <a-col span="12">
                <a-button
                  size="large"
                  type="primary"
                  class="register-button"
                  @click="handleSubmit">保存
                </a-button>
              </a-col>
            </a-row>
          </a-form-item>
        </a-form>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { currentUserInfo } from '@/api/login'
import { evaluateAPI, evaluateCurAPI } from '@/api/evaluate'

export default {
  data () {
    return {
      // cropper
      form: this.$form.createForm(this),
      preview: {},
      editShow: {
        selfEval: false,
        teacherEval: false,
        grade: false
      },
      account: {},
      evaluation: {
        id: '',
        selfEval: '',
        teacherEval: '',
        grade: ''
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
    termFilter (status) {
      const statusMap = {
        'CLASS_1': '大一第一学年',
        'CLASS_2': '大一第二学年',
        'CLASS_3': '大二第一学年',
        'CLASS_4': '大二第二学年',
        'CLASS_5': '大三第一学年',
        'CLASS_6': '大三第二学年',
        'CLASS_7': '大四第一学年',
        'CLASS_8': '大四第二学年'
      }
      return statusMap[status]
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
    loadCurrent () {
      currentUserInfo()
        .then(res => {
          this.account = res.info
          if (this.account.currentTerm) {
            evaluateCurAPI(this.account.currentTerm)
              .then(res => {
                if (res.code === 0) {
                  if (res.info) {
                    this.evaluation.id = res.info.id
                    this.evaluation.selfEval = res.info.selfEval
                    this.evaluation.teacherEval = res.info.teacherEval
                    this.evaluation.grade = res.info.grade
                  } else {
                    const param = {
                      studentNo: this.account.accountNo,
                      studentName: this.account.username,
                      semester: this.account.currentTerm
                    }
                    evaluateAPI(param)
                      .then(res => {
                        if (res.code === 0) {
                          this.evaluation.id = res.info.id
                          this.evaluation.selfEval = res.info.selfEval
                          this.evaluation.teacherEval = res.info.teacherEval
                          this.evaluation.grade = res.info.grade
                        }
                      })
                  }
                }
              })
          }
        })
    },
    handleSubmit () {
      var param = {}
      if (this.account.role === 'STUDENT') {
        param = {
          id: this.evaluation.id,
          selfEval: this.evaluation.selfEval
        }
      } else {
        param = {
          id: this.evaluation.id,
          teacherEval: this.evaluation.teacherEval,
          grade: this.evaluation.grade
        }
      }
      evaluateAPI(param)
        .then(res => {
          if (res.code === 0) {
            this.$message.info('评定成功')
            this.loadCurrent()
            this.editShow = {
              selfEval: false,
              teacherEval: false,
              grade: false
            }
          } else {
            this.$message.info('系统错误，评定失败')
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
