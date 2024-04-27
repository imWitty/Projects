import java.sql.*;

public class LoginManager {

    public static boolean login(String username, String password) {
        ;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Returns true if a matching user is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

