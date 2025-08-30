import React from 'react'
import Post from '../components/Post.jsx'
import { postService } from '../service/post.service.js'

const HomePage = () => {
  return (
    <div className={'pt-[72px]'}>
      <Post service={postService.getPublicPost}/>
    </div>
  )
}

export default HomePage