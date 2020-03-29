<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">

      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="semester" slot-scope="text">
          {{ text | statusFilter}}
        </span>
        <span slot="grade" slot-scope="text">
          {{ text | nullFilter}}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
        </span>
      </a-table>

      <a-modal
        title="评价详情"
        :width="800"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false">
          <detail-list title="学生信息">
            <detail-list-item term="学号">{{evaluation.studentNo}}</detail-list-item>
            <detail-list-item term="姓名">{{evaluation.studentName}}</detail-list-item>
            <detail-list-item term="评价学期">{{evaluation.semester | statusFilter}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="自我评价">
            {{evaluation.selfEval}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="老师评价">
            {{evaluation.teacherEval | nullFilter}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list>
            <detail-list-item term="评定等级">{{evaluation.grade | nullFilter}}</detail-list-item>
            <detail-list-item term="评价老师">{{evaluation.teacherName | nullFilter}}</detail-list-item>
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { queryCurAPI } from '@/api/evaluate'

const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  data () {
    return {
      description: '评价总览',
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
      evaluation: {},
      accountdata: [],
      // 表头
      columns: [
        {
          title: '学年',
          dataIndex: 'semester',
          scopedSlots: { customRender: 'semester' }
        },
        {
          title: '学期总评价',
          dataIndex: 'grade',
          scopedSlots: { customRender: 'grade' }
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
        'CLASS_1': '大一第一学年',
        'CLASS_2': '大一第二学年',
        'CLASS_3': '大二第一学年',
        'CLASS_4': '大二第二学年',
        'CLASS_5': '大三第一学年',
        'CLASS_6': '大三第二学年',
        'CLASS_7': '大四第一学年',
        'CLASS_8': '大四第二学年'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      queryCurAPI()
        .then(res => {
          const result = res.list
          this.accountdata = result
        })
    },
    accountDetail (record) {
      this.evaluation = record
      this.visible = true
    },
    handleOk () {
      this.visible = false
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
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
