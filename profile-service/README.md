# Profile Service

The **Profile Service** stores and manages user profiles, including personal information and avatar images.  
It offers both **internal APIs** for other services and **public APIs** for end users.

---

## 🚀 Technologies

- **Spring Boot & Spring Security** – builds secure REST endpoints.
- **Spring Data Neo4j** – uses a Neo4j graph database to model profiles and their relationships.
- **OpenFeign** – communicates with other microservices.

---

## ⚙️ Configuration

- Runs on **port `8081`** with context path: `/profile`
- Requires the following environment variables:

| Variable         | Description                                  |
|------------------|----------------------------------------------|
| `NEO4J_URI`      | Neo4j connection URI                        |
| `NEO4J_USERNAME` | Neo4j username                              |
| `NEO4J_PASSWORD` | Neo4j password                              |
| `MAX_FILE_SIZE`  | Max file upload size (e.g., for avatar images) |
| `MAX_REQUEST_SIZE` | Max request size for multipart uploads     |

---

## 📡 API Endpoints

### 🔒 Internal Endpoints

| Method | Path                        | Description                                      |
|--------|-----------------------------|--------------------------------------------------|
| POST   | `/internal`                 | Create a new user profile when a user registers. |
| GET    | `/internal/users/{userId}`  | Retrieve a profile by user ID (used by services). |

### 🌍 Public Endpoints

| Method | Path                                | Description                                   |
|--------|-------------------------------------|-----------------------------------------------|
| GET    | `/`                                 | Get the profile of the currently authenticated user. |
| DELETE | `/{userId}`                         | Delete a user profile by ID.                  |
| PUT    | `/{userId}`                         | Update a profile (typically by an admin).     |
| PUT    | `/avatar`                           | Upload/update the user’s avatar image (`multipart/form-data`). |
| GET    | `/find-by-full-name?fullName=`      | Search profiles by full name.                 |

---

## 🛠 Running Locally

1. Start a Neo4j instance and provide environment variables:

   ```bash
   export NEO4J_URI=bolt://localhost:7687
   export NEO4J_USERNAME=neo4j
   export NEO4J_PASSWORD=your_password
   export MAX_FILE_SIZE=10MB
   export MAX_REQUEST_SIZE=10MB
   ```

2. Build and run:

   ```bash
   cd profile-service
   mvn clean package
   mvn spring-boot:run
   ```

3. The service will start at:  
   👉 [http://localhost:8081/profile](http://localhost:8081/profile)

---
