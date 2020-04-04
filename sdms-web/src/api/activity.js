import { axios } from '@/utils/request'

export function activityAdd (parameter) {
  return axios({
    url: '/api/activity/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getActivityList () {
  return axios({
    url: '/api/activity/query',
    method: 'get'
  })
}

export function getCurActivityList () {
  return axios({
    url: '/api/activity/joined',
    method: 'get'
  })
}

export function getActivity (id) {
  return axios({
    url: `/api/activity/detail/${id}`,
    method: 'get'
  })
}

export function joinActivity (parameter) {
  return axios({
    url: '/api/activity/join',
    method: 'post',
    data: parameter
  })
}

export function summaryActivity (parameter) {
  return axios({
    url: '/api/activity/summary',
    method: 'post',
    data: parameter
  })
}
