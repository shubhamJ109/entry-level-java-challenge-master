# Submission & Verification Guide

This document outlines the architecture, security configuration, and steps to run/verify the completed REST API endpoints.

---

## 1. Project Architecture

The application has been restructured using a clean, layered Spring Boot architecture:
1. **Controller Layer (`EmployeeController`)**: Exposes REST endpoints, validates payloads, maps responses, and manages status codes (e.g., `201 Created` for POST).
2. **Service Layer (`EmployeeService`, `EmployeeServiceImpl`)**: Handles all data operations. Includes thread-safe in-memory storage (`ConcurrentHashMap`) pre-seeded with mock employees.
3. **Data Model (`EmployeeImpl`, `CreateEmployeeRequest`)**: Concrete implementation of the `Employee` interface using Lombok for clean, boiler-free code.
4. **Security Layer (`ApiKeyInterceptor`, `WebMvcConfigurerImpl`)**: Protects the endpoints against unauthorized public access.

---

## 2. Webhook Security (API Key Header)

To satisfy the requirement of a **protected, secure REST API** for webhooks, we implemented an API Key verification interceptor. All endpoints require the client to supply the secret API key in the `X-API-KEY` request header.

* **Secret Header Name:** `X-API-KEY`
* **Local Development Key:** Set your secret key inside the local, gitignored `api/src/main/resources/application-local.yml` file. (Refer to `application-local.yml` on your disk).
* **Production Key:** Loaded dynamically via the system environment variable `WEBHOOK_API_KEY` (configured in `application.yml` with no fallback, meaning no production secrets are committed to the codebase).

---

## 3. How to Run the Application

In your terminal, navigate to the project directory and start the server:
```bash
./gradlew bootRun
```
Wait until you see `Started EntryLevelJavaChallengeApplication` in the logs. The server will run on `http://localhost:8080`.

---

## 4. How to Verify Endpoints

> **Note:** Replace `YOUR_API_KEY` in the commands below with the secret key value configured in your local `application-local.yml` or `WEBHOOK_API_KEY` environment variable.

### Option A: Using curl commands (Terminal)

* **Verify security rejects unauthorized requests (without header):**
  ```bash
  curl http://localhost:8080/api/v1/employee
  ```
  *Expected result: `401 Unauthorized` with an error message.*

* **GET All Employees (with key):**
  ```bash
  curl -H "X-API-KEY: YOUR_API_KEY" http://localhost:8080/api/v1/employee
  ```
  *Expected result: `200 OK` returning the list of mock employees.*

* **POST Create a New Employee (with key):**
  ```bash
  curl -X POST -H "X-API-KEY: YOUR_API_KEY" -H "Content-Type: application/json" -d "{\"firstName\":\"Shubh\",\"lastName\":\"Kumar\",\"salary\":90000,\"age\":25,\"jobTitle\":\"Developer\",\"email\":\"shubh@company.com\"}" http://localhost:8080/api/v1/employee
  ```
  *Expected result: `201 Created` returning the newly created employee with their random UUID.*

* **GET Single Employee by UUID (with key):**
  *(Copy the UUID from the GET or POST responses and replace `YOUR_UUID_HERE`)*
  ```bash
  curl -H "X-API-KEY: YOUR_API_KEY" http://localhost:8080/api/v1/employee/YOUR_UUID_HERE
  ```

---

### Option B: Using Browser Developer Console

Since standard web browsers don't allow setting custom headers inside the URL address bar, you can test the API by opening the browser developer tools (**F12 -> Console**) on any page (e.g., `http://localhost:8080`) and executing this script:

```javascript
fetch('http://localhost:8080/api/v1/employee', {
    headers: { 'X-API-KEY': 'YOUR_API_KEY' }
})
.then(res => res.json())
.then(data => console.log(data));
```
This will print the list of employees directly in your browser console!
