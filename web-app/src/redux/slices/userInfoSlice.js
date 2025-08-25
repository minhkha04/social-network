import {createSlice} from '@reduxjs/toolkit'

const initialState = {
  userInfo:{
    id: '',
    email: '',
    fullName: '',
    role: '',
    birthDate: '',
    createdAt: '',
    updatedAt: '',
    avatarUrl: ''
  }
}

const userInfoSlice = createSlice({
  name: 'userInfo',
  initialState,
  reducers: {
    updateUserInfo: (state, action) => {
      state.userInfo = action.payload;
    },

  }
})

export const {updateUserInfo} = userInfoSlice.actions
export default userInfoSlice.reducer
