import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlan {
    private List<String> workouts;

    private WorkoutPlan(List<String> workouts) {
        this.workouts = workouts;
    }

    public static WorkoutPlan generateWorkouts(UserProfile userProfile, String muscleGroup) {
        List<String> workouts = fetchWorkoutsFromDatabase(userProfile.getGoalsFromDatabase(), muscleGroup);

        return new WorkoutPlan(workouts);
    }

    private static List<String> fetchWorkoutsFromDatabase(String fitnessGoal, String muscleGroup) {
        List<String> workouts = new ArrayList<>();



        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT Exercise FROM Workout WHERE MuscleGroup = ? AND GoalType = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, muscleGroup);
                preparedStatement.setString(2, fitnessGoal);

                ResultSet resultSet = preparedStatement.executeQuery();

                List<String> allWorkouts = new ArrayList<>();

                while (resultSet.next()) {
                    String exercise = resultSet.getString("Exercise");
                    allWorkouts.add(exercise);
                }

                // Shuffle the list to randomize the order
                java.util.Collections.shuffle(allWorkouts);

                // Take the first 6 workouts (or less if there are fewer than 6)
                workouts = allWorkouts.subList(0, Math.min(6, allWorkouts.size()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workouts;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String workout : workouts) {
            sb.append(workout).append("\n");
        }
        return sb.toString();
    }
}
