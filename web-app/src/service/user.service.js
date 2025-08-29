import { http } from './config.js'

export const userService = {
  getMyInfo: () => {
    return http.get('/profile/')
  },

  uploadAvatar: (formData) => {
    return http.put('/profile/avatar', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },

  findByFullName: (fullName) => {
    return http.get(`/profile/find-by-full-name?fullName=${fullName}`)
  }
}