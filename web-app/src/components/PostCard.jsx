import React from 'react'

const PostCard = ({ post }) => {
  const initials = (post?.userFullName || 'U')
    .split(' ')
    .filter(Boolean)
    .map(s => s[0])
    .slice(0, 2)
    .join('')
    .toUpperCase()

  const hasAvatar = Boolean(post?.userAvatarUrl)

  return (
    <div
      key={post.id}
      className="group relative rounded-xl border border-zinc-800 bg-zinc-950 p-6 transition-shadow shadow-md hover:shadow-xl"
    >
      {/* Top glow line */}
      <div className="absolute inset-x-0 top-0 h-0.5 bg-gradient-to-r from-transparent via-white/20 to-transparent group-hover:opacity-100 opacity-60" />

      {/* Header */}
      <div className="flex gap-4 items-start">
        {/* Avatar */}
        {hasAvatar ? (
          <img
            src={post.userAvatarUrl}
            alt={post.userFullName || 'User Avatar'}
            className="h-14 w-14 rounded-full object-cover ring-1 ring-white/20 shadow-sm"
            onError={(e) => {
              e.currentTarget.style.display = 'none'
              e.currentTarget.nextSibling.style.display = 'flex'
            }}
          />
        ) : null}

        {/* Fallback initials */}
        <div
          style={{ display: hasAvatar ? 'none' : 'flex' }}
          className="h-14 w-14 rounded-full bg-zinc-800 text-white items-center justify-center font-semibold select-none ring-1 ring-white/10"
        >
          {initials}
        </div>

        {/* Info */}
        <div className="flex-1">
          <div className="flex justify-between items-center">
            <h3 className="text-white text-lg font-semibold">{post.userFullName || 'Unknown User'}</h3>
            <span className="text-xs bg-zinc-800 px-2 py-1 rounded border border-zinc-700 text-zinc-400 font-mono">
              {new Date(post.createdAt).toLocaleString()}
            </span>
          </div>

          {/* ID Tags */}
          <div className="mt-2 flex flex-wrap gap-2 items-center">
            <span className="text-xs text-zinc-500 uppercase tracking-wide">IDs</span>
            <span className="text-xs bg-zinc-900 px-2 py-1 rounded border border-zinc-800 text-white font-mono">
              post: {post.id}
            </span>
            <span className="text-xs bg-zinc-900 px-2 py-1 rounded border border-zinc-800 text-white font-mono">
              user: {post.userId}
            </span>
          </div>
        </div>
      </div>

      {/* Nội dung hoặc email */}
      <div className="mt-5 text-sm text-zinc-200 whitespace-pre-wrap leading-relaxed bg-zinc-900 rounded-lg p-4 border border-zinc-800">
        {post.content || post.email}
      </div>

      {/* Meta section */}
      <div className="mt-6 grid grid-cols-1 sm:grid-cols-3 gap-3 text-sm">
        {/* Created At */}
        <div className="bg-zinc-900 border border-zinc-800 rounded-lg p-3">
          <span className="block text-zinc-500">Created At</span>
          <div className="text-white font-medium">
            {new Date(post.createdAt).toLocaleString()}
          </div>
        </div>

        {/* Created (x hours ago) từ BE */}
        <div className="bg-zinc-900 border border-zinc-800 rounded-lg p-3">
          <span className="block text-zinc-500">Created</span>
          <div className="text-white font-medium">
            {post.created}
          </div>
        </div>

        {/* Modified At */}
        <div className="bg-zinc-900 border border-zinc-800 rounded-lg p-3">
          <span className="block text-zinc-500">Modified At</span>
          <div className="text-white font-medium">
            {new Date(post.modifiedAt).toLocaleString()}
          </div>
        </div>
      </div>

      {/* Bottom glow line */}
      <div className="absolute inset-x-6 bottom-0 h-px bg-gradient-to-r from-transparent via-white/10 to-transparent opacity-60 group-hover:opacity-100 transition-opacity" />
    </div>
  )
}

export default PostCard
