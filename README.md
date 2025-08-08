# 🎓 Enhanced TimeTable App v2.0

A modern, feature-rich Android application for managing and viewing timetables with separate interfaces for teachers and students, featuring MongoDB Atlas integration for online data storage and real-time synchronization.

![App Logo](docs/images/app-logo.png)
*[App logo will be added here]*

## 📋 Table of Contents

- [Features](#-features)
- [Screenshots](#-screenshots)
- [Architecture](#-architecture)
- [Setup Instructions](#-setup-instructions)
- [Usage Guide](#-usage-guide)
- [Technical Details](#-technical-details)
- [API Documentation](#-api-documentation)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 🎓 Teacher View
- **🔐 Secure Authentication**: Login/Register with email and password
- **📊 Modern Dashboard**: Material Design 3 interface with real-time data
- **📝 Lecture Management**: Add, edit, and delete lectures with intuitive UI
- **📅 Day-wise Organization**: View lectures organized by days of the week
- **🔄 Real-time Updates**: Swipe to refresh functionality
- **🔑 Unique Teacher Codes**: Each teacher gets a unique 6-character code for sharing
- **📱 Responsive Design**: Works seamlessly on all screen sizes
- **🎨 Material Design 3**: Modern UI with dynamic colors and animations

### 👨‍🎓 Student View
- **🔑 Teacher Code Access**: Students can view timetables using teacher's unique code
- **📅 Today & Tomorrow View**: See current and next day's schedule
- **🎨 Modern UI**: Clean, intuitive interface with Material Design 3
- **🔄 Real-time Updates**: Automatic refresh of timetable data
- **📱 Offline Support**: View cached data when offline
- **🎯 Quick Navigation**: Easy switching between days

### 🔧 Technical Features
- **☁️ MongoDB Integration**: Full online data storage using MongoDB Atlas REST API
- **💾 Local Storage**: Fallback to local storage when offline
- **🔄 Data Synchronization**: Automatic sync between local and cloud data
- **🔒 Security**: Secure authentication and data encryption
- **📊 Performance**: Optimized for fast loading and smooth interactions
- **🐛 Error Handling**: Comprehensive error handling and user feedback
- **📱 Cross-platform**: Designed for Android with modern development practices

### 🎨 UI/UX Improvements
- **🎨 Material Design 3**: Modern design system with dynamic colors
- **📱 Responsive Layout**: Works on all screen sizes (phone, tablet)
- **✨ Smooth Animations**: Fade-in animations and smooth transitions
- **🎯 Color-coded Days**: Each day has a distinct color theme
- **🃏 Card-based Design**: Information displayed in elegant cards
- **♿ Accessibility**: Proper content descriptions and touch targets
- **🌙 Dark Mode**: Support for light and dark themes

## 📸 Screenshots

### Main Interface
![Main Activity](docs/images/main-activity.png)
*[Screenshot of main activity with role selection will be added here]*

### Teacher Dashboard
![Teacher Dashboard](docs/images/teacher-dashboard.png)
*[Screenshot of teacher dashboard with lecture management will be added here]*

### Student View
![Student Timetable](docs/images/student-timetable.png)
*[Screenshot of student timetable view will be added here]*

### Login/Register
![Login Screen](docs/images/login-screen.png)
*[Screenshot of login/register interface will be added here]*

### Lecture Management
![Lecture Management](docs/images/lecture-management.png)
*[Screenshot of lecture editing interface will be added here]*

## 🏗️ Architecture

### Data Models
- **📚 Lecture**: Enhanced with ID, timing, teacher code, and active status
- **👨‍🏫 Teacher**: Complete teacher information with unique codes
- **🔧 MongoDBService**: Centralized data management service with REST API integration

### Activities
- **🏠 MainActivity**: Role selection (Teacher/Student)
- **🔐 TeacherLoginActivity**: Teacher authentication
- **👨‍🎓 StudentLoginActivity**: Student access with teacher codes
- **📊 TeacherDashboardActivity**: Teacher's lecture management
- **📅 StudentTimetableActivity**: Student's timetable view
- **✏️ EditLectureActivity**: Lecture editing interface

### Adapters
- **📋 EnhancedLectureAdapter**: Modern RecyclerView adapter with Material Design 3

### Configuration
- **⚙️ ConfigManager**: Manages app configuration and MongoDB settings
- **📄 config.properties**: Configuration file for database and API settings

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (API level 24)
- Gradle 7.0+
- MongoDB Atlas account (for online data storage)
- Internet connection for initial setup

### Installation

#### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/timetable-app.git
cd timetable-app
```

#### Step 2: Open in Android Studio
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the cloned directory and select it
4. Wait for Gradle sync to complete

#### Step 3: Configure MongoDB
1. Sign up for MongoDB Atlas at [MongoDB Atlas](https://www.mongodb.com/atlas)
2. Create a new cluster
3. Enable MongoDB Data API
4. Edit `app/src/main/assets/config.properties`
5. Replace username and password in the MongoDB URI

#### Step 4: Build and Run
1. Connect an Android device or start an emulator
2. Click "Run" in Android Studio
3. Select your device and click "OK"

### MongoDB Configuration
The app now uses MongoDB Atlas REST API for online data storage:

**Current Configuration:**
```properties
MONGODB_URI=mongodb+srv://Mehta:utsav@main.coglx0c.mongodb.net/?retryWrites=true&w=majority&appName=Main
DB_NAME=timetable_db
COLLECTION_LECTURES=lectures
COLLECTION_TEACHERS=teachers
```

See `CONFIGURATION.md` for detailed setup instructions.

## 📖 Usage Guide

### For Teachers

#### Getting Started
1. **Launch the app** and select "Teacher"
2. **Register** with your details or **login** if already registered
3. **Access dashboard** to manage your lectures
4. **Share your unique code** with students

#### Managing Lectures
1. **Add Lecture**: Tap the floating action button (+)
2. **Edit Lecture**: Tap on any lecture card
3. **Delete Lecture**: Swipe left on lecture card
4. **View by Day**: Use tab navigation to switch between days

#### Features
- **Real-time Sync**: All changes sync automatically with MongoDB
- **Offline Support**: Work offline with local cache
- **Data Backup**: Automatic cloud backup of all data

### For Students

#### Getting Started
1. **Launch the app** and select "Student"
2. **Enter teacher's unique code** (provided by your teacher)
3. **View timetable** for today and tomorrow
4. **Swipe down** to refresh the data

#### Features
- **Today's Schedule**: View current day's lectures
- **Tomorrow's Schedule**: Plan ahead for next day
- **Real-time Updates**: Automatic refresh when teacher makes changes
- **Offline Access**: View cached data when offline

## 🔧 Technical Details

### Dependencies
```gradle
// Material Design 3
implementation("com.google.android.material:material:1.11.0")

// RecyclerView and UI Components
implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.cardview:cardview:1.0.0")
implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

// Network and JSON
implementation("com.squareup.okhttp3:okhttp:4.12.0")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("org.json:json:20231013")
```

### MongoDB Integration
- **🔄 REST API Approach**: Uses MongoDB Data API for Android compatibility
- **🔗 Automatic Connection**: Tests connection on startup
- **🛡️ Error Handling**: Comprehensive error logging and fallback mechanisms
- **💾 Local Cache**: Maintains local data for immediate access
- **🔒 Thread Safety**: Uses ExecutorService for background operations

### Performance Optimizations
- **⚡ Fast Loading**: Optimized data loading and caching
- **🔄 Efficient Updates**: Smart sync only when needed
- **📱 Memory Management**: Proper memory usage and cleanup
- **🔍 Search Optimization**: Fast search and filtering

### Security Features
- **🔐 Secure Authentication**: Email and password verification
- **🔒 Data Encryption**: Sensitive data encryption
- **🛡️ API Security**: Secure API key management
- **🔍 Input Validation**: Comprehensive input validation

## 📚 API Documentation

### MongoDB Data API Endpoints

#### Base URL
```
https://data.mongodb-api.com/app/{app-id}/endpoint/data/v1/action/
```

#### Authentication
All requests require an `api-key` header with your MongoDB Atlas API key.

#### Common Operations

##### 1. Insert Document
```http
POST /insertOne
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "document": {
    "id": "t001",
    "name": "AK",
    "email": "ak@dpu.ac.in",
    "password": "password123",
    "department": "Computer Science",
    "uniqueCode": "AK",
    "active": true
  }
}
```

##### 2. Find Documents
```http
POST /find
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "filter": {
    "active": true
  }
}
```

##### 3. Update Document
```http
POST /updateOne
Content-Type: application/json
api-key: {your-api-key}

{
  "dataSource": "Main",
  "database": "timetable_db",
  "collection": "teachers",
  "filter": {
    "id": "t001"
  },
  "update": {
    "$set": {
      "name": "Updated Name",
      "active": false
    }
  }
}
```

## 🎯 Sample Data

The app comes with comprehensive sample data for demonstration:

### Teachers
- **AK** - Computer Science (ak@dpu.ac.in)
- **CS** - Computer Science (cs@dpu.ac.in)
- **IS** - Information Systems (is@dpu.ac.in)
- **SRS** - Software Engineering (srs@dpu.ac.in)
- **SS** - Management (ss@dpu.ac.in)
- **APK** - Computer Science (apk@dpu.ac.in)
- **SV** - Computer Science (sv@dpu.ac.in)
- **VV** - Statistics (vv@dpu.ac.in)

### Sample Lectures
- **DSA** - Data Structures and Algorithms
- **IOT** - Internet of Things
- **STATISTICS** - Statistical Analysis
- **SE** - Software Engineering
- **DSA Lab** - Practical sessions
- **IOT Lab** - Hands-on IoT projects

All sample data is automatically initialized in MongoDB on first run.

## 🔄 Recent Updates

### Version 2.0 (Latest)
- ✅ **MongoDB REST API integration** completed
- ✅ **Automatic connection testing** implemented
- ✅ **Error handling and fallback mechanisms** added
- ✅ **Sample data initialization** in MongoDB
- ✅ **Thread-safe operations** with ExecutorService
- ✅ **Material Design 3** UI enhancements
- ✅ **Comprehensive documentation** updated
- ✅ **Performance optimizations** implemented

### Version 1.0
- ✅ Basic timetable functionality
- ✅ Local storage implementation
- ✅ Simple UI design

## 🚧 Future Enhancements

### Planned Features
- [ ] **Push Notifications**: Real-time notifications for schedule changes
- [ ] **Offline Mode**: Full offline functionality with local database
- [ ] **Multiple Teachers**: Support for multiple teachers per student
- [ ] **Calendar Integration**: Sync with device calendar
- [ ] **Export/Import**: Data export and import functionality
- [ ] **Advanced Search**: Advanced filtering and search capabilities
- [ ] **Analytics**: Usage analytics and reporting
- [ ] **Real-time Collaboration**: Real-time collaboration features
- [ ] **Voice Commands**: Voice-activated timetable management
- [ ] **Widget Support**: Home screen widgets for quick access

### Technical Improvements
- [ ] **Performance**: Further performance optimizations
- [ ] **Security**: Enhanced security features
- [ ] **Testing**: Comprehensive unit and integration tests
- [ ] **CI/CD**: Automated testing and deployment
- [ ] **Monitoring**: Application monitoring and analytics

## 🤝 Contributing

We welcome contributions! Please follow these steps:

### Development Setup
1. **Fork** the repository
2. **Clone** your fork locally
3. **Create** a feature branch: `git checkout -b feature/amazing-feature`
4. **Make** your changes
5. **Test** thoroughly
6. **Commit** your changes: `git commit -m 'Add amazing feature'`
7. **Push** to your branch: `git push origin feature/amazing-feature`
8. **Submit** a pull request

### Code Standards
- Follow Android development best practices
- Use Material Design 3 guidelines
- Write comprehensive tests
- Document your code
- Follow the existing code style

### Testing
- Run unit tests: `./gradlew test`
- Run instrumentation tests: `./gradlew connectedAndroidTest`
- Test on multiple devices and screen sizes

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Developer

**Utsav Mehta** - Enhanced TimeTable App v2.0

- **Email**: utsavmehta24072003@gmail.com
- **GitHub**: [@yourusername](https://github.com/utsavmehta24)
- **LinkedIn**: [Utsav Mehta](https://www.linkedin.com/in/utsav-mehta-462653258)

## 🙏 Acknowledgments

- **MongoDB Atlas** for cloud database services
- **Material Design** team for design guidelines
- **Android Developer Community** for support and resources
- **Open Source Contributors** for various libraries and tools

## 📞 Support

For support and questions:
- **Email**: utsavmehta24072003@gmail.com
- **Issues**: [GitHub Issues](https://github.com/utsavmehta24/Time-Table/issues)
- **Documentation**: [Wiki](https://github.com/utsavmehta24/Time-Table/wiki)

---

*Built with ❤️ using Material Design 3, MongoDB Atlas, and modern Android development practices.*

*Last updated: August 2025*
