# TimeTable App Configuration Guide

## Overview
This guide explains how to configure the TimeTable app for MongoDB connectivity and other settings using the MongoDB Atlas REST API.

## Configuration File Location
The configuration file is located at: `app/src/main/assets/config.properties`

## MongoDB Setup

### 1. Get Your MongoDB Atlas Account
1. Sign up for MongoDB Atlas (https://www.mongodb.com/atlas)
2. Create a new cluster
3. Get your connection string from the cluster
4. Enable MongoDB Data API for REST access

### 2. Update Configuration
Edit `app/src/main/assets/config.properties` and replace the placeholder values:

```properties
# MongoDB Configuration
# Replace "Mehta" with your actual username and "utsav" with your actual password
MONGODB_URI=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@main.coglx0c.mongodb.net/?retryWrites=true&w=majority&appName=Main

# App Configuration
APP_NAME=TimeTable
APP_VERSION=2.0

# Database Settings
DB_NAME=timetable_db
COLLECTION_LECTURES=lectures
COLLECTION_TEACHERS=teachers

# API Configuration (if using REST API)
API_BASE_URL=https://your-api-endpoint.com
API_TIMEOUT=30000

# Security
ENCRYPTION_KEY=your_secure_encryption_key_here

# Development Settings
DEBUG_MODE=true
LOG_LEVEL=DEBUG
```

### 3. Current Configuration
The app is currently configured with:
- **Username**: Mehta
- **Password**: utsav
- **Database**: timetable_db
- **Collections**: teachers, lectures
- **Cluster**: main.coglx0c.mongodb.net

### 4. Important Notes
- **Security**: Never commit your actual MongoDB URI to version control
- **Network**: Ensure your device has internet connectivity
- **Permissions**: The app requires INTERNET and ACCESS_NETWORK_STATE permissions
- **REST API**: Uses MongoDB Data API for Android compatibility

## Configuration Parameters

### MongoDB Settings
- `MONGODB_URI`: Your MongoDB connection string (REST API compatible)
- `DB_NAME`: Database name (default: timetable_db)
- `COLLECTION_LECTURES`: Collection for lecture data (default: lectures)
- `COLLECTION_TEACHERS`: Collection for teacher data (default: teachers)

### API Settings (Optional)
- `API_BASE_URL`: Base URL for REST API calls
- `API_TIMEOUT`: Request timeout in milliseconds (default: 30000)

### Security Settings
- `ENCRYPTION_KEY`: Key for data encryption (recommended: 32+ characters)

### Development Settings
- `DEBUG_MODE`: Enable debug logging (true/false)
- `LOG_LEVEL`: Logging level (DEBUG, INFO, WARN, ERROR)

## MongoDB Integration Details

### REST API Approach
The app uses MongoDB Data API for Android compatibility:
- **No Native Driver**: Avoids Android compatibility issues
- **HTTP Requests**: Uses OkHttp for network communication
- **JSON Processing**: Handles data serialization/deserialization
- **Error Handling**: Comprehensive error logging and fallback

### Connection Process
1. **URI Parsing**: Extracts username, password, and cluster info
2. **App ID Extraction**: Derives MongoDB App ID from cluster URL
3. **Connection Test**: Pings MongoDB Atlas on startup
4. **Data Sync**: Automatically syncs data with MongoDB

### Data Operations
- **Create**: Insert new teachers and lectures
- **Read**: Fetch teachers and lectures with filtering
- **Update**: Modify existing records
- **Delete**: Soft delete (mark as inactive)

## Testing Configuration

### 1. Check Configuration Loading
The app will log configuration status on startup:
- Look for "Configuration loaded successfully" in logs
- Check if "MongoDB configured" appears
- Verify "Successfully connected to MongoDB Atlas" message

### 2. Test MongoDB Connection
1. Start the app
2. Try to register a teacher
3. Check if data persists across app restarts
4. Verify data appears in MongoDB Atlas dashboard

### 3. Troubleshooting
- **Connection Failed**: Check your MongoDB URI and network connectivity
- **Permission Denied**: Ensure your MongoDB user has read/write permissions
- **Timeout**: Increase API_TIMEOUT value
- **REST API Error**: Verify MongoDB Data API is enabled in Atlas

## Local Development
If you don't have MongoDB set up yet, the app will work with local storage:
- No configuration changes needed
- Data will be stored in memory
- Data will be lost when app is closed
- Automatic fallback when MongoDB is unavailable

## Security Best Practices
1. Use environment variables for sensitive data
2. Enable MongoDB authentication
3. Use strong encryption keys
4. Regularly rotate credentials
5. Monitor database access logs
6. Enable MongoDB Atlas security features

## Network Requirements
- **Internet Access**: Required for MongoDB Atlas connectivity
- **HTTPS**: All API calls use secure HTTPS
- **Timeout**: 30-second timeout for network requests
- **Retry Logic**: Automatic retry on connection failures

## Support
For configuration issues, contact: utsavmehta24072003@gmail.com

## Recent Updates
- ✅ MongoDB REST API integration completed
- ✅ Automatic connection testing implemented
- ✅ Error handling and fallback mechanisms added
- ✅ Sample data initialization in MongoDB
- ✅ Thread-safe operations with ExecutorService
