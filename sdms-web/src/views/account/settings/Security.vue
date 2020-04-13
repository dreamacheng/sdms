<template>
  <div>
    <a-list
      itemLayout="horizontal"
      :dataSource="data"
    >
      <a-list-item slot="renderItem" slot-scope="item, index" :key="index">
        <a-list-item-meta>
          <a slot="title">{{ item.title }}</a>
          <span slot="description">
            <span class="security-list-description">{{ item.description }}</span>
            <span v-if="item.value"> : </span>
            <span class="security-list-value">{{ item.value }}</span>
          </span>
        </a-list-item-meta>
        <template v-if="item.actions">
          <a slot="actions" @click="item.actions.callback">{{ item.actions.title }}</a>
        </template>

      </a-list-item>
    </a-list>
    <a-modal
      title="密码修改"
      :width="600"
      v-model="updatePwdShow"
      @ok="handleEditPwd"
    >
      <a-form-item label="原密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="原密码"
          v-model="pwdEdit.oldPwd"
        ></a-input>
      </a-form-item>
      <a-form-item label="密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="至少6位密码，区分大小写"
          v-model="pwdEdit.newPwd"
        ></a-input>
      </a-form-item>
      <a-form-item label="确认密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="确认密码"
          v-model="pwdEdit.newPwd2"
        ></a-input>
      </a-form-item>
    </a-modal>
    <a-modal
      title="电话修改"
      :width="600"
      v-model="updateTelShow"
      @ok="handleEditTel"
    >
      <a-form-item label="原手机号" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          placeholder="原手机号"
          v-model="telEdit.oldTel"
        ></a-input>
      </a-form-item>
      <a-form-item label="新手机号" :label-col="{ span: 6 }" :wrapper-col="{ span: 12 }">
        <a-input
          size="large"
          autocomplete="false"
          placeholder="新手机号"
          v-model="telEdit.newTel"
        ></a-input>
      </a-form-item>
    </a-modal>
  </div>
</template>

<script>
import { updatePwdApi, currentUserInfo, changeTel } from '@/api/login'
import md5 from 'md5'

export default {
  data () {
    return {
      data: [
        { title: '账户密码', description: '当前密码强度', value: '强', actions: { title: '修改', callback: () => { this.updatePwdShow = true } } },
        { title: '密保手机', description: '已绑定手机', value: '1**********', actions: { title: '修改', callback: () => { this.updateTelShow = true } } }
      ],
      updatePwdShow: false,
      updateTelShow: false,
      pwdEdit: {
        oldPwd: '',
        newPwd: '',
        newPwd2: ''
      },
      telEdit: {
        oldTel: '',
        newTel: ''
      }
    }
  },
  created () {
    this.loadCurrent()
  },
  methods: {
    loadCurrent () {
      const self = this
      currentUserInfo()
        .then(res => {
          if (res.code === 0) {
            self.accountInfo = res.info
            self.data[1].value = res.info.tel.substring(0, 3) + '****' + res.info.tel.substring(7)
          }
        })
    },
    handleEditTel () {
      const self = this
      if (this.telEdit.oldTel !== this.accountInfo.tel) {
        this.$message.error('原手机号错误')
        return
      }
      var reg = /^1[3456789]\d{9}$/
      if (!reg.test(this.telEdit.newTel)) {
        this.$message.error('新手机号格式错误')
        return
      }
      var parameter = new FormData()
      parameter.append('newTel', this.telEdit.newTel)
      changeTel(parameter)
        .then(res => {
          if (res.code === 0) {
            this.$message.info('电话修改成功')
            self.loadCurrent()
            self.telEdit = {}
            self.updateTelShow = false
          } else {
            this.$message.error('电话修改失败')
          }
        })
    },
    handleEditPwd () {
      if (!this.handlePasswordCheck()) {
        return
      }
      const param = {
        oldPwd: md5(this.pwdEdit.oldPwd),
        newPwd: md5(this.pwdEdit.newPwd)
      }
      const self = this
      updatePwdApi(param)
        .then(res => {
          if (res.code === 0) {
            this.$message.info('密码修改成功')
            self.pwdEdit = {}
            self.updatePwdShow = false
          }
          if (res.code === 91005) {
            this.$message.error('密码错误，更新失败')
          }
        })
    },
    handlePasswordCheck (value) {
      const newPwd = this.pwdEdit.newPwd
      const newPwd2 = this.pwdEdit.newPwd2
      const oldPwd = this.pwdEdit.oldPwd
      if (newPwd === '' || newPwd2 === '' || oldPwd === '') {
        this.$message.error('请输入密码')
        return false
      }
      if (newPwd && newPwd2 && newPwd.trim() !== newPwd2.trim()) {
        this.$message.error('两次密码不一致')
        return false
      }
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(newPwd)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(newPwd)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(newPwd)) {
        level++
      }
      if (level < 2) {
        this.$message.error('密码强度不够')
      }
      return true
    }
  }
}
</script>

<style scoped>

</style>
