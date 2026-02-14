"# IT342_G5_Sabornido_Lab1 - Authentication System

A full-stack authentication system with user registration, login, session management, and protected dashboard access. Implemented across three platforms: backend, web frontend, and native mobile app.

---

## 📋 Project Description

This project demonstrates a complete authentication flow across multiple platforms:
- **User Registration**: Create new accounts with validation
- **User Login**: Authenticate with JWT tokens
- **Dashboard**: View authenticated user profile and session data
- **Logout**: Clear session and return to login
- **Protected Routes**: Prevent unauthorized access to protected pages

The system uses **JWT (JSON Web Tokens)** for stateless authentication, **Session Management** for maintaining client-side auth state, and **Input Validation** for security.

---

## 🛠 Technologies Used

### Backend
- **Framework**: Spring Boot 2.7.18
- **Language**: Java 11
- **Database**: MySQL 8.0
- **Security**: Spring Security, JWT (jjwt 0.11.5)
- **Build Tool**: Maven
- **API**: RESTful HTTP endpoints

### Web Frontend
- **Framework**: React 19.2.0
- **Routing**: React Router DOM 7.13.0
- **Build Tool**: Vite
- **Package Manager**: npm

### Mobile App
- **Platform**: Androidv

---

## 🚀 Setup Instructions

### Prerequisites
- **Backend**: Java 11+, Maven 3.6+, MySQL 8.0
- **Web**: Node.js 16+, npm 7+
- **Mobile**: Android Studio, SDK 21+, Gradle 8.4

---

### 1. Backend Setup

#### Step 1: Start MySQL Database
```bash
# Windows (if MySQL is installed locally)
mysql -u root -p
# Create database
CREATE DATABASE authdb;
EXIT;

# OR using service (if configured)
services.msc  # Start MySQL service
```

#### Step 2: Configure Backend
```bash
cd backend/backend

# The application.properties is pre-configured for:
# - Database: authdb on localhost:3306
# - Username: root (no password)
# - Port: 8082
# - Update these values if needed in src/main/resources/application.properties
```

#### Step 3: Build and Run Backend
```bash
# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Alternative: Run the JAR directly
java -jar target/backend-0.0.1-SNAPSHOT.jar

# Backend will start on: http://localhost:8082
```

#### Verification
```bash
# Test backend health
curl http://localhost:8082/api/auth/health

# You should see the API is running
```

---

### 2. Web Frontend Setup

#### Step 1: Install Dependencies
```bash
cd web
npm install
```

#### Step 2: Configure API Endpoint
```bash
# The web app is configured to connect to backend at http://localhost:8082
# Update src/api/authApi.js if backend runs on a different port

# Current API base URL:
const API_BASE_URL = 'http://localhost:8082/api';
```

#### Step 3: Run Development Server
```bash
# Start development server
npm run dev

# Web app will start on: http://localhost:5173 (or shown in terminal)
```

#### Step 4: Build for Production
```bash
# Create optimized build
npm run build

# Preview production build
npm run preview
```

#### Available Scripts
```bash
npm run dev      # Start dev server
npm run build    # Build for production
npm run lint     # Check code quality
npm run preview  # Preview production build
```

---

### 3. Mobile App Setup

#### Step 1: Open Project in Android Studio
```bash
# Open the mobile folder in Android Studio
# File → Open → Select mobile/ directory
```

#### Step 2: Configure API Endpoint
```bash
# Edit: mobile/app/src/main/java/com/example/mobile/api/ApiClient.kt
# Update BASE_URL if backend runs on different host/port

// Current configuration:
const val BASE_URL = "http://10.0.2.2:8082/api"  // 10.0.2.2 for emulator localhost
```

#### Step 3: Build and Run
```bash
# Option 1: Using Android Studio
1. Click "Run" → "Run 'app'" or press Shift+F10
2. Select emulator or connected device
3. App will install and launch

# Option 2: Using Gradle command line
cd mobile
./gradlew build           # Build APK
./gradlew installDebug    # Install on emulator/device
```

#### Step 4: First Run
1. App launches on MainActivity
2. Click "Log In" to navigate to login screen
3. Or click "Register" to create a new account
4. After login, Dashboard displays user profile

---

## 📡 API Endpoints

### Base URL
```
http://localhost:8082/api
```

### Authentication Endpoints

#### 1. User Registration
```
POST /auth/register
Content-Type: application/json

Request Body:
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123"
}

Response (200 OK):
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2026-02-14T10:30:00Z",
  "roles": ["ROLE_USER"]
}

Error Responses:
- 409 Conflict: Username or email already exists
- 400 Bad Request: Validation failed
```

#### 2. User Login
```
POST /auth/login
Content-Type: application/json

Request Body:
{
  "username": "john_doe",
  "password": "SecurePassword123"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "createdAt": "2026-02-14T10:30:00Z",
    "roles": ["ROLE_USER"]
  }
}

Error Responses:
- 401 Unauthorized: Invalid credentials
- 400 Bad Request: Missing fields
```

#### 3. User Logout
```
POST /auth/logout
Authorization: Bearer {token}

Response (200 OK):
{
  "message": "Logout successful"
}
```

#### 4. Get Current User Profile
```
GET /user/me
Authorization: Bearer {token}

Response (200 OK):
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2026-02-14T10:30:00Z",
  "roles": ["ROLE_USER"]
}

Error Response:
- 401 Unauthorized: Missing or invalid token
```

#### 5. Get All Users (Admin only)
```
GET /user/all
Authorization: Bearer {token}

Response (200 OK):
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "createdAt": "2026-02-14T10:30:00Z",
    "roles": ["ROLE_USER"]
  },
  ...
]

Error Response:
- 403 Forbidden: Insufficient permissions
- 401 Unauthorized: Not authenticated
```

---

## 🔐 Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Storage**: Bcrypt hashing for secure password storage
- **Input Validation**: Server-side validation on all inputs
- **CORS Configuration**: Configured for frontend/mobile clients
- **Protected Routes**: Backend validates tokens on protected endpoints
- **Session Management**: Client-side session storage with token persistence
- **Token Expiration**: 24-hour token expiry (configurable in application.properties)

---

## 📝 Project Structure

```
IT342_G5_Sabornido_Lab1/
├── backend/               # Spring Boot backend
│   └── backend/
│       ├── src/main
│       │   ├── java/com/backend/
│       │   │   ├── controller/     # REST controllers
│       │   │   ├── service/        # Business logic
│       │   │   ├── model/          # Entity models
│       │   │   ├── security/       # JWT & Security config
│       │   │   └── repository/     # Database repositories
│       │   └── resources/
│       │       └── application.properties
│       └── pom.xml
├── web/                  # React Vite frontend
│   ├── src/
│   │   ├── pages/       # Page components
│   │   ├── components/  # Reusable components
│   │   ├── context/     # Auth context
│   │   ├── api/         # API client
│   │   └── App.jsx
│   └── package.json
├── mobile/              # Android Kotlin mobile app
│   ├── app/
│   │   ├── src/main
│   │   │   ├── java/com/example/mobile/
│   │   │   │   ├── *Activity.kt      # Screen components
│   │   │   │   ├── api/              # Retrofit API client
│   │   │   │   └── session/          # Session management
│   │   │   └── res/                  # Resources (layouts, drawables, values)
│   │   └── build.gradle.kts
│   ├── settings.gradle.kts
│   └── build.gradle.kts
├── README.md            # This file
└── TASK_CHECKLIST.md    # Project progress
```

---

## 🧪 Testing the System

### Test Flow (Manual)

1. **Register a New User**
   - Web: Navigate to Register page, fill form, submit
   - Mobile: Open app, tap Register, fill form, submit
   - Backend: POST to `/api/auth/register`

2. **Login with Credentials**
   - Web/Mobile: Enter username and password on login screen
   - Backend: POST to `/api/auth/login`, receive JWT token

3. **Access Dashboard**
   - Web/Mobile: After login, view user profile on dashboard
   - Backend: GET `/api/user/me` with Authorization header

4. **Logout**
   - Web/Mobile: Click logout button
   - Backend: POST to `/api/auth/logout`, session cleared

---

## 📚 Additional Resources

- Backend API Documentation: See [HELP.md](backend/backend/HELP.md)
- Task Checklist: See [TASK_CHECKLIST.md](TASK_CHECKLIST.md)
- Mobile Setup Guide: See [mobile/BACKEND_VERIFICATION.md](mobile/BACKEND_VERIFICATION.md)

---

## 👤 Author
**Xavier John A. Sabornido**

---

## 📅 Last Updated
February 14, 2026
" 
