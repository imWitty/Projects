import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutManager {


    public List<String> generateWorkouts(String muscleGroup, String fitnessGoal) {
        List<String> workouts = getAvailableWorkouts(muscleGroup, fitnessGoal);

        return workouts.size() >= 6 ? workouts.subList(0, 6) : workouts;
    }

    private List<String> getAvailableWorkouts(String muscleGroup, String fitnessGoal) {
        List<String> workouts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT Exercise FROM Workout WHERE MuscleGroup = ? AND FitnessGoal = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, muscleGroup);
                statement.setString(2, fitnessGoal);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        workouts.add(resultSet.getString("Exercise"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workouts;
    }
}
