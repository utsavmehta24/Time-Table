package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView teacherCard;
    private MaterialCardView studentCard;
    private FloatingActionButton infoFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enhanced);

        try {
            initializeViews();
            setupAnimations();
            setupClickListeners();
        } catch (Exception e) {
            android.util.Log.e("MainActivity", "Error in onCreate: " + e.getMessage());
            // Show a simple error dialog
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("App initialization failed. Please restart the app.")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        }
    }

    private void initializeViews() {
        teacherCard = findViewById(R.id.teacherCard);
        studentCard = findViewById(R.id.studentCard);
        infoFab = findViewById(R.id.infoFab);
    }

    private void setupAnimations() {
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        teacherCard.startAnimation(fadeIn);
        studentCard.startAnimation(fadeIn);
    }

    private void setupClickListeners() {
        teacherCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeacherLoginActivity.class);
            startActivity(intent);
        });

        studentCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentLoginActivity.class);
            startActivity(intent);
        });

        infoFab.setOnClickListener(v -> {
            // Show app information dialog
            showInfoDialog();
        });
        
        // Add MongoDB test button (for debugging)
        findViewById(R.id.mongodbTestBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, MongoDBTestActivity.class);
            startActivity(intent);
        });
    }

    private void showInfoDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("About TimeTable App")
                .setMessage("Enhanced TimeTable App v2.0\n\n" +
                        "Features:\n" +
                        "• Teacher View: Manage lectures and schedules\n" +
                        "• Student View: View timetables using teacher codes\n" +
                        "• Real-time updates with MongoDB\n" +
                        "• Modern Material Design 3 UI\n\n" +
                        "Developed by: Utsav Mehta")
                .setPositiveButton("OK", null)
                .show();
    }
}