import { axios } from '@/utils/request'

export function getClubList () {
  return axios({
    url: '/api/club/query',
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
