package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed( () -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }, 1500);
    }
}