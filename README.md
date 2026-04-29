# ✅ TodoApp — Android

A native Android task management application built with **Kotlin** and **Jetpack Compose**. Connects to a Spring Boot backend for data storage and user authentication.

> 🖥️ Backend API: [TodoAppBack](https://github.com/Ginseku/TodoAppBack)

---

## 🚀 Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| Navigation | Navigation Component |
| Networking | Retrofit |
| Image Loading | Glide |
| Async | Kotlin Coroutines |
| Build Tool | Gradle (Kotlin DSL) |
| Min SDK | Android 8.0+ |

---

## ✨ Features

- 🔐 **Authentication** — register and login via REST API
- 📝 **Task Management** — create, edit, delete notes
- 🗂️ **Categories** — organize notes by categories
- 🔄 **Real-time sync** — data stored on backend via REST API
- 🎨 **Modern UI** — built entirely with Jetpack Compose
- ⚡ **Async operations** — smooth UX with Kotlin Coroutines

---

## 📁 Project Structure

```
TodoApp/
├── app/
│   └── src/main/
│       ├── java/
│       │   ├── ui/              # Compose screens & components
│       │   ├── viewmodel/       # ViewModels (MVVM)
│       │   ├── network/         # Retrofit API client
│       │   ├── model/           # Data models
│       │   └── navigation/      # Navigation graph
│       └── res/                 # Resources
├── build.gradle.kts
└── settings.gradle.kts
```

---

## ⚙️ Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android device or emulator (API 26+)
- Running instance of [TodoAppBack](https://github.com/Ginseku/TodoAppBack)

### 1. Clone the repository

```bash
git clone https://github.com/Ginseku/TodoApp.git
```

### 2. Open in Android Studio

Open the project folder in Android Studio and let Gradle sync.

### 3. Configure API base URL

In the network configuration file, set your backend URL:

```kotlin
const val BASE_URL = "http://10.0.2.2:8080/" // for Android emulator
// or
const val BASE_URL = "http://your-server-ip:8080/" // for real device
```

### 4. Run the app

Click **Run** in Android Studio or use:

```bash
./gradlew assembleDebug
```

---

## 🔗 Related

- 🖥️ [TodoAppBack — Spring Boot Backend](https://github.com/Ginseku/TodoAppBack)

---

## 👤 Author

**Mykyta Bondarchuk**
- GitHub: [@Ginseku](https://github.com/Ginseku)
- LinkedIn: [mykyta-bondarchuk](https://www.linkedin.com/in/mykyta-bondarchuk-a61150268/)
