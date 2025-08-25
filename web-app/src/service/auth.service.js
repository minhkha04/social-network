import { http } from './config.js'

export const authService = {
  login: (data, provider) => {
    return http.post(`identity/login?provider=${provider}`, data)
  },

  register: (data) => {
    return http.post('identity/register', data)
  },

  sendOTP: (data, type) => {
    return http.post(`identity/send-otp?type=${type}`, data)
  },

  restPassword: (data) => {
    return http.put('identity/reset-password', data)
  },

  introspect: (data) => {
    return http.post('identity/introspect', data)
  }
};