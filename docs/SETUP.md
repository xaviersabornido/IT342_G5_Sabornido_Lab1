# Auth System - Setup & Run Guide

## Prerequisites

- **Java 11** (JDK 11)
- **Node.js** 18+
- **Maven** (or use `./mvnw` wrapper)

## Backend (Spring Boot)

### Run

```bash
cd backend/backend
./mvnw spring-boot:run
```

Backend runs at **http://localhost:8082**

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login (returns JWT) |
| POST | `/api/auth/logout` | Logout |
| GET | `/api/users/me` | Get current user (requires JWT) |

### Database (MySQL)

- Uses **MySQL** database
- Create database `authdb` in MySQL before first run (e.g. `CREATE DATABASE authdb;`)
- Default connection: `localhost:3306`, user `root`, password (empty)
- Edit `application.properties` to change `spring.datasource.username` and `spring.datasource.password` if needed

## Frontend (React + Vite)

### Run

```bash
cd web
npm install
npm run dev
```

Frontend runs at **http://localhost:5173**

### Routes

- `/` - Home (public)
- `/login` - Login (public)
- `/register` - Register (public)
- `/dashboard` - Dashboard (protected, requires login)

## Flow

1. **Register**: Go to /register → Create account → Auto-login → Redirect to Dashboard
2. **Login**: Go to /login → Enter credentials → Redirect to Dashboard
3. **Logout**: Click "Log Out" on Dashboard
4. **Protected pages**: Visiting /dashboard when logged out redirects to /login

## Troubleshooting: Port already in use

If you see `Port XXXX is already in use`:

1. **Find the process** (PowerShell):
   ```powershell
   netstat -ano | findstr :8082
   ```

2. **Stop the process** (replace `PID` with the number from the last column):
   ```powershell
   taskkill /PID PID /F
   ```

3. **Or use a different port**: Edit `backend/backend/src/main/resources/application.properties` and set `server.port=9090`, then update `web/vite.config.js` proxy target to `http://localhost:9090`.
