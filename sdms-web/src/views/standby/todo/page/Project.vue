<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;待审批列表</p>
        <a-divider/>
      </div>

      <a-table :columns="columns" :dataSource="evaluateList">
        <span slot="level" slot-scope="text">
          {{ text | typeFilter }}
        </span>
        <span slot="term" slot-scope="text">
          {{ text | termFilter }}
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

      <a-table :columns="columns" :dataSource="evaluateList2">
        <span slot="level" slot-scope="text">
          {{ text | typeFilter }}
        </span>
        <span slot="term" slot-scope="text">
          {{ text | termFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="evaluateDetail(record)">详情</a>
        </span>
      </a-table>

      <a-modal
        title="审批"
        :width="1200"
        v-model="visible"
        @ok="handleOk"
        okText="提交审批"
      >
        <a-card :bordered="false">
          <detail-list >
            <detail-list-item term="申请人学号">{{applyInfo.studentNo}}</detail-list-item>
            <detail-list-item term="申请人姓名">{{applyInfo.studentName}}</detail-list-item>
            <detail-list-item term="所在年级">{{applyInfo.term | termFilter}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="申请类型">{{applyInfo.level | typeFilter}}</detail-list-item>
            <detail-list-item term="申请时间">{{applyInfo.applyTime}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="学院">{{applyInfo.college}}</detail-list-item>
            <detail-list-item term="专业">{{applyInfo.major}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="申请文">
            {{applyInfo.applyText}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="审批意见">
            <div>
              <a-textarea v-model="evaluation.applyComment" style="width:800px" :rows="6"  placeholder="老师评价"/>
            </div>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="审批结果">
            <a-radio-group v-model="evaluation.status">
              <a-radio value="Approved" defaultChecked>审核通过</a-radio>
              <a-radio value="Rejected">审核驳回</a-radio>
            </a-radio-group>
          </detail-list>
        </a-card>
      </a-modal>
      <a-modal
        title="审批详情"
        :width="800"
        v-model="visible2"
        @ok="close"
      >
        <a-card :bordered="false">
          <detail-list >
            <detail-list-item term="申请人学号">{{applyInfo2.studentNo}}</detail-list-item>
            <detail-list-item term="申请人姓名">{{applyInfo2.studentName}}</detail-list-item>
            <detail-list-item term="所在年级">{{applyInfo2.term | termFilter}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="申请类型">{{applyInfo2.level | typeFilter}}</detail-list-item>
            <detail-list-item term="申请时间">{{applyInfo2.applyTime}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="学院">{{applyInfo2.college}}</detail-list-item>
            <detail-list-item term="专业">{{applyInfo2.major}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="申请文">
            {{applyInfo2.applyText}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="老师评价">
            {{applyInfo2.applyComment | nullFilter}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list>
            <detail-list-item term="审核结果">{{applyInfo2.status | statusFilter}}</detail-list-item>
            <detail-list-item term="评定单位">{{applyInfo2.approver | nullFilter}}</detail-list-item>
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { getAllScholarshipList, scholarshipApproval } from '@/api/scholarship'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'project',
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
      evaluateList: [],
      evaluateList2: [],
      evaluation: {
        id: '',
        applyComment: '',
        status: ''
      },
      // 表头
      columns: [
        {
          title: '申请人学号',
          dataIndex: 'studentNo',
          key: 'id'
        },
        {
          title: '申请人姓名',
          dataIndex: 'studentName'
        },
        {
          title: '学院',
          dataIndex: 'college'
        },
        {
          title: '专业',
          dataIndex: 'major'
        },
        {
          title: '申请类型',
          dataIndex: 'level',
          scopedSlots: { customRender: 'level' }
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
    statusFilter (status) {
      const statusMap = {
        'WaitForApproval': '待审核',
        'Rejected': '驳回',
        'Approved': '通过'
      }
      return statusMap[status]
    },
    typeFilter (status) {
      const statusMap = {
        'COUNTRY_1': '国家奖学金',
        'COUNTRY_2': '国家励志奖学金',
        'SCHOOL_1': '校级一等奖',
        'SCHOOL_2': '校级二等奖',
        'SCHOOL_3': '校级三等奖'
      }
      return statusMap[status]
    },
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
    },
    termFilter (status) {
      const statusMap = {
        'CLASS_1': '大一',
        'CLASS_2': '大一',
        'CLASS_3': '大二',
        'CLASS_4': '大二',
        'CLASS_5': '大三',
        'CLASS_6': '大三',
        'CLASS_7': '大四',
        'CLASS_8': '大四'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      getAllScholarshipList()
        .then(res => {
          this.evaluateList = res.info.notList
          this.evaluateList2 = res.info.list
        })
    },
    accountDetail (record) {
      this.applyInfo = Object.assign({}, record)
      this.evaluation.id = record.id
      this.visible = true
    },
    evaluateDetail (record) {
      this.applyInfo2 = record
      this.visible2 = true
    },
    close () {
      this.visible2 = false
    },
    handleOk () {
      if (!this.evaluation.applyComment) {
        this.$message.error('请录入审批意见')
        return
      }
      if (!this.evaluation.status) {
        this.$message.error('请录入审批结果')
        return
      }
      var param = {
        id: this.evaluation.id,
        applyComment: this.evaluation.applyComment,
        status: this.evaluation.status
      }
      scholarshipApproval(param)
        .then(res => {
          if (res.code === 0) {
            this.$message.info('评定成功')
            this.loadAccountList()
            this.visible = false
          } else {
            this.$message.info('系统错误，评定失败')
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
