# 🤝 Contributing to TimeTable App

Thank you for your interest in contributing to the TimeTable App! This document provides guidelines and information for contributors.

## 📋 Table of Contents

- [Getting Started](#-getting-started)
- [Development Setup](#-development-setup)
- [Code Standards](#-code-standards)
- [Testing](#-testing)
- [Pull Request Process](#-pull-request-process)
- [Issue Reporting](#-issue-reporting)
- [Code of Conduct](#-code-of-conduct)

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (API level 24)
- Gradle 7.0+
- Git
- MongoDB Atlas account (for testing)

### Fork and Clone
1. **Fork** the repository on GitHub
2. **Clone** your fork locally:
   ```bash
   git clone https://github.com/yourusername/timetable-app.git
   cd timetable-app
   ```
3. **Add upstream** remote:
   ```bash
   git remote add upstream https://github.com/original-owner/timetable-app.git
   ```

## 🔧 Development Setup

### 1. Project Setup
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the cloned directory and select it
4. Wait for Gradle sync to complete

### 2. MongoDB Configuration
1. Sign up for MongoDB Atlas at [MongoDB Atlas](https://www.mongodb.com/atlas)
2. Create a new cluster
3. Enable MongoDB Data API
4. Edit `app/src/main/assets/config.properties`
5. Replace username and password in the MongoDB URI

### 3. Build and Test
1. Connect an Android device or start an emulator
2. Run `./gradlew assembleDebug` to build the project
3. Run `./gradlew test` to run unit tests
4. Run `./gradlew connectedAndroidTest` to run instrumentation tests

## 📝 Code Standards

### Java/Kotlin Guidelines
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add comprehensive comments for complex logic
- Keep methods small and focused (max 50 lines)
- Use proper exception handling

### Android Guidelines
- Follow [Android Developer Guidelines](https://developer.android.com/guide)
- Use Material Design 3 components
- Implement proper lifecycle management
- Handle configuration changes
- Use ViewBinding for view references

### Architecture Guidelines
- Follow MVVM architecture pattern
- Use Repository pattern for data access
- Implement proper separation of concerns
- Use dependency injection where appropriate
- Follow SOLID principles

### Code Style
```java
// Good example
public class TeacherService {
    private static final String TAG = "TeacherService";
    private final MongoDBService mongoDBService;
    
    public TeacherService(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }
    
    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            Log.w(TAG, "Teacher is null");
            return;
        }
        
        mongoDBService.addTeacher(teacher);
    }
}
```

### Naming Conventions
- **Classes**: PascalCase (e.g., `TeacherDashboardActivity`)
- **Methods**: camelCase (e.g., `getTeacherByCode()`)
- **Variables**: camelCase (e.g., `teacherName`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_TEACHER_COUNT`)
- **Resources**: snake_case (e.g., `activity_teacher_dashboard.xml`)

## 🧪 Testing

### Unit Tests
- Write unit tests for all business logic
- Use JUnit 4 or 5
- Mock external dependencies
- Aim for 80%+ code coverage

```java
@Test
public void testAddTeacher() {
    // Given
    Teacher teacher = new Teacher("AK", "ak@dpu.ac.in", "password123", "Computer Science");
    
    // When
    teacherService.addTeacher(teacher);
    
    // Then
    verify(mongoDBService).addTeacher(teacher);
}
```

### Instrumentation Tests
- Test UI interactions
- Test integration with MongoDB
- Test on multiple devices
- Use Espresso for UI testing

```java
@Test
public void testTeacherLogin() {
    // Given
    onView(withId(R.id.emailInput)).perform(typeText("ak@dpu.ac.in"));
    onView(withId(R.id.passwordInput)).perform(typeText("password123"));
    
    // When
    onView(withId(R.id.loginButton)).perform(click());
    
    // Then
    onView(withId(R.id.teacherDashboard)).check(matches(isDisplayed()));
}
```

### Manual Testing
- Test on different screen sizes
- Test with different Android versions
- Test offline functionality
- Test error scenarios

## 🔄 Pull Request Process

### 1. Create Feature Branch
```bash
git checkout -b feature/amazing-feature
```

### 2. Make Changes
- Write clean, well-documented code
- Add tests for new functionality
- Update documentation if needed
- Follow the code standards

### 3. Commit Changes
```bash
git add .
git commit -m "feat: Add amazing feature

- Added new teacher management functionality
- Implemented real-time data sync
- Added comprehensive error handling
- Updated documentation

Closes #123"
```

### 4. Push to Fork
```bash
git push origin feature/amazing-feature
```

### 5. Create Pull Request
1. Go to your fork on GitHub
2. Click "New Pull Request"
3. Select your feature branch
4. Fill out the PR template
5. Submit the PR

### PR Template
```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Instrumentation tests pass
- [ ] Manual testing completed
- [ ] Screenshots added (if UI changes)

## Checklist
- [ ] Code follows the style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] Tests added/updated
- [ ] No breaking changes

## Screenshots (if applicable)
[Add screenshots here]
```

## 🐛 Issue Reporting

### Bug Reports
When reporting bugs, please include:
- **Description**: Clear description of the bug
- **Steps to Reproduce**: Step-by-step instructions
- **Expected Behavior**: What should happen
- **Actual Behavior**: What actually happens
- **Environment**: Device, Android version, app version
- **Screenshots**: If applicable

### Feature Requests
When requesting features, please include:
- **Description**: Clear description of the feature
- **Use Case**: Why this feature is needed
- **Proposed Solution**: How you think it should work
- **Alternatives**: Any alternative solutions considered

### Issue Template
```markdown
## Issue Type
- [ ] Bug Report
- [ ] Feature Request
- [ ] Documentation Issue
- [ ] Other

## Description
[Describe the issue or feature request]

## Steps to Reproduce (for bugs)
1. [First step]
2. [Second step]
3. [And so on...]

## Expected Behavior
[What you expected to happen]

## Actual Behavior
[What actually happened]

## Environment
- Device: [e.g., Samsung Galaxy S21]
- Android Version: [e.g., Android 12]
- App Version: [e.g., 2.0.0]

## Screenshots
[Add screenshots if applicable]
```

## 📚 Documentation

### Code Documentation
- Add Javadoc comments for public methods
- Document complex algorithms
- Include usage examples
- Keep documentation up-to-date

### README Updates
- Update README.md for new features
- Add screenshots for UI changes
- Update installation instructions
- Keep version information current

## 🎯 Areas for Contribution

### High Priority
- [ ] **Performance Optimization**: Improve app performance
- [ ] **Error Handling**: Enhance error handling and user feedback
- [ ] **Testing**: Add more comprehensive tests
- [ ] **Documentation**: Improve documentation and guides

### Medium Priority
- [ ] **UI/UX Improvements**: Enhance user interface
- [ ] **New Features**: Add requested features
- [ ] **Bug Fixes**: Fix reported bugs
- [ ] **Code Refactoring**: Improve code quality

### Low Priority
- [ ] **Documentation**: Update comments and docs
- [ ] **Code Style**: Improve code formatting
- [ ] **Minor UI**: Small UI improvements
- [ ] **Optimization**: Minor performance improvements

## 🤝 Code of Conduct

### Our Standards
- Be respectful and inclusive
- Use welcoming and inclusive language
- Be collaborative and constructive
- Focus on what is best for the community
- Show empathy towards other community members

### Unacceptable Behavior
- Harassment or discrimination
- Trolling or insulting comments
- Publishing others' private information
- Other conduct inappropriate for a professional environment

## 📞 Getting Help

### Communication Channels
- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For general questions and discussions
- **Email**: utsavmehta24072003@gmail.com

### Resources
- [Android Developer Documentation](https://developer.android.com/docs)
- [Material Design Guidelines](https://material.io/design)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [GitHub Guides](https://guides.github.com/)

## 🙏 Recognition

Contributors will be recognized in:
- **README.md**: Contributors section
- **Release Notes**: Credit for significant contributions
- **GitHub**: Contributor statistics and profile

---

Thank you for contributing to the TimeTable App! 🎉

*Last updated: January 2024*
