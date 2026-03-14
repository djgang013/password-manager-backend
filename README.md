# 🛡️ Vault: Zero-Knowledge Password Manager

A highly secure, full-stack password manager built with **Angular**, **Spring Boot**, and **PostgreSQL**. 

Vault is designed with a strict **Zero-Knowledge Architecture**. The backend server, database, and database administrators never see your plain-text passwords. All encryption and decryption happen strictly in the client's browser using the native Web Crypto API.



## 🔗 Project Repositories
This project is separated into a microservice architecture:
* **Frontend (Angular):** [djgang013/password-manager-frontend](https://github.com/djgang013/password-manager-frontend)
* **Backend (Spring Boot):** [djgang013/password-manager-backend](https://github.com/djgang013/password-manager-backend)

## ✨ Key Features

* **True Zero-Knowledge Security:** Passwords are encrypted in the browser using **AES-GCM (256-bit)** before being transmitted over the network.
* **In-Memory Key Management:** Decryption keys are derived from the user's Master Password using **PBKDF2** and are stored strictly in the browser's volatile RAM. They are never saved to cookies, local storage, or databases.
* **Cryptographically Secure Generator:** Built-in tool to generate highly secure, random passwords using `window.crypto.getRandomValues()`.
* **Stateless Authentication:** API secured via JSON Web Tokens (JWT) and Spring Security.
* **Anti-IDOR Protection:** Database utilizes UUIDs for user identification to prevent Insecure Direct Object Reference attacks.
* **Modern UI/UX:** Responsive, custom dark-themed interface built with pure CSS and CSS Flexbox.

## 🛠️ Tech Stack

**Frontend:**
* Angular (TypeScript)
* Native Web Crypto API
* RxJS & Angular Signals

**Backend:**
* Java & Spring Boot
* Spring Security (JWT, BCrypt)
* Spring Data JPA / Hibernate
* PostgreSQL

## 🔐 How the Cryptography Works

1. **Authentication:** When a user logs in or registers, their master password is hashed via BCrypt on the backend to verify identity. 
2. **Key Derivation:** Upon successful login, the frontend uses PBKDF2 to derive a symmetric AES-256 key from the master password.
3. **Encryption:** When adding a new password to the vault, the frontend generates a random Initialization Vector (IV) and encrypts the plain-text password using AES-GCM. 
4. **Storage:** Only the resulting Base64 ciphertext and IV are sent to the Spring Boot backend to be stored in PostgreSQL. 
5. **Decryption:** When retrieving the vault, the frontend fetches the ciphertext and decrypts it locally using the AES key held in RAM. 

> **Note on Refreshing:** Because the AES decryption key is held strictly in volatile RAM to prevent XSS attacks, refreshing the browser tab will intentionally destroy the key and safely log the user out.

## 🚀 Getting Started

### Prerequisites
* Node.js & Angular CLI
* Java Development Kit (JDK)
* PostgreSQL running locally on port `5433` (or update `application.properties`)

### 1. Database Setup
Create a new PostgreSQL database named `vault_db`. Spring Boot's Hibernate will automatically generate the required `users` and `password_entries` tables upon startup.

### 2. Backend Setup
Clone the backend repository and run the Spring Boot application:

```bash
git clone [https://github.com/djgang013/password-manager-backend.git](https://github.com/djgang013/password-manager-backend.git)
cd password-manager-backend
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`.

### 3. Frontend Setup
Clone the frontend repository, install dependencies, and start the development server:

```bash
git clone [https://github.com/djgang013/password-manager-frontend.git](https://github.com/djgang013/password-manager-frontend.git)
cd password-manager-frontend
npm install
ng serve
```

Open your browser and navigate to `http://localhost:4200`. Create a new account and start securing your passwords!

## 📝 License
This project is open-source and available under the MIT License.
