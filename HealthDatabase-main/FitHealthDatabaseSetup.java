import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FitHealthDatabaseSetup {

    public static void main(String[] args) {
        // JDBC URL, username, and password


        createTables(url, username, password);
    }

    public static void createTables(String url, String username, String password) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // User Table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    "UserID INT PRIMARY KEY AUTO_INCREMENT," +
                    "Username VARCHAR(255) NOT NULL UNIQUE," +
                    "Password VARCHAR(255) NOT NULL," +
                    "FirstName VARCHAR(255) NOT NULL," +
                    "LastName VARCHAR(255)," +
                    "Age INT," +
                    "Gender VARCHAR(10) CHECK(Gender IN ('male', 'female'))," +
                    "Weight DECIMAL(5,2)," +
                    "FitnessLevel VARCHAR(20) DEFAULT 'Beginner' CHECK(FitnessLevel IN ('Brand New', 'Beginner', 'Intermediate', 'Advanced', 'Expert'))," +
                    "Goals VARCHAR(255) DEFAULT 'General Health' CHECK(Goals IN ('Lose Weight', 'Muscle Gain', 'General Health', 'Build Endurance')))");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PersonalBests (" +
                    "PersonalBestID INT PRIMARY KEY AUTO_INCREMENT," +
                    "UserID INT NOT NULL," +
                    "OneRMSquat VARCHAR(255)," +
                    "OneRMDeadLift VARCHAR(255)," +
                    "OneRMBench VARCHAR(255)," +
                    "FastestMile VARCHAR(255)," +
                    "FastestMPH VARCHAR(255)," +
                    "LongestDistance VARCHAR(255)," +
                    "MostWeeklyDistance VARCHAR(255)," +
                    "FOREIGN KEY (UserID) REFERENCES User(UserID))");



            // DietaryOption Table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS DietaryOption (" +
                    "MealPlanID INT PRIMARY KEY AUTO_INCREMENT," +
                    "FoodName VARCHAR(255)," +
                    "FoodType INT CHECK(FoodType IN (1, 2, 3))," +
                    "GoalType VARCHAR(20) CHECK(GoalType IN ('Lose Weight', 'Muscle Gain', 'General Health', 'Build Endurance'))" +
                    ")");


            // Workout Table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Workout (" +
                    "WorkoutID INT PRIMARY KEY AUTO_INCREMENT," +
                    "MuscleGroup VARCHAR(50) NOT NULL," +
                    "Exercise VARCHAR(50) NOT NULL," +
                    "WeightLevel VARCHAR(10) CHECK(WeightLevel IN ('Light', 'Medium', 'Heavy'))," +
                    "RepsRec VARCHAR(20) NOT NULL," +
                    "GoalType VARCHAR(255) DEFAULT 'Beginner' CHECK(GoalType IN ('Lose Weight', 'Muscle Gain', 'General Health', 'Build Endurance')))");

            // ProgressLog Table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ProgressLog (" +
                    "ProgressID INT PRIMARY KEY AUTO_INCREMENT," +
                    "UserID INT," +
                    "Exercise VARCHAR(50) NOT NULL," +
                    "Weight DECIMAL(5,2) DEFAULT 0," +
                    "Time TIME DEFAULT '00:00:00'," +
                    "Reps INT DEFAULT 0," +
                    "Distance DECIMAL(8,2) DEFAULT 0.00," +
                    "Date DATE," +
                    "FOREIGN KEY (UserID) REFERENCES User(UserID))");

            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
