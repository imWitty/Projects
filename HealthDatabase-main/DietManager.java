import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DietManager {


    public void getDiet(String username) {
        String fitnessGoal = getUserFitnessGoal(username);
        if (fitnessGoal == null) {
            fitnessGoal = "General Health"; // Set a default value if the user has no specified fitness goal
        }

        // Use DietCreation class to generate random meals for each food type
        List<String> mainCourses = getRandomFood(1, "General Health");
        List<String> smallSides = getRandomFood(2, "General Health");
        List<String> healthySides = getRandomFood(3, "General Health");

        System.out.println("Diet Plan for " + username);
        System.out.println("Main Courses: " + mainCourses);
        System.out.println("Small Sides: " + smallSides);
        System.out.println("Healthy Sides: " + healthySides);
    }


    private String getUserFitnessGoal(UserProfile userProfile) {
        String fitnessGoal = null;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT FitnessLevel FROM User WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, userProfile.getUsername());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        fitnessGoal = resultSet.getString("FitnessLevel");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fitnessGoal;
    }
    private String getUserFitnessGoal(String username) {
        String fitnessGoal = null;

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT FitnessLevel FROM User WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        fitnessGoal = resultSet.getString("FitnessLevel");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fitnessGoal;
    }


    private List<String> getRandomFood(int foodType, String fitnessGoal) {
        List<String> foods = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT FoodName FROM DietaryOption WHERE FoodType = ? AND GoalType = ? ORDER BY RAND() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, foodType);
                statement.setString(2, fitnessGoal);


                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String food = resultSet.getString("FoodName");
                        foods.add(food);
                    }
                }
            }
        } catch (SQLException e) {

        }

        return foods;
    }






}
