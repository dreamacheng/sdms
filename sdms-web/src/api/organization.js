import { axios } from '@/utils/request'

export function getCurAccountApply (type) {
  return axios({
    url: `/api/apply/curAccount?type=${type}`,
    method: 'get'
  })
}

export function applyAddAPI (parameter) {
  return axios({
    url: '/api/apply/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function evaluateCurAPI (curTerm) {
  return axios({
    url: `/api/semesterEval/evaluate/${curTerm}`,
    method: 'get'
  })
}

export function queryCurAPI () {
  return axios({
    url: '/api/semesterEval/curAccount',
    method: 'get'
  })
}
