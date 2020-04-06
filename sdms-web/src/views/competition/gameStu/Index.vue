<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;已发布竞赛</p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="competitionList">
          <span slot="action" slot-scope="text, record">
            <a-button @click="showCompetionDetail(record)">详情</a-button>
          </span>
      </a-table>

      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;已参赛列表</p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="joinList">
          <span slot="action" slot-scope="text, record">
            <a-button @click="showCompetionDetail(record)">详情</a-button>
          </span>
      </a-table>

      <a-modal
        title="竞赛信息"
        :width="1000"
        v-model="visible"
        @ok="handleOk"
        okText="参加比赛"
        :okButtonProps="joinBtn"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="竞赛名称">{{competitionDetail.name}}</detail-list-item>
            <detail-list-item term="竞赛级别" style="margin-left: 100px">{{competitionDetail.level | statusFilter}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:20px">
            <detail-list-item term="报名起始时间">{{competitionDetail.registrationStartTime}}</detail-list-item>
            <detail-list-item term="报名结束时间" style="margin-left: 100px">{{competitionDetail.registrationEndTime}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:20px">
            <detail-list-item term="竞赛时间">{{competitionDetail.competitionTime}}</detail-list-item>
          </detail-list>
          <detail-list>
          </detail-list>
          <a-divider style="margin-bottom: 10px"/>
          <detail-list title="比赛描述">
            {{competitionDetail.desc}}
          </detail-list>
          <a-divider style="margin-bottom: 10px"/>
          <div class="title">参赛学生列表</div>
            <a-table :columns="attendeeColumns" :dataSource="attendeeList"/>
          <div v-show="competitionDetail.type === '已结束'">
            <div class="title">获奖学生列表</div>
            <a-table :columns="winnerColumns" :dataSource="prizewinnerList"/>
          </div>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { getCompetitionList, getCompetition, joinCompetition, getCompetitionPersonal } from '@/api/competition'
// import E from 'wangeditor'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'competitionGameStu',
  data () {
    return {
      description: '发布学科竞赛信息与竞赛信息查询',
      confirmOpt: false,
      visible: false,
      editor: '',
      account: {},
      competitionList: [],
      joinList: [],
      attendeeList: [],
      prizewinnerList: [],
      competitionDetail: {},
      // 表头
      columns: [
        {
          title: '比赛名称',
          dataIndex: 'name'
        },
        {
          title: '比赛时间',
          dataIndex: 'competitionTime'
        },
        {
          title: '状态',
          dataIndex: 'type'
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      selectedRows: [],
      joinBtn: {
        props: {
          disabled: true
        }
      },
      attendeeColumns: [
        {
          title: '参赛者学号',
          dataIndex: 'accountNo'
        },
        {
          title: '参赛者姓名',
          dataIndex: 'username'
        },
        {
          title: '学院',
          dataIndex: 'college'
        },
        {
          title: '科系',
          dataIndex: 'major'
        }
      ],
      winnerColumns: [
        {
          title: '获奖者学号',
          dataIndex: 'accountNo'
        },
        {
          title: '获奖者姓名',
          dataIndex: 'accountName'
        },
        {
          title: '成绩',
          dataIndex: 'grade'
        }
      ]
    }
  },
  //  mounted () {
  //    this.editor = new E('#editor')
  //    this.editor.create()
  //  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'NATIONAL': '国家级',
        'PROVINCIAL': '省级',
        'CITY': '市级',
        'SCHOOL': '校级',
        'COLLEGE': '院级'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      getCompetitionList()
        .then(res => {
          this.competitionList = res.list
        })
      getCompetitionPersonal()
        .then(res => {
          this.joinList = res.list
        })
    },
    showCompetionDetail (record) {
      const _this = this
      this.competitionDetail = record
      getCompetition(record.id)
        .then(res => {
          if (res.code === 0) {
            _this.attendeeList = res.info.attendeeList
            _this.prizewinnerList = res.info.prizewinnerList
          }
        })
      if (record.type === '报名中') {
        this.joinBtn.props.disabled = false
      }
      this.visible = true
    },
    handleOk () {
      const _this = this
      var parameter = new FormData()
      parameter.append('competitionId', this.competitionDetail.id)
      joinCompetition(parameter)
        .then(res => {
          if (res.code === 0) {
            _this.loadAccountList()
            _this.$message.success('报名成功！')
            _this.visible = false
          } else {
            _this.$message.error('报名失败！')
          }
        })
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
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

<style lang="less" scoped>
  .title {
    color: rgba(0,0,0,.85);
    font-size: 16px;
    font-weight: 500;
    margin-bottom: 16px;
  }
</style>
