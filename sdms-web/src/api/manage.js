import { axios } from '@/utils/request'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  orgTree: '/org/tree'
}

export default api

export function queryAccountAPI (parameter) {
  return axios({
    url: '/api/account/query',
    method: 'post',
    data: parameter
  })
}

export function getTeacher () {
  return axios({
    url: '/api/account/teacher',
    method: 'get'
  })
}

export function updateAvatar (parameter) {
  return axios({
    url: '/api/account/avatar',
    method: 'post',
    data: parameter
  })
}

export function lockUser (parameter) {
  return axios({
    url: `/api/account/lock/${parameter}`,
    method: 'get',
    data: parameter
  })
}

export function getUserList (parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList (parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList (parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions (parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

export function getOrgTree (parameter) {
  return axios({
    url: api.orgTree,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return axios({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}
