<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <!-- <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="学号">
                <a-input :value="queryParam.accountNo" placeholder="请输入"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="用户姓名">
                <a-input :value="queryParam.username" placeholder="请输入"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <span class="table-page-search-submitButtons" >
                <a-button type="primary" @click="loadAccountList">查询</a-button>
                <a-button style="margin-left: 8px">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form> -->
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;审批列表</p>
      </div>

      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="applyStatus" slot-scope="text">
          {{ text | statusFilter }}
        </span>
        <span slot="type" slot-scope="text">
          {{ text | applyFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">审批</a>
        </span>
      </a-table>

      <a-modal
        title="审批"
        :width="800"
        v-model="visible"
        @ok="handleOk"
        okText="提交审批"
      >
        <a-card :bordered="false">
          <detail-list title="基本信息">
            <detail-list-item term="申请人学号">{{applyInfo.proposer}}</detail-list-item>
            <detail-list-item term="申请人姓名">{{applyInfo.proposerName}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="申请类型">{{applyInfo.type | applyFilter}}</detail-list-item>
            <detail-list-item term="申请时间">{{applyInfo.applyTime}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="申请书">
            {{applyInfo.applyText}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="审核意见">
            <div>
              <a-textarea v-model="applyResult.applyComment" style="width:400px" :rows="5" placeholder="审核意见"/>
            </div>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="审核结果">
            <a-radio-group v-model="applyResult.applyStatus">
              <a-radio value="Approved" defaultChecked>审核通过</a-radio>
              <a-radio value="Rejected">审核驳回</a-radio>
            </a-radio-group>
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { queryCurAll, applyApproval } from '@/api/organization'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'todo',
  data () {
    return {
      description: '',
      confirmOpt: false,
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: null,
      applyInfo: {},
      accountdata: [],
      applyResult: {
        id: '',
        applyComment: '',
        applyStatus: ''
      },
      // 表头
      columns: [
        {
          title: '申请人学号',
          dataIndex: 'proposer',
          key: 'id'
        },
        {
          title: '申请人姓名',
          dataIndex: 'proposerName'
        },
        {
          title: '申请类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '状态',
          dataIndex: 'applyStatus',
          scopedSlots: { customRender: 'applyStatus' }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  filters: {
    applyFilter (status) {
      const statusMap = {
        'LEAGUE_APPLY': '入团申请',
        'PART_APPLY': '入党申请'
      }
      return statusMap[status]
    },
    statusFilter (status) {
      const statusMap = {
        'WaitForApproval': '待审核',
        'Rejected': '拒绝',
        'Approved': '批准'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      queryCurAll()
        .then(res => {
          const result = res.list
          this.accountdata = result
        })
    },
    accountDetail (record) {
      this.applyInfo = Object.assign({}, record)
      this.applyResult.id = record.id
      this.visible = true
    },
    handleOk () {
      const self = this
      if (!this.applyResult.applyComment) {
        this.$message.error('请填写审批意见')
        return
      }
      if (!this.applyResult.applyStatus) {
        this.$message.error('请勾选审批结果')
        return
      }
      applyApproval(this.applyResult)
        .then(res => {
          if (res.code === 0) {
            this.$message.info('审批成功')
            self.loadAccountList()
            this.visible = false
          } else {
            this.$message.error('审批失败')
          }
        })
    }
  },
  watch: {
    /*
      'selectedRows': function (selectedRows) {
        this.needTotalList = this.needTotalList.map(item => {
          return {
            ...item,
            total: selectedRows.reduce( (sum, val) => {
              return sum + val[item.dataIndex]
            }, 0)
          }
        })
      }
      */
  }
}
</script>
