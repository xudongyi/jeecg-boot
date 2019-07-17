import {http} from '../plugins/http'

export function getUserList(params) {
  return http({
    url: '/user/getUserList.do',
    method: 'post',
    params
  })
}

export function uploadCreditImg(params) {
  return http({
    url: '/api/apply/uploadCreditImg.do',
    method: 'post',
    params
  })
}



