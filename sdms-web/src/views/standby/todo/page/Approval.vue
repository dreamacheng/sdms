<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;待审批列表</p>
        <a-divider/>
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
      <a-divider/>
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;审批记录</p>
        <a-divider/>
      </div>
      <a-table :columns="columns" :dataSource="accountdata2">
        <span slot="applyStatus" slot-scope="text">
          {{ text | statusFilter }}
        </span>
        <span slot="type" slot-scope="text">
          {{ text | applyFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="displayDetail(record)">详情</a>
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

      <a-modal
        title="审批信息"
        :width="800"
        v-model="visible2"
        @ok="close"
      >
        <a-card :bordered="false">
          <detail-list title="基本信息">
            <detail-list-item term="申请人学号">{{applyInfo2.proposer}}</detail-list-item>
            <detail-list-item term="申请人姓名">{{applyInfo2.proposerName}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="申请类型">{{applyInfo2.type | applyFilter}}</detail-list-item>
            <detail-list-item term="申请时间">{{applyInfo2.applyTime}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="申请书">
            {{applyInfo2.applyText}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="审核意见">
            {{applyInfo2.applyComment}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list>
            <detail-list-item term="审核结果">{{applyInfo2.applyStatus | statusFilter}}</detail-list-item>
            <detail-list-item term="审核教师">{{applyInfo2.approver | nullFilter}}</detail-list-item>
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
      visible2: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      applyInfo: {},
      applyInfo2: {},
      accountdata: [],
      accountdata2: [],
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
        'Rejected': '驳回',
        'Approved': '通过'
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
          this.accountdata = res.info.notList
          this.accountdata2 = res.info.list
        })
    },
    accountDetail (record) {
      this.applyInfo = Object.assign({}, record)
      this.applyResult.id = record.id
      this.visible = true
    },
    displayDetail (record) {
      this.applyInfo2 = record
      this.visible2 = true
    },
    close () {
      this.visible2 = false
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
