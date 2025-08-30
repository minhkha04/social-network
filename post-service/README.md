# Post Service

The **Post Service** manages user-generated posts and provides APIs for creating and retrieving posts.  
It supports both **user-scoped** and **public queries** within the social-network backend.

---

## 🛠 Technologies

- **Spring Boot + Spring Security** → REST API + secured endpoints  
- **Spring Data MongoDB** → persistence for post documents  
- **OpenFeign** → inter-service HTTP communication (e.g., verifying user identities)  

---

## ⚙️ Configuration

The service runs on **port 8083** with base context path: `/post`.

Environment variables:

| Variable       | Description                       |
|----------------|-----------------------------------|
| `MONGODB_URI`  | MongoDB connection string         |

---

## 📡 API Endpoints

| Method & Path            | Description                                                                 |
|---------------------------|-----------------------------------------------------------------------------|
| **POST** `/create`        | Create a new post. Body contains post content and optional media.           |
| **GET** `/my-post`        | Retrieve posts created by the authenticated user. Supports pagination (`page`, `size`). |
| **GET** `/public`         | List all public posts with pagination. Implemented in `PublicPostController` under `/public`. |

---

## ▶️ Running Locally

1. Set MongoDB connection string:

```env
MONGODB_URI=mongodb://localhost:27017/social_network_post
