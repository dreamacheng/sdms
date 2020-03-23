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
        label="入团申请书"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-textarea
          :rows="10"
          placeholder="请填写入团申请书"
          v-decorator="['applyText', { rules: [{required: true, message: '入团申请书必须填写'}] }]"/>
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
      <h4>提交入团申请书</h4>
      <p>要求入团的青年，必须向团支部委员会提出书面或口头申请；凡申请入团的青年，必须有本支部团员做介绍人；<br>
        申请人必须认真如实地填写入团申请书；经支部委员会审查，提交支部大会讨论通过；<br>
        报上级团委批准；在团旗下进行入团宣誓；向新团员颁发团员证。</p>
      <h4>发展团员，必须把政治标准放在首位，严格履行下列手续：</h4>
      <p> （一）申请入团的青年应有两名团员作介绍人。<br>
        （二）介绍人应负责地向被介绍人说明团章，向团的组织说明被介绍人的思想、表现和经历。<br>
        （三）要求入团的青年要向支部委员会提出申请，填写入团志愿书，经支部大会讨论通过和上级委员会批准，才能成为团员。被批准入团的青年从支部大会通过之日起取得团籍。</p>
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
            type: 'LEAGUE_APPLY',
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
