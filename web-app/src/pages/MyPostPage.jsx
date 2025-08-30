import React, { useContext } from 'react'
import { AuthContext } from '../components/context/AuthContext.jsx'
import Post from '../components/Post.jsx'
import { postService } from '../service/post.service.js'

const MyPostPage = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext)
  return (
    <div className={'pt-[72px]'}>
      {isLoggedIn
        ? <Post service={postService.getMyPost}/>
        : <div>Please login to see posts</div>
      }
    </div>
  )
}

export default MyPostPage