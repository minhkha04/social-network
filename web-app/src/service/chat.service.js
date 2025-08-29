import { http } from './config.js'

export const chatService = {
  getMyConversation: () => {
    return http.get('/chat/my-conversations')
  },

  createConversation: (data) => {
    return http.post('/chat/create', data)
  },

  creatChatMessages: (data) => {
    return http.post('/chat/message/create', data)
  },

  getMessage: (conversationId) => {
    return http.get(`/chat/message?conversationId=${conversationId}`)
  }
}