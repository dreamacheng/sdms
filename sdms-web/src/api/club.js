import { axios } from '@/utils/request'

export function clubAdd (parameter) {
  return axios({
    url: '/api/club/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getClubList () {
  return axios({
    url: '/api/club/query',
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function myMention () {
  return axios({
    url: '/api/club/myMention',
    method: 'get'
  })
}

export function getClubApprovalList () {
  return axios({
    url: '/api/club/approval/list',
    method: 'get'
  })
}

export function approvalAdd (parameter) {
  return axios({
    url: '/api/club/add/approval',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getClubListByType (parameter) {
  return axios({
    url: `/api/club/queryDetail?type=${parameter.type}&clubId=${parameter.club}`,
    method: 'get'
  })
}

export function approval (parameter) {
  return axios({
    url: '/api/club/join/approval',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function joinClub (parameter) {
  return axios({
    url: `/api/club/join/${parameter}`,
    method: 'get'
  })
}

export function applyCompetition (parameter) {
  return axios({
    url: '/api/competition/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
