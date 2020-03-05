<template>
  <div class="mainR user-layout-register">
    <a-form  ref="formRegister" :form="form" id="formRegister">
      <a-row>
        <a-col :span="6">&nbsp;</a-col>
        <a-col :span="6">
          <a-form-item label="学号" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input placeholder="学号" v-decorator="['accountNo', {rules: [{ required: true, message: '请输入正确的学号', pattern: /^\d{7,}$/ }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item>
          <a-form-item label="姓名" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input placeholder="姓名" v-decorator="['username', {rules: [{ required: true, message: '请输入姓名' }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item>
          <a-popover
            placement="rightTop"
            :trigger="['focus']"
            :getPopupContainer="(trigger) => trigger.parentElement"
            v-model="state.passwordLevelChecked">
            <template slot="content">
              <div :style="{ width: '240px' }" >
                <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
                <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " />
                <div style="margin-top: 10px;">
                  <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
                </div>
              </div>
            </template>
            <a-form-item label="密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
              <a-input
                size="large"
                type="password"
                @click="handlePasswordInputClick"
                autocomplete="false"
                placeholder="至少6位密码，区分大小写"
                v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
            </a-form-item>
          </a-popover>
          <a-form-item label="确认密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input
              size="large"
              type="password"
              autocomplete="false"
              placeholder="确认密码"
              v-decorator="['password2', {rules: [{ required: true, message: '至少6位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
          <a-form-item label="在校公寓" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input size="large" placeholder="在校公寓" v-decorator="['lodgingHouse', {rules: [{ required: true, message: '请输入在校公寓' }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item>
          <a-form-item label="身份证号码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input size="large" placeholder="身份证号码" v-decorator="['identityCard', {rules: [{ required: true, message: '请输入正确的身份证号码', pattern: /^\d{17}(\w?\d?)$/ }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item >
          <a-form-item label="专业科系" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input size="large" placeholder="专业科系" v-decorator="['department', {rules: [{ required: true, message: '请输入专业科系' }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="年龄" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-input size="large" placeholder="年龄" v-decorator="['age', {rules: [{ required: true, message: '请输入正确的年龄', pattern: /^\d{1,2}$/ }], validateTrigger: ['change', 'blur'] }]"/>
          </a-form-item>
          <a-form-item label="性别" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-radio-group name="gender" :defaultValue="1" v-decorator="['gender', {rules: [{ required: true, message: '请选择性别'}], validateTrigger: ['change', 'blur'] }]">
              <a-radio :value="1">男</a-radio>
              <a-radio :value="2">女</a-radio>
            </a-radio-group>
          </a-form-item>
          <a-form-item label="民族" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
            <a-select
              size="default"
              :defaultValue="nations[0]"
              v-decorator="['nation', {rules: [{ required: true, message: '请选择民族'}], validateTrigger: ['change', 'blur'] }]"
            >
              <a-select-option v-for="value in nations" :key="value">
                {{ value }}
              </a-select-option>
            </a-select>
            </a-form-item>
            <a-form-item label="政治面貌" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
              <a-radio-group name="politicsStatus" :defaultValue="1" v-decorator="['politicsStatus', {rules: [{ required: true, message: '请选择政治面貌'}], validateTrigger: ['change', 'blur'] }]">
                <a-radio :value="1">团员</a-radio>
                <a-radio :value="2">党员</a-radio>
                <a-radio :value="3">群众</a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="注册类型" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
              <a-radio-group name="role" :defaultValue="1" v-decorator="['role', {rules: [{ required: true, message: '请选择注册类型'}], validateTrigger: ['change', 'blur'] }]">
                <a-radio :value="1">学生</a-radio>
                <a-radio :value="2">教师</a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="出生日期" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }" v-decorator="['birthday', {rules: [{ required: true, message: '请选择出生日期'}], validateTrigger: ['change', 'blur'] }]">
              <a-date-picker size="default" placeholder="出生日期" />
            </a-form-item>
            <a-form-item label="手机号" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
              <a-input size="large" placeholder="11 位手机号" v-decorator="['tel', {rules: [{ required: true, message: '请输入正确的手机号', pattern: /^1[3456789]\d{9}$/ }, { validator: this.handlePhoneCheck } ], validateTrigger: ['change', 'blur'] }]">
                <a-select slot="addonBefore" size="large" defaultValue="+86">
                  <a-select-option value="+86">+86</a-select-option>
                  <a-select-option value="+87">+87</a-select-option>
                </a-select>
              </a-input>
            </a-form-item>
        </a-col>
    </a-row>
      <a-form-item>
        <a-row>
          <a-col span="8"></a-col>
          <a-col span="12">
            <a-button
              size="large"
              type="primary"
              htmlType="submit"
              class="register-button"
              :loading="registerBtn"
              @click.stop.prevent="handleSubmit"
              :disabled="registerBtn">注册
            </a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { mixinDevice } from '@/utils/mixin.js'
import { getSmsCaptcha } from '@/api/login'
import { nation } from './nation.js'

const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Register',
  components: {
  },
  mixins: [mixinDevice],
  data () {
    return {
      form: this.$form.createForm(this),
      nations: nation,
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    handlePasswordLevel (rule, value, callback) {
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      console.log('value', value)
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },

    handlePhoneCheck (rule, value, callback) {
      console.log('handlePhoneCheck, rule:', rule)
      console.log('handlePhoneCheck, value', value)
      console.log('handlePhoneCheck, callback', callback)

      callback()
    },

    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },

    handleSubmit () {
      const { form: { validateFields }, state, $router } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          state.passwordLevelChecked = false
          $router.push({ name: 'registerResult', params: { ...values } })
        }
      })
    },

    getCaptcha (e) {
      e.preventDefault()
      const { form: { validateFields }, state, $message, $notification } = this

      validateFields(['mobile'], { force: true },
        (err, values) => {
          if (!err) {
            state.smsSendBtn = true

            const interval = window.setInterval(() => {
              if (state.time-- <= 0) {
                state.time = 60
                state.smsSendBtn = false
                window.clearInterval(interval)
              }
            }, 1000)

            const hide = $message.loading('验证码发送中..', 0)

            getSmsCaptcha({ mobile: values.mobile }).then(res => {
              setTimeout(hide, 2500)
              $notification['success']({
                message: '提示',
                description: '验证码获取成功，您的验证码为：' + res.result.captcha,
                duration: 8
              })
            }).catch(err => {
              setTimeout(hide, 1)
              clearInterval(interval)
              state.time = 60
              state.smsSendBtn = false
              this.requestFailed(err)
            })
          }
        }
      )
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.registerBtn = false
    }
  },
  watch: {
    'state.passwordLevel' (val) {
      console.log(val)
    }
  }
}
</script>
<style lang="less">
  .user-register {

    &.error {
      color: #ff0000;
    }

    &.warning {
      color: #ff7e05;
    }

    &.success {
      color: #52c41a;
    }

  }

  .user-layout-register {
    .ant-input-group-addon:first-child {
      background-color: #fff;
    }
  }
</style>
<style lang="less" scoped>
  .user-layout-register {

    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }

    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }

    .register-button {
      width: 60%;
      text-align: center;
      margin: 0 auto;
    }

    .login {
      float: right;
      line-height: 40px;
    }
    .mainR {
        min-width: 260px;
        width: 1000px;
        margin: 0 auto;
      }
  }
</style>
