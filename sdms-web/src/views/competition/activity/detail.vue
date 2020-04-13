<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <h1 style="text-align:center">{{competitionDetail.name}}</h1>
      <a-divider style="margin-bottom: 32px"/>
      <detail-list>
        <detail-list-item term="活动主题"><p style="font-size:18px">{{competitionDetail.title}}</p></detail-list-item>
        <detail-list-item term="发起组织" style="margin-left: 100px"><p style="font-size:18px">{{ competitionDetail.organization }}</p></detail-list-item>
      </detail-list>
      <detail-list style="margin-top:30px">
        <detail-list-item term="报名起始时间"><p style="font-size:18px">{{competitionDetail.applyStartTime}}</p></detail-list-item>
        <detail-list-item term="报名结束时间" style="margin-left: 100px"><p style="font-size:18px">{{competitionDetail.applyEndTime}}</p></detail-list-item>
      </detail-list>
      <detail-list style="margin-top:30px">
        <detail-list-item term="活动开始时间"><p style="font-size:18px">{{competitionDetail.startTime}}</p></detail-list-item>
        <detail-list-item term="活动结束时间" style="margin-left: 100px"><p style="font-size:18px">{{competitionDetail.endTime}}</p></detail-list-item>
      </detail-list>
      <a-divider style="margin-bottom: 32px"/>
      <detail-list title="活动内容">
        {{competitionDetail.content}}
      </detail-list>
      <a-divider style="margin-bottom: 32px"/>

      <div class="title">参加学生列表</div>
      <a-table :columns="attendeeColumns" :dataSource="attendeeList">
        <span slot="action" slot-scope="text, record">
          <a @click="viewSummary(record.accountNo)">查看总结</a>
        </span>
      </a-table>
      <a-divider style="margin-bottom: 32px"/>

      <a-modal
        title="学生总结"
        :width="1000"
        v-model="visible2"
        @ok="close"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="总结学生">{{activityResult.accountVO.username}}</detail-list-item>
            <detail-list-item term="学生学号" style="margin-left: 100px">{{activityResult.accountVO.accountNo}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="活动总结">
            {{activityResult.summary}}
          </detail-list>
        </a-card>
      </a-modal>

    </a-card>
  </div>
</template>

<script>
import DetailList from '@/components/tools/DetailList'
import { getActivity, getStudentSummary } from '@/api/activity'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'activityDetail',
  created () {
    this.loadCurrent(this.$route.query.id)
  },
  data () {
    return {
      activityResult: {
        activityPracticeVO: {
          title: '',
          organization: '',
          startTime: '',
          endTime: ''
        },
        accountVO: {
          accountNo: '',
          username: ''
        },
        summary: ''
      },
      visible: false,
      visible2: false,
      competitionDetail: {},
      attendeeList: [],
      attendeeColumns: [
        {
          title: '学生学号',
          dataIndex: 'accountNo'
        },
        {
          title: '学生姓名',
          dataIndex: 'username'
        },
        {
          title: '学院',
          dataIndex: 'college'
        },
        {
          title: '科系',
          dataIndex: 'major'
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ]
    }
  },
  methods: {
    loadCurrent (id) {
      if (id) {
        getActivity(id)
          .then(res => {
            if (res.code === 0) {
              this.competitionDetail = res.info
              this.attendeeList = res.info.attendeeList
            }
          })
      }
    },
    viewSummary (accountNo) {
      const _this = this
      const param = {
        accountNo: accountNo,
        activityId: this.competitionDetail.id
      }
      getStudentSummary(param)
        .then(res => {
          if (res.code === 0 & res.info.summary !== '' && res.info.summary != null) {
            _this.activityResult = res.info
            _this.visible2 = true
          } else {
            _this.$message.info('该生尚未录入总结')
          }
        })
    },
    enterHandler () {
      this.visible = true
    },
    close () {
      this.visible2 = false
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
