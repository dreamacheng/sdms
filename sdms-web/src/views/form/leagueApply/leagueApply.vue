<template>
  <a-card :bordered="false">
    <a-steps class="steps" :current="currentTab">
      <a-step title="填写申请信息" />
      <a-step title="完成" />
    </a-steps>
    <div class="content">
      <step1 v-if="currentTab === 0" @nextStep="nextStep"/>
      <step2 v-if="currentTab === 1"/>
    </div>
  </a-card>
</template>

<script>
import Step1 from './Step1'
import Step2 from './Step2'
import { getCurAccountApply } from '@/api/organization'

export default {
  name: 'StepForm',
  components: {
    Step1,
    Step2
  },
  data () {
    return {
      description: '',
      currentTab: 0
    }
  },
  created () {
    getCurAccountApply('LEAGUE_APPLY')
      .then(res => {
        if (res.code === 0) {
          this.currentTab = 1
        }
      })
  },
  methods: {

    // handler
    nextStep () {
      if (this.currentTab < 1) {
        this.currentTab += 1
      }
    },
    prevStep () {
      if (this.currentTab > 0) {
        this.currentTab -= 1
      }
    },
    finish () {
      this.currentTab = 0
    }
  }
}
</script>

<style lang="less" scoped>
  .steps {
    max-width: 750px;
    margin: 16px auto;
  }
</style>
