# Identity Service

The **Identity Service** is responsible for **authentication, registration, and token management** across the social-network platform.

---

## 🛠 Technologies

- **Spring Boot & Spring Security** → REST API + JWT-based security  
- **Spring Data JPA + MySQL** → persistence layer for user accounts  
- **Kafka** → produces events (e.g., sending OTP codes)  

---

## ⚙️ Configuration

The service is configured via `application.yaml`.  
It runs on **port 8080** with the base context path: `/identity`.

Environment variables required:

| Variable                | Description                     |
|--------------------------|---------------------------------|
| `DB_URL`                 | MySQL JDBC connection string   |
| `DB_USERNAME`            | MySQL username                 |
| `DB_PASSWORD`            | MySQL password                 |
| `JWT_SECRET`             | Secret key for signing tokens  |
| `KAFKA_BOOTSTRAP_SERVERS`| Kafka broker address           |

---

## 📡 API Endpoints

Implemented by **AuthController**:

| Method & Path          | Description                                                                 |
|-------------------------|-----------------------------------------------------------------------------|
| **POST** `/login`       | Authenticate user. Accepts `provider` param (`local`, `google`, etc.) and request body with credentials. |
| **POST** `/register`    | Register a new account. Payload: `UserCreateRequest`.                      |
| **POST** `/introspect`  | Validate a JWT token and return its metadata.                              |
| **POST** `/send-otp`    | Send OTP code for login or password reset. Requires `type` query parameter. |
| **PUT** `/reset-password` | Reset password using OTP + new password.                                 |

---

## ▶️ Running Locally

1. Provide required environment variables:  

```env
DB_URL=jdbc:mysql://localhost:3306/social_network_identity
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SECRET=your_jwt_secret
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
