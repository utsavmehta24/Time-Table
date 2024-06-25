# College Lecture Timetable App

<p align="center">
  <img src="https://r-dumpsite.netlify.app/img/image.png" alt="App Icon" style="width: 200px;"/>
</p>
<p align="center"><em>App Icon: Displaying the current day's timetable.</em></p>

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [License](#license)
- [Contributing](#contributing)
- [Contact](#contact)

## Overview
The **College Lecture Timetable App** is designed to help students easily access and view their lecture schedules on a daily basis. The app dynamically displays the lectures for the current day, helping students stay organized and keep track of their classes.

## Features
- **Daily Timetable**: Automatically displays the timetable for the current day.
- **Lecture Details**: Provides information about lecture subjects, classroom locations, and time slots.
- **Interactive Navigation**: Navigate through different days to view lecture schedules.
- **User-Friendly Interface**: Simple and intuitive UI for easy access to timetable details.

## Screenshots

<div align="center">
  <img src="https://r-dumpsite.netlify.app/img/screenshot-1.jpg" alt="Main Screen" style="width: 300px; height: auto;" />
  <p><em>Landing Screen: Credentails of timetable Application.</em></p>
</div>

<div align="center">
  <img src="https://r-dumpsite.netlify.app/img/screenshot-2.jpg" alt="Lecture Details" style="width: 300px; height: auto;" />
  <p><em>Lecture Details: Information about each lecture.</em></p>
</div>


## Installation
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/utsavmehta24/Time-Table
    cd TimeTable
    ```

2. **Open in Android Studio**:
    - Launch Android Studio.
    - Select `File -> Open...` and navigate to the project directory.

3. **Build the Project**:
    - Click on the `Build` menu and select `Make Project`.

4. **Run the App**:
    - Connect an Android device or start an emulator.
    - Click the `Run` button or select `Run -> Run 'app'`.

## Usage
1. **Launch the App**: Open the app on your device.
2. **View Today's Timetable**: The app will display the current day's lectures automatically.
3. **Navigate**: Use the navigation buttons to switch between different days.
4. **Lecture Details**: Click on a lecture to view detailed information such as subject, location, and timing.

## Code Structure

### MainActivity.java
This is the splash screen activity that launches and transitions to the main timetable view after a short delay.

```java
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
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }, 1500);
    }
}
```

### MainActivity2.java
This activity handles displaying the timetable for the current day. It retrieves the current day's lectures and displays them in a list.

```java
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

        String currentDay = getCurrentDayOfWeek();
        ArrayList<Lecture> lecturesForToday = getLecturesForDay(currentDay);

        LectureAdapter adapter = new LectureAdapter(this, lecturesForToday);
        lectureListView.setAdapter(adapter);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });
    }

    private String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Day mapping logic
    }

    private ArrayList<Lecture> getLecturesForDay(String day) {
        ArrayList<Lecture> lectures = new ArrayList<>();
        // Populate lectures based on the day
    }
}
```

### MainActivity3.java
Handles additional functionality and possibly navigation for future extensions.

```java
package com.dpu.timetimetable_Utsav_Mehta;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ListView lectureListView = findViewById(R.id.lectureListView);

        String currentDay = getCurrentDayOfWeek();
        ArrayList<Lecture> lecturesForToday = getLecturesForDay(currentDay);

        LectureAdapter adapter = new LectureAdapter(this, lecturesForToday);
        lectureListView.setAdapter(adapter);
    }

    private String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Day mapping logic
    }

    private ArrayList<Lecture> getLecturesForDay(String day) {
        ArrayList<Lecture> lectures = new ArrayList<>();
        // Populate lectures based on the day
    }
}
```

## License
This project is licensed under the MIT License.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any features or fixes you would like to see.

## Contact
For any inquiries or support, feel free to contact [Utsav Mehta](mailto:utsavmehta24072003@gmail.com).

---

*Enjoy using the app and stay organized with your lectures!* 🚀