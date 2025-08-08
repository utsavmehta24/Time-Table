package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class StudentLoginActivity extends AppCompatActivity {

    private TextInputEditText teacherCodeInput;
    private MaterialButton viewTimetableButton;
    private MaterialTextView titleText;
    private MaterialTextView instructionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        teacherCodeInput = findViewById(R.id.teacherCodeInput);
        viewTimetableButton = findViewById(R.id.viewTimetableButton);
        titleText = findViewById(R.id.titleText);
        instructionText = findViewById(R.id.instructionText);
    }

    private void setupClickListeners() {
        viewTimetableButton.setOnClickListener(v -> handleViewTimetable());
    }

    private void handleViewTimetable() {
        String teacherCode = teacherCodeInput.getText().toString().trim().toUpperCase();

        if (teacherCode.isEmpty()) {
            Toast.makeText(this, "Please enter a teacher code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate teacher code
        MongoDBService mongoDBService = MongoDBService.getInstance(this);
        Teacher teacher = mongoDBService.getTeacherByCode(teacherCode);

        if (teacher != null) {
            // Teacher found, proceed to student view
            Intent intent = new Intent(this, StudentTimetableActivity.class);
            intent.putExtra("TEACHER_CODE", teacherCode);
            intent.putExtra("TEACHER_NAME", teacher.getName());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid teacher code. Please check and try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
