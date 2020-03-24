<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <p style="font-size: 20px"><a-icon type="align-center" />&nbsp;&nbsp;已发布竞赛
        <a-button style="float:right" @click="publish"><a-icon type="plus-circle" />发布竞赛</a-button>
      </p>
      <a-divider/>
      <a-table :columns="columns" :dataSource="competitionList">
          <span slot="name" slot-scope="text, record">
            <a @click="showCompetionDetail(record)">{{ text }}</a>
            </span>
      </a-table>
      <a-modal
        title="学科竞赛发布"
        :width="1000"
        v-model="addVisible"
        @ok="publishInfo"
        okText="发布"
      >
        <a-card :bordered="false">
            <a-form  ref="formRegister" :form="form" id="formRegister">
                <a-form-item label="比赛名称" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                    <a-input size="large" v-decorator="['name', {rules: [{ required: true, message: '请输入比赛名称' }] }]"/>
                </a-form-item>
                <a-form-item label="比赛级别" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                    <a-select v-decorator="['level', {rules: [{ required: true, message: '请选择比赛级别' }] }]">
                        <a-select-option value="NATIONAL">国家级</a-select-option>
                        <a-select-option value="PROVINCIAL">省级</a-select-option>
                        <a-select-option value="CITY">市级</a-select-option>
                        <a-select-option value="SCHOOL">校级</a-select-option>
                        <a-select-option value="COLLEGE">院级</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="比赛时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="比赛时间" v-decorator="['competitionTime', {rules: [{ required: true, message: '请选择比赛时间'}] }]"/>
                </a-form-item>
                <a-form-item label="报名开始时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="报名开始时间" v-decorator="['registrationStartTime', {rules: [{ required: true, message: '请选择报名开始时间'}] }]"/>
                </a-form-item>
                <a-form-item label="报名结束时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" showTime placeholder="报名结束时间" v-decorator="['registrationEndTime', {rules: [{ required: true, message: '请选择报名结束时间'}] }]"/>
                </a-form-item>
                <a-form-item label="比赛描述" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-textarea  style="width:500px" :rows="10"  v-decorator="['desc', {rules: [{ required: true, message: '请添加比赛描述'}] }]"/>
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
import { getCompetitionList, applyCompetition } from '@/api/competition'
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
          title: '比赛名称',
          dataIndex: 'name',
          scopedSlots: { customRender: 'name' }
        },
        {
          title: '比赛时间',
          dataIndex: 'competitionTime'
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
      getCompetitionList()
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
          console.log('---info:')
          console.log(addInfo)
          applyCompetition(addInfo)
            .then((res) => {
              if (res.code === 0) {
                self.$message.info('该竞赛发布成功')
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
        name: 'competitionDetail',
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
