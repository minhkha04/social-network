# Notification Service

The **Notification Service** handles asynchronous notifications triggered by events in the platform.  
It listens for **Kafka events** and stores notification records.

---

## 🚀 Technologies

- **Spring Boot & WebFlux** – provides reactive event handling.
- **Spring Data MongoDB** – persists notifications to MongoDB.
- **Apache Kafka** – consumes notification events from the `notification-delivery` topic.
- **Resend (Email API)** – integrated for sending emails (currently disabled/commented).

---

## ⚙️ Configuration

- Runs on **port `8082`** with base path: `/notification`
- Key properties configured in `application.yaml`

Environment variables:

| Variable                 | Description                    |
|---------------------------|--------------------------------|
| `MONGODB_URI`            | MongoDB connection             |
| `KAFKA_BOOTSTRAP_SERVERS`| Kafka cluster address          |
| `KAFKA_GROUP_ID`         | Consumer group name            |

---

## ✨ Features

- **Kafka Consumer** – Listens for messages on the `notification-delivery` topic and processes them via `@KafkaListener`.
- **Email Sending** – Code for sending emails is included but commented out in `EmailController`.  
  Can be enabled by configuring **Resend API credentials**.

---

## 📡 API Endpoints

- 🚫 No public HTTP endpoints are currently defined.  
  The service operates **solely via Kafka consumers**.

---

## 🛠 Running Locally

1. Start MongoDB and Kafka.  
2. Set environment variables:

   ```bash
   export MONGODB_URI=mongodb://localhost:27017/notificationdb
   export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
   export KAFKA_GROUP_ID=notification-group
   ```

3. Build and run:

   ```bash
   cd notification-service
   mvn clean package
   mvn spring-boot:run
   ```

4. The service will start at:  
   👉 [http://localhost:8082/notification](http://localhost:8082/notification)

---
