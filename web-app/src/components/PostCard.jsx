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
    <div className="bg-white text-black rounded-xl shadow p-6 border border-zinc-200">
      {/* Header */}
      <div className="flex justify-between items-start mb-5">
        <div className="flex items-start gap-4">
          {/* Avatar */}
          {hasAvatar ? (
            <img
              src={post.userAvatarUrl}
              alt={post.userFullName || 'User Avatar'}
              className="h-16 w-16 rounded-full object-cover bg-zinc-100 border border-zinc-300"
              onError={e => {
                e.currentTarget.style.display = 'none'
                e.currentTarget.nextSibling.style.display = 'flex'
              }}
            />
          ) : null}
          {/* Fallback Initials */}
          {!hasAvatar && (
            <div className="h-16 w-16 rounded-full bg-zinc-200 text-black flex items-center justify-center text-xl font-bold border border-zinc-300">
              {initials}
            </div>
          )}
          {/* Name + Created label */}
          <div>
            <div className="font-bold text-lg">{post.userFullName || 'Unknown User'}</div>
            <div className="text-xs text-zinc-500 mt-1">{post.created}</div>
          </div>
        </div>
        {/* CreatedAt (top right) */}
        <div className="text-xs bg-zinc-100 rounded px-3 py-1 border border-zinc-200 font-mono font-medium">
          {new Date(post.createdAt).toLocaleString()}
        </div>
      </div>
      {/* Content */}
      <div className="bg-zinc-100 text-black rounded-lg p-5 text-base leading-relaxed border border-zinc-200">
        {post.content || post.email}
      </div>
    </div>
  )
}

export default PostCard
