<template>
  <div style="font-size:20px;margin-top:50px">
    <span v-if="code !== ''">您生成的注册码为 <span style="margin-left:20px;color:#0088FF">{{code}}</span></span>
    <span v-else>您尚未生成注册码或生成的注册码已过期，点击<a-button @click="generateCode" style="margin:0px 10px">注册邀请码</a-button>生成注册码</span>
  </div>
</template>

<script>
import { generate, getCode } from '@/api/login'
export default {
  data () {
    return {
      code: ''
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    loadData () {
      getCode()
        .then(res => {
          if (res.code === 0 && res.info.available === 1) {
            this.code = res.info.code
          }
        })
    },
    generateCode () {
      const self = this
      this.$confirm({
        title: '确认',
        content: '确认生成新的注册码吗？',
        onOk () {
          generate()
            .then(res => {
              if (res.code === 0) {
                self.$message.info('生成注册邀请码成功')
                self.code = res.info
              } else {
                self.$message.error('生成失败')
              }
            })
        },
        onCancel () {}
      })
    }
  }
}
</script>

<style scoped>

</style>
