import java.sql.*;
import java.util.Scanner;

public class RegistrationManager {

    public static boolean register(String username, String password) {


        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Check if the username is already taken
            if (isUsernameTaken(connection, username)) {
                System.out.println("Username is already taken. Please choose a different one.");
                return false;
            }

            // Prompt the user to input a firstName
            String firstName = promptForFirstName();

            // Check if the firstName is provided
            if (firstName != null && !firstName.isEmpty()) {
                // If username is available and firstName is provided, add a new user
                String insertUserQuery = "INSERT INTO User (Username, Password, FirstName) VALUES (?, ?, ?)";
                try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                    userStatement.setString(1, username);
                    userStatement.setString(2, password);
                    userStatement.setString(3, firstName);
                    int affectedRows = userStatement.executeUpdate();

                    if (affectedRows > 0) {
                        // Get the auto-generated user ID
                        try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int userId = generatedKeys.getInt(1);
                                // Insert initial personal bests with all 0 values
                                insertInitialPersonalBests(connection, userId);
                            }
                        }

                        return true; // Returns true if a new user is added
                    }
                }
            } else {
                System.out.println("First name is required. Registration aborted.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }



    private static void insertInitialPersonalBests(Connection connection, int userId) throws SQLException {
        String insertPersonalBestsQuery = "INSERT INTO PersonalBests (UserID, OneRMSquat, OneRMDeadLift, OneRMBench, FastestMile, FastestMPH, LongestDistance, MostWeeklyDistance) VALUES (?, 0, 0, 0, 0, 0, 0, 0)";

        try (PreparedStatement personalBestsStatement = connection.prepareStatement(insertPersonalBestsQuery)) {
            personalBestsStatement.setInt(1, userId);
            personalBestsStatement.executeUpdate();
        }
    }



    private static boolean isUsernameTaken(Connection connection, String username) throws SQLException {
        String checkQuery = "SELECT * FROM User WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a user with the given username exists
            }
        }
    }

    private static String promptForFirstName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        return scanner.nextLine();
    }
}
