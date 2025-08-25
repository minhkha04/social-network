import React, { useContext, useEffect, useState } from 'react'
import { Modal } from 'antd'
import InputCustom from '../Input/InputCustom.jsx'
import { DatePicker } from '@mui/x-date-pickers'
import { Button } from '@mui/material'
import { authService } from '../../service/auth.service.js'
import { NotificationContext } from '../../App.jsx'
import { useDispatch, useSelector } from 'react-redux'
import { updateUserInfo } from '../../redux/slices/userInfoSlice.js'
import { userService } from '../../service/user.service.js'
import { GoogleLogin } from '@react-oauth/google'
import FacebookLogin from '@greatsumini/react-facebook-login'

const AuthModal = ({ isOpen, onClose, mode, setMode, setIsLoggedIn }) => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [fullName, setFullName] = useState('')
  const [birthDate, setBirthDate] = useState(null)
  const [otp, setOtp] = useState('')
  const [confirmLoading, setConfirmLoading] = useState(false)
  const [countdown, setCountdown] = useState(0)
  const [isCounting, setIsCounting] = useState(false)
  const valueContext = useContext(NotificationContext)
  const dispatch = useDispatch()

  useEffect(() => {
    let timer
    if (isCounting && countdown > 0) {
      timer = setInterval(() => {
        setCountdown((prev) => prev - 1)
      }, 1000)
    } else if (countdown === 0) {
      setIsCounting(false)
      clearInterval(timer)
    }
    return () => clearInterval(timer)
  }, [isCounting, countdown])

  const getMyInfo = () => {
    userService.getMyInfo()
      .then(res => {
        dispatch(updateUserInfo(res.data.data))
      })
      .catch(err => {
        console.log(err)
      })
  }

  const handleSubmit = () => {
    setConfirmLoading(true)
    if (mode === 'login') {
      authService.login({ email, password }, 'EMAIL')
        .then((res) => {
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          setIsLoggedIn(true)
          onClose()
          getMyInfo()
          valueContext.handleNotification('success', 'Login successfully')
          setEmail('')
          setPassword('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {
          setConfirmLoading(false)
        })
    } else if (mode === 'register') {
      authService.register({
        email,
        password,
        fullName,
        birthDate: birthDate ? birthDate.format('YYYY-MM-DD') : null,
        otp
      })
        .then((res) => {
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          setIsLoggedIn(true)
          onClose()
          getMyInfo()
          valueContext.handleNotification('success', 'Register successfully')
          setEmail('')
          setPassword('')
          setFullName('')
          setBirthDate(null)
          setOtp('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {})
    } else if (mode === 'forgot_password') {
      authService.restPassword({ email, otp, password })
        .then((res) => {
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          getMyInfo()
          setIsLoggedIn(true)
          onClose()
          valueContext.handleNotification('success', 'Reset password successfully')
          setEmail('')
          setPassword('')
          setOtp('')
        })
        .catch((err) => {
          valueContext.handleNotification('error', err.response.data.message)
        })
        .finally(() => {})

    }
  }

  const handleSendOtp = (type) => {
    authService.sendOTP({ email }, type)
      .then(() => {
        valueContext.handleNotification('success', 'Send OTP successfully')
        setIsCounting(true)
        setCountdown(60)
      })
      .catch((err) => {
        valueContext.handleNotification('error', err.response.data.message)
      })

  }

  const handleLoginWithGgSuccess = (credentialResponse) => {
    const accessToken = credentialResponse.credential;

    authService.login({ accessToken }, 'GOOGLE')
      .then((res) => {
        localStorage.setItem('token', JSON.stringify(res.data.data.token))
        setIsLoggedIn(true)
        onClose()
        getMyInfo()
        valueContext.handleNotification('success', 'Login successfully')
      })
      .catch((err) => {
        console.log(err)
        valueContext.handleNotification('error', 'Login failed. Please try again.')
      })
  };

  const handleError = () => {
    valueContext.handleNotification('error', 'Login failed. Please try again.')
  };


  const handleLoginWithFbSuccess = (response) => {
    const accessToken = response.accessToken;

    authService.login({ accessToken }, 'FACEBOOK')
      .then((res) => {
        localStorage.setItem('token', JSON.stringify(res.data.data.token))
        setIsLoggedIn(true)
        onClose()
        getMyInfo()
        valueContext.handleNotification('success', 'Login successfully')
      })
      .catch((err) => {
        console.log(err)
        valueContext.handleNotification('error', 'Login failed. Please try again.')
      })
  }

  return (
    <Modal
      title={mode === 'login' ? 'Login' : mode === 'register' ? 'Register' : 'Forgot Password'}
      centered
      open={isOpen}
      onOk={handleSubmit}
      onCancel={onClose}
      okText={mode === 'login' ? 'Login' : mode === 'register' ? 'Register' : 'Change Password'}
      confirmLoading={confirmLoading}
    >
      <InputCustom label={'Email'} placeholder={'Enter your email'} value={email}
                   onChange={(e) => setEmail(e.target.value)}/>
      <InputCustom label={'Password'}
                   placeholder={`Enter your ${mode === 'login' ? 'password' : mode === 'register' ? 'password' : 'new password'}`}
                   isPassword={true} value={password} onChange={(e) => setPassword(e.target.value)}/>
      {mode === 'register' &&
        <>
          <InputCustom label={'Full Name'} placeholder={'Enter your full name'} value={fullName}
                       onChange={(e) => setFullName(e.target.value)}/>
          <DatePicker label="Choose your birthdate" value={birthDate} onChange={(newValue) => setBirthDate(newValue)}
                      slotProps={{
                        textField: {
                          fullWidth: true,
                          size: 'small',
                          margin: 'dense',
                        }
                      }}
          />
          <div className={'flex flex-row items-center gap-3'}>
            <div className={'w-7/12 sm:w-11/12'}>
              <InputCustom label={'OTP'} placeholder={'Enter OTP'} isOTP={true} value={otp}
                           onChange={(e) => setOtp(e.target.value)}/>
            </div>
            <div className={'w-1/3'}>
              <Button variant="contained" size="large" onClick={() => handleSendOtp('REGISTER_ACCOUNT')}
                      disabled={isCounting}
                      sx={{
                        marginTop: '5px',
                        height: '39px',
                        minWidth: '100px',
                        whiteSpace: 'nowrap'
                      }}
              >
                {isCounting ? `Resend ${countdown}s` : 'Send OTP'}
              </Button>
            </div>
          </div>
        </>}
      {mode === 'forgot_password'
        && <div className={'flex flex-row items-center gap-3'}>
          <div className={'w-7/12 sm:w-11/12'}>
            <InputCustom label={'OTP'} placeholder={'Enter OTP'} isOTP={true} value={otp}
                         onChange={(e) => setOtp(e.target.value)}/>
          </div>
          <div className={'w-1/3'}>
            <Button variant="contained" size="large" onClick={() => handleSendOtp('RESET_PASSWORD')}
                    disabled={isCounting}
                    sx={{
                      marginTop: '5px',
                      height: '39px',
                      minWidth: '100px',
                      whiteSpace: 'nowrap'
                    }}
            >
              {isCounting ? `Resend ${countdown}s` : 'Send OTP'}
            </Button>
          </div>
        </div>
      }
      <div className={'flex flex-row justify-between'}>
        {mode === 'login' ? (
          <span>
            <a onClick={() => setMode('register')} className={'text-red-500 hover:text-black'}>Don't have an account?</a>
          </span>
        ) : (
          <span>
            <a onClick={() => setMode('login')} className={'text-red-500 hover:text-black'}>Have an account?</a>
          </span>
        )}
        {mode !== 'forgot_password'
          ? <span>
            <a onClick={() => setMode('forgot_password')}
               className={'text-red-500 hover:text-black'}>Forgot Password?</a>
          </span>
          : <span>
            <a onClick={() => setMode('register')} className={'text-red-500 hover:text-black'}>Don't have an account?</a>
          </span>
        }

      </div>
      <div className={'flex  flex-col sm:flex-row justify-between gap-3 mt-4'}>
        <div className={'w-full sm:w-1/2'}>
          <GoogleLogin onSuccess={handleLoginWithGgSuccess} onError={handleError} text='Sign in with Google'/>

        </div>
        <div className={'w-full sm:w-1/2 h-[40px] flex items-center bg-[#1877F2] rounded-md hover:bg-[#166FE5] text-white cursor-pointer justify-center gap-3 border border-gray-50 text-center'}>
          <svg viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" fill="#ffffff" className={'w-5 h-5 ml-4 sm:ml-0'}><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <rect x="0" fill="none" width="20" height="20"></rect> <g> <path d="M8.46 18h2.93v-7.3h2.45l.37-2.84h-2.82V6.04c0-.82.23-1.38 1.41-1.38h1.51V2.11c-.26-.03-1.15-.11-2.19-.11-2.18 0-3.66 1.33-3.66 3.76v2.1H6v2.84h2.46V18z"></path> </g> </g>
          </svg>
          <FacebookLogin
            appId="611552785131039"
            fields="name,email,picture"
            onSuccess={handleLoginWithFbSuccess}
            render={renderProps => (
              <button
                onClick={renderProps.onClick}
              >
                Đăng nhập bằng Facebook
              </button>
            )}
          />
        </div>
      </div>
    </Modal>
  )
}

export default AuthModal