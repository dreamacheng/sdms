
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

    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="profile"/>&nbsp;获奖信息</p>
      </div>
      <a-table
        :columns="operationColumns"
        :dataSource="scholarshipList"
      >
        <span slot="level" slot-scope="text">
            {{text | levelFilter}}
        </span>
        <span slot="term" slot-scope="text">
            {{text | termFilter }}
        </span>
        <span slot="approver" slot-scope="text">
            {{text | nullFilter}}
        </span>
      </a-table>
    </a-card>

    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <P style="font-size: 20px"><a-icon type="profile"/>&nbsp;所获证书<a-button style="float:right" @click="publish"><a-icon type="plus-circle" />录入证书</a-button></p>
      </div>
      <a-table
        :columns="columns"
        :dataSource="certificateList"
      >
      </a-table>
    </a-card>

    <a-modal
        title="证书信息"
        :width="600"
        v-model="addVisible"
        @ok="publishInfo"
        okText="录入"
      >
        <a-card :bordered="false">
            <a-form  ref="formRegister" :form="form" id="formRegister">
                <a-form-item label="证书名称" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-input size="large" v-decorator="['name', {rules: [{ required: true, message: '请输入证书名称' }] }]"/>
                </a-form-item>
                <a-form-item label="成绩" :label-col="{ span: 6 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-input size="large" v-decorator="['grade', {rules: [{ required: true, message: '请输入成绩' }] }]"/>
                </a-form-item>
            </a-form>
        </a-card>
      </a-modal>

  </page-view>
</template>

<script>
import { mixinDevice } from '@/utils/mixin'
import { PageView } from '@/layouts'
import { currentUserInfo } from '@/api/login'
import DetailList from '@/components/tools/DetailList'
import { getScholarshipGetList } from '@/api/scholarship'
import { certificateAdd, getCertificateList } from '@/api/certificate'

const DetailListItem = DetailList.Item

export default {
  name: 'practice',
  components: {
    PageView,
    DetailList,
    DetailListItem
  },
  mixins: [mixinDevice],
  data () {
    return {
      form: this.$form.createForm(this),
      accountInfo: {},
      activeTabKey: '1',
      scholarshipList: [],
      certificateList: [],
      addVisible: false,
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
          title: '审核单位',
          dataIndex: 'approver',
          key: 'approver',
          scopedSlots: { customRender: 'approver' }
        }
      ],
      columns: [
        {
          title: '证书名称',
          dataIndex: 'name',
          key: 'name'
        },
        {
          title: '证书成绩',
          dataIndex: 'grade',
          key: 'grade'
        }
      ]
    }
  },
  filters: {
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
    statusFilter (status) {
      const statusMap = {
        'WaitForApproval': '待审核',
        'Rejected': '拒绝',
        'Approved': '通过'
      }
      return statusMap[status]
    },
    levelFilter (status) {
      const statusMap = {
        'COUNTRY_1': '国家奖学金',
        'COUNTRY_2': '国家励志奖学金',
        'SCHOOL_1': '校级一等奖',
        'SCHOOL_2': '校级二等奖',
        'SCHOOL_3': '校级三等奖'
      }
      return statusMap[status]
    },
    nullFilter (value) {
      if (!value) {
        return '暂未录入'
      }
      return value
    }
  },
  created () {
    this.loadCurrent()
  },
  methods: {
    publish () {
      this.addVisible = true
    },
    publishInfo () {
      const self = this
      const { form: { validateFields } } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          const addInfo = { ...values }
          certificateAdd(addInfo)
            .then((res) => {
              if (res.code === 0) {
                self.$message.info('录入成功')
                self.form.resetFields()
                this.loadCurrent()
                this.addVisible = false
              } else {
                self.$message.error('提交失败')
              }
            })
        }
      })
    },
    loadCurrent () {
      const self = this
      currentUserInfo()
        .then(res => {
          if (res.code === 0) {
            self.accountInfo = res.info
            getScholarshipGetList(res.info.accountNo)
              .then(res => {
                if (res.code === 0) {
                  self.scholarshipList = res.list
                }
              })
            getCertificateList(res.info.accountNo)
              .then(res => {
                if (res.code === 0) {
                  self.certificateList = res.list
                }
              })
          }
        })
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
