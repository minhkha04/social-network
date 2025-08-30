import React, { useCallback, useContext, useEffect, useMemo, useRef, useState } from 'react';
import { AuthContext } from '../components/context/AuthContext.jsx';
import { HiSearch } from 'react-icons/hi';
import InputCustom from '../components/Input/InputCustom.jsx';
import { chatService } from '../service/chat.service.js';
import { userService } from '../service/user.service.js';
import debounce from 'lodash.debounce';
import { io } from "socket.io-client";
import { useSelector } from 'react-redux'

const FALLBACK_AVATAR = 'https://via.placeholder.com/50';

const ChatPage = () => {
  const { isLoggedIn } = useContext(AuthContext);

  const [conversations, setConversations] = useState([]);
  const [selectedConversationId, setSelectedConversationId] = useState(null);

  const [fullName, setFullName] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchResults, setSearchResults] = useState([]);

  // Messages state
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const [loadingMessages, setLoadingMessages] = useState(false);
  const [sending, setSending] = useState(false);

  const listRef = useRef(null);
  const bottomRef = useRef(null);

  const { userInfo } = useSelector((state) => state.userInfoSlice)
  // Lấy conversation (KHÔNG auto-select lần đầu)
  useEffect(() => {
    if (isLoggedIn) {
      chatService.getMyConversation()
        .then(res => {
          const list = res.data?.data || [];
          setConversations(list);
        })
        .catch(console.error);
    } else {
      setConversations([]);
      setSelectedConversationId(null);
      setMessages([]);
    }
  }, [isLoggedIn]);

  useEffect(() => {
    console.log("Initializing socket connection...")
    console.log("UserId: ", userInfo?.userId)
    const socket = io(`http://localhost:8099?token=${JSON.parse(localStorage.getItem('token'))}&userId=${userInfo?.userId}`);

    socket.on('connect', () => {
      console.log('Connected to WebSocket server');
    });

    socket.on('disconnect', () => {
      console.log('Disconnected from WebSocket server');
    })

    socket.on('message', (rawMsg) => {
      let message;
      try {
        message = typeof rawMsg === 'string' ? JSON.parse(rawMsg) : rawMsg;
      } catch (e) {
        console.error("Không parse được message:", rawMsg, e);
        return;
      }

      console.log('Received message:', message);

      // CHỈ append nếu conversationId đang chọn trùng với message
      setMessages(prev => {
        // Nếu đang ở đúng conversation thì append
        if (selectedConversationId && message.conversationId === selectedConversationId) {
          // Tránh duplicate nếu đã có id này (trường hợp gửi xong cũng append local)
          if (prev.some(m => m.id === message.id)) return prev;
          return [...prev, message];
        }
        return prev;
      });
    });

    return () => {
      console.log('Closing socket connection...');
      socket.close();
    }

  }, [selectedConversationId, isLoggedIn, userInfo?.userId])

  // Debounced search
  const debouncedFetch = useCallback(
    debounce(name => {
      userService.findByFullName(name)
        .then(res => setSearchResults(res.data?.data || []))
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

  // Conversation đang chọn
  const selectedConversation = useMemo(
    () => conversations.find(c => c.id === selectedConversationId) || null,
    [conversations, selectedConversationId]
  );

  // Fetch messages khi đổi conversation
  useEffect(() => {
    if (!selectedConversationId) {
      setMessages([]);
      return;
    }
    setLoadingMessages(true);
    chatService.getMessage(selectedConversationId) // <-- gọi đúng service
      .then(res => {
        const list = res.data?.data || [];
        setMessages(list);
        // Auto scroll xuống cuối
        setTimeout(() => bottomRef.current?.scrollIntoView({ behavior: 'auto' }), 0);
      })
      .catch(console.error)
      .finally(() => setLoadingMessages(false));
  }, [selectedConversationId]);

  // Auto scroll khi có message mới
  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  // Tạo mới conversation và AUTO chọn ngay cái vừa tạo
  const handleCreateConversation = (participantId) => {
    chatService.createConversation({ type: 'DIRECT', participantId: [participantId] })
      .then(res => {
        const newConv = res.data?.data;
        if (!newConv) return;
        setConversations(prev => (prev.some(c => c.id === newConv.id) ? prev : [...prev, newConv]));
        setSelectedConversationId(newConv.id); // auto-select sau khi tạo
      })
      .catch(console.error);
  };

  // Gửi message (khung đỏ)
  const handleSendMessage = async () => {
    const msg = inputMessage.trim();
    if (!msg || !selectedConversationId || sending) return;


    chatService.creatChatMessages({conversationId: selectedConversationId, message: msg})
      .then(res => {
        const newMsg = res.data?.data;
        if (newMsg) {
          setInputMessage('');
        }
      })
      .catch(console.error)
      .finally(() => {
        setSending(false);
      })
  };

  // Enter để gửi, Shift+Enter để xuống dòng
  const onKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  // Format thời gian nhanh (vi-VN)
  const fmtTime = (iso) => {
    try {
      return new Date(iso).toLocaleString('vi-VN');
    } catch {
      return iso;
    }
  };

  return (
    <div className={'container pt-[72px]'}>
      {isLoggedIn ? (
        <div className="flex flex-col h-[calc(100vh-160px)] bg-gray-100">
          <div className="flex flex-1 overflow-hidden">
            {/* Sidebar (vàng) */}
            <aside className="w-1/4 border-r flex flex-col">
              <div className="flex items-center justify-between bg-black text-white px-4 py-3">
                <h2 className="text-lg font-semibold">Chat</h2>
                <button
                  onClick={() => setIsModalOpen(true)}
                  className="bg-white p-2 rounded-full hover:opacity-90"
                  aria-label="Search friends"
                >
                  <HiSearch className="w-5 h-5 text-gray-800" />
                </button>
              </div>
              <ul className="flex-1 overflow-y-auto">
                {conversations.map(c => {
                  const active = c.id === selectedConversationId;
                  return (
                    <li
                      key={c.id}
                      className={`flex items-center px-4 py-3 cursor-pointer ${
                        active ? 'bg-gray-100' : 'hover:bg-gray-50'
                      }`}
                      onClick={() => setSelectedConversationId(c.id)}
                    >
                      <img
                        src={c.conversationAvatarUrl || FALLBACK_AVATAR}
                        alt={c.conversationName || 'avatar'}
                        className="w-10 h-10 rounded-full mr-3 object-cover"
                      />
                      <span className="truncate">{c.conversationName}</span>
                    </li>
                  );
                })}
              </ul>
            </aside>

            {/* Chat Content */}
            <section className="flex-1 bg-white flex flex-col">
              {/* Header (đã làm) */}
              <div className="flex items-center justify-between px-4 py-3 border-b">
                <div className="flex items-center min-w-0">
                  {selectedConversation && (
                    <img
                      src={selectedConversation?.conversationAvatarUrl || FALLBACK_AVATAR}
                      alt={selectedConversation?.conversationName || 'Chat avatar'}
                      className="w-10 h-10 rounded-full mr-3 object-cover"
                    />
                  )}
                  <h3 className="text-lg font-semibold truncate">
                    {selectedConversation?.conversationName || 'Choose conversation'}
                  </h3>
                </div>
              </div>

              {/* Messages list */}
              <div ref={listRef} className="flex-1 overflow-y-auto px-4 py-3 space-y-3">
                {!selectedConversation ? (
                  <div className="h-full flex flex-col items-center justify-center text-gray-400 select-none">
                    <div className="text-2xl font-semibold mb-2">Choose conversation</div>
                    <div className="text-sm">Hãy chọn một cuộc trò chuyện ở sidebar để bắt đầu.</div>
                  </div>
                ) : loadingMessages ? (
                  <div className="text-center text-gray-400">Đang tải tin nhắn...</div>
                ) : (
                  messages.map(m => {
                    const isMe = !!m.me;
                    return (
                      <div
                        key={m.id}
                        className={`flex items-end ${isMe ? 'justify-end' : 'justify-start'}`}
                      >
                        {/* Avatar bên trái nếu người khác, bên phải nếu mình */}
                        {!isMe && (
                          <img
                            src={m.sender?.avatarUrl || FALLBACK_AVATAR}
                            alt={m.sender?.fullName || 'avatar'}
                            className="w-8 h-8 rounded-full mr-2 object-cover"
                          />
                        )}

                        <div className={`max-w-[70%]`}>
                          <div
                            className={`px-3 py-2 rounded-lg break-words ${
                              isMe
                                ? 'bg-green-500 text-white rounded-br-none'
                                : 'bg-orange-400 text-white rounded-bl-none'
                            }`}
                          >
                            <div className="whitespace-pre-wrap">{m.message}</div>
                          </div>
                          <div className={`text-xs mt-1 text-gray-500 ${isMe ? 'text-right' : 'text-left'}`}>
                            {fmtTime(m.createdAt)}
                          </div>
                        </div>

                        {isMe && (
                          <img
                            src={m.sender?.avatarUrl || FALLBACK_AVATAR}
                            alt={m.sender?.fullName || 'avatar'}
                            className="w-8 h-8 rounded-full ml-2 object-cover"
                          />
                        )}
                      </div>
                    );
                  })
                )}
                <div ref={bottomRef} />
              </div>

              {/* Composer (khung đỏ) */}
              {selectedConversation && (
                <div className="border-t px-3 py-2">
                  <div className="flex gap-2">
                    <textarea
                      className="flex-1 resize-none rounded-md border border-black px-3 py-2"
                      rows={2}
                      placeholder="Nhập tin nhắn... (Enter để gửi, Shift+Enter xuống dòng)"
                      value={inputMessage}
                      onChange={e => setInputMessage(e.target.value)}
                      onKeyDown={onKeyDown}
                      disabled={sending}
                    />
                    <button
                      className={`min-w-[80px] rounded-md px-4 py-2 font-medium ${
                        inputMessage.trim() && !sending
                          ? 'bg-black hover:bg-black/50 text-white'
                          : 'bg-black/50 cursor-not-allowed text-black'
                      }`}
                      onClick={handleSendMessage}
                      disabled={!inputMessage.trim() || sending}
                    >
                      {sending ? 'Đang gửi...' : 'Gửi'}
                    </button>
                  </div>
                </div>
              )}
            </section>
          </div>

          {/* Modal Search Bạn Bè */}
          {isModalOpen && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-start justify-center z-50">
              <div className="bg-white rounded-lg w-11/12 max-w-md mt-20 shadow-lg">
                {/* Header */}
                <div className="flex items-center justify-between px-4 py-2 border-b">
                  <h3 className="text-lg font-medium">Tìm bạn bè</h3>
                  <button onClick={() => setIsModalOpen(false)} aria-label="Close">✕</button>
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
                        handleCreateConversation(user.userId); // hoặc user.id nếu BE yêu cầu
                        setIsModalOpen(false);
                        setFullName('');
                        setSearchResults([]);
                      }}
                    >
                      <img
                        src={user.avatarUrl || FALLBACK_AVATAR}
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
