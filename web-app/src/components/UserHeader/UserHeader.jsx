import React, { useContext, useEffect, useState } from 'react'
import { Link, NavLink } from 'react-router-dom'
import { path } from '../../common/path.js'
import { assets } from '../../assets/assets.jsx'
import AuthModal from './AuthModal.jsx'
import { Avatar, Button, Menu, MenuItem } from '@mui/material'
import { AuthContext } from '../context/AuthContext.jsx'
import { useDispatch, useSelector } from 'react-redux'
import { updateUserInfo } from '../../redux/slices/userInfoSlice.js'
import { NotificationContext } from '../../App.jsx'

const UserHeader = () => {
  const [modalOpen, setModalOpen] = useState(false)
  const [model, setModel] = useState('')
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext)
  const [anchorEl, setAnchorEl] = useState(false)
  const { userInfo } = useSelector((state) => state.userInfoSlice)
  const dispatch = useDispatch()
  const valueContext = useContext(NotificationContext)

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget)
  }
  const handleClose = () => {
    setAnchorEl(false)
  }

  const handleLogout = () => {
    localStorage.removeItem('token')
    dispatch(updateUserInfo(null))
    setIsLoggedIn(false)
    setAnchorEl(false)
    valueContext.handleNotification('success', 'Logout successfully')
  }

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) {
      setIsLoggedIn(true)
    }
  }, [])

  return (
    <header>
      <AuthModal isOpen={modalOpen} onClose={() => setModalOpen(false)} title={'Login'} mode={model} setMode={setModel}
                 setIsLoggedIn={setIsLoggedIn}/>
      <div className={'container flex flex-row justify-between items-center'}>
        <div className={'logo'}>
          <Link to={path.homePage} onClick={() => scrollTo(0, 0)}>
            <img src={assets.logo} alt={'logo'} className={'h-16 w-16'}/>
          </Link>
        </div>
        <nav className={'flex flex-row gap-4 items-center'}>
          <ul className={'flex flex-row gap-4'}>
            <li>
              <NavLink
                to={path.homePage}
                className={({ isActive }) =>
                  isActive
                    ? 'text-black font-bold border-b-2 border-black'
                    : 'text-gray-700 hover:text-black'
                }
              >
                Home
              </NavLink>
            </li>
            <li>
              <NavLink
                to={path.profilePage}
                className={({ isActive }) =>
                  isActive
                    ? 'text-black font-bold border-b-2 border-black'
                    : 'text-gray-700 hover:text-black'
                }
              >
                Profile
              </NavLink>
            </li>
          </ul>
          {!isLoggedIn
            ? <div className={'flex flex-row gap-4'}>
              <Button onClick={() => {
                setModalOpen(true)
                setModel('login')
              }} variant="outlined" size="small"
              >Login</Button>

              <Button variant="contained" size="small"
                      onClick={() => {
                        setModalOpen(true)
                        setModel('register')
                      }}
              >Register</Button>
            </div>
            : <div>
              <Avatar
                alt="Remy Sharp"
                src={userInfo?.avatarUrl}
                sx={{ width: 50, height: 50 }}
                onClick={handleClick}
                className={'cursor-pointer'}
              />
              <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={anchorEl}
                onClose={handleClose}
                slotProps={{
                  list: {
                    'aria-labelledby': 'basic-button',
                  },
                }}
              >
                <MenuItem onClick={handleClose}>Profile</MenuItem>
                <MenuItem onClick={handleLogout}>Logout</MenuItem>
              </Menu>
            </div>
          }
        </nav>
      </div>
    </header>
  )
}

export default UserHeader