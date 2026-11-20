package edu.campusconnect.service;

import edu.campusconnect.dao.ServiceRequestDao;
import edu.campusconnect.exception.ValidationException;
import edu.campusconnect.model.ServiceRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class RequestService {
    public static final List<String> CATEGORIES =
            List.of("Academic", "Facilities", "IT Support", "Student Records", "Other");
    public static final List<String> PRIORITIES = List.of("Low", "Medium", "High");
    public static final List<String> STATUSES =
            List.of("Pending", "In Progress", "Completed", "Cancelled");

    private final ServiceRequestDao dao;

    public RequestService(ServiceRequestDao dao) {
        this.dao = dao;
    }

    public int add(String name, String number, String category, String description,
                   String priority, int estimate) throws ValidationException, SQLException {
        validate(name, number, category, description, priority, estimate);
        ServiceRequest request = new ServiceRequest(
                0, name.trim(), number.trim(), category, description.trim(),
                priority, estimate, "Pending", LocalDateTime.now()
        );
        return dao.add(request);
    }

    public List<ServiceRequest> getAll() throws SQLException {
        return dao.findAll();
    }

    public List<ServiceRequest> search(String keyword) throws SQLException {
        String term = keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
        return dao.findAll().stream()
                .filter(r -> (r.getStudentName() + " " + r.getStudentNumber() + " " +
                        r.getDescription() + " " + r.getCategory()).toLowerCase(Locale.ROOT).contains(term))
                .toList();
    }

    public boolean updateStatus(int id, String newStatus)
            throws ValidationException, SQLException {
        if (!STATUSES.contains(newStatus)) {
            throw new ValidationException("Choose a valid status.");
        }
        ServiceRequest current = dao.findById(id)
                .orElseThrow(() -> new ValidationException("Request ID was not found."));
        if (!isAllowedTransition(current.getStatus(), newStatus)) {
            throw new ValidationException("That status change is not allowed in the starter rules.");
        }
        return dao.updateStatus(id, newStatus);
    }

    // Starter transition rules only. Replace these with the rules approved in your TSAs.
    private boolean isAllowedTransition(String current, String next) {
        if (current.equals(next)) return true;
        return switch (current) {
            case "Pending" -> next.equals("In Progress") || next.equals("Cancelled");
            case "In Progress" -> next.equals("Completed") || next.equals("Cancelled");
            default -> false;
        };
    }

    public boolean delete(int id) throws SQLException {
        return dao.delete(id);
    }

    public Optional<ServiceRequest> processNext() throws SQLException {
        return dao.findAll().stream()
                .filter(r -> r.getStatus().equals("Pending"))
                .min(Comparator.comparing(ServiceRequest::getCreatedAt,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparingInt(ServiceRequest::getRequestId));
    }

    public boolean startNext(int id) throws ValidationException, SQLException {
        return updateStatus(id, "In Progress");
    }

    public String createSummary(List<ServiceRequest> requests) {
        long pending = requests.stream().filter(r -> r.getStatus().equals("Pending")).count();
        long inProgress = requests.stream().filter(r -> r.getStatus().equals("In Progress")).count();
        long completed = requests.stream().filter(r -> r.getStatus().equals("Completed")).count();
        long cancelled = requests.stream().filter(r -> r.getStatus().equals("Cancelled")).count();
        return "Total requests: " + requests.size() +
                "\nPending: " + pending +
                "\nIn Progress: " + inProgress +
                "\nCompleted: " + completed +
                "\nCancelled: " + cancelled;
    }

    private void validate(String name, String number, String category, String description,
                          String priority, int estimate) throws ValidationException {
        if (name == null || name.trim().isEmpty()) throw new ValidationException("Student name is required.");
        if (number == null || number.trim().isEmpty()) throw new ValidationException("Student number is required.");
        if (!CATEGORIES.contains(category)) throw new ValidationException("Choose a valid category.");
        if (description == null || description.trim().isEmpty()) throw new ValidationException("Description is required.");
        if (!PRIORITIES.contains(priority)) throw new ValidationException("Choose a valid priority.");
        if (estimate < 1 || estimate > 365) throw new ValidationException("Estimate must be from 1 to 365 days.");
    }
}
