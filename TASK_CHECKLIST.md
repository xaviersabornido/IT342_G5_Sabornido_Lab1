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
- [ ] Create a detailed User Registration flow diagram (steps, data, validations)
- [ ] Create a Login flow diagram (auth steps, session/token handling, errors)
- [ ] Create Dashboard/Profile access flow (what data is shown, authorized calls)
- [ ] Define access control rules for protected pages (redirects, middleware)
- [ ] Document data model fields required for users (username, email, password, metadata)
- [ ] List error cases and UX for failures (invalid input, duplicate account, expired session)
- [ ] Draft security notes (password hashing, session/tokens, CSRF, input validation)
- [ ] Prepare sequence diagrams (registration, login, logout)
- [ ] Create simple wireframes for registration/login/dashboard pages

### IN-PROGRESS
- [ ] Create TODO/IN-PROGRESS/DONE structure in TASK_CHECKLIST.md (this file)
- [ ] Scan project for CVEs

### DONE
- **start: backend -> frontend progress marker**: commit `5dff73e`
 - **commit remaining files (backend & web)**: commit `9ee1032`
 - **backend (auth & security)**: added `SecurityConfig.java`, `AuthController.java`, `UserController.java`, `AuthService.java`, `User.java`, `JwtTokenProvider.java`, `JwtAuthenticationFilter.java`, `CustomUserDetailsService.java` — commit `9ee1032`
 - **frontend (auth UI & wiring)**: added `web/src/pages/Login.jsx`, `web/src/pages/Register.jsx`, `web/src/pages/Home.jsx`, `web/src/pages/Dashboard.jsx`, `web/src/components/ProtectedRoute.jsx`, `web/src/context/AuthContext.jsx`, `web/src/api/authApi.js` — commit `9ee1032`

---

## Acceptance Criteria / Definition of Done
- The documentation includes: registration, login, dashboard, and logout flow diagrams.
- Sequence diagrams or step-by-step sequences exist for each flow.
- Access control behavior for protected pages is specified.
- Security considerations are documented (password storage, session handling, token expiry).
- The checklist is clear and ready to guide next week's coding session.

---

_Last updated: 2026-02-07 — updated by automated task commit_
