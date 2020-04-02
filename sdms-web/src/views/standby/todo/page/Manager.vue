<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;待评价列表</p>
        <a-divider/>
      </div>

      <a-table :columns="columns" :dataSource="evaluateList">
        <span slot="semester" slot-scope="text">
          {{ text | termFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">录入评语</a>
        </span>
      </a-table>

      <a-divider/>

      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;已评价列表</p>
        <a-divider/>
      </div>

      <a-table :columns="columns" :dataSource="evaluateList2">
        <span slot="semester" slot-scope="text">
          {{ text | termFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="evaluateDetail(record)">详情</a>
        </span>
      </a-table>

      <a-modal
        title="审批"
        :width="800"
        v-model="visible"
        @ok="handleOk(applyInfo.id)"
        okText="提交评语"
      >
        <a-card :bordered="false">
          <detail-list >
            <detail-list-item term="学生学号">{{applyInfo.studentNo}}</detail-list-item>
            <detail-list-item term="学生姓名">{{applyInfo.studentName}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="学院">{{applyInfo.college}}</detail-list-item>
            <detail-list-item term="专业">{{applyInfo.major}}</detail-list-item>
            <detail-list-item term="所在年级">{{applyInfo.semester | termFilter}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="自我评价">
            {{applyInfo.selfEval}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="教师评价">
            <div>
              <a-textarea v-model="evaluation.teacherEval" style="width:500px" :rows="6"  placeholder="老师评价"/>
            </div>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="评定等级">
            <a-select v-model="evaluation.grade" style="width: 200px">
                <a-select-option value="A">A</a-select-option>
                <a-select-option value="B">B</a-select-option>
                <a-select-option value="C">C</a-select-option>
                <a-select-option value="D">D</a-select-option>
                <a-select-option value="E">E</a-select-option>
              </a-select>
          </detail-list>
        </a-card>
      </a-modal>
      <a-modal
        title="评价详情"
        :width="800"
        v-model="visible2"
        @ok="close"
      >
        <a-card :bordered="false">
          <detail-list title="学生信息">
            <detail-list-item term="学号">{{applyInfo2.studentNo}}</detail-list-item>
            <detail-list-item term="姓名">{{applyInfo2.studentName}}</detail-list-item>
            <detail-list-item term="学生年级">{{applyInfo2.semester | termFilter}}</detail-list-item>
            <detail-list-item term="学院">{{applyInfo2.college}}</detail-list-item>
            <detail-list-item term="专业">{{applyInfo2.major}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="自我评价">
            {{applyInfo2.selfEval}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="老师评价">
            {{applyInfo2.teacherEval | nullFilter}}
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list>
            <detail-list-item term="评定等级">{{applyInfo2.grade | nullFilter}}</detail-list-item>
            <detail-list-item term="评价老师">{{applyInfo2.teacherName | nullFilter}}</detail-list-item>
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { getAllStudentEvaluate, evaluateAPI } from '@/api/evaluate'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'manager',
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
        teacherEval: '',
        grade: ''
      },
      // 表头
      columns: [
        {
          title: '学生学号',
          dataIndex: 'studentNo',
          key: 'id'
        },
        {
          title: '学生姓名',
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
          title: '所在年级',
          dataIndex: 'semester',
          scopedSlots: { customRender: 'semester' }
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
      getAllStudentEvaluate()
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
      if (!this.evaluation.teacherEval) {
        this.$message.error('请录入教师评语')
        return
      }
      if (!this.evaluation.grade) {
        this.$message.error('请录入评定等级')
        return
      }
      var param = {
        id: this.evaluation.id,
        teacherEval: this.evaluation.teacherEval,
        grade: this.evaluation.grade
      }
      evaluateAPI(param)
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
