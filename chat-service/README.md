# Chat Service

The **Chat Service** provides real‑time messaging capabilities, allowing users to create conversations and exchange messages over **WebSocket**.

---

## 🚀 Technologies

- **Spring Boot & WebFlux** – builds reactive REST endpoints.
- **Spring Data MongoDB** – stores conversations and messages.
- **Netty Socket.IO** – implements the Socket.IO protocol for WebSocket communication.

---

## ⚙️ Configuration

- Runs on **port `8085`** with context path: `/chat`
- Required environment variables:

| Variable                 | Description                                    |
|---------------------------|------------------------------------------------|
| `MONGODB_URI`            | MongoDB connection string                      |
| `JWT_SECRET`             | Secret for validating tokens passed via WebSocket |
| `IDENTITY_SERVICE_URL`   | Base URL for Identity Service                  |
| `POST_SERVICE_URL`       | Base URL for Post Service                      |
| ...                      | Other service URLs as needed                   |

---

## 📡 API Endpoints

| Method | Path               | Description                                                                 |
|--------|--------------------|-----------------------------------------------------------------------------|
| POST   | `/create`          | Create a new conversation between one or more users.                        |
| GET    | `/my-conversations`| Retrieve all conversations belonging to the current user.                   |
| POST   | `/message/create`  | Send a new chat message within a conversation.                              |
| GET    | `/message`         | Get messages for a specific conversation (requires `conversationId` query param). |

---

## 🔌 WebSocket Interaction

Clients connect to the **Socket.IO endpoint**:  

```
ws://localhost:8085/chat/socket.io/?token=...
```

- The `SocketHandler` authenticates the token.  
- Tracks session state.  
- Cleans up on disconnect.  

---

## 🛠 Running Locally

1. Start MongoDB and set environment variables:

   ```bash
   export MONGODB_URI=mongodb://localhost:27017/chatdb
   export JWT_SECRET=your_secret
   export IDENTITY_SERVICE_URL=http://localhost:8080/identity
   export POST_SERVICE_URL=http://localhost:8083/post
   ```

2. Build and run:

   ```bash
   cd chat-service
   mvn clean package
   mvn spring-boot:run
   ```

3. The service will start at:  
   👉 [http://localhost:8085/chat](http://localhost:8085/chat)

---
