
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

    <a-card :bordered="false" title="获奖信息">
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

    <a-card style="margin-top: 24px" :bordered="false" title="用户信息">
      <detail-list>
        <detail-list-item term="用户姓名">付晓晓</detail-list-item>
        <detail-list-item term="会员卡号">32943898021309809423</detail-list-item>
        <detail-list-item term="身份证">3321944288191034921</detail-list-item>
        <detail-list-item term="联系方式">18112345678</detail-list-item>
        <detail-list-item term="联系地址">浙江省杭州市西湖区黄姑山路工专路交叉路口</detail-list-item>
      </detail-list>
      <detail-list title="信息组">
        <detail-list-item term="某某数据">725</detail-list-item>
        <detail-list-item term="该数据更新时间">2018-08-08</detail-list-item>
        <detail-list-item ></detail-list-item>
        <detail-list-item term="某某数据">725</detail-list-item>
        <detail-list-item term="该数据更新时间">2018-08-08</detail-list-item>
        <detail-list-item ></detail-list-item>
      </detail-list>
      <a-card type="inner" title="多层信息组">
        <detail-list title="组名称" size="small">
          <detail-list-item term="负责人">林东东</detail-list-item>
          <detail-list-item term="角色码">1234567</detail-list-item>
          <detail-list-item term="所属部门">XX公司-YY部</detail-list-item>
          <detail-list-item term="过期时间">2018-08-08</detail-list-item>
          <detail-list-item term="描述">这段描述很长很长很长很长很长很长很长很长很长很长很长很长很长很长...</detail-list-item>
        </detail-list>
        <a-divider style="margin: 16px 0" />
        <detail-list title="组名称" size="small" :col="1">
          <detail-list-item term="学名">	Citrullus lanatus (Thunb.) Matsum. et Nakai一年生蔓生藤本；茎、枝粗壮，具明显的棱。卷须较粗..</detail-list-item>
        </detail-list>
        <a-divider style="margin: 16px 0" />
        <detail-list title="组名称" size="small" :col="2">
          <detail-list-item term="负责人">付小小</detail-list-item>
          <detail-list-item term="角色码">1234567</detail-list-item>
        </detail-list>
      </a-card>

    </a-card>

    <a-card style="margin-top: 24px" :bordered="false" title="用户近半年来电记录">
      <div class="no-data"><a-icon type="frown-o"/>暂无数据</div>
    </a-card>

  </page-view>
</template>

<script>
import { mixinDevice } from '@/utils/mixin'
import { PageView } from '@/layouts'
import { currentUserInfo } from '@/api/login'
import DetailList from '@/components/tools/DetailList'
import { getScholarshipList } from '@/api/scholarship'

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
      accountInfo: {},
      activeTabKey: '1',

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
        'agree': '成功',
        'reject': '驳回'
      }
      return statusMap[status]
    },
    statusTypeFilter (type) {
      const statusTypeMap = {
        'agree': 'success',
        'reject': 'error'
      }
      return statusTypeMap[type]
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
      getScholarshipList()
        .then(res => {
          if (res.code === 0) {
            self.scholarshipList = res.list
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
