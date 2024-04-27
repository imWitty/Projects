import java.sql.*;

public class UserProfileManager {

    public static UserProfile getUserProfile(String username) {


        UserProfile userProfile = null;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            userProfile = retrieveBasicUserProfile(connection, username);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    private static UserProfile retrieveBasicUserProfile(Connection connection, String username) throws SQLException {
        UserProfile userProfile = new UserProfile();

        String profileQuery = "SELECT * FROM User WHERE Username = ?";
        try (PreparedStatement profileStatement = connection.prepareStatement(profileQuery)) {
            profileStatement.setString(1, username);

            try (ResultSet profileResultSet = profileStatement.executeQuery()) {
                if (profileResultSet.next()) {
                    userProfile.setFirstName(profileResultSet.getString("FirstName"));
                    userProfile.setLastName(profileResultSet.getString("LastName"));
                    userProfile.setAge(profileResultSet.getInt("Age"));
                    userProfile.setGender(profileResultSet.getString("Gender"));
                    userProfile.setWeight(profileResultSet.getDouble("Weight"));
                    userProfile.setGoals(profileResultSet.getString("Goals"));
                    userProfile.setFitnessLevel(profileResultSet.getString("FitnessLevel"));
                }
            }
        }

        return userProfile;
    }



    private static int getUserIdByUsername(Connection connection, String username) throws SQLException {
        int userId = -1;  // Default value or handle the case where the username is not found

        String query = "SELECT UserID FROM User WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("UserID");
                }
            }
        }

        return userId;
    }
}
