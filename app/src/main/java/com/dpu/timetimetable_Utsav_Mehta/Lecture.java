package com.dpu.timetimetable_Utsav_Mehta;

import java.util.UUID;

public class Lecture {
    private String id;
    private String name;
    private String room;
    private String teacherName;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String teacherCode;
    private boolean active;

    public Lecture() {
        this.id = UUID.randomUUID().toString();
        this.active = true;
    }

    public Lecture(String name, String room, String teacherName, String startTime, String endTime, String dayOfWeek, String teacherCode) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.room = room;
        this.teacherName = teacherName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacherCode = teacherCode;
        this.active = true;
    }

    // Legacy constructor for backward compatibility
    public Lecture(String name, String room, String teacher) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.room = room;
        this.teacherName = teacher;
        this.startTime = "";
        this.endTime = "";
        this.dayOfWeek = "";
        this.teacherCode = "";
        this.active = true;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getRoom() { return room; }
    public String getTeacherName() { return teacherName; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getTeacherCode() { return teacherCode; }
    public boolean isActive() { return active; }

    // Legacy getters for backward compatibility
    public String getClassroom() { return room; }
    public String getTeacher() { return teacherName; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRoom(String room) { this.room = room; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setTeacherCode(String teacherCode) { this.teacherCode = teacherCode; }
    public void setActive(boolean active) { this.active = active; }

    // Legacy setters for backward compatibility
    public void setClassroom(String classroom) { this.room = classroom; }
    public void setTeacher(String teacher) { this.teacherName = teacher; }

    public String getTimeRange() {
        if (startTime.isEmpty() || endTime.isEmpty()) {
            return "";
        }
        return startTime + " - " + endTime;
    }

    public String getFormattedInfo() {
        return teacherName + "\n" + getTimeRange();
    }
}

