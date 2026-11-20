USE campusconnect;

INSERT INTO service_requests
(student_name, student_number, category, description, priority, estimated_days, status)
VALUES
('Juan Dela Cruz', '2026-00001', 'Academic', 'Request for certificate of enrollment', 'Medium', 3, 'Pending'),
('Maria Santos', '2026-00002', 'IT Support', 'Cannot access student portal', 'High', 1, 'In Progress'),
('Pedro Reyes', '2026-00003', 'Facilities', 'Classroom projector needs checking', 'Low', 5, 'Pending');
