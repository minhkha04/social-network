# Web Application

The **Web Application** is the front‑end client for the social‑network platform.  
It is built with **React** and uses several UI and state‑management libraries to provide a responsive single‑page application.

---

## 🚀 Technologies

- **React 19 + Vite** – modern JavaScript framework and build tool for rapid development.
- **Material‑UI v5** and **Ant Design** – comprehensive UI component libraries.
- **Redux Toolkit & RTK Query** – manage application state and API calls.
- **socket.io‑client** – real‑time messaging integration.
- Additional libraries: **Formik**, **Day.js**, **React Query**, **Cloudinary integration**.

---

## ⚙️ Setup

1. Install dependencies:

   ```bash
   cd web-app
   npm install
   ```

2. Create a `.env` file with the following variables:

   ```env
   VITE_API_URL=http://localhost:8888/api/v1
   ```

3. Start the development server:

   ```bash
   npm run dev
   ```

   The app will be available at:  
   👉 [http://localhost:5173](http://localhost:5173) (default)

---

## 📦 Building for Production

1. Run:

   ```bash
   npm run build
   ```

2. This will generate optimized static assets in the `dist/` directory.  
3. Deploy the contents of `dist/` behind a web server and ensure that API endpoints are correctly configured.

---

