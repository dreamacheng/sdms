<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <h1 style="text-align:center">{{competitionDetail.name}}</h1>
      <a-divider style="margin-bottom: 32px"/>
      <detail-list>
        <detail-list-item term="比赛时间"><p style="font-size:18px">{{competitionDetail.competitionTime}}</p></detail-list-item>
        <detail-list-item term="竞赛级别" style="margin-left: 100px"><p style="font-size:18px">{{competitionDetail.level | levelFilter}}</p></detail-list-item>
      </detail-list>
      <detail-list style="margin-top:30px">
        <detail-list-item term="报名起始时间"><p style="font-size:18px">{{competitionDetail.registrationStartTime}}</p></detail-list-item>
        <detail-list-item term="报名结束时间" style="margin-left: 100px"><p style="font-size:18px">{{competitionDetail.registrationEndTime}}</p></detail-list-item>
      </detail-list>
      <a-divider style="margin-bottom: 32px"/>
      <detail-list title="比赛描述">
        {{competitionDetail.desc}}
      </detail-list>
      <a-divider style="margin-bottom: 32px"/>

      <div class="title">参赛学生列表</div>
      <a-table :columns="attendeeColumns" :dataSource="attendeeList"/>
      <div v-show="competitionDetail.type === '已结束'" class="title" style="margin-top:30px">获奖学生列表 <a-button style="float:right" @click="enterHandler">录入获奖学生</a-button></div>
      <a-table v-show="competitionDetail.type === '已结束'" :columns="winnerColumns" :dataSource="prizewinnerList"/>

      <a-modal
        title="获奖信息录入"
        :width="500"
        v-model="visible"
        @ok="handleOk"
        okText="录入"
      >
        <a-form-item
            label="获奖学生学号"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 10, offset:1 }"
          >
          <a-input v-model="winnerInfo.accountNo"/>
        </a-form-item>
        <a-form-item
            label="获奖学生姓名"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 10, offset:1  }"
          >
          <a-input v-model="winnerInfo.accountName"/>
        </a-form-item>
        <a-form-item
            label="成绩"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 10, offset:1 }"
          >
          <a-select v-model="winnerInfo.grade" style="width: 180px">
                <a-select-option value="特等奖">特等奖</a-select-option>
                <a-select-option value="一等奖">一等奖</a-select-option>
                <a-select-option value="二等奖">二等奖</a-select-option>
                <a-select-option value="三等奖">三等奖</a-select-option>
              </a-select>
        </a-form-item>
      </a-modal>

    </a-card>
  </div>
</template>

<script>
import DetailList from '@/components/tools/DetailList'
import { getCompetition, addWinner } from '@/api/competition'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'competitionDetail',
  created () {
    this.loadCurrent(this.$route.query.id)
  },
  data () {
    return {
      visible: false,
      competitionDetail: {},
      attendeeList: [],
      prizewinnerList: [],
      winnerInfo: {
        competitionId: '',
        accountNo: '',
        accountName: '',
        grade: '',
        competitionName: ''
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
  filters: {
    levelFilter (status) {
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
  methods: {
    loadCurrent (id) {
      getCompetition(id)
        .then(res => {
          if (res.code === 0) {
            this.competitionDetail = res.info
            this.attendeeList = res.info.attendeeList
            this.prizewinnerList = res.info.prizewinnerList
          }
        })
    },
    enterHandler () {
      this.visible = true
    },
    handleOk () {
      if (this.winnerInfo.accountNo === '') {
        this.$message.info('请键入获奖者学号')
        return
      }
      if (this.winnerInfo.accountName === '') {
        this.$message.info('请键入获奖者姓名')
        return
      }
      if (this.winnerInfo.grade === '') {
        this.$message.info('请选择获奖级别')
        return
      }
      this.winnerInfo.competitionId = this.competitionDetail.id
      this.winnerInfo.competitionName = this.competitionDetail.name
      const self = this
      addWinner(this.winnerInfo)
        .then(res => {
          if (res.code === 0) {
            if (res.info === 'notJoin') {
              this.$message.info('该生未参加该比赛')
            }
            this.$message.info('录入成功')
            self.loadCurrent(self.competitionDetail.id)
            self.visible = false
          } else {
            self.$message.error('录入失败')
          }
        })
    }
  },
  computed: {
    title () {
      return this.$route.meta.title
    }
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
