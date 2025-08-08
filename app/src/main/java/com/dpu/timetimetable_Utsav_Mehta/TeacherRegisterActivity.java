package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class TeacherRegisterActivity extends AppCompatActivity {

    private TextInputEditText nameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;
    private TextInputEditText departmentInput;
    private MaterialButton registerButton;
    private MaterialButton backToLoginButton;
    private MaterialTextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        departmentInput = findViewById(R.id.departmentInput);
        registerButton = findViewById(R.id.registerButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);
        titleText = findViewById(R.id.titleText);
    }

    private void setupClickListeners() {
        registerButton.setOnClickListener(v -> handleRegister());
        backToLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeacherLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleRegister() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        String department = departmentInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if teacher already exists
        MongoDBService mongoDBService = MongoDBService.getInstance(this);
        Teacher existingTeacher = mongoDBService.getTeacherByEmail(email);

        if (existingTeacher != null) {
            Toast.makeText(this, "Teacher with this email already exists. Please login instead.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new teacher
        Teacher newTeacher = new Teacher(name, email, password, department);
        mongoDBService.addTeacher(newTeacher);

        Toast.makeText(this, "Teacher registered successfully! Your code: " + newTeacher.getUniqueCode(), Toast.LENGTH_LONG).show();

        // Proceed to dashboard
        Intent intent = new Intent(this, TeacherDashboardActivity.class);
        intent.putExtra("TEACHER_ID", newTeacher.getId());
        intent.putExtra("TEACHER_NAME", newTeacher.getName());
        intent.putExtra("TEACHER_CODE", newTeacher.getUniqueCode());
        startActivity(intent);
        finish();
    }
}
