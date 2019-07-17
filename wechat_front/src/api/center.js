import {http} from '../plugins/http'

export function myAgentAndJiameng(params) {
  return http({
    url: '/api/center/myAgentAndJiameng.do',
    method: 'post',
    params
  })
}
export function recommendBackFinish(params) {
  return http({
    url: '/api/center/recommendBackFinish.do',
    method: 'post',
    params
  })
}

export function recommendBackTODO(params) {
  return http({
    url: '/api/center/recommendBackTODO.do',
    method: 'post',
    params
  })
}

export function agentBack(params) {
  return http({
    url: '/api/center/agentBack.do',
    method: 'post',
    params
  })
}




