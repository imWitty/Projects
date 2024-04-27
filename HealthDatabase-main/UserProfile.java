import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private double weight;
    private String goals;
    private String fitnessLevel;

    public UserProfile(){}

    public UserProfile(String username, String firstName, String lastName, int age, String gender, double weight, String goals, String fitnessLevel) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.goals = goals;
        this.fitnessLevel = fitnessLevel;
    }

    public static UserProfile getUserProfile(String username) {
        UserProfile userProfile = null;


        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM User WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userProfile = new UserProfile(
                                resultSet.getString("Username"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getInt("Age"),
                                resultSet.getString("Gender"),
                                resultSet.getDouble("Weight"),
                                resultSet.getString("Goals"),
                                resultSet.getString("FitnessLevel")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    public void updateField(String columnName, Object value) {


        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "UPDATE User SET " + columnName + " = ? WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setObject(1, value);
                statement.setString(2, this.username);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPersonalBestsFromDatabase() {
        List<String> personalBests = new ArrayList<>();



        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {

            String query = "SELECT OneRMSquat, OneRMDeadLift, OneRMBench, FastestMile, FastestMPH, LongestDistance, MostWeeklyDistance " +
                    "FROM PersonalBests WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, getUserId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    int count = 0; // to count the number of records retrieved
                    while (resultSet.next()) {
                        String oneRMSquat = resultSet.getString("OneRMSquat");
                        String oneRMDeadLift = resultSet.getString("OneRMDeadLift");
                        String oneRMBench = resultSet.getString("OneRMBench");
                        String fastestMile = resultSet.getString("FastestMile");
                        String fastestMPH = resultSet.getString("FastestMPH");
                        String longestDistance = resultSet.getString("LongestDistance");
                        String mostWeeklyDistance = resultSet.getString("MostWeeklyDistance");

                        personalBests.add("OneRMSquat: " + oneRMSquat);
                        personalBests.add("OneRMDeadLift: " + oneRMDeadLift);
                        personalBests.add("OneRMBench: " + oneRMBench);
                        personalBests.add("FastestMile: " + fastestMile);
                        personalBests.add("FastestMPH: " + fastestMPH);
                        personalBests.add("LongestDistance: " + longestDistance);
                        personalBests.add("MostWeeklyDistance: " + mostWeeklyDistance);
                        count++;
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personalBests;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
        updateField("Username", username);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateField("FirstName", firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateField("LastName", lastName);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        updateField("Age", age);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        updateField("Gender", gender);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        updateField("Weight", weight);
    }

    public String getGoals() {
        return this.goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
        updateField("Goals", goals);
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
        updateField("FitnessLevel", fitnessLevel);
    }


    // Getters for personal best fields
    public int getOneRMSquat() {
        return getPersonalBestField("OneRMSquat");
    }

    public int getOneRMDeadlift() {
        return getPersonalBestField("OneRMDeadLift");
    }

    public int getOneRMBench() {
        return getPersonalBestField("OneRMBench");
    }

    public int getFastestMile() {
        return getPersonalBestField("FastestMile");
    }

    public int getFastestMPH() {
        return getPersonalBestField("FastestMPH");
    }

    public int getLongestDistance() {
        return getPersonalBestField("LongestDistance");
    }

    public int getMostWeeklyDistance() {
        return getPersonalBestField("MostWeeklyDistance");
    }

    public int getUserId() {


        int userId = -1;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT UserID FROM User WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, getUsername());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("UserID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }


    private String getStringFieldFromDatabase(String field) {
        String result = null;



        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT " + field + " FROM User WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = resultSet.getString(field);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    private int getPersonalBestField(String field) {
        int result = 0;



        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT " + field + " FROM PersonalBests WHERE UserID = (SELECT UserID FROM User WHERE Username = ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, this.username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = resultSet.getInt(field);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void updateField(String columnName, String value) {


        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "UPDATE User SET " + columnName + " = ? WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, value);
                statement.setString(2, this.username);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserIdByUsername(Connection connection, String username) throws SQLException {
        int userId = -1;

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

    public String getGoalsFromDatabase() {
        return getStringFieldFromDatabase("Goals");
    }



    public void updatePersonalBestField(String columnName, int value) {


        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Assuming you have a method getUserIdByUsername in your UserProfile class
            int userId = getUserIdByUsername(connection, this.username);

            String query = "UPDATE PersonalBests SET " + columnName + " = ? WHERE UserID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, value);
                statement.setInt(2, userId);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String toString() {
        return "UserProfile{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", weight=" + weight +
                ", goals='" + goals + '\'' +
                ", fitnessLevel='" + fitnessLevel + '\'' +
                '}';
    }
}
