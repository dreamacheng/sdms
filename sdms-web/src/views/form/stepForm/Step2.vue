<template>
  <div>
    <a-form style="margin: 40px auto 0;">
      <result title="申请成功" :is-success="true" :description="applyResult" style="max-width: 560px;">
        <div class="information">
          <a-row>
            <a-col :sm="8" :xs="24">申请人学号：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.proposer}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">申请人姓名：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.proposerName}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">审核人：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.approverName}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">申请日期：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.applyTime}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">申请状态：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.applyStatus | statusFilter}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">审批意见：</a-col>
            <a-col :sm="16" :xs="24">{{organizationDetail.applyComment | nullFilter}}</a-col>
          </a-row>
        </div>
        <div slot="action">
          <a-button style="margin-left: 8px" @click="applyDetailHandler">查看入党申请书</a-button>
        </div>
      </result>
    </a-form>
    <a-modal
        title="入党申请书"
        :width="800"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false">
          <detail-list title="入党申请书">
            {{organizationDetail.applyText}}
          </detail-list>
        </a-card>
      </a-modal>
  </div>
</template>

<script>
import { Result } from '@/components'
import { getCurAccountApply } from '@/api/organization'

export default {
  name: 'Step2',
  components: {
    Result
  },
  data () {
    return {
      applyResult: '请等待审核人审批',
      organizationDetail: {},
      visible: false
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'WaitForApproval': '待审核',
        'Rejected': '拒绝',
        'Approved': '通过'
      }
      return statusMap[status]
    },
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
    }
  },
  created () {
    getCurAccountApply('PART_APPLY')
      .then(res => {
        if (res.code === 0) {
          this.organizationDetail = res.info
        }
      })
  },
  methods: {
    applyDetailHandler () {
      this.visible = true
    },
    handleOk () {
      this.visible = false
    }
  }
}
</script>
<style lang="less" scoped>
  .information {
    line-height: 22px;

    .ant-row:not(:last-child) {
      margin-bottom: 24px;
    }
  }
  .money {
    font-family: "Helvetica Neue",sans-serif;
    font-weight: 500;
    font-size: 20px;
    line-height: 14px;
  }
</style>
