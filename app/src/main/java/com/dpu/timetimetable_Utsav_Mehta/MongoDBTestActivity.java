package com.dpu.timetimetable_Utsav_Mehta;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MongoDBTestActivity extends AppCompatActivity {
    private static final String TAG = "MongoDBTestActivity";
    private MongoDBService mongoDBService;
    private TextView statusText;
    private TextView dataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mongodb_test);

        mongoDBService = MongoDBService.getInstance(this);
        statusText = findViewById(R.id.statusText);
        dataText = findViewById(R.id.dataText);

        Button testConnectionBtn = findViewById(R.id.testConnectionBtn);
        Button addSampleDataBtn = findViewById(R.id.addSampleDataBtn);
        Button checkDataBtn = findViewById(R.id.checkDataBtn);
        Button forceReconnectBtn = findViewById(R.id.forceReconnectBtn);

        testConnectionBtn.setOnClickListener(v -> testConnection());
        addSampleDataBtn.setOnClickListener(v -> addSampleData());
        checkDataBtn.setOnClickListener(v -> checkData());
        forceReconnectBtn.setOnClickListener(v -> forceReconnect());

        // Initial status check
        updateStatus();
    }

    private void testConnection() {
        Log.d(TAG, "Testing MongoDB connection...");
        statusText.setText("Testing connection...");
        
        boolean isConnected = mongoDBService.isConnected();
        if (isConnected) {
            statusText.setText("✅ Connected to MongoDB");
            Log.d(TAG, "MongoDB connection successful");
        } else {
            statusText.setText("❌ Not connected to MongoDB");
            Log.e(TAG, "MongoDB connection failed");
        }
    }

    private void addSampleData() {
        Log.d(TAG, "Adding sample data...");
        statusText.setText("Adding sample data...");
        
        // Add a test teacher
        Teacher testTeacher = new Teacher("Test Teacher", "test@dpu.ac.in", "password123", "Computer Science");
        mongoDBService.addTeacher(testTeacher);
        
        // Add a test lecture
        Lecture testLecture = new Lecture("Test Lecture", "Test Room", "Test Teacher", "10:00", "11:00", "Monday", testTeacher.getUniqueCode());
        mongoDBService.addLecture(testLecture);
        
        statusText.setText("Sample data added. Check logs for details.");
        Log.d(TAG, "Sample data added successfully");
    }

    private void checkData() {
        Log.d(TAG, "Checking data in MongoDB...");
        statusText.setText("Checking data...");
        
        List<Teacher> teachers = mongoDBService.getAllTeachers();
        List<Lecture> lectures = mongoDBService.getAllLectures();
        
        StringBuilder dataInfo = new StringBuilder();
        dataInfo.append("Teachers: ").append(teachers.size()).append("\n");
        dataInfo.append("Lectures: ").append(lectures.size()).append("\n\n");
        
        dataInfo.append("Teacher Details:\n");
        for (Teacher teacher : teachers) {
            dataInfo.append("- ").append(teacher.getName()).append(" (").append(teacher.getEmail()).append(")\n");
        }
        
        dataInfo.append("\nLecture Details:\n");
        for (Lecture lecture : lectures) {
            dataInfo.append("- ").append(lecture.getName()).append(" in ").append(lecture.getRoom()).append(" by ").append(lecture.getTeacherName()).append("\n");
        }
        
        dataText.setText(dataInfo.toString());
        statusText.setText("Data check completed");
        Log.d(TAG, "Data check completed - Teachers: " + teachers.size() + ", Lectures: " + lectures.size());
    }

    private void forceReconnect() {
        Log.d(TAG, "Forcing MongoDB reconnection...");
        statusText.setText("Forcing reconnection...");
        
        mongoDBService.forceReconnect();
        
        // Wait a bit and then check connection
        statusText.postDelayed(() -> {
            updateStatus();
        }, 2000);
    }

    private void updateStatus() {
        boolean isConnected = mongoDBService.isConnected();
        if (isConnected) {
            statusText.setText("✅ Connected to MongoDB");
        } else {
            statusText.setText("❌ Not connected to MongoDB");
        }
    }
}
