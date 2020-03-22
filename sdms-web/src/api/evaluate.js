import { axios } from '@/utils/request'

export function getCurAccountEvaluation (parameter) {
  return axios({
    url: '/api/semesterEval/',
    method: 'get',
    data: parameter
  })
}

export function evaluateAPI (parameter) {
  return axios({
    url: '/api/semesterEval/evaluate',
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
