import { http } from './config.js'

export const postService = {
  getMyPost: (page = 1, size = 10) => {
    return http.get(`/post/my-post?page=${page}&size=${size}`)
  },

  getPublicPost: (page = 1, size = 10) => {
    return http.get(`/post/public?page=${page}&size=${size}`)
  },

  creatPost: (data) => {
    return http.post('/post/create', data)
  }
};