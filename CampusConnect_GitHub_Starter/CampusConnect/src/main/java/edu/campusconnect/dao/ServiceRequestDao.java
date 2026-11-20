package edu.campusconnect.dao;

import edu.campusconnect.model.ServiceRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ServiceRequestDao {
    int add(ServiceRequest request) throws SQLException;
    List<ServiceRequest> findAll() throws SQLException;
    Optional<ServiceRequest> findById(int id) throws SQLException;
    boolean updateStatus(int id, String status) throws SQLException;
    boolean delete(int id) throws SQLException;
}
