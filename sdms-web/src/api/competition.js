import { axios } from '@/utils/request'

export function getCompetitionList () {
  return axios({
    url: '/api/competition/list',
    method: 'get'
  })
}

export function getCompetition (id) {
  return axios({
    url: `/api/competition/${id}`,
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