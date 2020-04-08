import api from './index'
import { axios } from '@/utils/request'

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return axios({
    url: '/api/login',
    method: 'post',
    data: parameter
  })
}

export function welcomeData () {
  return axios({
    url: '/api/index/welcome',
    method: 'get'
  })
}

export function registerAccount (parameter) {
  return axios({
    url: '/api/register',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function updateAccountApi (parameter) {
  return axios({
    url: '/api/account/update',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function updatePwdApi (parameter) {
  return axios({
    url: '/api/account/pwd',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function currentUserInfo () {
  return axios({
    url: '/api/account/current',
    method: 'get'
  })
}

export function getSmsCaptcha (parameter) {
  return axios({
    url: api.SendSms,
    method: 'post',
    data: parameter
  })
}

export function getInfo () {
  return axios({
    // url: '/api/account/current',
    url: '/user/info',
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurrentUserNav (token) {
  return axios({
    url: '/user/nav',
    method: 'get'
  })
}

export function logout () {
  return axios({
    url: '/api/logout',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function generate () {
  return axios({
    url: '/api/account/code',
    method: 'post'
  })
}

export function getCode () {
  return axios({
    url: '/api/account/code',
    method: 'get'
  })
}

export function resetPwdAPI (data) {
  return axios({
    url: '/api/account/checkInfo',
    method: 'post',
    data: data
  })
}
