# Identity Service

The **Identity Service** is responsible for **authentication, registration, and token management** across the social-network platform.

---

## 🚀 Technologies

- **Spring Boot & Spring Security** – provides the REST API and JWT-based security.
- **Spring Data JPA + MySQL** – persists user accounts to a relational database configured via environment variables.
- **Kafka** – produces events for sending OTP codes.

---

## ⚙️ Configuration

The service is configured via `application.yaml`.  
It runs on **port `8080`** with the base context path: `/identity`.

Environment variables:

| Variable              | Description                          |
|------------------------|--------------------------------------|
| `DB_URL`              | MySQL connection URL                 |
| `DB_USERNAME`         | MySQL username                       |
| `DB_PASSWORD`         | MySQL password                       |
| `JWT_SECRET`          | Secret key for signing tokens        |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address             |

---

## 📡 API Endpoints

The following REST endpoints are provided by `AuthController`:

| Method | Path             | Description                                                                 |
|--------|------------------|-----------------------------------------------------------------------------|
| POST   | `/login`         | Authenticate a user. Accepts a `provider` param (e.g. `local`, `google`) and request body with credentials. |
| POST   | `/register`      | Register a new account using the `UserCreateRequest` payload.              |
| POST   | `/introspect`    | Validate a JWT access token and return its metadata.                       |
| POST   | `/send-otp`      | Send an OTP code for login or password reset. Requires a `type` query param specifying the template. |
| PUT    | `/reset-password`| Reset a user’s password using an OTP and new password.                     |

---

## 🛠 Running Locally

1. Provide environment variables:

   ```bash
   export DB_URL=jdbc:mysql://localhost:3306/identity
   export DB_USERNAME=root
   export DB_PASSWORD=your_password
   export JWT_SECRET=your_secret
   export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
   ```

2. Build and run the service:

   ```bash
   cd identity-service
   mvn clean package
   mvn spring-boot:run
   ```

3. The service will start at:  
   👉 [http://localhost:8080/identity](http://localhost:8080/identity)

---
