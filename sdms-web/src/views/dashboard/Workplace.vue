<template>
  <page-view :avatar="accountInfo.avatar" :title="false">
    <div slot="headerContent">
      <div class="title">{{ timeFix }}，{{ accountInfo.username }}<span class="welcome-text">，{{ welcome }}</span></div>
      <div>{{accountInfo.major}} | 湖南工学院 - {{accountInfo.college}}</div>
    </div>
    <!-- <div slot="extra">
      <a-row class="more-info">
        <a-col :span="8">
          <head-info title="项目" content="56" :center="false" :bordered="false"/>
        </a-col>
        <a-col :span="8">
          <head-info title="团队排名" content="8/24" :center="false" :bordered="false"/>
        </a-col>
        <a-col :span="8">
          <head-info title="项目数" content="2,223" :center="false" />
        </a-col>
      </a-row>
    </div> -->

    <div>
      <a-row :gutter="24">
        <a-col :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card
            class="project-list"
            :loading="loading"
            style="margin-bottom: 24px;"
            :bordered="false"
            title="快速通道"
            :body-style="{ padding: 0 }">
            <div>
              <a-card-grid class="project-card-grid" :key="i" v-for="(item, i) in projects">
                <a-card :bordered="false" :body-style="{ padding: 0 }">
                  <a-card-meta>
                    <div slot="title" class="card-title">
                      <a-avatar size="small" :src="item.cover"/>
                      <a :href="item.link">{{ item.title }}</a>
                    </div>
                    <div slot="description" class="card-description">
                      {{ item.description }}
                    </div>
                  </a-card-meta>
                </a-card>
              </a-card-grid>
            </div>
          </a-card>

          <a-card :loading="loading" title="媒体湖工" :bordered="false">
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in mediaCollege">
                <a-list-item-meta>
                  <div slot="title">
                    <a-icon type="double-right" /><a style="margin-left: 10px" :href="item.link">{{ item.title }}</a> <span style="float:right">{{ item.date }}</span>
                  </div>
                  <div slot="description"></div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </a-card>
        </a-col>
        <a-col
          style="padding: 0 12px"
          :xl="8"
          :lg="24"
          :md="24"
          :sm="24"
          :xs="24">
          <a-card title="校园资讯" style="margin-bottom: 24px" :bordered="false" :body-style="{padding: 0}">
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in schoolNews">
                <a-list-item-meta>
                  <div slot="title">
                    <a-icon type="right-circle" style="margin-left: 10px"/><span class="cutTitle"><a :title="item.title" :href="item.link">{{ item.title | lengthFilter }}</a></span> <span style="float:right;margin-right:10px">{{ item.date }}</span>
                  </div>
                  <div slot="description"></div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </a-card>
          <a-card title="快速开始 / 便捷导航" style="margin-bottom: 24px" :bordered="false" :body-style="{padding: 0}">
            <div class="item-group">
              <a-button href="http://localhost:8000/form/base-form">入团申请</a-button>
              <a-button style="margin-left:18px" href="http://localhost:8000/form/step-form">入党申请</a-button>
              <a-button style="margin-left:18px" href="http://localhost:8000/studentDoc/money">奖学金申请</a-button>
              <div></div>
              <a-button href="http://localhost:8000/competition/club">社团中心</a-button>
              <a-button style="margin-left:18px" href="http://localhost:8000/competition/gameStu/Index">参加比赛</a-button>
              <a-button style="margin-left:18px" href="http://localhost:8000/studentDoc/study/evaluate">学期总结</a-button>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </page-view>
</template>

<script>
import { timeFix } from '@/utils/util'
import { mapState } from 'vuex'

import { PageView } from '@/layouts'
import HeadInfo from '@/components/tools/HeadInfo'
import { Radar } from '@/components'

import { currentUserInfo, welcomeData } from '@/api/login'

const DataSet = require('@antv/data-set')

export default {
  name: 'Workplace',
  components: {
    PageView,
    HeadInfo,
    Radar
  },
  data () {
    return {
      timeFix: timeFix(),
      avatar: '',
      user: {},
      projects: [],
      loading: true,
      radarLoading: true,
      accountInfo: {},
      activities: [],
      teams: [],
      schoolNews: [],
      notices: [],
      dataURLPrefix: 'http://www.hnit.edu.cn/',
      mediaCollege: [],
      basicInfo: [],
      // data
      axis1Opts: {
        dataKey: 'item',
        line: null,
        tickLine: null,
        grid: {
          lineStyle: {
            lineDash: null
          },
          hideFirstLine: false
        }
      },
      axis2Opts: {
        dataKey: 'score',
        line: null,
        tickLine: null,
        grid: {
          type: 'polygon',
          lineStyle: {
            lineDash: null
          }
        }
      },
      scale: [{
        dataKey: 'score',
        min: 0,
        max: 80
      }],
      axisData: [
        { item: '学习', a: 70, b: 30, c: 40 },
        { item: '技能', a: 60, b: 70, c: 40 },
        { item: '实践', a: 50, b: 60, c: 40 },
        { item: '竞赛', a: 40, b: 50, c: 40 },
        { item: '课外', a: 60, b: 70, c: 40 },
        { item: '业余', a: 70, b: 50, c: 40 }
      ],
      radarData: []
    }
  },
  computed: {
    ...mapState({
      nickname: (state) => state.user.nickname,
      welcome: (state) => state.user.welcome
    }),
    userInfo () {
      return this.$store.getters.userInfo
    }
  },
  created () {
    this.user = this.userInfo
    this.avatar = this.userInfo.avatar
  },
  mounted () {
    this.getProjects()
    this.getActivity()
    this.getTeams()
    this.initRadar()
    this.loadCurrent()
  },
  filters: {
    lengthFilter (value) {
      if (value.length > 20) {
        return value.substring(0, 20) + '...'
      }
      return value
    }
  },
  methods: {
    getProjects () {
      this.$http.get('/list/search/projects')
        .then(res => {
          this.projects = res.result && res.result.data
          this.loading = false
        })
    },
    loadCurrent () {
      const self = this
      currentUserInfo()
        .then(res => {
          if (res.code === 0) {
            self.accountInfo = res.info
          }
        })
      welcomeData()
        .then(res => {
          if (res.code === 0) {
            self.schoolNews = res.info.schoolNews
            self.notices = res.info.notices
            if (res.info.mediaCollege.length > 7) {
              for (let i = 0; i < 7; i++) {
                res.info.mediaCollege[i].link = self.dataURLPrefix + res.info.mediaCollege[i].link
                self.mediaCollege.push(res.info.mediaCollege[i])
              }
              for (let i = 7; i < res.info.mediaCollege.length; i++) {
                self.basicInfo.push(res.info.mediaCollege[i])
              }
            }
          }
        })
    },
    getActivity () {
      this.$http.get('/workplace/activity')
        .then(res => {
          this.activities = res.result
        })
    },
    getTeams () {
      this.$http.get('/workplace/teams')
        .then(res => {
          this.teams = res.result
        })
    },
    initRadar () {
      this.radarLoading = true

      this.$http.get('/workplace/radar')
        .then(res => {
          const dv = new DataSet.View().source(res.result)
          dv.transform({
            type: 'fold',
            fields: ['个人', '专业', '学院'],
            key: 'user',
            value: 'score'
          })

          this.radarData = dv.rows
          this.radarLoading = false
        })
    }
  }
}
</script>

<style lang="less" scoped>
  .cutTitle {
      width: 240px !important;
      white-space: nowrap !important;
      overflow: hidden !important;
      text-overflow: ellipsis !important;
      margin-left: 10px
    }
  .project-list {
    .card-title {
      font-size: 0;

      a {
        color: rgba(0, 0, 0, 0.85);
        margin-left: 12px;
        line-height: 24px;
        height: 24px;
        display: inline-block;
        vertical-align: top;
        font-size: 14px;

        &:hover {
          color: #1890ff;
        }
      }
    }
    .card-description {
      color: rgba(0, 0, 0, 0.45);
      height: 44px;
      line-height: 22px;
      overflow: hidden;
    }
    .project-item {
      display: flex;
      margin-top: 8px;
      overflow: hidden;
      font-size: 12px;
      height: 20px;
      line-height: 20px;
      a {
        color: rgba(0, 0, 0, 0.45);
        display: inline-block;
        flex: 1 1 0;

        &:hover {
          color: #1890ff;
        }
      }
      .datetime {
        color: rgba(0, 0, 0, 0.25);
        flex: 0 0 auto;
        float: right;
      }
    }
    .ant-card-meta-description {
      color: rgba(0, 0, 0, 0.45);
      height: 44px;
      line-height: 22px;
      overflow: hidden;
    }
  }

  .item-group {
    padding: 20px 0 8px 24px;
    font-size: 0;
    a {
      color: rgba(0, 0, 0, 0.65);
      display: inline-block;
      font-size: 14px;
      margin-bottom: 13px;
      width: 25%;
    }
  }

  .members {
    a {
      display: block;
      margin: 12px 0;
      line-height: 24px;
      height: 24px;
      .member {
        font-size: 14px;
        color: rgba(0, 0, 0, .65);
        line-height: 24px;
        max-width: 100px;
        vertical-align: top;
        margin-left: 12px;
        transition: all 0.3s;
        display: inline-block;
      }
      &:hover {
        span {
          color: #1890ff;
        }
      }
    }
  }

  .mobile {

    .project-list {

      .project-card-grid {
        width: 100%;
      }
    }

    .more-info {
      border: 0;
      padding-top: 16px;
      margin: 16px 0 16px;
    }

    .headerContent .title .welcome-text {
      display: none;
    }
  }

</style>
