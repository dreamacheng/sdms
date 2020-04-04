<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;已发布活动
        <a-button style="float:right" @click="publish"><a-icon type="plus-circle" />发布活动</a-button>
      </p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="competitionList">
          <span slot="name" slot-scope="text, record">
            <a @click="showCompetionDetail(record)">{{ text }}</a>
            </span>
      </a-table>
      <a-modal
        title="活动发布"
        :width="800"
        v-model="addVisible"
        @ok="publishInfo"
        okText="发布"
      >
        <a-card :bordered="false">
            <a-form  ref="formRegister" :form="form" id="formRegister">
                <a-form-item label="活动主题" :label-col="{ span: 6 }" :wrapper-col="{ span: 8, offset: 2 }">
                    <a-input size="large" v-decorator="['title', {rules: [{ required: true, message: '请输入活动主题' }] }]"/>
                </a-form-item>
                <a-form-item label="活动开始时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="活动开始时间" v-decorator="['startTime', {rules: [{ required: true, message: '请选择活动开始时间'}] }]"/>
                </a-form-item>
                <a-form-item label="活动结束时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="活动结束时间" v-decorator="['endTime', {rules: [{ required: true, message: '请选择活动结束时间'}] }]"/>
                </a-form-item>
                <a-form-item label="报名开始时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="报名开始时间" v-decorator="['applyStartTime', {rules: [{ required: true, message: '请选择报名开始时间'}] }]"/>
                </a-form-item>
                <a-form-item label="报名结束时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="报名结束时间" v-decorator="['applyEndTime', {rules: [{ required: true, message: '请选择报名结束时间'}] }]"/>
                </a-form-item>
                <a-form-item label="活动内容" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-textarea  style="width:500px" :rows="10"  v-decorator="['content', {rules: [{ required: true, message: '请添加活动内容'}] }]"/>
                    <!-- <div id="editor" class="editor"></div> -->
                </a-form-item>
            </a-form>
        </a-card>
      </a-modal>

      <a-modal
        title="竞赛信息"
        :width="1000"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false">
          <detail-list>
            <detail-list-item term="竞赛名称">{{competitionDetail.name}}</detail-list-item>
            <detail-list-item term="竞赛级别" style="margin-left: 100px">{{competitionDetail.level | statusFilter}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="报名起始时间">{{competitionDetail.registrationStartTime}}</detail-list-item>
            <detail-list-item term="报名结束时间" style="margin-left: 100px">{{competitionDetail.registrationEndTime}}</detail-list-item>
          </detail-list>
          <detail-list style="margin-top:30px">
            <detail-list-item term="竞赛时间">{{competitionDetail.competitionTime}}</detail-list-item>
          </detail-list>
          <detail-list>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="比赛描述">
            {{competitionDetail.desc}}
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { activityAdd, getActivityList } from '@/api/activity'
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
      editor: '',
      account: {},
      competitionList: [],
      competitionDetail: {},
      // 表头
      columns: [
        {
          title: '活动主题',
          dataIndex: 'title',
          scopedSlots: { customRender: 'name' }
        },
        {
          title: '开始时间',
          dataIndex: 'startTime'
        },
        {
          title: '结束时间',
          dataIndex: 'endTime'
        },
        {
          title: '状态',
          dataIndex: 'type'
        }
      ],
      selectedRowKeys: [],
      selectedRows: []
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
    publish () {
      this.addVisible = true
    },
    loadAccountList () {
      getActivityList()
        .then(res => {
          this.competitionList = res.list
        })
    },
    accountDetail (record) {
      this.account = Object.assign({}, record)
      this.visible = true
    },
    publishInfo () {
      const self = this
      const { form: { validateFields } } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          const addInfo = { ...values }
          activityAdd(addInfo)
            .then((res) => {
              if (res.code === 0) {
                self.$message.info('活动发布成功')
                this.loadAccountList()
                this.addVisible = false
              } else {
                self.$message.error('发布失败')
              }
            })
        }
      })
    },
    showCompetionDetail (record) {
      this.$router.push({
        name: 'activityDetail',
        query: {
          id: record.id
        }
      })
    },
    handleOk () {
      this.visible = false
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
