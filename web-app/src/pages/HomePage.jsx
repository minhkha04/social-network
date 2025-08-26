import React, { useContext } from 'react'
import Post from '../components/Post.jsx'
import { AuthContext } from '../components/context/AuthContext.jsx'

const HomePage = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext)
  return (
    <div>
      {isLoggedIn
        ? <Post />
        : <div>Please login to see posts</div>
      }

    </div>
  )
}

export default HomePage