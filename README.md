# 💸 Xazna Banking Clone

<div align="center">

# 🚀 Modern Digital Banking Experience Built with Jetpack Compose

### A production-level fintech mobile banking application inspired by real banking systems.

<img src="https://img.shields.io/badge/Kotlin-2.0-orange?style=for-the-badge&logo=kotlin" />
<img src="https://img.shields.io/badge/Jetpack%20Compose-Native-blue?style=for-the-badge&logo=jetpackcompose" />
<img src="https://img.shields.io/badge/MVI-Orbit-green?style=for-the-badge" />
<img src="https://img.shields.io/badge/Clean%20Architecture-Scalable-red?style=for-the-badge" />
<img src="https://img.shields.io/badge/Hilt-DI-yellow?style=for-the-badge" />
<img src="https://img.shields.io/badge/Status-Active-success?style=for-the-badge" />

</div>

---

# ✨ Overview

Xazna Banking Clone is a fully-featured modern Android banking application developed using **Jetpack Compose**, **MVI architecture**, and **Clean Architecture principles**.

This project simulates the architecture and user experience of a real-world digital banking product with:

- Secure authentication
- KYC verification flow
- Transfers between cards
- Loan system
- Transaction history
- Camera + ML integration
- State-driven UI
- Biometric authentication
- Advanced Compose animations
- Modular architecture
- Reactive state management

The goal of this project was not just UI replication — but building a scalable fintech-grade architecture.

---

# 🧠 Architecture

```text
Presentation Layer
    ↓
ViewModel (Orbit MVI)
    ↓
UseCases
    ↓
Repository
    ↓
Remote / Local Data Sources
    ↓
Retrofit / DataStore
```

### Project Structure

```text
app/
core/
entity/
presenter/
useCase/
```

The project follows a **multi-module clean architecture** approach for scalability and maintainability.

---

# ⚡ Core Features

## 🔐 Authentication
- PIN code system
- Fingerprint authentication
- Face authentication
- Verification flow
- Session handling
- Secure token storage
- Biometric authentication

## 🌍 Localization
- Multi-language support
- Dynamic language switching
- Localized resources
- RTL/LTR ready architecture

## 💳 Banking Operations
- Transfer between cards
- Card-to-card payments
- Transaction history
- Transaction paging
- Monitoring system
- Payment services

## 🪪 KYC System
- Passport validation
- Camera integration
- Face detection using ML Kit
- Base64 image processing
- Real-time image capture

## 💰 Loans
- Create loan requests
- Loan monitoring
- Repayment system
- Active loan tracking

## 📊 Advanced UI
- Fully Compose-based UI
- Animated transitions
- State-driven rendering
- Responsive layouts
- Dark theme support
- Reusable component system

---

# 🛠 Tech Stack

| Category | Technologies |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | Clean Architecture + MVI |
| State Management | Orbit MVI |
| Dependency Injection | Hilt |
| Navigation | Voyager |
| Networking | Retrofit + OkHttp |
| Image Loading | Coil |
| Local Storage | DataStore |
| Pagination | Paging 3 |
| Camera | CameraX |
| ML | ML Kit Face Detection |
| Async | Kotlin Coroutines + Flow |

---

# 🔥 Advanced Engineering Highlights

## ✅ Modularized Architecture

The project is separated into multiple modules to improve:

- scalability
- testability
- build speed
- maintainability

---

## ✅ Reactive State Management

Implemented using:

- Orbit MVI
- StateFlow
- Kotlin Flow
- Immutable UI State

---

## ✅ Compose-First Development

The entire UI is developed using modern Android standards:

- Stateless composables
- Reusable UI components
- Custom animations
- Optimized recomposition
- Material 3 design

---

## ✅ Production-Level Networking

- Auth interceptors
- Generic API response handling
- Error mapping
- Safe API calls
- Paging integration

---

## ✅ Real Camera + ML Integration

Implemented:

- CameraX preview
- Face detection
- Image compression
- KYC verification flow

---

# 📂 Main Modules

| Module | Description |
|---|---|
| app | Application entry point |
| core | Shared utilities and base classes |
| entity | Data models and DTOs |
| presenter | UI layer and ViewModels |
| useCase | Business logic layer |

---

# 🚀 Performance Focus

The project is optimized for:

- minimal recomposition
- scalable state handling
- smooth animations
- fast navigation
- efficient paging
- memory-efficient image processing

---

# 🧪 Engineering Concepts Practiced

- Clean Architecture
- SOLID principles
- MVI pattern
- Repository pattern
- Dependency Injection
- State-driven UI
- Modularization
- Reactive programming
- Pagination
- Camera processing
- ML integration

---

# 📈 Why This Project Matters

This project demonstrates practical experience with:

✅ Modern Android Development

✅ Fintech App Architecture

✅ Real-world API integration

✅ Scalable Compose UI systems

✅ Production-ready state management

✅ Advanced mobile engineering patterns

---

# 🧑‍💻 Developer Skills Demonstrated

## Android
- Jetpack Compose
- Compose Navigation
- Material 3
- Paging 3
- CameraX
- ML Kit

## Architecture
- Clean Architecture
- MVI
- Modularization
- State management
- Dependency injection

## Backend Integration
- REST APIs
- Authentication
- Token handling
- Error parsing
- Request/response mapping

## Performance
- Optimized recomposition
- Efficient Flow collection
- Lazy loading
- Image compression

---

# ⚙️ Installation

```bash
git clone https://github.com/your-username/XaznaBankingClone.git
```

```bash
Open project in Android Studio
```

```bash
Sync Gradle
```

```bash
Run on device or emulator
```

---

# 🤝 Contribution

Contributions are welcome.

If you want to improve architecture, UI, animations, or banking flows — feel free to fork the project and create a pull request.

---

# 📄 License

This project is developed for educational and portfolio purposes.

---

<div align="center">

### Built with passion using Kotlin & Jetpack Compose.

</div>
