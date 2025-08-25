
import React, { useMemo } from 'react'
import { useSelector } from 'react-redux'
import dayjs from 'dayjs'

const ProfilePage = () => {
  const userInfo = useSelector((state) => state.userInfoSlice.userInfo)

  const displayName = userInfo?.fullName || 'Người dùng'
  const email = userInfo?.email || '—'
  const birthDate = userInfo?.birthDate ? dayjs(userInfo.birthDate).format('DD/MM/YYYY') : '—'
  const city = userInfo?.city || '—'
  const sex = userInfo?.sex ?? '—'
  const avatarUrl = userInfo?.avatarUrl

  const initials = useMemo(() => {
    if (!displayName) return 'U'
    const parts = displayName.trim().split(' ').filter(Boolean)
    const first = parts[0]?.[0] || ''
    const last = parts.length > 1 ? parts[parts.length - 1]?.[0] : ''
    return (first + last).toUpperCase() || 'U'
  }, [displayName])

  return (
    <div className="min-h-[calc(100vh-160px)] bg-gray-50 py-10 px-4">
      <div className="mx-auto max-w-3xl">
        <div className="bg-white shadow-sm rounded-2xl p-6 sm:p-8">
          {/* Header */}
          <div className="flex items-center gap-5">
            {avatarUrl ? (
              <img
                src={avatarUrl}
                alt={displayName}
                className="h-20 w-20 rounded-full object-cover border border-gray-200"
              />
            ) : (
              <div className="h-20 w-20 rounded-full bg-indigo-600 text-white flex items-center justify-center text-2xl font-semibold border border-indigo-500/20">
                {initials}
              </div>
            )}
            <div>
              <h1 className="text-2xl font-semibold text-gray-900">{displayName}</h1>
              <p className="text-gray-500">{email}</p>
            </div>
          </div>

          {/* Divider */}
          <div className="h-px bg-gray-100 my-6" />

          {/* Info grid */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="rounded-xl border border-gray-100 p-4">
              <div className="text-sm text-gray-500">Họ và tên</div>
              <div className="mt-1 font-medium text-gray-900">{displayName}</div>
            </div>
            <div className="rounded-xl border border-gray-100 p-4">
              <div className="text-sm text-gray-500">Email</div>
              <div className="mt-1 font-medium text-gray-900">{email}</div>
            </div>
            <div className="rounded-xl border border-gray-100 p-4">
              <div className="text-sm text-gray-500">Ngày sinh</div>
              <div className="mt-1 font-medium text-gray-900">{birthDate}</div>
            </div>
            <div className="rounded-xl border border-gray-100 p-4">
              <div className="text-sm text-gray-500">Giới tính</div>
              <div className="mt-1 font-medium text-gray-900">
                {sex === null || sex === undefined || sex === '—' ? '—' : sex}
              </div>
            </div>
            <div className="rounded-xl border border-gray-100 p-4 sm:col-span-2">
              <div className="text-sm text-gray-500">Thành phố</div>
              <div className="mt-1 font-medium text-gray-900">{city}</div>
            </div>
          </div>

          {/* Footer actions (placeholder) */}
          <div className="mt-6 flex flex-wrap gap-3">
            <button
              type="button"
              className="inline-flex items-center justify-center rounded-lg bg-indigo-600 px-4 py-2.5 text-white font-medium hover:bg-indigo-700 active:bg-indigo-800 transition"
            >
              Chỉnh sửa hồ sơ
            </button>
            <button
              type="button"
              className="inline-flex items-center justify-center rounded-lg border px-4 py-2.5 text-gray-700 font-medium hover:bg-gray-50 active:bg-gray-100 transition"
            >
              Đổi ảnh đại diện
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProfilePage