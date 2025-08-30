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

## 🧰 Tech Stack
<p>
  <!-- Backend -->
  <img alt="Java" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" height="40" />
  <img alt="Spring" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" height="40" />
  <img alt="Maven" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/maven/maven-original.svg" height="40" />
  <img alt="Kafka" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/apachekafka/apachekafka-original.svg" height="40" />
  <img alt="Netty" src="https://avatars.githubusercontent.com/u/288455?s=200&v=4" height="40" />
  <img alt="Socket.IO" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/socketio/socketio-original.svg" height="40" />
  
  <!-- Databases -->
  <img alt="MySQL" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original.svg" height="40" />
  <img alt="MongoDB" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mongodb/mongodb-original.svg" height="40" />
  <img alt="Neo4j" src="https://avatars.githubusercontent.com/u/1955060?s=200&v=4" height="40" />
  <img alt="Cloudinary" src="https://res.cloudinary.com/cloudinary/image/upload/v1694700000/marketing/brand/cloudinary_icon_blue.svg" height="40" />
  
  <!-- Frontend -->
  <img alt="React" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/react/react-original.svg" height="40" />
  <img alt="Vite" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/vitejs/vitejs-original.svg" height="40" />
  <img alt="Redux" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redux/redux-original.svg" height="40" />
  <img alt="Material UI" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/materialui/materialui-original.svg" height="40" />
  <img alt="Ant Design" src="https://avatars.githubusercontent.com/u/12101536?s=200&v=4" height="40" />
  
  <!-- DevOps / Others -->
  <img alt="Docker" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original.svg" height="40" />
  <img alt="Git" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/git/git-original.svg" height="40" />
  <img alt="GitHub" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/github/github-original.svg" height="40" />
</p>

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

## 👤 About Me (Giới thiệu)
Hi, I'm **Minh Kha** – a backend-focused developer exploring scalable distributed systems and real-time features.  
Xin chào! Mình là **Minh Kha**, yêu thích kiến trúc microservices, messaging systems (Kafka) và tối ưu hiệu năng.

- 🌐 Portfolio: https://minhkha.techleaf.pro/  
- 💼 GitHub: https://github.com/minhkha04  
- 💬 Facebook: https://www.facebook.com/imMinhKha  
- ✉️ Liên hệ nhanh: mở issue hoặc kết nối qua Facebook.  

If you find this project interesting, feel free to star ⭐ the repo or reach out!

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome.  
Please open an **issue** or **pull request** to suggest improvements or features.  
Each microservice is self‑contained and uses **Maven** for dependency management.