<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="form"/>&nbsp;处分列表<a-button style="float:right" @click="publish"><a-icon type="plus-circle" />录入学生处分</a-button></p>
      </div>
      <a-divider/>
      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="isCancel" slot-scope="text">
          {{ text | cancelFilter }}
        </span>
        <span slot="type" slot-scope="text">
          {{ text | typeFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
        </span>
      </a-table>

      <a-modal
        title="添加处分信息"
        :width="1000"
        v-model="addVisible"
        @ok="publishInfo"
        okText="提交"
      >
        <a-card :bordered="false">
            <a-form  ref="formRegister" :form="form" id="formRegister">
                <a-form-item label="受处分人学号" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                    <a-input size="large" v-decorator="['punishmentNo', {rules: [{ required: true, message: '请输入受处分人学号' }] }]"/>
                </a-form-item>
                <a-form-item label="受处分人姓名" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                    <a-input size="large" v-decorator="['punishmentName', {rules: [{ required: true, message: '请输入受处分人姓名' }] }]"/>
                </a-form-item>
                <a-form-item label="受处分类型" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                    <a-select v-decorator="['type', {rules: [{ required: true, message: '请选择受处分类型' }] }]">
                        <a-select-option value="WARNING">警告</a-select-option>
                        <a-select-option value="FAULT_1">记过</a-select-option>
                        <a-select-option value="FAULT_2">记大过</a-select-option>
                        <a-select-option value="SCHOOL_VIEW">留校查看</a-select-option>
                        <a-select-option value="EXIT">开除学籍</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="处分生效时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" placeholder="比赛时间" v-decorator="['punishmentTime', {rules: [{ required: true, message: '请选择比赛时间'}] }]"/>
                </a-form-item>
                <a-form-item label="取消时间" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-date-picker size="large" placeholder="报名开始时间" v-decorator="['cancelTime', {rules: [{ required: true, message: '请选择报名开始时间'}] }]"/>
                </a-form-item>
                <a-form-item label="处分原因及描述" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-textarea  style="width:500px" :rows="10"  v-decorator="['desc', {rules: [{ required: true, message: '请添加比赛描述'}] }]"/>
                    <!-- <div id="editor" class="editor"></div> -->
                </a-form-item>
            </a-form>
        </a-card>
      </a-modal>

      <a-modal
        title="审批"
        :width="800"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false" class="fo">
          <detail-list title="基本信息">
            <detail-list-item term="受处分人学号">{{applyInfo.punishmentNo}}</detail-list-item>
            <detail-list-item style="margin-left:50px" term="受处分人姓名">{{applyInfo.punishmentName}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="处分类型">{{applyInfo.type | typeFilter}}</detail-list-item>
            <detail-list-item style="margin-left:50px" term="处分时间">{{applyInfo.punishmentTime}}</detail-list-item>
          </detail-list>
          <detail-list>
            <detail-list-item term="是否取消">{{applyInfo.isCancel | cancelFilter}}</detail-list-item>
            <detail-list-item style="margin-left:50px" term="取消时间">{{applyInfo.cancelTime}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="收处分原因及描述">
            {{applyInfo.desc}}
          </detail-list>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { punishmentAdd, getPunishmentAll } from '@/api/punishment'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'member',
  data () {
    return {
      form: this.$form.createForm(this),
      addVisible: false,
      description: '',
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
      applyInfo: {},
      accountdata: [],
      // 表头
      columns: [
        {
          title: '受处分人学号',
          dataIndex: 'punishmentNo',
          key: 'id'
        },
        {
          title: '受处分人姓名',
          dataIndex: 'punishmentName'
        },
        {
          title: '处分类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '是否取消',
          dataIndex: 'isCancel',
          scopedSlots: { customRender: 'isCancel' }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      queryParam: {
        username: '',
        accountNo: '',
        type: '',
        pageInfo: {
          pageNum: '0',
          pageSize: '1000'
        }
      },
      selectedRows: []
    }
  },
  filters: {
    typeFilter (status) {
      const statusMap = {
        'WARNING': '警告',
        'FAULT_1': '记过',
        'FAULT_2': '记大过',
        'SCHOOL_VIEW': '留校查看',
        'EXIT': '开除学籍'
      }
      return statusMap[status]
    },
    cancelFilter (status) {
      const statusMap = {
        '0': '未取消',
        '1': '已取消'
      }
      return statusMap[status]
    },
    applyFilter (status) {
      const statusMap = {
        'LEAGUE_APPLY': '入团申请',
        'PART_APPLY': '入党申请'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      getPunishmentAll(this.queryParam)
        .then(res => {
          const result = res.list
          this.accountdata = result
        })
    },
    publish () {
      this.addVisible = true
    },
    publishInfo () {
      const self = this
      const { form: { validateFields } } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          const addInfo = { ...values }
          punishmentAdd(addInfo)
            .then((res) => {
              if (res.code === 0) {
                self.$message.info('提交成功')
                this.loadAccountList()
                this.addVisible = false
              } else {
                self.$message.error('提交失败')
              }
            })
        }
      })
    },
    accountDetail (record) {
      this.applyInfo = Object.assign({}, record)
      this.visible = true
    },
    handleOk () {
      this.visible = false
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
