package edu.campusconnect.dao;

import edu.campusconnect.model.ServiceRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcServiceRequestDao implements ServiceRequestDao {
    private final Connection connection;

    public JdbcServiceRequestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int add(ServiceRequest request) throws SQLException {
        String sql = "INSERT INTO service_requests " +
                "(student_name, student_number, category, description, priority, estimated_days, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, request.getStudentName());
            ps.setString(2, request.getStudentNumber());
            ps.setString(3, request.getCategory());
            ps.setString(4, request.getDescription());
            ps.setString(5, request.getPriority());
            ps.setInt(6, request.getEstimatedDays());
            ps.setString(7, request.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("The database did not return a request ID.");
    }

    @Override
    public List<ServiceRequest> findAll() throws SQLException {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM service_requests ORDER BY created_at ASC, request_id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) requests.add(mapRow(rs));
        }
        return requests;
    }

    @Override
    public Optional<ServiceRequest> findById(int id) throws SQLException {
        String sql = "SELECT * FROM service_requests WHERE request_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE service_requests SET status = ? WHERE request_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM service_requests WHERE request_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private ServiceRequest mapRow(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("created_at");
        return new ServiceRequest(
                rs.getInt("request_id"),
                rs.getString("student_name"),
                rs.getString("student_number"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getString("priority"),
                rs.getInt("estimated_days"),
                rs.getString("status"),
                timestamp == null ? null : timestamp.toLocalDateTime()
        );
    }
}
