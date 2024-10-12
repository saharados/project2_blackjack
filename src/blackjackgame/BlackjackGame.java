package blackjackgame;

import javax.swing.*;
import java.sql.SQLException;

public class BlackjackGame {
    public static void main(String[] args) {
        try {
            // Establish a connection
            DatabaseManager.getConnection();
            
            // Create the Users table
            DatabaseManager.createUsersTable(); // Ensure users table is created
            
            // Create the GameResultsTable
            DatabaseManager.createGameResultsTable(); // Ensure game results table is created
            
            // Launch the Login GUI
            SwingUtilities.invokeLater(() -> {
                new LoginGUI();
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection when done
            DatabaseManager.closeConnection();
        }
    }
}
