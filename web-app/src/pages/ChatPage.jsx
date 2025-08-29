import React, { useCallback, useContext, useEffect, useState } from 'react';
import { AuthContext } from '../components/context/AuthContext.jsx';
import { HiSearch } from 'react-icons/hi';
import InputCustom from '../components/Input/InputCustom.jsx';
import { chatService } from '../service/chat.service.js';
import { userService } from '../service/user.service.js';
import debounce from 'lodash.debounce';

const ChatPage = () => {
  const { isLoggedIn } = useContext(AuthContext);
  const [conversations, setConversations] = useState([]);
  const [fullName, setFullName] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchResults, setSearchResults] = useState([]);

  // Lấy conversation như cũ
  useEffect(() => {
    if (isLoggedIn) {
      chatService.getMyConversation()
        .then(res => setConversations(res.data.data))
        .catch(console.error);
    } else {
      setConversations([]);
    }
  }, [isLoggedIn]);

  // Debounced search
  const debouncedFetch = useCallback(
    debounce(name => {
      userService.findByFullName(name)
        .then(res => setSearchResults(res.data.data))
        .catch(console.error);
    }, 400),
    []
  );

  useEffect(() => {
    const name = fullName.trim();
    if (name.length > 0) {
      debouncedFetch(name);
    } else {
      setSearchResults([]);
    }
    return () => debouncedFetch.cancel();
  }, [fullName, debouncedFetch]);


  const handleCreateConversation = (participantId) => {
    chatService.createConversation({"type": "DIRECT", "participantId": [participantId]})
    .then(res => {
      const newConv = res.data.data;
      setConversations(prev => {
        // nếu đã tồn tại, giữ nguyên prev
        if (prev.some(c => c.id === newConv.id)) {
          return prev;
        }
        return [...prev, newConv];
      });
    })
    .catch(console.error)
  }
  return (
    <div>
      {isLoggedIn ? (
        <div className="flex flex-col h-screen">
          <div className="flex flex-1 overflow-hidden">
            {/* Sidebar */}
            <aside className="w-1/4 border-r flex flex-col">
              <div className="flex items-center justify-between bg-black text-white px-4 py-3">
                <h2 className="text-lg font-semibold">Chat</h2>
                <button
                  onClick={() => setIsModalOpen(true)}
                  className="bg-white p-2 rounded-full hover:opacity-90"
                >
                  <HiSearch className="w-5 h-5 text-gray-800"/>
                </button>
              </div>
              <ul className="flex-1 overflow-y-auto">
                {conversations.map(c => (
                  <li key={c.id} className="flex items-center px-4 py-3 hover:bg-gray-100 cursor-pointer">
                    <img src={c.conversationAvatarUrl} alt="" className="w-10 h-10 rounded-full mr-3"/>
                    <span>{c.conversationName}</span>
                  </li>
                ))}
              </ul>
            </aside>
            {/* Chat Content */}
            <section className="flex-1 bg-white">{/* … */}</section>
          </div>

          {/* Modal Search Bạn Bè */}
          {isModalOpen && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-start justify-center z-50">
              <div className="bg-white rounded-lg w-1/3 mt-20 shadow-lg">
                {/* Header */}
                <div className="flex items-center justify-between px-4 py-2 border-b">
                  <h3 className="text-lg font-medium">Tìm bạn bè</h3>
                  <button onClick={() => setIsModalOpen(false)}>✕</button>
                </div>
                {/* Input */}
                <div className="px-4 py-2">
                  <InputCustom
                    label="Full Name"
                    placeholder="Enter full name to search for friends.."
                    value={fullName}
                    onChange={e => setFullName(e.target.value)}
                  />
                </div>
                {/* Kết quả */}
                <ul className="max-h-60 overflow-y-auto">
                  {searchResults.map(user => (
                    <li
                      key={user.id}
                      className="flex items-center px-4 py-2 hover:bg-gray-100 cursor-pointer"
                      onClick={() => {
                        handleCreateConversation(user.userId);    // hoặc user.userId
                        setIsModalOpen(false);                 // đóng modal
                        setFullName('');                       // xóa input
                        setSearchResults([]);                  // reset kết quả
                      }}
                    >
                      <img
                        src={user.avatarUrl}
                        alt={user.fullName}
                        className="w-10 h-10 rounded-full mr-3 object-cover"
                      />
                      <span className="text-gray-800">{user.fullName}</span>
                    </li>
                  ))}
                  {fullName.trim().length > 0 && searchResults.length === 0 && (
                    <li className="px-4 py-2 text-gray-500">Không tìm thấy kết quả</li>
                  )}
                </ul>
              </div>
            </div>
          )}
        </div>
      ) : (
        <div>Please login to use chat</div>
      )}
    </div>
  );
};

export default ChatPage;
