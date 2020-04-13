import { axios } from '@/utils/request'

export function certificateAdd (parameter) {
  return axios({
    url: '/api/certificate/add',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCertificateList (accountNo) {
  return axios({
    url: `/api/certificate/current/${accountNo}`,
    method: 'get'
  })
}
