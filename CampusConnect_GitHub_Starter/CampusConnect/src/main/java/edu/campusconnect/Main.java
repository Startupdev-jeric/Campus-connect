
package edu.campusconnect;

import java.sql.SQLException;
import edu.campusconnect.dao.JdbcServiceRequestDao;
import edu.campusconnect.database.DatabaseConnection;
import edu.campusconnect.service.RequestService;
import edu.campusconnect.view.CampusConnectFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // The default Swing look and feel will be used.
            }

            try {
                RequestService service = new RequestService(
                        new JdbcServiceRequestDao(DatabaseConnection.getConnection())
                );

                new CampusConnectFrame(service).setVisible(true);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Database connection error: " + e.getMessage(),
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }
}
