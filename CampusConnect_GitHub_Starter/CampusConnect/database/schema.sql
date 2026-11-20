CREATE DATABASE IF NOT EXISTS campusconnect;
USE campusconnect;

CREATE TABLE IF NOT EXISTS service_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    student_number VARCHAR(30) NOT NULL,
    category VARCHAR(40) NOT NULL,
    description VARCHAR(500) NOT NULL,
    priority VARCHAR(10) NOT NULL,
    estimated_days INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_student_request UNIQUE (student_number, description(100)),
    CONSTRAINT chk_priority CHECK (priority IN ('Low', 'Medium', 'High')),
    CONSTRAINT chk_status CHECK (status IN ('Pending', 'In Progress', 'Completed', 'Cancelled')),
    CONSTRAINT chk_estimated_days CHECK (estimated_days BETWEEN 1 AND 365)
);
