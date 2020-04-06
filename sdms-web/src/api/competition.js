import { axios } from '@/utils/request'

export function getCompetitionList () {
  return axios({
    url: '/api/competition/list',
    method: 'get'
  })
}

export function getCompetitionPersonal () {
  return axios({
    url: '/api/competition/personal',
    method: 'get'
  })
}

export function getCompetition (id) {
  return axios({
    url: `/api/competition/${id}`,
    method: 'get'
  })
}

export function addWinner (parameter) {
  return axios({
    url: '/api/competition/winner/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
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

export function joinCompetition (parameter) {
  return axios({
    url: '/api/competition/join',
    method: 'post',
    data: parameter
  })
}
