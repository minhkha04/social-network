# File Service

The **File Service** handles media uploads for the social‑network platform.  
It stores files in **Cloudinary** and returns accessible URLs.

---

## 🚀 Technologies

- **Spring Boot & WebFlux** – reactive API for handling uploads.
- **MongoDB** – stores metadata about uploaded files.
- **Cloudinary Java SDK** – uploads files to the Cloudinary cloud.

---

## ⚙️ Configuration

- Configured via `application.yaml`  
- Runs on **port `8084`** with context path: `/file`

Required environment variables:

| Variable                  | Description                           |
|----------------------------|---------------------------------------|
| `MONGODB_URI`             | Connection string for MongoDB         |
| `CLOUDINARY_API_KEY`      | Cloudinary API key                    |
| `CLOUDINARY_API_SECRET`   | Cloudinary API secret                 |
| `CLOUDINARY_CLOUD_NAME`   | Cloudinary cloud name                 |

---

## 📡 API Endpoints

This service exposes an **internal API** used by other services:

| Method | Path                     | Description                                                                  |
|--------|--------------------------|------------------------------------------------------------------------------|
| POST   | `/internal/upload/image` | Upload an image file (`multipart/form-data`) and return its Cloudinary URL.  |

---

## 🛠 Running Locally

1. Provide environment variables:

   ```bash
   export MONGODB_URI=mongodb://localhost:27017/filedb
   export CLOUDINARY_API_KEY=your_key
   export CLOUDINARY_API_SECRET=your_secret
   export CLOUDINARY_CLOUD_NAME=your_cloud
   ```

2. Build and run:

   ```bash
   cd file-service
   mvn clean package
   mvn spring-boot:run
   ```

3. The service will start at:  
   👉 [http://localhost:8084/file](http://localhost:8084/file)

---
