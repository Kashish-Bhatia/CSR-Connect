# 🤝 CSR-Connect: Bridging FMCG Companies and NGOs

[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-purple.svg)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/Firebase-Real--time%20Database-orange.svg)](https://firebase.google.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Technologies Used](#️-technologies-used)
- [Screenshots](#-screenshots)
- [Installation](#-installation)
- [Configuration](#️-configuration)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Database Schema](#️-database-schema)
- [API Integration](#-api-integration)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

## 🌟 Overview

**CSR-Connect** is an innovative Android application designed to bridge the gap between Fast-Moving Consumer Goods (FMCG) companies and Non-Governmental Organizations (NGOs) for effective Corporate Social Responsibility (CSR) initiatives. The app enables seamless product donation management, helping companies fulfill their CSR obligations while supporting NGOs in accessing essential resources.

### 🎯 Mission
To create a transparent, efficient, and user-friendly platform that facilitates the redistribution of excess inventory from FMCG companies to NGOs, promoting sustainability and social welfare.

## ✨ Features

### 🏢 For Companies
- **Product Donation Management**: Add, update, and delete donated products
- **Inventory Tracking**: Monitor donated items with detailed information
- **Real-time Synchronization**: Firebase integration for instant data updates
- **Date Picker Integration**: Easy expiry date selection
- **Company Profile Integration**: Pre-configured major FMCG company profiles

### 🏛️ For NGOs
- **Product Discovery**: Browse available donated products
- **Search & Filter**: Advanced search functionality by product name and category
- **Request Products**: Request specific products for their initiatives
- **Product Details**: View comprehensive product information including condition, quantity, and location

### 🔄 Shared Features
- **Firebase Authentication**: Secure user login and registration
- **Dual Interface**: Dynamic UI adapting to user type (Company/NGO)
- **Offline Support**: Local Room database for offline functionality
- **Real-time Sync**: Automatic synchronization between local and cloud databases
- **Interactive ViewPager**: Engaging carousel for CSR awareness content
- **Company Showcase**: Horizontal scrolling list of major FMCG companies

## 🏗️ Architecture

The application follows **MVVM (Model-View-ViewModel)** architecture pattern with the following components:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │    Business     │    │      Data       │
│     Layer       │◄──►│     Logic       │◄──►│     Layer       │
│                 │    │                 │    │                 │
│ • Activities    │    │ • ViewModels    │    │ • Room DB       │
│ • Adapters      │    │ • Use Cases     │    │ • Firebase      │
│ • Layouts       │    │ • Repositories  │    │ • DAOs          │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🛠️ Technologies Used

### Core Technologies
- **Language**: Kotlin 100%
- **Platform**: Android (API 24+)
- **IDE**: Android Studio

### Libraries & Frameworks

#### Firebase Stack
- **Firebase Authentication**: User authentication and management
- **Firebase Realtime Database**: Cloud data storage and synchronization
- **Firebase BoM**: Version management for Firebase libraries

#### Database & Storage
- **Room Database**: Local data persistence
- **Room KTX**: Kotlin extensions for Room

#### UI & UX
- **ViewBinding**: Type-safe view references
- **RecyclerView**: Efficient list displays
- **ViewPager2**: Interactive page sliding
- **CardView**: Material design cards
- **CircleIndicator**: Page indicators for ViewPager

#### Asynchronous Programming
- **Kotlin Coroutines**: Background operations
- **LiveData**: Reactive data observation

#### Dependencies Management
- **Gradle Version Catalog**: Centralized dependency management

### 📱 Target Specifications
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

## 📷 Screenshots

*Screenshots showcasing the app's main features*

| Login Screen | Home Page | Company Selection | Product Listing |
|--------------|-----------|------------------|-----------------|
| ![Login Screen](GitHub%20Images/Screenshot%202025-09-27%20124843.png) | ![Home Page](GitHub%20Images/Screenshot%202025-09-27%20124911.png) | ![Company Selection](GitHub%20Images/Screenshot%202025-09-27%20125214.png) | ![Product Listing](GitHub%20Images/Screenshot%202025-09-27%20125326.png) |

| Product Details | Add Product |
|-----------------|-------------|
| ![Product Details](GitHub%20Images/Screenshot%202025-09-27%20125432.png) | ![Add Product](GitHub%20Images/Screenshot%202025-09-27%20125506.png) |

## 🚀 Installation

### Prerequisites
- Android Studio Arctic Fox (2021.3.1) or later
- Android SDK 35
- JDK 18
- Git

### Clone Repository
```bash
git clone https://github.com/Kashish-Bhatia/CSR-Connect.git
cd CSR-Connect
```

### Setup Instructions

1. **Open Project**
   ```bash
   # Open Android Studio and select "Open an existing project"
   # Navigate to the cloned directory and open it
   ```

2. **Sync Project**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues

3. **Firebase Configuration**
   - Download `google-services.json` from Firebase Console
   - Place it in the `app/` directory
   - Ensure Firebase project is properly configured

4. **Build Project**
   ```bash
   ./gradlew build
   ```

5. **Run Application**
   - Connect Android device or start emulator
   - Click "Run" button in Android Studio

## ⚙️ Configuration

### Firebase Setup

1. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create new project or use existing one

2. **Add Android App**
   - Register app with package name: `com.example.firebaseauthenticationapp`
   - Download `google-services.json`

3. **Enable Authentication**
   - Enable Email/Password authentication
   - Configure Google Sign-In (optional)

4. **Setup Realtime Database**
   - Create database in test mode
   - Update security rules as needed

### Local Properties
Create `local.properties` file:
```properties
sdk.dir=YOUR_ANDROID_SDK_PATH
```

## 📖 Usage

### Getting Started

1. **Launch Application**
   - Install and open CSR-Connect

2. **User Registration/Login**
   - Create new account or sign in
   - Choose user type: Company or NGO

3. **Company Workflow**
   ```
   Login → Select Company → View Dashboard → Add Products → Manage Inventory
   ```

4. **NGO Workflow**
   ```
   Login → Select NGO → Browse Products → Search/Filter → Request Products
   ```

### Key Operations

#### Adding Products (Companies)
1. Tap the "+" button on the homepage
2. Fill in product details:
   - Product name
   - Category
   - Company name
   - Quantity
   - Expiry date
   - Condition
   - Location
3. Tap "Donate" to save

#### Searching Products (NGOs)
1. Use the search bar on the homepage
2. Type product name or category
3. Browse filtered results
4. Tap on products for detailed view

## 📁 Project Structure

```
CSR-Connect/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/firebaseauthenticationapp/
│   │   │   │   ├── adapters/           # RecyclerView adapters
│   │   │   │   │   ├── CompanyAdapter.kt
│   │   │   │   │   ├── ProductAdapter.kt
│   │   │   │   │   └── ViewPagerAdapter.kt
│   │   │   │   ├── classes/            # Data classes
│   │   │   │   │   ├── Company.kt
│   │   │   │   │   └── Product.kt
│   │   │   │   ├── database_Firebase/   # Firebase models
│   │   │   │   │   └── ProductTableModel.kt
│   │   │   │   ├── database_RoomDB/     # Local database
│   │   │   │   │   ├── DBHandler.kt
│   │   │   │   │   ├── ProductDao.kt
│   │   │   │   │   ├── ProductDatabase.kt
│   │   │   │   │   └── ProductTable.kt
│   │   │   │   ├── loginORsignup/       # Authentication
│   │   │   │   │   ├── CompanyOrNGO.kt
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   └── SignUPActivity.kt
│   │   │   │   ├── CompanyInsertProduct.kt
│   │   │   │   ├── HomePage.kt
│   │   │   │   └── ProductDetailPageActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/           # UI assets
│   │   │   │   ├── layout/            # XML layouts
│   │   │   │   ├── values/            # Strings, colors, themes
│   │   │   │   └── font/              # Custom fonts
│   │   │   └── AndroidManifest.xml
│   │   └── test/ & androidTest/       # Testing
│   ├── build.gradle.kts              # App-level build config
│   └── google-services.json          # Firebase config
├── gradle/
│   └── libs.versions.toml            # Version catalog
├── build.gradle.kts                  # Project-level build config
├── gradle.properties               # Gradle properties
└── settings.gradle.kts             # Project settings
```

## 🗄️ Database Schema

### Room Database (Local)

#### ProductTable Entity
```kotlin
@Entity(tableName = "product_table")
data class ProductTable(
    @PrimaryKey val room_timestamp: String,
    var rooom_productId: Int = 0,
    var room_productName: String,
    var room_category: String,
    var room_companyOfTheProduct: String,
    var room_quantity: Int,
    var room_date: String,
    var room_condition: String,
    var room_location: String,
    var isSynced: Boolean = false
)
```

### Firebase Realtime Database Structure
```json
{
  "NewProducts": {
    "timestamp_1": {
      "row_productId": 1,
      "row_productName": "Product Name",
      "row_category": "Category",
      "row_companyOfTheProduct": "Company",
      "row_quantity": 100,
      "row_date": "31/12/2024",
      "row_condition": "Good",
      "row_location": "Location",
      "isSynced": true,
      "row_timestamp": "timestamp_1"
    }
  }
}
```

## 🔗 API Integration

### Firebase Authentication
- Email/Password authentication
- User session management
- Secure token handling

### Firebase Realtime Database
- Real-time data synchronization
- Offline persistence
- Cloud data backup

### Local Room Database
- Offline data access
- Data caching
- Sync status tracking

## 🤝 Contributing

We welcome contributions to CSR-Connect! Please follow these steps:

### How to Contribute

1. **Fork the Repository**
   ```bash
   git fork https://github.com/Kashish-Bhatia/CSR-Connect.git
   ```

2. **Create Feature Branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Commit Changes**
   ```bash
   git commit -m "Add amazing feature"
   ```

4. **Push to Branch**
   ```bash
   git push origin feature/amazing-feature
   ```

5. **Open Pull Request**
   - Describe your changes
   - Add screenshots if applicable
   - Reference any related issues

### Development Guidelines

- Follow Kotlin coding conventions
- Write meaningful commit messages
- Add comments for complex logic
- Test your changes thoroughly
- Update documentation as needed

### Issues & Bug Reports

- Use GitHub Issues for bug reports
- Provide detailed reproduction steps
- Include device and Android version information
- Add relevant screenshots or logs

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 CSR-Connect

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📞 Contact

### Project Maintainer
- **Name**: Kashish Bhatia
- **GitHub**: [@Kashish-Bhatia](https://github.com/Kashish-Bhatia)
- **Repository**: [CSR-Connect](https://github.com/Kashish-Bhatia/CSR-Connect)

### Support
- 📧 **Email**: [Create an issue](https://github.com/Kashish-Bhatia/CSR-Connect/issues) for support
- 📱 **Discord**: Coming soon
- 📘 **Documentation**: Available in the repository

---

## 🙏 Acknowledgments

- **Major FMCG Companies**: For inspiration and CSR data
  - Hindustan Unilever Limited
  - Parle Products Pvt. Ltd.
  - Procter & Gamble
  - Nestlé India
  - Coca-Cola India
  - Britannia Industries
  - PepsiCo India

- **Open Source Community**: For amazing libraries and tools
- **Firebase Team**: For robust backend services
- **Android Team**: For comprehensive development platform

---

**⭐ Star this repository if you find it useful!**

**Made with ❤️ for a better tomorrow through technology and social responsibility.**
