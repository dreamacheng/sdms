<template>
  <div class="app-list">
    <a-list itemLayout="vertical" size="large" :pagination="pagination" :dataSource="clubList">
      <a-list-item slot="renderItem" slot-scope="item" key="item.title">
        <div slot="footer"></div>
        <template slot="actions" >
          <a-button type="primary" @click="joinClub(item.id)">加入社团</a-button>
        </template>
        <img
          slot="extra"
          width="272"
          height="168"
          alt="logo"
          :src="item.logoUrl"
        />
        <a-list-item-meta :description="item.type">
          <a slot="title" :href="item.href">{{item.name}}</a>
        </a-list-item-meta>
         {{item.introduction}}
      </a-list-item>
    </a-list>
  </div>
</template>

<script>
import { getClubList, joinClub } from '@/api/club'

export default {
  name: 'Project',
  components: {},
  data () {
    return {
      pagination: {
        pageSize: 3
      },
      clubList: []
    }
  },
  created () {
    this.loadClubList()
  },
  methods: {
    loadClubList () {
      getClubList()
        .then(res => {
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
