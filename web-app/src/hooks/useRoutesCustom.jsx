import React from 'react'
import { useRoutes } from 'react-router-dom'
import UserTemplate from '../template/UserTemplate/UserTemplate.jsx'
import PageNotFound from '../components/PageNotFound/PageNotFound.jsx'
import { path } from '../common/path.js'
import HomePage from '../pages/HomePage.jsx'
import ProfilePage from '../pages/ProfilePage.jsx'

const UseRoutesCustom = () => {
  return useRoutes([
    {
      path: path.homePage,
      element: <UserTemplate/>,
      children: [
        {
          path: path.homePage,
          index: true,
          element: <HomePage/>,
        },
        {
          path: path.profilePage,
          element: <ProfilePage/>,
        },
      ],
    },
    {
      path: path.pageNotFound,
      element: <PageNotFound/>,
    }
  ])

}

export default UseRoutesCustom