<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="学号工号">
                <a-input :value="queryParam.accountNo" placeholder="请输入"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="专业科系">
                <a-input :value="queryParam.department" placeholder="请输入"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="学院">
                <a-input :value="queryParam.lodgingHouse" placeholder="请输入"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="用户姓名">
                <a-input :value="queryParam.username" placeholder="请输入"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="政治面貌">
                <a-select :value="queryParam.politicsStatus">
                  <a-select-option value="PART_MEMBER">党员</a-select-option>
                  <a-select-option value="LEAGUE_MEMBER">团员</a-select-option>
                  <a-select-option value="MASSES">群众</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="用户类型">
                <a-select :value="queryParam.role">
                  <a-select-option value="STUDENT">学生</a-select-option>
                  <a-select-option value="MANAGER">教师</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <span class="table-page-search-submitButtons" >
                <a-button type="primary" @click="loadAccountList">查询</a-button>
                <a-button style="margin-left: 8px">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="role" slot-scope="text">
          {{ text | roleFilter }}
        </span>
        <span slot="isLock" slot-scope="text">
          {{ text | statusFilter }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a v-show="record.isLock == 0" @click="lockAccountConfirm(record.accountNo)">锁定用户</a>
                <a v-show="record.isLock == 1" @click="unLockAccountConfirm(record.accountNo)">解锁用户</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="delAccountConfirm(record.accountNo)">删除用户</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>

      <a-modal
        title="用户详情"
        :width="800"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false">
          <detail-list title="基本信息">
            <detail-list-item term="学号/职工号">{{account.accountNo}}</detail-list-item>
            <detail-list-item term="姓名">{{account.username}}</detail-list-item>
            <detail-list-item term="年龄">{{account.age}}</detail-list-item>
            <detail-list-item term="在校公寓">{{account.lodgingHouse}}</detail-list-item>
            <detail-list-item term="专业科系">{{account.department}}</detail-list-item>
            <detail-list-item term="民族">{{account.nation}}</detail-list-item>
            <detail-list-item term="电话号码">{{account.tel}}</detail-list-item>
            <detail-list-item term="身份证">{{account.identityCard}}</detail-list-item>
            <detail-list-item term="政治面貌">{{ account.politicsStatus | politicsStatusFilter }}</detail-list-item>
            <detail-list-item term="性别">{{ account.gender | genderFileter }}</detail-list-item>
            <detail-list-item term="出生年月">{{account.birthday}}</detail-list-item>
            <detail-list-item term="用户类型">{{ account.role | roleFilter }}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <div class="table-page-search-wrapper">
            <P style="font-size: 20px"><a-icon type="profile"/>&nbsp;获奖信息</p>
          </div>
          <a-table
            :columns="scholarshipColumns"
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
          <div class="table-page-search-wrapper">
            <P style="font-size: 20px"><a-icon type="profile"/>&nbsp;所获证书<a-button style="float:right" @click="publish"><a-icon type="plus-circle" />录入证书</a-button></p>
          </div>
          <a-table
            :columns="certificateColumns"
            :dataSource="certificateList"
          >
          </a-table>
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
import { queryAccountAPI, lockUser } from '@/api/manage'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  name: 'manager',
  data () {
    return {
      description: '用户管理',
      scholarshipList: [],
      certificateList: [],
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: null,
      account: {},
      accountdata: [],
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {
        username: '',
        accountNo: '',
        lodgingHouse: '',
        department: '',
        politicsStatus: '',
        role: '',
        pageInfo: {
          pageNum: '0',
          pageSize: '1000'
        }
      },
      // 表头
      columns: [
        {
          title: '学号/职工号',
          dataIndex: 'accountNo',
          key: 'id'
        },
        {
          title: '用户姓名',
          dataIndex: 'username'
        },
        {
          title: '专业科系',
          dataIndex: 'major'
        },
        {
          title: '电话',
          dataIndex: 'tel'
        },
        {
          title: '账号类型',
          dataIndex: 'role',
          scopedSlots: { customRender: 'role' }
        },
        {
          title: '状态',
          dataIndex: 'isLock',
          scopedSlots: { customRender: 'isLock' }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      selectedRows: [],
      scholarshipColumns: [
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
      certificateColumns: [
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
    statusFilter (status) {
      const statusMap = {
        0: '正常',
        1: '锁定'
      }
      return statusMap[status]
    },
    roleFilter (role) {
      const statusMap = {
        'STUDENT': '学生',
        'MANAGER': '管理员'
      }
      return statusMap[role]
    },
    politicsStatusFilter (status) {
      const statusMap = {
        'PART_MEMBER': '党员',
        'LEAGUE_MEMBER': '团员',
        'MASSES': '群众'
      }
      return statusMap[status]
    },
    genderFileter (gender) {
      const statusMap = {
        'MALE': '男',
        'FEMALE': '女'
      }
      return statusMap[gender]
    }
  },
  created () {
    this.loadAccountList()
  },
  methods: {
    loadAccountList () {
      queryAccountAPI(this.queryParam)
        .then(res => {
          const result = res.info.resultList
          this.accountdata = result
        })
    },
    accountDetail (record) {
      const self = this
      getScholarshipGetList(record.accountNo)
        .then(res => {
          if (res.code === 0) {
            self.scholarshipList = res.list
          }
        })
      getCertificateList(record.accountNo)
        .then(res => {
          if (res.code === 0) {
            self.certificateList = res.list
          }
        })
      this.account = Object.assign({}, record)
      this.visible = true
    },
    handleOk () {
      this.visible = false
    },
    lockAccountConfirm (accountNo) {
      const self = this
      this.$confirm({
        title: '确认',
        content: '确认锁定 ' + accountNo + ' 用户吗？',
        onOk () {
          lockUser(accountNo)
            .then(res => {
              if (res.code === 0) {
                self.$message.info('锁定该用户成功！')
                self.loadAccountList()
              } else {
                self.$message.error('锁定该用户失败！')
              }
            })
        },
        onCancel () {}
      })
    },
    unLockAccountConfirm (accountNo) {
      const self = this
      this.$confirm({
        title: '确认',
        content: '确认解锁 ' + accountNo + ' 用户吗？',
        onOk () {
          lockUser(accountNo)
            .then(res => {
              if (res.code === 0) {
                self.$message.info('解锁该用户成功！')
                self.loadAccountList()
              } else {
                self.$message.error('解锁该用户失败！')
              }
            })
        },
        onCancel () {}
      })
    },
    delAccountConfirm (accountNo) {
      this.$confirm({
        title: '确认',
        content: '确认删除 ' + accountNo + ' 用户吗？',
        onOk () {
          this.$http.get('/api/account/delete', {
            accountNo: this.account.accountNo
          }).then(res => {
            if (res.info === 0) {
              this.$message.info('删除该用户成功！')
            } else {
              this.$message.error('删除该用户失败！')
            }
          })
        },
        onCancel () {}
      })
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
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
