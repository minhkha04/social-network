# Social Network Microservices

This repository contains a micro‑services‑based social networking platform built using **Java/Spring Boot** for the backend and **React/Vite** for the frontend.  
The system is organized into multiple services that can be developed, tested, and scaled independently.  
An **API Gateway** provides a single entry point for clients and performs routing, authentication, and cross‑cutting concerns.

---

## 🚀 Architecture

The platform is composed of the following services:

| Service             | Description                                                                 | Default Port |
|---------------------|-----------------------------------------------------------------------------|--------------|
| **API Gateway**     | Spring Cloud Gateway exposing a unified `/api/v1` prefix and routing rules. | `8888`       |
| **Identity Service**| Authentication, registration, JWT token introspection (MySQL + Spring Sec). | `8080` (`/identity`) |
| **Profile Service** | User profiles & avatars (Neo4j + Feign client).                             | `8081` (`/profile`) |
| **Notification Service** | Listens to Kafka events, stores notifications in MongoDB.             | `8082` (`/notification`) |
| **Post Service**    | Manages posts (MongoDB + OpenFeign).                                        | `8083` (`/post`) |
| **File Service**    | File uploads using MongoDB + Cloudinary.                                    | `8084` (`/file`) |
| **Chat Service**    | Real‑time chat using MongoDB + Netty Socket.IO.                             | `8085` (`/chat`) |
| **Web App**         | React 19 + Vite frontend with Redux, Material‑UI, Ant Design.               | `5173` (Vite dev server) |

Each service is a standalone Maven project and can run independently.

---

## ✨ Features

- **User Authentication & Registration**: login, register, JWT, OTP, password reset.  
- **Post Management**: create, fetch, and list posts with pagination.  
- **Profile Management**: view, update, delete profiles; avatar uploads.  
- **File Uploads**: internal endpoint for uploading images to Cloudinary.  
- **Chat Service**: create conversations, send/receive messages via WebSockets.  
- **Notifications**: Kafka consumer for notifications, email sending scaffolded.  
- **API Gateway**: unified `/api/v1` endpoint routing.  

---

## ⚙️ Running Locally

### Prerequisites
- Java 24 & Maven 3.x  
- Node.js (for frontend)  
- Databases: **MySQL**, **MongoDB**, **Neo4j**  
- **Kafka** (notifications & producers)  
- **Cloudinary** account for file uploads  

### Setup & Run

1. Clone repository and navigate to a service, e.g.:

```bash
cd identity-service
mvn clean package
mvn spring-boot:run
```

2. Repeat for each service (`post-service`, `profile-service`, `file-service`, `chat-service`, `notification-service`).  
   Each service runs on its configured port.

3. Run API Gateway:

```bash
cd api-gateway
mvn spring-boot:run
```

   Configure environment variables:
   ```env
   URI_IDENTITY=http://localhost:8080/identity
   URI_PROFILE=http://localhost:8081/profile
   URI_NOTIFICATION=http://localhost:8082/notification
   URI_POST=http://localhost:8083/post
   URI_FILE=http://localhost:8084/file
   URI_CHAT=http://localhost:8085/chat
   ```

4. Run Web App:

```bash
cd web-app
npm install
npm run dev
```

Frontend connects to API Gateway for backend operations.

---

## 🛠 Development Notes

- All services are **OAuth2 resource servers** → JWT required in `Authorization` header.  
- **Feign Clients** used for inter‑service HTTP communication.  
- **Kafka** used for asynchronous messaging.  
- **Database per service**:  
  - Identity → MySQL  
  - Post, Chat, Notification, File → MongoDB  
  - Profile → Neo4j  
- **Cloudinary credentials** required for image uploads.

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome.  
Please open an **issue** or **pull request** to suggest improvements or features.  
Each microservice is self‑contained and uses **Maven** for dependency management.
