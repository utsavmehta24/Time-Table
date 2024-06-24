package com.dpu.timetimetable_Utsav_Mehta;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView lectureListView = findViewById(R.id.lectureListView);

        // Get the current day dynamically
        String currentDay = getCurrentDayOfWeek();

        // Create an ArrayList of Lecture objects for the current day
        ArrayList<Lecture> lecturesForToday = getLecturesForDay(currentDay);

        // Create a custom adapter and set it to the ListView
        LectureAdapter adapter = new LectureAdapter(this, lecturesForToday);
        lectureListView.setAdapter(adapter);// Reference the button by its ID
        Button nextButton = findViewById(R.id.nextButton);

        // Set an OnClickListener to open the next activity when the button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the next activity (replace NextActivity.class with the actual name of your next activity)
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                // Start the next activity
                startActivity(intent);
            }
        });

    }

    private String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Map the day of the week to a string
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
    }

    private ArrayList<Lecture> getLecturesForDay(String day) {
        ArrayList<Lecture> lectures = new ArrayList<>();

        switch (day) {
            case "Monday":
                lectures.add(new Lecture("DSA", "Classroom 409", "AK\n8:40-9:40"));
                lectures.add(new Lecture("IOT", "Classroom 409", "CS\n9:40-10:40"));
                lectures.add(new Lecture("STAISTICS", "Classroom 414", "IS\n10:45-11:45"));
                lectures.add(new Lecture("SE", "Classroom 414", "SRS\n11:45-12:45"));
                lectures.add(new Lecture("1.DSAL\t2.IOTL\t3.IOTL", "Classroom 415\tPHL\t403", "AK\tCS\tSS\n1:30-3:30"));
                break;
            case "Tuesday":
                lectures.add(new Lecture("SE", "Classroom 409", "SRS\n8:40-9:40"));
                lectures.add(new Lecture("STAISTICS", "Classroom 409", "IS\n9:40-10:40"));
                lectures.add(new Lecture("DSA", "Classroom BS-04", "AK\n10:45-11:45"));
                lectures.add(new Lecture("MIS", "Classroom BS-04", "SS\n11:45-12:45"));
                lectures.add(new Lecture("1.IOTL\t2.PBL\t3.DSAL", "Classroom 403\tPHL\t415", "CS\tAPK\tAK\n1:30-3:30"));
                break;
            case "Wednesday":
                lectures.add(new Lecture("1.PBL\t2.DSAL\t3.PBL", "Classroom 405\t404\t415", "APK\tAK\tSV\n8:40-10:40"));
                lectures.add(new Lecture("MIS", "Classroom 811", "SS\n10:45-11:45"));
                lectures.add(new Lecture("DSA", "Classroom 811", "AK\n11:45-12:45"));
                lectures.add(new Lecture("1.DSAL\t2.IOTL\t3.PBL", "Classroom 403\tPHL\t415", "CS\tAPK\tAK\n1:30-3:30"));

                break;
            case "Thursday":
                lectures.add(new Lecture("1.PBL\t2.PBL\t3.IOTL", "Classroom 405\t407\tPHL", "APK\tAPK\tCS\n8:40-10:40"));
                lectures.add(new Lecture("CC", "Classroom 414", "APK\n10:45-11:45"));
                lectures.add(new Lecture("SE", "Classroom 414", "SRS\n11:45-12:45"));
                lectures.add(new Lecture("IOT", "Classroom BS-04", "CS\n1:30-2:30"));
                lectures.add(new Lecture("STAISTICS", "Classroom BS-04", "CS\n2:30-3:30"));
                break;
            case "Friday":
                lectures.add(new Lecture("1.IOTL\t2.DSAL\t3.DSAL", "Classroom 415\t416\t417", "CS\tAK\tAK\n8:40-10:40"));
                lectures.add(new Lecture("MIS", "Classroom BS-04", "SS\n10:45-11:45"));
                lectures.add(new Lecture("IOT", "Classroom BS-04", "CS\n11:45-12:45"));
                lectures.add(new Lecture("STAISTICS", "Classroom BS-04", "VV\n1:30-2:30"));
                lectures.add(new Lecture("LH" , "-" , "-"));
                break;
            case "Saturday":
                lectures.add(new Lecture("No Lecture Today \nAnd\nTomorrow", "ENJOY", "Whole day\nIf you are lonely contact the mail given below.."));
                break;
            case "Sunday":
                lectures.add(new Lecture("No Lecture Today", "ENJOY", "Whole day \nGet Ready for Tomorrow\nIf you are lonely contact the mail given below.."));
                break;
        }

        return lectures;
    }
}
