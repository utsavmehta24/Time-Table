package com.dpu.timetimetable_Utsav_Mehta;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class EditLectureActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText nameInput;
    private TextInputEditText classroomInput;
    private TextInputEditText teacherInput;
    private TextInputEditText startTimeInput;
    private TextInputEditText endTimeInput;
    private Spinner daySpinner;
    private MaterialButton saveButton;
    private MaterialButton cancelButton;
    
    private MongoDBService mongoDBService;
    private Lecture currentLecture;
    private String lectureId;
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lecture);

        lectureId = getIntent().getStringExtra("LECTURE_ID");
        String teacherCode = getIntent().getStringExtra("TEACHER_CODE");
        String teacherName = getIntent().getStringExtra("TEACHER_NAME");
        mongoDBService = MongoDBService.getInstance(this);
        
        initializeViews();
        setupToolbar();
        setupDaySpinner();
        loadLectureData();
        setupClickListeners();
        
        // If no lecture ID, this is a new lecture - pre-fill teacher info
        if (lectureId == null && teacherName != null) {
            teacherInput.setText(teacherName);
            teacherInput.setEnabled(false); // Don't allow editing teacher name
        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        nameInput = findViewById(R.id.nameInput);
        classroomInput = findViewById(R.id.classroomInput);
        teacherInput = findViewById(R.id.teacherInput);
        startTimeInput = findViewById(R.id.startTimeInput);
        endTimeInput = findViewById(R.id.endTimeInput);
        daySpinner = findViewById(R.id.daySpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void setupDaySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (lectureId != null) {
            getSupportActionBar().setTitle("Edit Lecture");
        } else {
            getSupportActionBar().setTitle("Add New Lecture");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadLectureData() {
        if (lectureId != null) {
            currentLecture = findLectureById(lectureId);
            if (currentLecture != null) {
                nameInput.setText(currentLecture.getName());
                classroomInput.setText(currentLecture.getClassroom());
                teacherInput.setText(currentLecture.getTeacher());
                startTimeInput.setText(currentLecture.getStartTime());
                endTimeInput.setText(currentLecture.getEndTime());
                
                // Set the selected day in spinner
                String currentDay = currentLecture.getDayOfWeek();
                for (int i = 0; i < days.length; i++) {
                    if (days[i].equals(currentDay)) {
                        daySpinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private Lecture findLectureById(String id) {
        List<Lecture> allLectures = mongoDBService.getAllLectures();
        for (Lecture lecture : allLectures) {
            if (lecture.getId().equals(id)) {
                return lecture;
            }
        }
        return null;
    }

    private void setupClickListeners() {
        saveButton.setOnClickListener(v -> saveLecture());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveLecture() {
        String name = nameInput.getText().toString().trim();
        String classroom = classroomInput.getText().toString().trim();
        String teacher = teacherInput.getText().toString().trim();
        String startTime = startTimeInput.getText().toString().trim();
        String endTime = endTimeInput.getText().toString().trim();
        String day = daySpinner.getSelectedItem().toString();

        if (name.isEmpty() || classroom.isEmpty() || teacher.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentLecture != null) {
            // Update existing lecture
            currentLecture.setName(name);
            currentLecture.setClassroom(classroom);
            currentLecture.setTeacher(teacher);
            currentLecture.setStartTime(startTime);
            currentLecture.setEndTime(endTime);
            currentLecture.setDayOfWeek(day);

            mongoDBService.updateLecture(currentLecture);
            Toast.makeText(this, "Lecture updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Create new lecture
            String teacherCode = getIntent().getStringExtra("TEACHER_CODE");
            Lecture newLecture = new Lecture(name, classroom, teacher, startTime, endTime, day, teacherCode);
            mongoDBService.addLecture(newLecture);
            Toast.makeText(this, "Lecture added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
