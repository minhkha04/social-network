import { http } from './config.js'

export const userService = {
  getMyInfo: () => {
    return http.get('/profile/')
  }
};