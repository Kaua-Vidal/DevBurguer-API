# 🍔 DevBurguer Front-end

Web interface of the **DevBurguer** system, developed with **ReactJS**, responsible for the entire user experience and communication with the API.

The project includes both the customer area and the administrative dashboard.

---

## 🚀 Features

- User authentication (Login / Register)  
- Category-based menu navigation  
- Shopping cart and checkout flow  
- Admin dashboard  
- Product, category and order management  
- Image upload and display  
- Full integration with the REST API  

---

## 🛠️ Technologies

| Category | Tools |
|---------|-------|
| Framework | ReactJS |
| Routing | React Router DOM |
| Styling | Styled-components |
| HTTP Client | Axios |
| State Management | Context API |
| Authentication | JWT |
| Build Tool | Vite |

---

## ⚙️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/your-username/devburguer-frontend.git
cd devburguer-frontend
```

### 2. Install dependencies
```
pnpm install
# or
npm install
# or
yarn install
```

### 3. Environment variables

Create a .env file:
```VITE_API_URL=http://localhost:3000```

### 4. Run the application

```
pnpm run dev
# or
npm run dev
```

### 🧩 Project Structure

```
src/
├── assets/
├── components/
├── pages/
├── routes/
├── services/
├── contexts/
├── styles/
└── main.jsx
```

### 🔗 API Integration
This front-end consumes the DevBurguer API, responsible for:

- Authentication
- Products
- Categories
- Orders
- Image uploads

### 🧾 License

ISC License

### 👨‍💻 Author

Kauã Vidal
Built with ❤️ and ReactJS
