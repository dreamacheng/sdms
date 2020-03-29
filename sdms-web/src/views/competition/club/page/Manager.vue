<template>
  <div class="app-list">
    <a-list itemLayout="vertical" size="large" :pagination="pagination" :dataSource="clubList">
      <a-list-item slot="renderItem" slot-scope="item" key="item.title">
        <div slot="footer">
        </div>
        <template slot="actions" >
          <p>活跃度：{{ item.liveness | livenessFilter }}</p>
          <p>社团规模：{{ item.club.number }} 人</p>
          <p>职位：{{ item.position | positionFilter }} </p>
          <a-button @click="approvalShow(item.club.id)">入社审批</a-button>
        </template>
        <img
          slot="extra"
          width="272"
          alt="logo"
          :src="item.club.logoUrl"
        />
        <a-list-item-meta :description="item.type">
          <a slot="title" :href="item.href">{{item.club.name}}</a>
        </a-list-item-meta>
         {{item.club.introduction}}
      </a-list-item>
    </a-list>
    <a-modal
      title="入社审批"
      :width="800"
      v-model="visible"
      @ok="handleOk"
      okText="完成"
    >
      <a-table :columns="approvalColumns" :dataSource="approvalList">
          <span slot="accountNo" slot-scope="text,record">
              {{record.member.accountNo}}
          </span>
          <span slot="accountName" slot-scope="text,record">
              {{record.member.username}}
          </span>
        <span slot="action" slot-scope="text, record">
          <a-button @click="approvalMember(record.id, 'Approved')">同意</a-button>
          <a-button style="margin-left:20px" @click="approvalMember(record.id, 'Rejected')">拒绝</a-button>
        </span>
      </a-table>
    </a-modal>
  </div>
</template>

<script>
import { getClubListByType, joinClub, approval } from '@/api/club'

export default {
  name: 'Manager',
  components: {},
  data () {
    return {
      pagination: {
        pageSize: 3
      },
      clubList: [],
      visible: false,
      approvalList: [],
      approvalClub: '',
      approvalColumns: [
        {
          title: '申请者学号',
          dataIndex: 'accountNo',
          scopedSlots: { customRender: 'accountNo' }
        },
        {
          title: '申请者姓名',
          dataIndex: 'accountName',
          scopedSlots: { customRender: 'accountName' }
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ]
    }
  },
  filters: {
    livenessFilter (status) {
      const statusMap = {
        'LEVEL_1': '不活跃',
        'LEVEL_2': '一般活跃',
        'LEVEL_3': '较活跃'
      }
      return statusMap[status]
    },
    positionFilter (status) {
      const statusMap = {
        'BOSS': '社长',
        'NORMAL_BOSS': '副社长',
        'NORMAL_MEMBER': '社员'
      }
      return statusMap[status]
    }
  },
  created () {
    this.loadClubList()
  },
  methods: {
    approvalMember (id, operation) {
      const self = this
      approval({
        id: id,
        operation: operation
      }).then(res => {
        if (res.code === 0) {
          self.$message.info('审核成功')
          self.approvalShow(self.approvalClub)
        } else {
          self.$message.info('审核失败')
        }
      })
    },
    approvalShow (clubId) {
      this.approvalClub = clubId
      getClubListByType({
        type: 'WaitForApproval',
        club: clubId
      }).then(res => {
        if (res.code === 0) {
          this.approvalList = res.list
          this.visible = true
        }
      })
    },
    handleOk () {
      this.visible = false
    },
    loadClubList () {
      getClubListByType({
        type: 'Manager',
        club: ''
      }).then(res => {
        if (res.code === 0) {
          this.clubList = res.list
        }
      })
    },
    joinClub (id) {
      const self = this
      this.$confirm({
        title: '确认',
        content: '确认申请加入该社团吗？',
        onOk () {
          self.confirmOpt = false
          joinClub(id)
            .then(res => {
              if (res.code === 0) {
                self.$message.info('申请成功！')
                self.loadClubList()
              } else {
                self.$message.error('申请失败！')
              }
            })
        },
        onCancel () {}
      })
    }
  }
}
</script>

<style lang="less" scoped>

  .app-list {

    .meta-cardInfo {
      zoom: 1;
      margin-top: 16px;

      > div {
        position: relative;
        text-align: left;
        float: left;
        width: 50%;

        p {
          line-height: 32px;
          font-size: 24px;
          margin: 0;

          &:first-child {
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }

      }
    }
  }

</style>
