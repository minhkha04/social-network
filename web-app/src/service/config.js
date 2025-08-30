import axios from 'axios'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_DOMAIN,
  timeout: 30000,
  headers: {},
})

http.interceptors.request.use(
  function (config) {
    if (config.url.includes('identity') || config.url.includes('public')) {
      return config
    }
    const token = JSON.parse(localStorage.getItem('token'))
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  },
)

// Add a response interceptor
http.interceptors.response.use(
  function (response) {
    return response
  },
  function (error) {
    return Promise.reject(error)
  }
)

export { http }