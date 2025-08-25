import React, { createContext, useEffect, useState } from 'react'
import { authService } from '../../service/auth.service.js'
import { userService } from '../../service/user.service.js'
import { useDispatch } from 'react-redux'
import { updateUserInfo } from '../../redux/slices/userInfoSlice.js'

export const AuthContext = createContext(null)

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const dispatch = useDispatch()

  useEffect(() => {
    const token = JSON.parse(localStorage.getItem('token'))
    if (!token) return
    console.log(token)

    authService.introspect({ token })
      .then(res => {
        if (res.data.data.valid) {
          setIsLoggedIn(true)
          userService.getMyInfo()
            .then(res => {
              dispatch(updateUserInfo(res.data.data))
              console.log('User info fetched successfully')
              console.log(res.data.data)
            })
            .catch(err => {
                console.log(err)
              }
            )
        } else {
          localStorage.removeItem('token')
          setIsLoggedIn(false)
        }
      })
      .catch((res) => {
        console.log(res)
        localStorage.removeItem('token')
        setIsLoggedIn(false)
      })
  }, [])

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      {children}
    </AuthContext.Provider>
  )
}
