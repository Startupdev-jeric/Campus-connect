package edu.campusconnect.model;

import java.time.LocalDateTime;

public class ServiceRequest {
    private int requestId;
    private String studentName;
    private String studentNumber;
    private String category;
    private String description;
    private String priority;
    private int estimatedDays;
    private String status;
    private LocalDateTime createdAt;

    public ServiceRequest(int requestId, String studentName, String studentNumber,
                          String category, String description, String priority,
                          int estimatedDays, String status, LocalDateTime createdAt) {
        this.requestId = requestId;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.estimatedDays = estimatedDays;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getRequestId() { return requestId; }
    public String getStudentName() { return studentName; }
    public String getStudentNumber() { return studentNumber; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public int getEstimatedDays() { return estimatedDays; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setRequestId(int requestId) { this.requestId = requestId; }
    public void setStatus(String status) { this.status = status; }
}
