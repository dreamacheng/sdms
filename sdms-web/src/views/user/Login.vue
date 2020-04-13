eslint-disable
<template>
  <div class="main">
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
      @submit="handleSubmit"
    >
      <a-form-item>
        <a-input
          size="large"
          type="text"
          placeholder="学号/教职工号"
          v-decorator="[
            'accountNo',
            {rules: [{ required: true, message: '请输入学号/教职工号' }], validateTrigger: 'change'}
          ]"
        >
          <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-form-item>
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="密码"
          v-decorator="[
            'password',
            {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
          ]"
        >
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-form-item>
        <a
          @click="resetPwd"
          class="forge-password"
          style="float: left;"
        > 忘记密码</a>
        <router-link class="register" :to="{ name: 'register' }" style="float: right;">注册账户</router-link>
      </a-form-item>

      <a-form-item style="margin-top:24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button>
      </a-form-item>
    </a-form>

    <a-modal
      title="密码重置"
      :width="600"
      v-model="updatePwdShow"
      @ok="handleEditPwd"
      okText="重置"
    >
      <a-form-item label="学号" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          v-model="pwdEdit.accountNo"
        ></a-input>
      </a-form-item>
      <a-form-item label="姓名" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          v-model="pwdEdit.username"
        ></a-input>
      </a-form-item>
      <a-form-item label="身份证" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          v-model="pwdEdit.identityCard"
        ></a-input>
      </a-form-item>
      <a-form-item label="电话" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          v-model="pwdEdit.tel"
        ></a-input>
      </a-form-item>
    </a-modal>

  </div>
</template>

<script>
import md5 from 'md5'
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
import { resetPwdAPI } from '@/api/login'

export default {
  data () {
    return {
      loginBtn: false,
      form: this.$form.createForm(this),
      updatePwdShow: false,
      pwdEdit: {
        accountNo: '',
        identityCard: '',
        tel: '',
        pwd: '',
        username: ''
      },
      state: {
        time: 60,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        smsSendBtn: false
      }
    }
  },
  created () {
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    handleEditPwd () {
      if (this.pwdEdit.accountNo === '' || this.pwdEdit.identityCard === '' ||
      this.pwdEdit.tel === '' || this.pwdEdit.username === '') {
        this.$message.error('请完整填写验证信息')
        return false
      }
      var data = new FormData()
      data.append('identityCard', this.pwdEdit.identityCard)
      data.append('accountNo', this.pwdEdit.accountNo)
      data.append('tel', this.pwdEdit.tel)
      data.append('pwd', md5(this.pwdEdit.identityCard))
      data.append('username', this.pwdEdit.username)
      resetPwdAPI(data)
        .then(res => {
          if (res.code === 0) {
            this.$message.success('密码重置成功')
          } else {
            this.$notification['error']({
              message: '验证失败',
              description: '请检查验证信息是否正确',
              duration: 4
            })
          }
        })
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state,
        Login
      } = this

      state.loginBtn = true

      const validateFieldsKey = ['accountNo', 'password']

      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          console.log('login form', values)
          const loginParams = { ...values }
          loginParams['accountNo'] = values.accountNo
          loginParams.password = md5(values.password)
          console.log(loginParams.password)
          Login(loginParams)
            .then((res) => this.loginSuccess(res))
            .catch(err => this.requestFailed(err))
            .finally(() => {
              state.loginBtn = false
            })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      })
    },
    loginSuccess (res) {
      this.$router.push({ path: '/' })
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
    },
    resetPwd () {
      this.updatePwdShow = true
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '账户或密码错误',
        description: ((err.response || {}).data || {}).message || '请检查学号或职工号与密码是否正确',
        duration: 4
      })
    }
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
