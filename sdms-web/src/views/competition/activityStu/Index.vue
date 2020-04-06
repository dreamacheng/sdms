<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;活动列表
      </p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="competitionList">
          <span slot="action" slot-scope="text, record">
            <a-button @click="showCompetionDetail(record)">详情</a-button>
            </span>
      </a-table>
      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;已参加列表
      </p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="activityList">
        <span slot="name" slot-scope="text, record">
            {{ record.activityPracticeVO.title }}
          </span>
          <span slot="startTime" slot-scope="text, record">
            {{ record.activityPracticeVO.startTime }}
          </span>
          <span slot="endTime" slot-scope="text, record">
            {{ record.activityPracticeVO.endTime }}
          </span>
          <span slot="type" slot-scope="text, record">
            <span v-if="record.summary === '' || record.summary === null">已参加，尚未录入总结</span>
            <span v-else>已结束归档</span>
          </span>
          <span slot="action"  slot-scope="text, record">
            <a-button v-if="record.summary === '' || record.summary === null" slot="action" @click="showDetail(record)">录入活动总结</a-button>
            <a-button v-else @click="showSummary(record)">查看详细</a-button>
          </span>
      </a-table>
      <a-modal
        title="活动信息"
        :width="1000"
        v-model="visible"
        @ok="handleOk"
        okText="参加活动"
        :okButtonProps="joinBtn"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="活动名称">{{competitionDetail.title}}</detail-list-item>
            <detail-list-item term="举办单位" style="margin-left: 100px">{{competitionDetail.organization}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="报名开始时间">{{competitionDetail.applyStartTime}}</detail-list-item>
            <detail-list-item term="报名结束时间" style="margin-left: 100px">{{competitionDetail.applyEndTime}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="活动开始时间">{{competitionDetail.startTime}}</detail-list-item>
            <detail-list-item term="活动结束时间" style="margin-left: 100px">{{competitionDetail.endTime}}</detail-list-item>
          </detail-list>
          <detail-list>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="活动内容">
            {{competitionDetail.content}}
          </detail-list>
        </a-card>
      </a-modal>
      <a-modal
        title="活动信息"
        :width="1000"
        v-model="visible2"
        @ok="close(activityResult.id)"
        okText="提交"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="活动名称">{{activityResult.activityPracticeVO.title}}</detail-list-item>
            <detail-list-item term="举办单位" style="margin-left: 100px">{{activityResult.activityPracticeVO.organization}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="报名开始时间">{{activityResult.activityPracticeVO.applyStartTime}}</detail-list-item>
            <detail-list-item term="报名结束时间" style="margin-left: 100px">{{activityResult.activityPracticeVO.applyEndTime}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="活动开始时间">{{activityResult.activityPracticeVO.startTime}}</detail-list-item>
            <detail-list-item term="活动结束时间" style="margin-left: 100px">{{activityResult.activityPracticeVO.endTime}}</detail-list-item>
          </detail-list>
          <detail-list>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="活动总结">
            <a-textarea v-model="summary" :rows="10" placeholder="请发挥不少于300字的活动总结" maxLength="500"/>
          </detail-list>
        </a-card>
      </a-modal>
      <a-modal
        title="活动信息"
        :width="1000"
        v-model="visible3"
        @ok="close2"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="活动名称">{{summaryDetail.activityPracticeVO.title}}</detail-list-item>
            <detail-list-item term="举办单位" style="margin-left: 100px">{{summaryDetail.activityPracticeVO.organization}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="报名开始时间">{{summaryDetail.activityPracticeVO.applyStartTime}}</detail-list-item>
            <detail-list-item term="报名结束时间" style="margin-left: 100px">{{summaryDetail.activityPracticeVO.applyEndTime}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="活动开始时间">{{summaryDetail.activityPracticeVO.startTime}}</detail-list-item>
            <detail-list-item term="活动结束时间" style="margin-left: 100px">{{summaryDetail.activityPracticeVO.endTime}}</detail-list-item>
          </detail-list>
          <detail-list>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="活动总结">
            {{summaryDetail.summary}}
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { getActivityList, joinActivity, getCurActivityList, summaryActivity } from '@/api/activity'
// import E from 'wangeditor'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'competitionGame',
  data () {
    return {
      form: this.$form.createForm(this),
      description: '发布学科竞赛信息与竞赛信息查询',
      confirmOpt: false,
      addVisible: false,
      visible: false,
      visible2: false,
      visible3: false,
      editor: '',
      account: {},
      competitionList: [],
      activityList: [],
      competitionDetail: {},
      joinBtn: {
        props: {
          disabled: true
        }
      },
      activityResult: {
        activityPracticeVO: {
          title: '',
          organization: '',
          startTime: '',
          endTime: ''
        }
      },
      summaryDetail: {
        activityPracticeVO: {
          title: '',
          organization: '',
          startTime: '',
          endTime: ''
        },
        summary: ''
      },
      summary: '',
      // 表头
      columns: [
        {
          title: '活动主题',
          dataIndex: 'title',
          scopedSlots: { customRender: 'name' }
        },
        {
          title: '开始时间',
          dataIndex: 'startTime',
          scopedSlots: { customRender: 'startTime' }
        },
        {
          title: '结束时间',
          dataIndex: 'endTime',
          scopedSlots: { customRender: 'endTime' }
        },
        {
          title: '状态',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    publish () {
      this.addVisible = true
    },
    loadAccountList () {
      const self = this
      getActivityList()
        .then(res => {
          self.competitionList = res.list
        })
      getCurActivityList()
        .then(res => {
          self.activityList = res.list
        })
    },
    accountDetail (record) {
      this.account = Object.assign({}, record)
      this.visible = true
    },
    showCompetionDetail (record) {
      this.competitionDetail = record
      if (record.type === '开始报名') {
        this.joinBtn.props.disabled = false
      }
      this.visible = true
    },
    showSummary (record) {
      this.summaryDetail = record
      this.visible3 = true
    },
    showDetail (record) {
      this.activityResult = record
      console.log(this.activityResult)
      this.visible2 = true
    },
    close (id) {
      const _this = this
      if (this.summary === '') {
        this.$message.warning('请发挥不少于300字的活动总结')
        return
      }
      var parameter = new FormData()
      parameter.append('activityResultId', id)
      parameter.append('summary', this.summary)
      summaryActivity(parameter)
        .then(res => {
          if (res.code === 0) {
            _this.$message.success('提交成功')
            this.loadAccountList()
            this.visible2 = false
          } else {
            _this.$message.error('提交失败')
          }
        })
    },
    close2 () {
      this.visible3 = false
    },
    handleOk () {
      const self = this
      var param = new FormData()
      param.append('activityId', this.competitionDetail.id)
      joinActivity(param).then(res => {
        if (res.code === 0) {
          self.$message.success('参加活动成功')
          this.loadAccountList()
          self.visible = false
        } else {
          self.$message.error('参加活动失败')
        }
      })
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    }
  }
}
</script>
