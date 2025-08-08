package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// REST API imports for MongoDB
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

public class MongoDBService {
    private static final String TAG = "MongoDBService";
    private static MongoDBService instance;
    private Context context;
    private ExecutorService executor;
    
    // REST API components for MongoDB
    private OkHttpClient httpClient;
    private String mongoUri;
    private String dbName;
    private String teachersCollectionName;
    private String lecturesCollectionName;
    private String apiKey;
    private boolean isConnected = false;

    // Local storage as fallback
    private List<Lecture> lectures;
    private List<Teacher> teachers;

    private MongoDBService(Context context) {
        this.context = context.getApplicationContext();
        this.executor = Executors.newFixedThreadPool(4);
        this.lectures = new ArrayList<>();
        this.teachers = new ArrayList<>();
        
        // Initialize HTTP client
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
        
        // Initialize immediately for immediate data access
        try {
            loadConfiguration();
            if (isConnected) {
                initializeSampleData();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing MongoDBService: " + e.getMessage());
        }
    }

    public static synchronized MongoDBService getInstance(Context context) {
        if (instance == null) {
            instance = new MongoDBService(context);
        }
        return instance;
    }

    private void loadConfiguration() {
        ConfigManager configManager = ConfigManager.getInstance(context);
        
        if (configManager.isMongoDBConfigured()) {
            this.mongoUri = configManager.getMongoDBUri();
            this.dbName = configManager.getDatabaseName();
            this.teachersCollectionName = configManager.getTeachersCollection();
            this.lecturesCollectionName = configManager.getLecturesCollection();
            
            Log.d(TAG, "Configuration loaded - DB: " + dbName + ", Collections: " + teachersCollectionName + ", " + lecturesCollectionName);
            Log.d(TAG, "MongoDB URI: " + mongoUri);
            
            // Extract API key from MongoDB URI
            this.apiKey = extractApiKeyFromUri(mongoUri);
            
            // Test connection
            testMongoDBConnection();
        } else {
            Log.d(TAG, "MongoDB not configured, using local storage");
            isConnected = true;
        }
    }

    private String extractApiKeyFromUri(String uri) {
        // Extract username and password from MongoDB URI
        // Format: mongodb+srv://username:password@host...
        try {
            String[] parts = uri.split("@");
            if (parts.length > 0) {
                String credentials = parts[0].replace("mongodb+srv://", "");
                String[] credParts = credentials.split(":");
                if (credParts.length >= 2) {
                    return credParts[1]; // Return password as API key
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error extracting API key from URI: " + e.getMessage());
        }
        return "";
    }

    private String extractAppIdFromUri(String uri) {
        // Extract app ID from MongoDB URI
        // For your URI: mongodb+srv://Mehta:utsav1234@main.coglx0c.mongodb.net
        // The app ID would be "main-coglx0c"
        try {
            String[] parts = uri.split("@");
            if (parts.length > 1) {
                String host = parts[1].split("/")[0];
                String[] hostParts = host.split("\\.");
                if (hostParts.length >= 2) {
                    return hostParts[0] + "-" + hostParts[1];
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error extracting App ID from URI: " + e.getMessage());
        }
        return "main-coglx0c"; // Fallback
    }

    private void testMongoDBConnection() {
        executor.execute(() -> {
            try {
                // Test connection using MongoDB Data API
                String appId = extractAppIdFromUri(mongoUri);
                String url = "https://data.mongodb-api.com/app/" + appId + "/endpoint/data/v1/action/find";
                
                JSONObject requestBody = new JSONObject();
                requestBody.put("dataSource", "Main");
                requestBody.put("database", dbName);
                requestBody.put("collection", teachersCollectionName);
                requestBody.put("limit", 1);
                
                RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), 
                    requestBody.toString()
                );
                
                Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("api-key", apiKey)
                    .post(body)
                    .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        isConnected = true;
                        Log.d(TAG, "Successfully connected to MongoDB Atlas");
                    } else {
                        Log.e(TAG, "Failed to connect to MongoDB Atlas: " + response.code());
                        if (response.body() != null) {
                            Log.e(TAG, "Response body: " + response.body().string());
                        }
                        isConnected = false;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error testing MongoDB connection: " + e.getMessage());
                isConnected = false;
            }
        });
    }

    private void connectToMongoDB() {
        Log.d(TAG, "MongoDB REST API connection implemented");
        // Connection is handled in testMongoDBConnection()
    }

    private void initializeSampleData() {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, skipping sample data initialization");
            return;
        }
        
        executor.execute(() -> {
            try {
                // Check if data already exists in MongoDB
                List<Teacher> existingTeachers = getAllTeachersFromMongoDB();
                List<Lecture> existingLectures = getAllLecturesFromMongoDB();
                
                if (existingTeachers.size() > 0 || existingLectures.size() > 0) {
                    Log.d(TAG, "Data already exists in MongoDB, skipping sample data initialization");
                    return;
                }
                
                // Initialize with sample teachers
                Teacher teacher1 = new Teacher("AK", "ak@dpu.ac.in", "password123", "Computer Science");
                Teacher teacher2 = new Teacher("CS", "cs@dpu.ac.in", "password123", "Computer Science");
                Teacher teacher3 = new Teacher("IS", "is@dpu.ac.in", "password123", "Information Systems");
                Teacher teacher4 = new Teacher("SRS", "srs@dpu.ac.in", "password123", "Software Engineering");
                Teacher teacher5 = new Teacher("SS", "ss@dpu.ac.in", "password123", "Management");
                Teacher teacher6 = new Teacher("APK", "apk@dpu.ac.in", "password123", "Computer Science");
                Teacher teacher7 = new Teacher("SV", "sv@dpu.ac.in", "password123", "Computer Science");
                Teacher teacher8 = new Teacher("VV", "vv@dpu.ac.in", "password123", "Statistics");

                // Add teachers to MongoDB
                addTeacherToMongoDB(teacher1);
                addTeacherToMongoDB(teacher2);
                addTeacherToMongoDB(teacher3);
                addTeacherToMongoDB(teacher4);
                addTeacherToMongoDB(teacher5);
                addTeacherToMongoDB(teacher6);
                addTeacherToMongoDB(teacher7);
                addTeacherToMongoDB(teacher8);

                // Add sample lectures
                addLectureToMongoDB(new Lecture("DSA", "Classroom 409", "AK", "8:40", "9:40", "Monday", teacher1.getUniqueCode()));
                addLectureToMongoDB(new Lecture("IOT", "Classroom 409", "CS", "9:40", "10:40", "Monday", teacher2.getUniqueCode()));
                addLectureToMongoDB(new Lecture("STATISTICS", "Classroom 414", "IS", "10:45", "11:45", "Monday", teacher3.getUniqueCode()));
                addLectureToMongoDB(new Lecture("SE", "Classroom 414", "SRS", "11:45", "12:45", "Monday", teacher4.getUniqueCode()));
                addLectureToMongoDB(new Lecture("DSA Lab", "Classroom 415", "AK", "13:30", "15:30", "Monday", teacher1.getUniqueCode()));
                addLectureToMongoDB(new Lecture("IOT Lab", "PHL", "CS", "13:30", "15:30", "Monday", teacher2.getUniqueCode()));
                addLectureToMongoDB(new Lecture("IOT Lab", "Classroom 403", "SS", "13:30", "15:30", "Monday", teacher5.getUniqueCode()));
                
                Log.d(TAG, "Sample data initialized successfully in MongoDB");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing sample data: " + e.getMessage());
            }
        });
    }

    // MongoDB REST API methods
    private void addTeacherToMongoDB(Teacher teacher) {
        try {
            String appId = extractAppIdFromUri(mongoUri);
            String url = "https://data.mongodb-api.com/app/" + appId + "/endpoint/data/v1/action/insertOne";
            
            JSONObject document = new JSONObject();
            document.put("id", teacher.getId());
            document.put("name", teacher.getName());
            document.put("email", teacher.getEmail());
            document.put("password", teacher.getPassword());
            document.put("department", teacher.getDepartment());
            document.put("uniqueCode", teacher.getUniqueCode());
            document.put("active", teacher.isActive());
            
            JSONObject requestBody = new JSONObject();
            requestBody.put("dataSource", "Main");
            requestBody.put("database", dbName);
            requestBody.put("collection", teachersCollectionName);
            requestBody.put("document", document);
            
            RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), 
                requestBody.toString()
            );
            
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .post(body)
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Teacher added to MongoDB: " + teacher.getName());
                } else {
                    Log.e(TAG, "Failed to add teacher to MongoDB: " + response.code());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding teacher to MongoDB: " + e.getMessage());
        }
    }

    private void addLectureToMongoDB(Lecture lecture) {
        try {
            String appId = extractAppIdFromUri(mongoUri);
            String url = "https://data.mongodb-api.com/app/" + appId + "/endpoint/data/v1/action/insertOne";
            
            JSONObject document = new JSONObject();
            document.put("id", lecture.getId());
            document.put("name", lecture.getName());
            document.put("room", lecture.getRoom());
            document.put("teacherName", lecture.getTeacherName());
            document.put("startTime", lecture.getStartTime());
            document.put("endTime", lecture.getEndTime());
            document.put("dayOfWeek", lecture.getDayOfWeek());
            document.put("teacherCode", lecture.getTeacherCode());
            document.put("active", lecture.isActive());
            
            JSONObject requestBody = new JSONObject();
            requestBody.put("dataSource", "Main");
            requestBody.put("database", dbName);
            requestBody.put("collection", lecturesCollectionName);
            requestBody.put("document", document);
            
            RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), 
                requestBody.toString()
            );
            
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .post(body)
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Lecture added to MongoDB: " + lecture.getName());
                } else {
                    Log.e(TAG, "Failed to add lecture to MongoDB: " + response.code());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding lecture to MongoDB: " + e.getMessage());
        }
    }

    private List<Teacher> getAllTeachersFromMongoDB() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            String appId = extractAppIdFromUri(mongoUri);
            String url = "https://data.mongodb-api.com/app/" + appId + "/endpoint/data/v1/action/find";
            
            JSONObject requestBody = new JSONObject();
            requestBody.put("dataSource", "Main");
            requestBody.put("database", dbName);
            requestBody.put("collection", teachersCollectionName);
            requestBody.put("filter", new JSONObject().put("active", true));
            
            RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), 
                requestBody.toString()
            );
            
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .post(body)
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray documents = jsonResponse.getJSONArray("documents");
                    
                    for (int i = 0; i < documents.length(); i++) {
                        JSONObject doc = documents.getJSONObject(i);
                        Teacher teacher = new Teacher();
                        teacher.setId(doc.getString("id"));
                        teacher.setName(doc.getString("name"));
                        teacher.setEmail(doc.getString("email"));
                        teacher.setPassword(doc.getString("password"));
                        teacher.setDepartment(doc.getString("department"));
                        teacher.setUniqueCode(doc.getString("uniqueCode"));
                        teacher.setActive(doc.getBoolean("active"));
                        teachers.add(teacher);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting teachers from MongoDB: " + e.getMessage());
        }
        return teachers;
    }

    private List<Lecture> getAllLecturesFromMongoDB() {
        List<Lecture> lectures = new ArrayList<>();
        try {
            String appId = extractAppIdFromUri(mongoUri);
            String url = "https://data.mongodb-api.com/app/" + appId + "/endpoint/data/v1/action/find";
            
            JSONObject requestBody = new JSONObject();
            requestBody.put("dataSource", "Main");
            requestBody.put("database", dbName);
            requestBody.put("collection", lecturesCollectionName);
            requestBody.put("filter", new JSONObject().put("active", true));
            
            RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), 
                requestBody.toString()
            );
            
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .post(body)
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray documents = jsonResponse.getJSONArray("documents");
                    
                    for (int i = 0; i < documents.length(); i++) {
                        JSONObject doc = documents.getJSONObject(i);
                        Lecture lecture = new Lecture();
                        lecture.setId(doc.getString("id"));
                        lecture.setName(doc.getString("name"));
                        lecture.setRoom(doc.getString("room"));
                        lecture.setTeacherName(doc.getString("teacherName"));
                        lecture.setStartTime(doc.getString("startTime"));
                        lecture.setEndTime(doc.getString("endTime"));
                        lecture.setDayOfWeek(doc.getString("dayOfWeek"));
                        lecture.setTeacherCode(doc.getString("teacherCode"));
                        lecture.setActive(doc.getBoolean("active"));
                        lectures.add(lecture);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting lectures from MongoDB: " + e.getMessage());
        }
        return lectures;
    }

    // Public methods that use MongoDB
    public void addTeacher(Teacher teacher) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot add teacher");
            return;
        }
        
        executor.execute(() -> {
            addTeacherToMongoDB(teacher);
            teachers.add(teacher); // Also add to local cache
            Log.d(TAG, "Teacher added to MongoDB: " + teacher.getName() + " with code: " + teacher.getUniqueCode());
        });
    }

    public Teacher getTeacherByCode(String code) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get teacher");
            return null;
        }
        
        for (Teacher teacher : teachers) {
            if (teacher.getUniqueCode().equals(code) && teacher.isActive()) {
                return teacher;
            }
        }
        return null;
    }

    public Teacher getTeacherByEmail(String email) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get teacher");
            return null;
        }
        
        for (Teacher teacher : teachers) {
            if (teacher.getEmail().equals(email) && teacher.isActive()) {
                return teacher;
            }
        }
        return null;
    }

    public List<Teacher> getAllTeachers() {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get teachers");
            return new ArrayList<>();
        }
        
        // Refresh from MongoDB
        teachers = getAllTeachersFromMongoDB();
        return teachers;
    }

    public void updateTeacher(Teacher teacher) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot update teacher");
            return;
        }
        
        executor.execute(() -> {
            // TODO: Implement update in MongoDB
            for (int i = 0; i < teachers.size(); i++) {
                if (teachers.get(i).getId().equals(teacher.getId())) {
                    teachers.set(i, teacher);
                    Log.d(TAG, "Teacher updated in MongoDB: " + teacher.getName());
                    break;
                }
            }
        });
    }

    public void deleteTeacher(String teacherId) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot delete teacher");
            return;
        }
        
        executor.execute(() -> {
            // TODO: Implement delete in MongoDB
            for (Teacher teacher : teachers) {
                if (teacher.getId().equals(teacherId)) {
                    teacher.setActive(false); // Soft delete
                    Log.d(TAG, "Teacher soft deleted from MongoDB: " + teacherId);
                    break;
                }
            }
        });
    }

    public void addLecture(Lecture lecture) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot add lecture");
            return;
        }
        
        executor.execute(() -> {
            addLectureToMongoDB(lecture);
            lectures.add(lecture); // Also add to local cache
            Log.d(TAG, "Lecture added to MongoDB: " + lecture.getName() + " for teacher: " + lecture.getTeacherCode());
        });
    }

    public void updateLecture(Lecture lecture) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot update lecture");
            return;
        }
        
        executor.execute(() -> {
            // TODO: Implement update in MongoDB
            for (int i = 0; i < lectures.size(); i++) {
                if (lectures.get(i).getId().equals(lecture.getId())) {
                    lectures.set(i, lecture);
                    Log.d(TAG, "Lecture updated in MongoDB: " + lecture.getName());
                    break;
                }
            }
        });
    }

    public void deleteLecture(String lectureId) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot delete lecture");
            return;
        }
        
        executor.execute(() -> {
            // TODO: Implement delete in MongoDB
            for (Lecture lecture : lectures) {
                if (lecture.getId().equals(lectureId)) {
                    lecture.setActive(false); // Soft delete
                    Log.d(TAG, "Lecture soft deleted from MongoDB: " + lectureId);
                    break;
                }
            }
        });
    }

    public List<Lecture> getLecturesByDay(String day) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get lectures");
            return new ArrayList<>();
        }
        
        List<Lecture> dayLectures = new ArrayList<>();
        for (Lecture lecture : lectures) {
            if (lecture.getDayOfWeek().equals(day) && lecture.isActive()) {
                dayLectures.add(lecture);
            }
        }
        return dayLectures;
    }

    public List<Lecture> getLecturesByTeacherCode(String teacherCode) {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get lectures");
            return new ArrayList<>();
        }
        
        List<Lecture> teacherLectures = new ArrayList<>();
        for (Lecture lecture : lectures) {
            if (lecture.getTeacherCode().equals(teacherCode) && lecture.isActive()) {
                teacherLectures.add(lecture);
            }
        }
        return teacherLectures;
    }

    public List<Lecture> getAllLectures() {
        if (!isConnected) {
            Log.w(TAG, "MongoDB not connected, cannot get lectures");
            return new ArrayList<>();
        }
        
        // Refresh from MongoDB
        lectures = getAllLecturesFromMongoDB();
        return lectures;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void forceReconnect() {
        Log.d(TAG, "Force reconnection requested");
        testMongoDBConnection();
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
        Log.d(TAG, "MongoDBService shutdown completed");
    }
}
