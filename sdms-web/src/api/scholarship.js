import { axios } from '@/utils/request'

export function scholarshipAdd (parameter) {
  return axios({
    url: '/api/scholarship/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getScholarshipList () {
  return axios({
    url: '/api/scholarship/query',
    method: 'get'
  })
}

export function getAllScholarshipList () {
  return axios({
    url: '/api/scholarship/queryAll',
    method: 'get'
  })
}

export function scholarshipApproval (parameter) {
  return axios({
    url: '/api/scholarship/approval',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurTerm (curTerm) {
  return axios({
    url: `/api/scholarship/query/${curTerm}`,
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
