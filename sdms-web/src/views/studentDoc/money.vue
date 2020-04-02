
<template>
  <page-view :title="`学号: ${accountInfo.accountNo}`" logo="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585513352783&di=5a0ecb8d17c59b7030c0d63003b35d81&imgtype=0&src=http%3A%2F%2Fimg.ccutu.com%2Fupload%2Fimages%2F2017-6%2Fp00032164.jpg">

    <div slot="headerContent">
      <detail-list  :col="3" class="detail-layout">
        <detail-list-item term="学院">{{accountInfo.college}}</detail-list-item>
        <detail-list-item term="专业">{{accountInfo.major}}</detail-list-item>
        <detail-list-item term="当前学年">{{accountInfo.currentTerm | termFilter}}</detail-list-item>
      </detail-list>
      <detail-list  :col="3" class="detail-layout">
        <detail-list-item term="入学时间">{{accountInfo.enrollment}}</detail-list-item>
        <detail-list-item term="联系方式">{{accountInfo.tel}}</detail-list-item>
        <detail-list-item term="联系地址">{{accountInfo.lodgingHouse}}</detail-list-item>
      </detail-list>
    </div>
    <!-- actions -->
    <template slot="action">
      <a-button type="primary" @click="showApplyModal = true" v-show="!showApply">申请当前学年奖学金</a-button>
    </template>
    <div v-show="showApply">
      <a-card :bordered="false" title="当前学期申请">
        <a-steps :current="approvalResult">
          <a-step title="提交申请">
            <a-icon type="edit" slot="icon" />
          </a-step>
          <a-step title="审核中">
            <a-icon type="solution" slot="icon" />
          </a-step>
          <a-step title="完成">
            <a-icon type="check" slot="icon" />
          </a-step>
        </a-steps>
      </a-card>
      <a-card style="margin-top: 24px" :bordered="false" title="申请信息">
        <detail-list>
          <detail-list-item term="姓名">{{accountInfo.username}}</detail-list-item>
          <detail-list-item term="当前学年">{{accountInfo.currentTerm | termFilter}}</detail-list-item>
          <detail-list-item term="学院">{{accountInfo.college}}</detail-list-item>
          <detail-list-item term="专业">{{accountInfo.major}}</detail-list-item>
          <detail-list-item term="联系方式">{{accountInfo.tel}}</detail-list-item>
          <detail-list-item term="联系地址">{{accountInfo.lodgingHouse}}</detail-list-item>
          <detail-list-item term="申请类型">{{currentScholarship.level | typeFilter}}</detail-list-item>
          <detail-list-item term="申请时间">{{currentScholarship.applyTime}}</detail-list-item>
        </detail-list>
        <detail-list :col="1">
          <detail-list-item term="申请文">{{currentScholarship.applyText}}</detail-list-item>
        </detail-list>
      </a-card>
    </div>

    <a-card style="margin-top: 24px" :bordered="false" title="申请人履历">
      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="semester" slot-scope="text">
          {{ text | termFilter}}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
        </span>
      </a-table>
    </a-card>

    <a-card style="margin-top: 24px" :bordered="false" title="奖学金申请记录">
      <a-table
        :columns="operationColumns"
        :dataSource="scholarshipList"
      >
        <span slot="level" slot-scope="text">
            {{text | typeFilter}}
        </span>
        <span slot="term" slot-scope="text">
            {{text | termFilter }}
        </span>
        <span slot="status" slot-scope="text">
            {{text | statusFilter}}
        </span>
        <span slot="approver" slot-scope="text">
            {{text | nullFilter}}
        </span>
        <span slot="applyComment" slot-scope="text">
            {{text | nullFilter}}
        </span>
      </a-table>
    </a-card>

    <a-modal
        title="评价详情"
        :width="800"
        v-model="visible"
        @ok="submitApply"
      >
        <a-card :bordered="false">
          <detail-list title="学生信息">
            <detail-list-item term="学号">{{evaluation.studentNo}}</detail-list-item>
            <detail-list-item term="姓名">{{evaluation.studentName}}</detail-list-item>
            <detail-list-item term="评价学期">{{evaluation.semester | termFilter}}</detail-list-item>
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

      <a-modal
        title="奖学金申请"
        :width="800"
        v-model="showApplyModal"
        @ok="submitApply"
        okText="提交"
      >
        <a-card :bordered="false">
          <a-form  ref="formRegister" :form="form" id="formRegister">
            <a-form-item label="学号" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                {{accountInfo.accountNo}}
            </a-form-item>
            <a-form-item label="姓名" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                {{accountInfo.username}}
            </a-form-item>
            <a-form-item label="当前学期" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
                {{accountInfo.currentTerm | termFilter}}
            </a-form-item>
            <a-form-item label="申请类型" :label-col="{ span: 6 }" :wrapper-col="{ span: 6, offset: 2 }">
              <a-select v-decorator="['level', {rules: [{ required: true, message: '请选择申请类型' }] }]">
                <a-select-option value="COUNTRY_1">国家奖学金</a-select-option>
                <a-select-option value="COUNTRY_2">国家励志奖学金</a-select-option>
                <a-select-option value="SCHOOL_1">校级一等奖</a-select-option>
                <a-select-option value="SCHOOL_2">校级二等奖</a-select-option>
                <a-select-option value="SCHOOL_3">校级三等奖</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="自我评价" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
              <a-textarea  style="width:500px" :rows="10"  v-decorator="['applyText', {rules: [{ required: true, message: '请添加自我评价'}] }]"/>
              <!-- <div id="editor" class="editor"></div> -->
            </a-form-item>
          </a-form>
        </a-card>
      </a-modal>

  </page-view>
</template>

<script>
import { mixinDevice } from '@/utils/mixin'
import { PageView } from '@/layouts'
import DetailList from '@/components/tools/DetailList'
import { currentUserInfo } from '@/api/login'
import { getCurTerm, getScholarshipList, scholarshipAdd } from '@/api/scholarship'
import { queryCurAPI } from '@/api/evaluate'

const DetailListItem = DetailList.Item

export default {
  name: 'money',
  components: {
    PageView,
    DetailList,
    DetailListItem
  },
  mixins: [mixinDevice],
  data () {
    return {
      form: this.$form.createForm(this),
      evaluation: {},
      visible: false,
      accountInfo: {},
      showApply: false,
      showApplyModal: false,
      approvalResult: 1,
      currentScholarship: {
        level: '',
        applyTime: ''
      },
      scholarshipList: [],
      accountdata: [],
      columns: [
        {
          title: '学年',
          dataIndex: 'semester',
          scopedSlots: { customRender: 'semester' }
        },
        {
          title: '学期总评价',
          dataIndex: 'grade'
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      operationColumns: [
        {
          title: '获奖级别',
          dataIndex: 'level',
          key: 'level',
          scopedSlots: { customRender: 'level' }
        },
        {
          title: '申请学期',
          dataIndex: 'term',
          key: 'term',
          scopedSlots: { customRender: 'term' }
        },
        {
          title: '审核结果',
          dataIndex: 'status',
          key: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '审核单位',
          dataIndex: 'approver',
          key: 'approver',
          scopedSlots: { customRender: 'approver' }
        },
        {
          title: '审批意见',
          dataIndex: 'applyComment',
          key: 'applyComment',
          scopedSlots: { customRender: 'applyComment' }
        }
      ]
    }
  },
  created () {
    this.loadCurrent()
  },
  methods: {
    loadCurrent () {
      const self = this
      currentUserInfo()
        .then(res => {
          if (res.code === 0) {
            self.accountInfo = res.info
            getCurTerm(res.info.currentTerm)
              .then(res => {
                if (res.code === 0) {
                  if (res.info) {
                    if (res.info.status !== 'WaitForApproval') {
                      self.approvalResult = 2
                    }
                    self.showApply = true
                    self.currentScholarship = res.info
                  }
                }
              })
          }
        })
      getScholarshipList()
        .then(res => {
          if (res.code === 0) {
            self.scholarshipList = res.list
          }
        })
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
    submitApply () {
      const self = this
      const { form: { validateFields } } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          const addInfo = { ...values }
          scholarshipAdd(addInfo)
            .then((res) => {
              if (res.code === 0) {
                self.$message.info('申请成功')
                self.loadCurrent()
                self.showApplyModal = false
              } else {
                self.$message.error('申请失败')
              }
            })
        }
      })
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'WaitForApproval': '待审核',
        'Rejected': '拒绝',
        'Approved': '通过'
      }
      return statusMap[status]
    },
    typeFilter (status) {
      const statusMap = {
        'COUNTRY_1': '国家奖学金',
        'COUNTRY_2': '国家励志奖学金',
        'SCHOOL_1': '校级一等奖',
        'SCHOOL_2': '校级二等奖',
        'SCHOOL_3': '校级三等奖'
      }
      return statusMap[status]
    },
    termFilter (status) {
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
    },
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
    },
    statusTypeFilter (type) {
      const statusTypeMap = {
        'agree': 'success',
        'reject': 'error'
      }
      return statusTypeMap[type]
    }
  }
}
</script>

<style lang="less" scoped>

  .detail-layout {
    margin-left: 44px;
  }
  .text {
    color: rgba(0, 0, 0, .45);
  }

  .heading {
    color: rgba(0, 0, 0, .85);
    font-size: 20px;
  }

  .no-data {
    color: rgba(0, 0, 0, .25);
    text-align: center;
    line-height: 64px;
    font-size: 16px;

    i {
      font-size: 24px;
      margin-right: 16px;
      position: relative;
      top: 3px;
    }
  }

  .mobile {
    .detail-layout {
      margin-left: unset;
    }
    .text {

    }
    .status-list {
      text-align: left;
    }
  }
</style>
