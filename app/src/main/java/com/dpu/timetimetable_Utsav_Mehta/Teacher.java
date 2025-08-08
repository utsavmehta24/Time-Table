package com.dpu.timetimetable_Utsav_Mehta;

import java.util.UUID;

public class Teacher {
    private String id;
    private String name;
    private String email;
    private String password;
    private String department;
    private String uniqueCode;
    private boolean active;

    public Teacher() {
        this.id = UUID.randomUUID().toString();
        this.uniqueCode = generateUniqueCode();
        this.active = true;
    }

    public Teacher(String name, String email, String department) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.department = department;
        this.uniqueCode = generateUniqueCode();
        this.active = true;
    }

    public Teacher(String name, String email, String password, String department) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.uniqueCode = generateUniqueCode();
        this.active = true;
    }

    private String generateUniqueCode() {
        // Generate a 6-character alphanumeric code
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return code.toString();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUniqueCode() { return uniqueCode; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    public String getPassword() { return password; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setUniqueCode(String uniqueCode) { this.uniqueCode = uniqueCode; }
    public void setDepartment(String department) { this.department = department; }
    public void setActive(boolean active) { this.active = active; }
    public void setPassword(String password) { this.password = password; }
}
