# 🍔 DevBurguer API

A RESTful API built with **Node.js** and **Express**, designed to manage users, authentication, and product data for the **DevBurguer** application.  
It includes secure authentication using **JWT**, file uploads with **Multer**, and data validation with **Yup**.

---

## 🚀 Features

- 🔐 **JWT Authentication** — Secure login and token validation  
- 🧂 **Password Hashing** — Using `bcrypt` for user password encryption  
- 🗃️ **Database Integration** — Supports both **MongoDB (Mongoose)** and **PostgreSQL (Sequelize)**  
- 📸 **File Uploads** — Managed through `multer` for images or assets  
- ✅ **Data Validation** — Handled by `yup`  
- 🧰 **UUID** — Unique IDs for entities  
- 🧹 **Biome** — Linting and formatting for a cleaner codebase  

---

## 🏗️ Technologies

| Category | Tools |
|-----------|--------|
| **Runtime** | Node.js |
| **Framework** | Express |
| **Database** | MongoDB (Mongoose) / PostgreSQL (Sequelize) |
| **Authentication** | JSON Web Token (JWT) |
| **Validation** | Yup |
| **File Uploads** | Multer |
| **Security** | bcrypt |
| **Linting** | Biome |
| **Utils** | UUID |

---

## ⚙️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/your-username/devburguer-api.git
cd devburguer-api


### 2. Install dependencies
pnpm install
# or
npm install
# or
yarn install


### 3. Create the environment file
PORT=3000
JWT_SECRET=your_secret_key
MONGO_URI=your_mongodb_connection
POSTGRES_URI=your_postgres_connection

### 4. Run the development server

pnpm run dev
# or
npm run dev

## 🧩 Project Structure

src/
├── config/          # Configuration files (DB, JWT, etc.)
├── controllers/     # Handles the business logic
├── models/          # Sequelize & Mongoose models
├── middlewares/     # Auth & validation middlewares
├── routes/          # API routes
├── services/        # Helper or service functions
└── server.js        # Entry point

## 🧾 License

This project is licensed under the ISC License.
Feel free to use it as a learning resource or base for your own projects.

## 👨‍💻 Author

Kauã Vidal

Built with ❤️ and Node.js