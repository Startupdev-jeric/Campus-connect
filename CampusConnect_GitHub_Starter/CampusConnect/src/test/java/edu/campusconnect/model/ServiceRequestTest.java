package edu.campusconnect.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestTest {
    @Test
    void constructorStoresRequestValues() {
        ServiceRequest request = new ServiceRequest(
                1, "Juan Dela Cruz", "2026-00001", "Academic",
                "Enrollment certificate", "Medium", 3, "Pending", null
        );

        assertEquals(1, request.getRequestId());
        assertEquals("Juan Dela Cruz", request.getStudentName());
        assertEquals("2026-00001", request.getStudentNumber());
        assertEquals("Pending", request.getStatus());
    }

    @Test
    void statusCanBeUpdated() {
        ServiceRequest request = new ServiceRequest(
                2, "Maria Santos", "2026-00002", "IT Support",
                "Portal login issue", "High", 1, "Pending", null
        );

        request.setStatus("In Progress");

        assertEquals("In Progress", request.getStatus());
    }
}
