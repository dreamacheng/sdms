<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="起始日期">
                <a-datePicker v-model="queryParam.startTime"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="结束日期">
                <a-datePicker v-model="queryParam.endTime"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" >
                <a-button type="primary" @click="loadAccountList">查询</a-button>
                <a-button style="margin-left: 8px">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <a-table :columns="columns" :dataSource="accountdata">
        <span slot="action" slot-scope="text, record">
          <a @click="accountDetail(record)">详情</a>
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
            <detail-list-item term="学号">{{account.accountNo}}</detail-list-item>
            <detail-list-item term="姓名">{{account.username}}</detail-list-item>
            <detail-list-item term="专业科系">{{account.department}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="获奖记录">
            <detail-list-item term="用户姓名">付小小</detail-list-item>
            <detail-list-item term="联系电话">18100000000</detail-list-item>
            <detail-list-item term="常用快递">菜鸟仓储</detail-list-item>
            <detail-list-item term="取货地址">浙江省杭州市西湖区万塘路18号</detail-list-item>
            <detail-list-item term="备注">	无</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>

          <div class="title">社会实践</div>
          <!-- <s-table
            style="margin-bottom: 24px"
            row-key="id"
            :columns="goodsColumns"
            :data="loadGoodsData">

          </s-table> -->

          <div class="title">退货进度</div>
          <!-- <s-table
            style="margin-bottom: 24px"
            row-key="key"
            :columns="scheduleColumns"
            :data="loadScheduleData">

            <template
              slot="status"
              slot-scope="status">
              <a-badge :status="status" :text="status | statusFilter"/>
            </template>

          </s-table> -->
        </a-card>
      </a-modal>
    </a-card>
  </div>
</template>
<script>
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item

export default {
  components: {
    DetailList,
    DetailListItem
  },
  data () {
    return {
      description: '用户管理',
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
      form: null,
      account: {},
      accountdata: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {
        startTime: '',
        endTime: '',
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
          dataIndex: 'accountNo'
        },
        {
          title: '学期总评价',
          dataIndex: 'tel'
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      selectedRowKeys: [],
      selectedRows: []
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
      this.$http.post('/api/account/query', this.queryParam)
        .then(res => {
          const result = res.info.resultlist
          this.accountdata = result
        })
    },
    accountDetail (record) {
      this.account = Object.assign({}, record)
      this.visible = true
    },
    handleOk () {
      this.visible = false
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
