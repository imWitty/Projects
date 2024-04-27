import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

//this class is only used to add more workouts and diets into the database, will most likely be commented out when not in use
public class AddToDatabase {


    public void addWorkout(String muscleGroup, String exercise, String weightLevel, Map.Entry<Integer, String> repsAndGoal) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String insertQuery = "INSERT INTO Workout (MuscleGroup, Exercise, WeightLevel, RepsRec, GoalType) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, muscleGroup);
                statement.setString(2, exercise);
                statement.setString(3, weightLevel);
                statement.setString(4, repsAndGoal.getKey().toString()); // Set reps
                statement.setString(5, repsAndGoal.getValue()); // Set fitness goal

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addFood(String foodName, int foodType, String goalType) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String insertQuery = "INSERT INTO DietaryOption (FoodName, FoodType, GoalType) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, foodName);
                statement.setInt(2, foodType);
                statement.setString(3, goalType);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage:
        AddToDatabase addToDatabase = new AddToDatabase();

        for (int i = 0; i < 10; i++) {
            // Workouts for Legs
            addToDatabase.addWorkout("Legs", "Squat", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Legs", "Deadlift", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Legs", "Leg Press", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Legs", "Lunges", getRandomWeightLevel(), getRandomRepsAndGoal());

            // Workouts for Chest
            addToDatabase.addWorkout("Chest", "Bench Press", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Chest", "Push-Ups", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Chest", "Dumbbell Flyes", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Chest", "Chest Press Machine", getRandomWeightLevel(), getRandomRepsAndGoal());

            // Workouts for Back
            addToDatabase.addWorkout("Back", "Pull-Ups", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Back", "Lat Pulldown", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Back", "Barbell Rows", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Back", "Deadlifts", getRandomWeightLevel(), getRandomRepsAndGoal());

            // Workouts for Shoulders
            addToDatabase.addWorkout("Shoulders", "Military Press", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Shoulders", "Lateral Raises", getRandomWeightLevel(),getRandomRepsAndGoal());
            addToDatabase.addWorkout("Shoulders", "Front Raises", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Shoulders", "Shrugs", getRandomWeightLevel(),getRandomRepsAndGoal());

            // Workouts for Arms
            addToDatabase.addWorkout("Arms", "Bicep Curls", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Arms", "Tricep Dips", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Arms", "Hammer Curls", getRandomWeightLevel(),getRandomRepsAndGoal());
            addToDatabase.addWorkout("Arms", "Skull Crushers", getRandomWeightLevel(), getRandomRepsAndGoal());

            // Workouts for Core
            addToDatabase.addWorkout("Core", "Planks", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Core", "Russian Twists", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Core", "Leg Raises", getRandomWeightLevel(), getRandomRepsAndGoal());
            addToDatabase.addWorkout("Core", "Crunches", getRandomWeightLevel(), getRandomRepsAndGoal());
        }

        // Mock foods for 'Lose Weight' goal type
        addToDatabase.addFood("Grilled Chicken Salad", 1, "Lose Weight");
        addToDatabase.addFood("Steamed Vegetables", 2, "Lose Weight");
        addToDatabase.addFood("Quinoa Bowl", 3, "Lose Weight");

        // Mock foods for 'Muscle Gain' goal type
        addToDatabase.addFood("Protein-Packed Chicken Breast", 1, "Muscle Gain");
        addToDatabase.addFood("Sweet Potato Mash", 2, "Muscle Gain");
        addToDatabase.addFood("Mixed Nuts", 3, "Muscle Gain");

        // Mock foods for 'General Health' goal type
        addToDatabase.addFood("Salmon with Brown Rice", 1, "General Health");
        addToDatabase.addFood("Kale and Spinach Smoothie", 2, "General Health");
        addToDatabase.addFood("Greek Yogurt with Berries", 3, "General Health");

        // Mock foods for 'Build Endurance' goal type
        addToDatabase.addFood("Quinoa Energy Bowl", 1, "Build Endurance");
        addToDatabase.addFood("Oatmeal with Banana Slices", 2, "Build Endurance");
        addToDatabase.addFood("Trail Mix", 3, "Build Endurance");


    }

    private static String getRandomWeightLevel() {
        Random random = new Random();
        int randNum = random.nextInt(3);

        switch (randNum) {
            case 0:
                return "Light";
            case 1:
                return "Medium";
            case 2:
                return "Heavy";
            default:
                return "Medium"; // Default to Medium if an unexpected value is generated
        }
    }



    private static Map.Entry<Integer, String> getRandomRepsAndGoal() {
        Random random = new Random();
        int reps;

        // Randomly determine the reps
        reps = random.nextInt(11) + 5; // Random number between 5 and 15

        // Determine the fitness goal based on the reps
        String fitnessGoal;
        if (reps >= 12) {
            fitnessGoal = "Build Endurance";
        } else if (reps >= 8) {
            fitnessGoal = "Muscle Gain";
        } else if (reps >= 7) {
            fitnessGoal = "General Health";
        } else {
            fitnessGoal = "Lose Weight";
        }

        return new AbstractMap.SimpleEntry<>(reps, fitnessGoal);
    }
    }


