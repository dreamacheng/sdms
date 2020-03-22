<template>
  <div>
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">
      <a-form-item
        label="申请人姓名"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-input
          placeholder="请填写姓名"
          v-decorator="['proposerName', { rules: [{required: true, message: '申请人姓名必须填写'}] }]"/>
      </a-form-item>
      <a-form-item
        label="申请人学号"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-input
          placeholder="请填写学号"
          v-decorator="['proposerNo', { rules: [{required: true, message: '申请人学号必须填写'}] }]"/>
      </a-form-item>
      <a-form-item
        label="申请时间"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-date-picker :defaultValue="moment(new Date(), 'yyyy-MM-dd')" disabled size="large" placeholder="申请时间" />
      </a-form-item>
      <a-form-item
        label="审核人选择"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-select
          v-decorator="['approver', {rules: [{ required: true, message: '请选择审核人'}], validateTrigger: ['change', 'blur'] }]"
        >
          <a-select-option v-for="item in approverList" :key="item.accountNo">
            {{ item.username }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        label="入党申请书"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-textarea
          :rows="10"
          placeholder="请填写入党申请书"
          v-decorator="['applyText', { rules: [{required: true, message: '入党申请书必须填写'}] }]"/>
      </a-form-item>
      <!-- <a-form-item
        label="上传附件材料"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-upload
          name="file"
          :multiple="true"
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          :headers="headers"
          @change="handleChange"
        >
          <a-button> <a-icon type="upload" /> Click to Upload </a-button>
        </a-upload>
      </a-form-item> -->
      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
      </a-form-item>
    </a-form>
    <a-divider />
    <div class="step-form-style-desc">
      <h3>说明</h3>
      <h4>提交入党申请书</h4>
      <p>条件：年满十八岁的中国工人、农民、军人、知识分子和其他社会阶层的先进分子，承认党的纲领和章程，愿意参加党的一个组织并在其中积极工作、执行党的决议和按期交纳党费。
        <br>要求：向工作、学习所在单位党组织提出入党申请；没有工作、学习单位或工作、学习单位未建立党组织的，向居住地党组织提出入党申请；流动人员还可以向单位所在地党组织或单位主管部门党组织、
        流动党员党组织提出入党申请。</p>
      <h4>党组织派人谈话</h4>
      <p>时间：收到入党申请书1个月内。<br>
        主体：党支部书记、副书记或组织委员。<br>
        内容：了解入党申请人基本情况；介绍入党条件和程序；加强教育引导。<br></p>
    </div>
  </div>
</template>

<script>
import { queryAccountAPI } from '@/api/manage'
import { applyAddAPI } from '@/api/organization'
import moment from 'moment'

export default {
  name: 'Step1',
  data () {
    return {
      labelCol: { lg: { span: 5 }, sm: { span: 5 } },
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 } },
      form: this.$form.createForm(this),
      approverList: [],
      proposer: {}
    }
  },
  created () {
    this.getApproverList()
  },
  methods: {
    moment,
    nextStep () {
      const { form: { validateFields } } = this
      // 先校验，通过表单校验后，才进入下一步
      validateFields((err, values) => {
        if (!err) {
          const applyInfo = {
            type: 'PART_APPLY',
            approver: values.approver,
            proposer: values.proposerNo,
            applyTime: new Date(),
            applyText: values.applyText,
            // applyAccessory: this.applyAccessory,
            applyStatus: 'WaitForApproval'
          }
          console.log(applyInfo)
          applyAddAPI(applyInfo)
            .then(res => {
              if (res.code === 0) {
                this.$emit('nextStep')
              } else {
                this.$message.error('提交申请失败')
              }
            })
        }
      })
    },
    getApproverList () {
      queryAccountAPI({
        role: 'MANAGER',
        pageInfo: {
          pageSize: 100,
          pageNum: 0
        }
      }).then(res => {
        const result = res.info.resultList
        this.approverList = result
      })
    }
  }
}
</script>

<style lang="less" scoped>
.step-form-style-desc {
  padding: 0 56px;
  color: rgba(0,0,0,.45);

  h3 {
    margin: 0 0 12px;
    color: rgba(0,0,0,.45);
    font-size: 16px;
    line-height: 32px;
  }

  h4 {
    margin: 0 0 4px;
    color: rgba(0,0,0,.45);
    font-size: 14px;
    line-height: 22px;
  }

  p {
    margin-top: 0;
    margin-bottom: 12px;
    line-height: 22px;
  }
}
</style>
