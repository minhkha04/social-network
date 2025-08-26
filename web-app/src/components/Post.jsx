import React, { useContext, useEffect, useState } from 'react'
import { postService } from '../service/post.service.js'
import { Modal } from 'antd'
import InputCustom from './Input/InputCustom.jsx'
import { Button } from '@mui/material'
import { NotificationContext } from '../App.jsx'
import InfiniteScroll from 'react-infinite-scroll-component'

const Post = () => {
  const [posts, setPosts] = useState([])
  const [page, setPage] = useState(1)
  const [hasMore, setHasMore] = useState(true)
  const [loading, setLoading] = useState(false)
  const [content, setContent] = useState('')
  const [confirmLoading, setConfirmLoading] = useState(false)
  const [isOpen, setIsOpen] = useState(false)

  const valueContext = useContext(NotificationContext)

  // 👑 Hàm fetch theo page, dùng khi khởi động hoặc tạo bài viết mới
  const fetchPostsByPage = async (pageNumber) => {
    console.log(`[CALL] fetchPostsByPage(${pageNumber})`)
    setLoading(true)
    try {
      const res = await postService.getMyPost(pageNumber, 4)
      const data = res.data.data

      setPosts(data.data)
      setHasMore(data.pageNumber < data.totalPages)
      setPage(pageNumber + 1)

      console.log(`[SUCCESS] Fetched page ${data.pageNumber}, totalPages: ${data.totalPages}`)
    } catch (err) {
      console.error('[ERROR] fetchPostsByPage:', err?.response?.data?.message || err.message)
      valueContext.handleNotification('error', err?.response?.data?.message || 'Failed to load posts')
    } finally {
      setLoading(false)
    }
  }

  // ⚡ Hàm dùng cho Infinite Scroll
  const fetchPosts = async () => {
    if (loading || !hasMore) return

    console.log(`[CALL] InfiniteScroll fetchPosts - page ${page}`)

    setLoading(true)
    try {
      const res = await postService.getMyPost(page, 4)
      const data = res.data.data

      setPosts(prev => [...prev, ...data.data])
      setHasMore(data.pageNumber < data.totalPages)
      setPage(prev => prev + 1)

      console.log(`[SUCCESS] Appended page ${data.pageNumber}, totalPages: ${data.totalPages}`)
    } catch (err) {
      console.error('[ERROR] fetchPosts:', err?.response?.data?.message || err.message)
      valueContext.handleNotification('error', err?.response?.data?.message || 'Failed to load posts')
    } finally {
      setLoading(false)
    }
  }

  const handleSubmit = async () => {
    if (!content.trim()) {
      valueContext.handleNotification('warning', 'Content is required')
      return
    }

    setConfirmLoading(true)
    try {
      await postService.creatPost({ content })
      valueContext.handleNotification('success', 'Post created successfully!')

      setContent('')
      setIsOpen(false)

      // 🔁 Reset lại danh sách và gọi lại trang đầu
      await fetchPostsByPage(1)
    } catch (err) {
      console.error('[ERROR] Create Post:', err?.response?.data?.message || err.message)
      valueContext.handleNotification('error', err?.response?.data?.message || 'Failed to create post')
    } finally {
      setConfirmLoading(false)
    }
  }

  useEffect(() => {
    console.log('[MOUNT] Component mounted → fetchPostsByPage(1)')
    fetchPostsByPage(1)
  }, [])

  return (
    <div className="container mx-auto mt-5 px-4">
      {/* Create Post Button */}
      <div className="mb-5">
        <Button onClick={() => setIsOpen(true)} variant="outlined">Create Post</Button>
      </div>

      {/* Modal */}
      <Modal
        title="Create Post"
        centered
        open={isOpen}
        onOk={handleSubmit}
        onCancel={() => setIsOpen(false)}
        okText="Post"
        confirmLoading={confirmLoading}
      >
        <InputCustom
          label="Content"
          placeholder="Enter your content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      </Modal>

      {/* Infinite Scroll Section */}
      <div className="mt-8">
        <h2 className="text-xl font-bold mb-4">Your Posts</h2>

        <InfiniteScroll
          dataLength={posts.length}
          next={fetchPosts}
          hasMore={hasMore}
          loader={<p className="text-center">Đang tải thêm bài viết...</p>}
          endMessage={<p className="text-center font-medium">Đã tải hết tất cả bài viết 🎉</p>}
        >
          {posts.map(post => (
            <div key={post.id} className="border p-4 rounded mb-4 bg-white shadow-sm">
              <p><strong>Id:</strong> {post.id}</p>
              <p><strong>User ID:</strong> {post.userId}</p>
              <p><strong>Content:</strong> {post.content}</p>
              <p><strong>Created At:</strong> {new Date(post.createdAt).toLocaleString()}</p>
              <p><strong>Created:</strong> {post.created}</p>
              <p><strong>Modified At:</strong> {new Date(post.modifiedAt).toLocaleString()}</p>
            </div>
          ))}
        </InfiniteScroll>
      </div>
    </div>
  )
}

export default Post
