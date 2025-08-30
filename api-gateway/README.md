# API Gateway

The **API Gateway** acts as the entry point for client requests.  
It uses **Spring Cloud Gateway** to route requests to individual microservices and applies common security policies.

---

## 🚀 Technologies

- **Spring Cloud Gateway** – provides routing, path rewriting, and filters.
- **Spring Security** – validates JWT tokens via a global `AuthenticationFilter`.

---

## ⚙️ Configuration

- Runs on **port `8888`**
- Routing rules defined in `application.yaml`
  - Each route prefixes `/api/v1`
  - Requests are forwarded to their corresponding service (Identity, Profile, Notification, Post, File, Chat)
- Environment variables define destination URLs:

| Variable         | Description                        |
|------------------|------------------------------------|
| `URI_IDENTITY`   | Base URL of Identity Service       |
| `URI_PROFILE`    | Base URL of Profile Service        |
| `URI_NOTIFICATION` | Base URL of Notification Service |
| `URI_POST`       | Base URL of Post Service           |
| `URI_FILE`       | Base URL of File Service           |
| `URI_CHAT`       | Base URL of Chat Service           |

---

## ✨ Key Features

- **Path Rewriting** – Strips `/api/v1` from incoming requests and forwards to the downstream service.  
- **Authentication Filter** – Custom filter validates JWT tokens on protected routes before forwarding.  
- **Centralized Configuration** – Simplifies client interaction by providing a single host and port.  

---

## 🛠 Running Locally

1. Ensure all downstream services are running and note their base URLs.  
2. Set environment variables:

   ```bash
   export URI_IDENTITY=http://localhost:8080/identity
   export URI_PROFILE=http://localhost:8081/profile
   export URI_NOTIFICATION=http://localhost:8082/notification
   export URI_POST=http://localhost:8083/post
   export URI_FILE=http://localhost:8084/file
   export URI_CHAT=http://localhost:8085/chat
   ```

3. Build and run:

   ```bash
   cd api-gateway
   mvn clean package
   mvn spring-boot:run
   ```

4. The gateway will start at:  
   👉 [http://localhost:8888/api/v1](http://localhost:8888/api/v1)

---
