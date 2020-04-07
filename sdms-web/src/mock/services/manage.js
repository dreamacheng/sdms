import Mock from 'mockjs2'
import { builder, getQueryParameters } from '../util'

const totalCount = 5701

const serverList = (options) => {
  const parameters = getQueryParameters(options)

  const result = []
  const pageNo = parseInt(parameters.pageNo)
  const pageSize = parseInt(parameters.pageSize)
  const totalPage = Math.ceil(totalCount / pageSize)
  const key = (pageNo - 1) * pageSize
  const next = (pageNo >= totalPage ? (totalCount % pageSize) : pageSize) + 1

  for (let i = 1; i < next; i++) {
    const tmpKey = key + i
    result.push({
      key: tmpKey,
      id: tmpKey,
      no: 'No ' + tmpKey,
      description: '这是一段描述',
      callNo: Mock.mock('@integer(1, 999)'),
      status: Mock.mock('@integer(0, 3)'),
      updatedAt: Mock.mock('@datetime'),
      editable: false
    })
  }

  return builder({
    pageSize: pageSize,
    pageNo: pageNo,
    totalCount: totalCount,
    totalPage: totalPage,
    data: result
  })
}

const projects = () => {
  return builder({
    'data': [{
      id: 1,
      cover: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585513352783&di=5a0ecb8d17c59b7030c0d63003b35d81&imgtype=0&src=http%3A%2F%2Fimg.ccutu.com%2Fupload%2Fimages%2F2017-6%2Fp00032164.jpg',
      title: '湖南工学院官网',
      description: '湖南工学院,首页',
      status: 1,
      picSrc: '/b41a.png',
      link: 'http://www.hnit.edu.cn/index.htm'
    },
    {
      id: 2,
      cover: 'http://www.hnit.edu.cn/images/jwxt_08.png',
      title: '湖南工学院教务系统',
      description: '教务管理 数字校园 综合教务管理 教学管理 ',
      status: 1,
      updatedAt: '2018-07-26 00:00:00',
      picSrc: '/b51b.png',
      link: 'http://jwgl.hnit.edu.cn/'
    },
    {
      id: 3,
      cover: 'http://jy.hnit.edu.cn/favicon.ico?1',
      title: '湖南工学院就业信息网',
      description: '湖南工学院云就业平台',
      status: 1,
      updatedAt: '2018-07-26 00:00:00',
      picSrc: '/b41a.png',
      link: 'http://jy.hnit.edu.cn/'
    },
    {
      id: 4,
      cover: 'http://www.hnit.edu.cn/images/kczx_11.png',
      title: '湖南工学院课程中心',
      description: '湖南工学院课程中心',
      status: 1,
      updatedAt: '2018-07-26 00:00:00',
      picSrc: '/b41a.png',
      link: 'http://kczx.hnit.edu.cn/G2S/ShowSystem/Index.aspx'
    },
    {
      id: 5,
      cover: 'http://www.hnit.edu.cn/images/tsg_19.png',
      title: '湖南工学院图书馆',
      description: '湖南工学院图书馆',
      status: 1,
      updatedAt: '2018-07-26 00:00:00',
      picSrc: '/b41a.png',
      link: 'http://www.hnit.edu.cn/tsg/'
    },
    {
      id: 6,
      cover: 'http://www.hnit.edu.cn/images/yjzx_22.png',
      title: '湖南工学院邮件系统',
      description: '湖南工学院邮件系统',
      status: 1,
      updatedAt: '2018-07-26 00:00:00',
      picSrc: '/b41a.png',
      link: 'http://mail.hnit.edu.cn/'
    }
    ],
    'pageSize': 10,
    'pageNo': 0,
    'totalPage': 6,
    'totalCount': 57
  })
}

const activity = () => {
  return builder([{
    id: 1,
    user: {
      nickname: '@name',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png'
    },
    project: {
      name: '白鹭酱油开发组',
      action: '更新',
      event: '番组计划'
    },
    time: '2018-08-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '蓝莓酱',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png'
    },
    project: {
      name: '白鹭酱油开发组',
      action: '更新',
      event: '番组计划'
    },
    time: '2018-08-23 09:35:37'
  },
  {
    id: 1,
    user: {
      nickname: '@name',
      avatar: '@image(64x64)'
    },
    project: {
      name: '白鹭酱油开发组',
      action: '创建',
      event: '番组计划'
    },
    time: '2017-05-27 00:00:00'
  },
  {
    id: 1,
    user: {
      nickname: '曲丽丽',
      avatar: '@image(64x64)'
    },
    project: {
      name: '高逼格设计天团',
      action: '更新',
      event: '六月迭代'
    },
    time: '2018-08-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '@name',
      avatar: '@image(64x64)'
    },
    project: {
      name: '高逼格设计天团',
      action: 'created',
      event: '六月迭代'
    },
    time: '2018-08-23 14:47:00'
  },
  {
    id: 1,
    user: {
      nickname: '曲丽丽',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png'
    },
    project: {
      name: '高逼格设计天团',
      action: 'created',
      event: '六月迭代'
    },
    time: '2018-08-23 14:47:00'
  }
  ])
}

const teams = () => {
  return builder([{
    id: 1,
    name: '科学搬砖组',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png'
  },
  {
    id: 2,
    name: '程序员日常',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/cnrhVkzwxjPwAaCfPbdc.png'
  },
  {
    id: 1,
    name: '设计天团',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/gaOngJwsRYRaVAuXXcmB.png'
  },
  {
    id: 1,
    name: '中二少女团',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/ubnKSIfAJTxIgXOKlciN.png'
  },
  {
    id: 1,
    name: '骗你学计算机',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WhxKECPNujWoWEFNdnJE.png'
  }
  ])
}

const radar = () => {
  return builder([{
    item: '学习',
    '个人': 70,
    '专业': 69,
    '学院': 77
  },
  {
    item: '技能',
    '个人': 60,
    '专业': 77,
    '学院': 69
  },
  {
    item: '实践',
    '个人': 50,
    '专业': 69,
    '学院': 70
  },
  {
    item: '竞赛',
    '个人': 40,
    '专业': 72,
    '学院': 69
  },
  {
    item: '课外',
    '个人': 60,
    '专业': 75,
    '学院': 77
  },
  {
    item: '业余',
    '个人': 70,
    '专业': 77,
    '学院': 75
  }
  ])
}

Mock.mock(/\/service/, 'get', serverList)
Mock.mock(/\/list\/search\/projects/, 'get', projects)
Mock.mock(/\/workplace\/activity/, 'get', activity)
Mock.mock(/\/workplace\/teams/, 'get', teams)
Mock.mock(/\/workplace\/radar/, 'get', radar)
