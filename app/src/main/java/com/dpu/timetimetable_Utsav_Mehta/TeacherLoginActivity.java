package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class TeacherLoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private MaterialButton registerButton;
    private MaterialTextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        titleText = findViewById(R.id.titleText);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeacherRegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if teacher exists by email and password
        MongoDBService mongoDBService = MongoDBService.getInstance(this);
        Teacher existingTeacher = mongoDBService.getTeacherByEmail(email);

        if (existingTeacher != null && existingTeacher.getPassword() != null && 
            existingTeacher.getPassword().equals(password)) {
            // Teacher exists and password matches, proceed to dashboard
            Intent intent = new Intent(this, TeacherDashboardActivity.class);
            intent.putExtra("TEACHER_ID", existingTeacher.getId());
            intent.putExtra("TEACHER_NAME", existingTeacher.getName());
            intent.putExtra("TEACHER_CODE", existingTeacher.getUniqueCode());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password. Please check your credentials.", Toast.LENGTH_SHORT).show();
        }
    }
}
