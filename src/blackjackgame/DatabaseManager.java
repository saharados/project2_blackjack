package blackjackgame;

import java.sql.*;

/**
 *
 * @author saharamichaeladosdos
 */
public class DatabaseManager {
    private static Connection connection;

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:derby://localhost:1527/BlackjackDB;user=pdc;password=pdc"; // Update with your DB name
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    // Method to drop the GameResultsTable if it exists
    public static void dropGameResultsTable() {
        String sql = "DROP TABLE GameResultsTable";
        try (Statement stmt = getConnection().createStatement()) {
            // Check if the table exists before attempting to drop it
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, "GAMERESULTSTABLE", null);
            if (rs.next()) { // If the table exists
                stmt.executeUpdate(sql);
                System.out.println("GameResultsTable dropped successfully.");
            } else {
                System.out.println("GameResultsTable does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error dropping GameResultsTable: " + e.getMessage());
        }
    }

    // Method to create the Users table for storing user information
    public static void createUsersTable() {
        String sql = "CREATE TABLE Users ("
                + "Username VARCHAR(255) PRIMARY KEY,"
                + "Password VARCHAR(255) NOT NULL,"
                + "Score INT DEFAULT 0)"; // You can store the score in the same table if desired

        try (Statement stmt = getConnection().createStatement()) {
            // Check if the table already exists
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, "USERS", null);
            if (!rs.next()) { // If the table doesn't exist
                stmt.executeUpdate(sql);
                System.out.println("Users table created successfully.");
            } else {
                System.out.println("Users table already exists.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating Users table: " + e.getMessage());
        }
    }

    // Method to create the GameResultsTable for storing game results
    public static void createGameResultsTable() {
        String sql = "CREATE TABLE GameResultsTable ("
                + "PlayerName VARCHAR(255),"
                + "Score INT,"
                + "PRIMARY KEY (PlayerName, Score))"; // Adjust according to your requirements

        try (Statement stmt = getConnection().createStatement()) {
            // Check if the table already exists
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, "GAMERESULTSTABLE", null);
            if (!rs.next()) { // If the table doesn't exist
                stmt.executeUpdate(sql);
                System.out.println("GameResultsTable created successfully.");
            } else {
                System.out.println("GameResultsTable already exists.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating GameResultsTable: " + e.getMessage());
        }
    }

    // Method to register a new user
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false; // Return false if registration fails
        }
    }

    // Method to authenticate a user
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT Password FROM Users WHERE Username = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                return storedPassword.equals(password); // Return true if password matches
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
        return false; // Return false if user not found or password does not match
    }

    // Method to retrieve a user's score
    public static int getUserScore(String username) {
        String sql = "SELECT Score FROM Users WHERE Username = ?";
        int score = 0;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                score = rs.getInt("Score");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving score: " + e.getMessage());
        }
        return score;
    }

    // Method to update a user's score
    public static void updateUserScore(String username, int score) {
        String sql = "UPDATE Users SET Score = ? WHERE Username = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            System.out.println("User score updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating score: " + e.getMessage());
        }
    }

    // Method to close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Method to insert a game result
    public static void insertGameResult(String playerName, int score) {
        String sql = "INSERT INTO GameResultsTable (PlayerName, Score) VALUES (?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) { // Ensure you get connection
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
            System.out.println("Game result inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting game result: " + e.getMessage());
        }
    }
    
    // Method to update the user's score after a game
    public static void handleGameResult(String username, int gameScore) {
        // Retrieve the current score
        int currentScore = getUserScore(username);
        
        // Update the user's score
        int newScore = currentScore + gameScore; // Update based on game result
        updateUserScore(username, newScore);
        
        // Optionally, insert the game result into GameResultsTable
        insertGameResult(username, gameScore);
    }
}
