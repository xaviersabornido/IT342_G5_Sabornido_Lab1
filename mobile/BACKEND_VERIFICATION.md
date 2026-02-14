# Mobile app ↔ Backend verification

The mobile app uses the **same backend** as the web app. Reference checklist:

## Backend (Spring Boot)

| Source | Value |
|--------|--------|
| Port | `server.port=8082` in `backend/backend/src/main/resources/application.properties` |
| Base path | `/api` (controllers use `@RequestMapping("/api/auth")` and `@RequestMapping("/api/users")`) |

## API endpoints (must match)

| Action | Backend | Mobile `AuthApi.kt` | Full URL (emulator) |
|--------|---------|--------------------|----------------------|
| Login | `POST /api/auth/login` | `@POST("auth/login")` | `http://10.0.2.2:8082/api/auth/login` |
| Register | `POST /api/auth/register` | `@POST("auth/register")` | `http://10.0.2.2:8082/api/auth/register` |
| Logout | `POST /api/auth/logout` | `@POST("auth/logout")` | `http://10.0.2.2:8082/api/auth/logout` |
| Current user | `GET /api/users/me` | `@GET("users/me")` | `http://10.0.2.2:8082/api/users/me` |

## Request/response bodies (aligned with backend DTOs)

- **Login:** `{ "username", "password" }` → `AuthResponse { token, type, user }`
- **Register:** `{ "username", "email", "password" }` → `AuthResponse { token, type, user }`
- **User:** `UserResponse { id, username, email, createdAt, roles }`

## Mobile base URL

- Set in `app/build.gradle.kts`: `buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8082/api\"")`
- **Emulator:** `10.0.2.2` = your host machine’s localhost.
- **Physical device:** Replace with your PC’s LAN IP, e.g. `"http://192.168.1.5:8082/api"`, then rebuild.

## If login/register still fail

1. Backend must be running (e.g. start Spring Boot; it listens on **8082**).
2. Rebuild the mobile app after any `API_BASE_URL` change.
3. On a **physical device**, use your computer’s IP, not `10.0.2.2`.
4. Device/emulator and PC must be on the same network (for physical device).
