
<template>
  <page-view :title="`学号: ${accountInfo.accountNo}    姓名： ${accountInfo.username}`" logo="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585513352783&di=5a0ecb8d17c59b7030c0d63003b35d81&imgtype=0&src=http%3A%2F%2Fimg.ccutu.com%2Fupload%2Fimages%2F2017-6%2Fp00032164.jpg">

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

    <a-card style="margin-top: 24px" :bordered="false" title="处分记录">
      <a-table
        :columns="operationColumns"
        :dataSource="punishmentList"
      >
        <span slot="type" slot-scope="text">
            {{text | typeFilter}}
        </span>
        <span slot="isCancel" slot-scope="text">
            {{text | statusFilter }}
        </span>
      </a-table>
    </a-card>

    <a-modal
        title="处分详情"
        :width="800"
        v-model="visible"
        @ok="handleOk"
      >
        <a-card :bordered="false">
          <detail-list title="学生信息">
            <detail-list-item term="受处分人学号">{{punishmentInfo.studentNo}}</detail-list-item>
            <detail-list-item term="受处分人姓名">{{punishmentInfo.studentName}}</detail-list-item>
            <detail-list-item term="处分类型">{{punishmentInfo.type}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list>
            <detail-list-item term="处分时间">{{punishmentInfo.punishmentTime}}</detail-list-item>
            <detail-list-item term="取消时间">{{punishmentInfo.cancelTime}}</detail-list-item>
            <detail-list-item term="是否取消">{{punishmentInfo.isCancel | statusFilter}}</detail-list-item>
          </detail-list>
          <a-divider style="margin-bottom: 32px"/>
          <detail-list title="处分原因及描述">
            {{punishmentInfo.desc}}
          </detail-list>
        </a-card>
      </a-modal>

  </page-view>
</template>

<script>
import { mixinDevice } from '@/utils/mixin'
import { PageView } from '@/layouts'
import DetailList from '@/components/tools/DetailList'
import { currentUserInfo } from '@/api/login'
import { getPunishmentList } from '@/api/punishment'

const DetailListItem = DetailList.Item

export default {
  name: 'punishment',
  components: {
    PageView,
    DetailList,
    DetailListItem
  },
  mixins: [mixinDevice],
  data () {
    return {
      punishmentInfo: {},
      visible: false,
      accountInfo: {},
      punishmentList: [],
      operationColumns: [
        {
          title: '处分级别',
          dataIndex: 'type',
          key: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '处分时间',
          dataIndex: 'punishmentTime',
          key: 'punishmentTime'
        },
        {
          title: '取消时间',
          dataIndex: 'cancelTime',
          key: 'cancelTime'
        },
        {
          title: '是否取消',
          dataIndex: 'isCancel',
          key: 'isCancel',
          scopedSlots: { customRender: 'isCancel' }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
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
          }
        })
      getPunishmentList()
        .then(res => {
          if (res.code === 0) {
            self.punishmentList = res.list
          }
        })
    },
    punishmentDetail (record) {
      this.punishmentInfo = record
      this.visible = true
    },
    handleOk () {
      this.visible = false
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        '0': '未取消',
        '1': '已取消'
      }
      return statusMap[status]
    },
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
