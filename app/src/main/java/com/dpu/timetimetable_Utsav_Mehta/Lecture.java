package com.dpu.timetimetable_Utsav_Mehta;

public class Lecture {
    private final String name;
    private final String classroom;
    private final String teacher;


    public Lecture(String name, String classroom, String teacher) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
    }
    public String getName() {
        return name;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getTeacher() {
        return teacher;
    }


}

