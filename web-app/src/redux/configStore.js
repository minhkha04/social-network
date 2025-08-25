import { configureStore } from '@reduxjs/toolkit'
import userInfoSlice from './slices/userInfoSlice.js'

export const store = configureStore({
  reducer: {
    userInfoSlice,
  },
})