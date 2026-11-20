package edu.campusconnect.view;

import edu.campusconnect.exception.ValidationException;
import edu.campusconnect.model.ServiceRequest;
import edu.campusconnect.service.RequestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CampusConnectFrame extends JFrame {
    private final RequestService service;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nameField = new JTextField(15);
    private final JTextField numberField = new JTextField(12);
    private final JComboBox<String> categoryBox = new JComboBox<>(
            RequestService.CATEGORIES.toArray(String[]::new));
    private final JTextField descriptionField = new JTextField(20);
    private final JComboBox<String> priorityBox = new JComboBox<>(
            RequestService.PRIORITIES.toArray(String[]::new));
    private final JSpinner estimateSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 365, 1));
    private final JTextField searchField = new JTextField(15);
    private final JComboBox<String> statusFilter = new JComboBox<>(
            new String[]{"All", "Pending", "In Progress", "Completed", "Cancelled"});

    public CampusConnectFrame(RequestService service) {
        this.service = service;
        setTitle("CampusConnect - Student Service Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 650);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Student Name", "Student Number", "Category",
                "Description", "Priority", "Estimate (days)", "Status", "Created"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(buildForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildActions(), BorderLayout.SOUTH);
        refresh();
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("New Service Request"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        addField(panel, c, 0, 0, "Student Name", nameField);
        addField(panel, c, 2, 0, "Student Number", numberField);
        addField(panel, c, 0, 1, "Category", categoryBox);
        addField(panel, c, 2, 1, "Priority", priorityBox);
        addField(panel, c, 0, 2, "Description", descriptionField);
        addField(panel, c, 2, 2, "Estimate (days)", estimateSpinner);

        JButton addButton = new JButton("Add Request");
        addButton.addActionListener(e -> addRequest());
        c.gridx = 4; c.gridy = 0; c.gridwidth = 1;
        panel.add(addButton, c);
        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints c, int x, int y,
                          String label, Component input) {
        c.gridx = x; c.gridy = y; c.gridwidth = 1;
        panel.add(new JLabel(label), c);
        c.gridx = x + 1;
        panel.add(input, c);
    }

    private JPanel buildActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Refresh");
        JButton searchButton = new JButton("Search");
        JButton statusButton = new JButton("Update Status");
        JButton deleteButton = new JButton("Delete");
        JButton processButton = new JButton("Process Next");
        JButton reportButton = new JButton("Summary Report");

        refreshButton.addActionListener(e -> refresh());
        searchButton.addActionListener(e -> search());
        statusButton.addActionListener(e -> updateStatus());
        deleteButton.addActionListener(e -> deleteRequest());
        processButton.addActionListener(e -> processNext());
        reportButton.addActionListener(e -> showSummary());

        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(new JLabel("Status filter:"));
        panel.add(statusFilter);
        statusFilter.addActionListener(e -> applyStatusFilter());
        panel.add(refreshButton);
        panel.add(statusButton);
        panel.add(deleteButton);
        panel.add(processButton);
        panel.add(reportButton);
        return panel;
    }

    private void addRequest() {
        try {
            int estimate = (Integer) estimateSpinner.getValue();
            runTask(() -> service.add(nameField.getText(), numberField.getText(),
                    (String) categoryBox.getSelectedItem(), descriptionField.getText(),
                    (String) priorityBox.getSelectedItem(), estimate),
                    result -> {
                        clearForm();
                        refresh();
                        message("Request added. ID: " + result);
                    });
        } catch (Exception ex) {
            message(ex.getMessage());
        }
    }

    private void refresh() {
        runTask(service::getAll, this::showRows);
    }

    private void search() {
        runTask(() -> service.search(searchField.getText()), this::showRows);
    }

    private void applyStatusFilter() {
        String selected = (String) statusFilter.getSelectedItem();
        runTask(service::getAll, rows -> showRows(
                selected.equals("All") ? rows :
                        rows.stream().filter(r -> r.getStatus().equals(selected)).toList()));
    }

    private void updateStatus() {
        Integer id = selectedId();
        if (id == null) return;
        String newStatus = (String) JOptionPane.showInputDialog(this,
                "Select the new status:", "Update Status",
                JOptionPane.QUESTION_MESSAGE, null,
                RequestService.STATUSES.toArray(), "In Progress");
        if (newStatus == null) return;
        runTask(() -> service.updateStatus(id, newStatus), changed -> {
            refresh();
            message(changed ? "Status updated." : "No record was changed.");
        });
    }

    private void deleteRequest() {
        Integer id = selectedId();
        if (id == null) return;
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete request #" + id + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        runTask(() -> service.delete(id), deleted -> {
            refresh();
            message(deleted ? "Request deleted." : "Request was not found.");
        });
    }

    private void processNext() {
        runTask(service::processNext, next -> {
            if (next.isEmpty()) {
                message("There are no pending requests.");
            } else {
                ServiceRequest request = next.get();
                runTask(() -> service.startNext(request.getRequestId()), changed -> {
                    refresh();
                    message("Processing request #" + request.getRequestId() +
                            " for " + request.getStudentName() + ".");
                });
            }
        });
    }

    private void showSummary() {
        runTask(service::getAll, rows ->
                JOptionPane.showMessageDialog(this, service.createSummary(rows),
                        "Request Summary", JOptionPane.INFORMATION_MESSAGE));
    }

    private Integer selectedId() {
        int row = table.getSelectedRow();
        if (row < 0) {
            message("Select a request from the table first.");
            return null;
        }
        int modelRow = table.convertRowIndexToModel(row);
        return (Integer) tableModel.getValueAt(modelRow, 0);
    }

    private void showRows(List<ServiceRequest> requests) {
        tableModel.setRowCount(0);
        for (ServiceRequest r : requests) {
            tableModel.addRow(new Object[]{
                    r.getRequestId(), r.getStudentName(), r.getStudentNumber(),
                    r.getCategory(), r.getDescription(), r.getPriority(),
                    r.getEstimatedDays(), r.getStatus(), r.getCreatedAt()
            });
        }
    }

    private void clearForm() {
        nameField.setText("");
        numberField.setText("");
        descriptionField.setText("");
        estimateSpinner.setValue(3);
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(this, text, "CampusConnect",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @FunctionalInterface
    private interface Task<T> {
        T run() throws Exception;
    }

    private <T> void runTask(Task<T> task, java.util.function.Consumer<T> onSuccess) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SwingWorker<T, Void> worker = new SwingWorker<>() {
            @Override
            protected T doInBackground() throws Exception {
                return task.run();
            }

            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                try {
                    onSuccess.accept(get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    message("The operation was interrupted.");
                } catch (ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    String detail = cause == null ? ex.getMessage() : cause.getMessage();
                    message("Operation failed: " + detail);
                }
            }
        };
        worker.execute();
    }
}
