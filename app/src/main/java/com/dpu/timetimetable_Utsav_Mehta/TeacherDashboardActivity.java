package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeacherDashboardActivity extends AppCompatActivity implements EnhancedLectureAdapter.OnLectureClickListener {

    private MaterialToolbar toolbar;
    private TabLayout dayTabLayout;
    private RecyclerView lectureRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton addLectureFab;
    
    private EnhancedLectureAdapter adapter;
    private MongoDBService mongoDBService;
    private String teacherId;
    private String teacherName;
    private String teacherCode;
    private String currentDay = "Monday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        // Get teacher info from intent
        teacherId = getIntent().getStringExtra("TEACHER_ID");
        teacherName = getIntent().getStringExtra("TEACHER_NAME");
        teacherCode = getIntent().getStringExtra("TEACHER_CODE");

        initializeViews();
        setupToolbar();
        setupTabLayout();
        setupRecyclerView();
        setupSwipeRefresh();
        loadLectures();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        dayTabLayout = findViewById(R.id.dayTabLayout);
        lectureRecyclerView = findViewById(R.id.lectureRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        addLectureFab = findViewById(R.id.addLectureFab);
        
        mongoDBService = MongoDBService.getInstance(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher Dashboard");
        getSupportActionBar().setSubtitle(teacherName + " (" + teacherCode + ")");
    }

    private void setupTabLayout() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            dayTabLayout.addTab(dayTabLayout.newTab().setText(day));
        }

        dayTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentDay = tab.getText().toString();
                loadLectures();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerView() {
        adapter = new EnhancedLectureAdapter(this, new ArrayList<>(), true);
        adapter.setOnLectureClickListener(this);
        lectureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lectureRecyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::loadLectures);
        
        // Setup FloatingActionButton click listener
        addLectureFab.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditLectureActivity.class);
            intent.putExtra("TEACHER_CODE", teacherCode);
            intent.putExtra("TEACHER_NAME", teacherName);
            startActivity(intent);
        });
    }

    private void loadLectures() {
        // Initialize MongoDBService if not already done
        mongoDBService = MongoDBService.getInstance(this);
        
        List<Lecture> teacherLectures = mongoDBService.getLecturesByTeacherCode(teacherCode);
        List<Lecture> dayLectures = new ArrayList<>();
        
        for (Lecture lecture : teacherLectures) {
            if (lecture.getDayOfWeek().equals(currentDay)) {
                dayLectures.add(lecture);
            }
        }
        
        adapter.updateLectures(dayLectures);
        swipeRefreshLayout.setRefreshing(false);
        
        // Show message if no lectures found
        if (dayLectures.isEmpty()) {
            Toast.makeText(this, "No lectures for " + currentDay, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLectureClick(Lecture lecture) {
        // Show lecture details
        showLectureDetails(lecture);
    }

    @Override
    public void onLectureEdit(Lecture lecture) {
        // Open edit lecture activity
        Intent intent = new Intent(this, EditLectureActivity.class);
        intent.putExtra("LECTURE_ID", lecture.getId());
        startActivity(intent);
    }

    @Override
    public void onLectureDelete(Lecture lecture) {
        // Show delete confirmation
        new AlertDialog.Builder(this)
                .setTitle("Delete Lecture")
                .setMessage("Are you sure you want to delete " + lecture.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    mongoDBService.deleteLecture(lecture.getId());
                    loadLectures();
                    Toast.makeText(this, "Lecture deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showLectureDetails(Lecture lecture) {
        new AlertDialog.Builder(this)
                .setTitle(lecture.getName())
                .setMessage("Classroom: " + lecture.getClassroom() + "\n" +
                        "Teacher: " + lecture.getTeacher() + "\n" +
                        "Time: " + lecture.getTimeRange() + "\n" +
                        "Day: " + lecture.getDayOfWeek())
                .setPositiveButton("Edit", (dialog, which) -> onLectureEdit(lecture))
                .setNegativeButton("Close", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teacher_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_profile) {
            showProfileDialog();
            return true;
        } else if (id == R.id.action_settings) {
            // Show logout confirmation
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        // Clear any stored data and go back to main activity
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void showProfileDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Teacher Profile")
                .setMessage("Name: " + teacherName + "\n" +
                        "Code: " + teacherCode + "\n" +
                        "ID: " + teacherId)
                .setPositiveButton("OK", null)
                .show();
    }
}
