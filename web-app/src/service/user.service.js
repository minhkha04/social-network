import { http } from './config.js'

export const userService = {
  getMyInfo: () => {
    return http.get('v1/users/myInfo')
  }
};