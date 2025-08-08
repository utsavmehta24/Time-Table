package com.dpu.timetimetable_Utsav_Mehta;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentTimetableActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TabLayout dayTabLayout;
    private RecyclerView lectureRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    
    private EnhancedLectureAdapter adapter;
    private MongoDBService mongoDBService;
    private String teacherCode;
    private String teacherName;
    private String currentDay = "Monday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);

        // Get teacher info from intent
        teacherCode = getIntent().getStringExtra("TEACHER_CODE");
        teacherName = getIntent().getStringExtra("TEACHER_NAME");

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
        
        mongoDBService = MongoDBService.getInstance(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student View");
        getSupportActionBar().setSubtitle(teacherName + " (" + teacherCode + ")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupTabLayout() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            dayTabLayout.addTab(dayTabLayout.newTab().setText(day));
        }

        // Set current day as selected
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String today = getDayName(dayOfWeek);
        currentDay = today;
        
        for (int i = 0; i < dayTabLayout.getTabCount(); i++) {
            if (dayTabLayout.getTabAt(i).getText().equals(today)) {
                dayTabLayout.selectTab(dayTabLayout.getTabAt(i));
                break;
            }
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

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "Sunday";
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            default: return "Monday";
        }
    }

    private void setupRecyclerView() {
        adapter = new EnhancedLectureAdapter(this, new ArrayList<>(), false);
        lectureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lectureRecyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::loadLectures);
    }

    private void loadLectures() {
        List<Lecture> teacherLectures = mongoDBService.getLecturesByTeacherCode(teacherCode);
        List<Lecture> dayLectures = new ArrayList<>();
        
        for (Lecture lecture : teacherLectures) {
            if (lecture.getDayOfWeek().equals(currentDay)) {
                dayLectures.add(lecture);
            }
        }
        
        if (dayLectures.isEmpty()) {
            // Add a placeholder lecture for empty days
            dayLectures.add(new Lecture("No Lectures Today", "Enjoy your day!", "Free time"));
        }
        
        adapter.updateLectures(dayLectures);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
