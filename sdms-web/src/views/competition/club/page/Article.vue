<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;已提交列表</p>
        <a-divider/>
      </div>

      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="applyStatus" slot-scope="text">
          {{ text | statusFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
        </span>
      </a-table>
      <a-divider/>

      <a-modal
        title="社团创建信息"
        :width="800"
        v-model="visible"
        @ok="close"
      >
        <a-card :bordered="false">
          <detail-list title="基本信息">
            <detail-list-item term="申请人学号">{{applyInfo.creatorNo}}</detail-list-item>
            <detail-list-item sytle="margin-left:50px" term="申请人姓名">{{applyInfo.creator}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="社团名称">{{applyInfo.name }}</detail-list-item>
            <detail-list-item sytle="margin-left:50px" term="社团类型">{{applyInfo.type}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 0px"/>
          <detail-list title="社团描述">
            {{applyInfo.introduction}}
          </detail-list>
          <a-divider style="margin-bottom: 0px"/>
          <detail-list title="社团logo">
            <img
              width="272"
              alt="logo"
              :src="applyInfo.logoUrl"
            />
          </detail-list>
          <a-divider style="margin-bottom: 0px"/>
          <detail-list title="审核意见">
            {{applyInfo.approvalComment | nullFilter}}
          </detail-list>
          <a-divider style="margin-bottom: 0px"/>
          <detail-list>
            <detail-list-item term="审核结果">{{applyInfo.applyStatus | nullFilter | statusFilter}}</detail-list-item>
            <detail-list-item term="审核教师">{{applyInfo.approver | nullFilter}}</detail-list-item>
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { myMention } from '@/api/club'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'clubApproval',
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
      applyInfo: {},
      applyInfo2: {},
      accountdata: [],
      applyResult: {
        id: '',
        applyComment: '',
        applyStatus: ''
      },
      // 表头
      columns: [
        {
          title: '申请人',
          dataIndex: 'creator',
          key: 'id'
        },
        {
          title: '创建社团名称',
          dataIndex: 'name',
          key: 'id'
        },
        {
          title: '类型',
          dataIndex: 'type'
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
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
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
      myMention()
        .then(res => {
          this.accountdata = res.list
        })
    },
    accountDetail (record) {
      this.applyInfo = Object.assign({}, record)
      this.visible = true
    },
    handleOk () {
      this.visible = false
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
<style scoped>
    .ant-modal-body {
      padding: 5px !important;
    }
</style>
