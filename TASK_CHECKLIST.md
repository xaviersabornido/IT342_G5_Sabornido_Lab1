# 🧾 TASK CHECKLIST

## Objective
Design the system flow for User Registration, Login, and Logout. This activity focuses only on documentation and diagrams. Coding will be done next week during the face-to-face session.

## Scenario
Design a system where:
- A user can register an account
- A user can log in
- A logged-in user can view their profile/dashboard
- A user can log out
- Protected pages cannot be accessed when logged out

---

## Checklist (Status sections)

### TODO
- [ ] Create visual flow diagrams for documentation (User Registration, Login, Dashboard, Logout)
- [ ] Create sequence diagrams for auth flows
- [ ] Create wireframes/UI mockups for all pages
- [ ] Scan project for CVEs
- [ ] End-to-end testing (mobile app with backend)
- [ ] Performance testing and optimization
- [ ] Accessibility testing (mobile app)

### IN-PROGRESS
- [ ] Scan project for CVEs

### DONE
 - **commit remaining files (backend & web)**: commit `9ee1032`
 - **backend (auth & security)**: added `SecurityConfig.java`, `AuthController.java`, `UserController.java`, `AuthService.java`, `User.java`, `JwtTokenProvider.java`, `JwtAuthenticationFilter.java`, `CustomUserDetailsService.java` — commit `9ee1032`
 - **frontend (auth UI & wiring)**: added `web/src/pages/Login.jsx`, `web/src/pages/Register.jsx`, `web/src/pages/Home.jsx`, `web/src/pages/Dashboard.jsx`, `web/src/components/ProtectedRoute.jsx`, `web/src/context/AuthContext.jsx`, `web/src/api/authApi.js` — commit `9ee1032`
 
 - **backend: DTO, entity, repository, service changes committed** — commit `88a47cc`
 - **frontend: Home page files removed** — commit `ea104cf`
 - **frontend: attempted commit of web app changes and new asset (pending error resolution)**

 - **frontend: rebuilt Login, Register, and Dashboard pages to final format**

 - **feat(mobile): Add LoginActivity with API integration and form validation** — commit `54e0780`
   - Implemented login form with username/password inputs
   - Added form validation (empty field checks)
   - Integrated with API client for authentication
   - Session token management and persistence
   - Error handling with user feedback
   - Navigation to RegisterActivity for new users
   
 - **feat(mobile): Add RegisterActivity with registration validation and user creation** — commit `250ef90`
   - Implemented registration form with username, email, password, confirm password
   - Form validation (empty fields, minimum lengths, password match)
   - API integration for user registration
   - Error parsing and display to user
   - Navigation back to LoginActivity after success
   
 - **feat(mobile): Add DashboardActivity with user profile display and session management** — commit `da4f455`
   - Dashboard view showing user profile information
   - Session validation on activity load
   - User profile data display (username, email, account creation date, roles)
   - Logout functionality with API call and session cleanup
   - Automatic redirect to MainActivity if not logged in
   
 - **build(mobile): Add gradle build configuration and wrapper** — commit `ea296ae`
   - Root-level build.gradle.kts with plugin dependencies
   - Gradle wrapper for consistent builds
   - settings.gradle.kts with module configuration
   - gradle.properties for build settings
   
 - **build(mobile-app): Add app gradle build configuration** — commit `11eebe1`
   - App-level build.gradle.kts with Android SDK, dependencies, and build variants
   - ProGuard rules for code obfuscation and optimization
   
 - **feat(mobile): Add MainActivity and MobileApp application class** — commit `35aae90`
   - Main entry point with navigation flow (Login → RegisterActivity or Dashboard)
   - Session check on app launch
   - MobileApp application class for global app configuration
   
 - **feat(mobile): Add API client module for backend communication** — commit `3ecdcd4`
   - ApiClient with Retrofit configuration
   - AuthApi interface for login and register endpoints
   - Request models: LoginRequest, RegisterRequest
   - Response models: AuthResponse, UserResponse
   - Logout endpoint support
   
 - **feat(mobile): Add session management module for authentication state** — commit `e42df2a`
   - SessionManager for token persistence using SharedPreferences
   - Methods to save/retrieve token and user data
   - Session validation and logout cleanup
   
 - **chore(mobile): Add Android application manifest** — commit `cd58f00`
   - Declared all activities (MainActivity, LoginActivity, RegisterActivity, DashboardActivity)
   - Network permissions and internet access
   - Application theme and launcher configuration
   
 - **feat(mobile): Add drawable resources and design components** — commit `7caf445`
   - Background shapes for auth cards, input fields, buttons
   - Error state styling
   - User badge and profile card backgrounds
   - App launcher icons (foreground and background)
   
 - **feat(mobile): Add MainActivity layout with navigation flow** — commit `653c94a`
   - ScrollView-based layout for intro/landing page
   - Navigation buttons to Login and Register
   
 - **feat(mobile): Add app launcher icons for various screen densities** — commit `bc4ee4c`
   - Icon assets for hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi
   - Adaptive icon support for Android 8.0+
   
 - **feat(mobile): Add color schemes and string resources** — commit `d924d31`
   - Color definitions for light and dark themes
   - String resources for UI labels and messages
   - Theme definitions (light and night modes)
   
 - **feat(mobile): Add XML resource files for backup configuration** — commit `b201574`
   - Backup rules for cloud backup
   - Data extraction rules for Android auto-backup
   
 - **test(mobile): Add unit and instrumented tests** — commit `5fb654f`
   - Example unit test template
   - Example instrumented (Android) test template
   
 - **chore(mobile): Add .gitignore files** — commit `1f6ecc4`
   - Mobile and app-level .gitignore for gradle build artifacts
   
 - **docs(mobile): Add backend verification documentation** — commit `2db746f`
   - Backend verification checklist and API endpoint testing guide


## Acceptance Criteria / Definition of Done


_Last updated: 2026-03-06 — Xavier John A. Sabornido
