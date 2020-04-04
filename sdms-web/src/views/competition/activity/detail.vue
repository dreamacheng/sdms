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
      <a-table :columns="attendeeColumns" :dataSource="attendeeList"/>
      <a-divider style="margin-bottom: 32px"/>

    </a-card>
  </div>
</template>

<script>
import DetailList from '@/components/tools/DetailList'
import { getActivity } from '@/api/activity'
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
      visible: false,
      competitionDetail: {},
      attendeeList: [],
      attendeeColumns: [
        {
          title: '学生学号',
          dataIndex: 'accountNo'
        },
        {
          title: '学生姓名',
          dataIndex: 'accountName'
        },
        {
          title: '学院',
          dataIndex: 'college'
        },
        {
          title: '科系',
          dataIndex: 'major'
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
    enterHandler () {
      this.visible = true
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
