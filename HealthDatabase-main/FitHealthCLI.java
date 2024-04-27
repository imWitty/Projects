import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FitHealthCLI {

    private static String currentUser;  // Store the currently logged-in user

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("FitHealth CLI");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("Q. Quit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    registerUser();
                    break;
                case "Q":
                    System.out.println("Goodbye! Exiting FitHealth CLI.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }

            if (currentUser != null) {
                // User is logged in, display additional options
                displayUserMenu();
            }
        }
    }

    private static void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // login using LoginManager
        if (LoginManager.login(username, password)) {
            System.out.println("Login successful!");
            currentUser = username;
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Implement logic for registration using RegistrationManager
        if (RegistrationManager.register(username, password)) {
            System.out.println("Registration successful!");
            currentUser = username;
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private static void displayUserMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("FitHealth User Menu for " + currentUser);
            System.out.println("1. View Profile");
            System.out.println("2. Get a Workout");
            System.out.println("3. Get a Diet");
            System.out.println("4. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewUserProfile();
                    break;
                case "2":
                    getWorkout(UserProfile.getUserProfile(currentUser));
                    break;
                case "3":
                    getDiet(currentUser);
                    break;
                case "4":
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void viewUserProfile() {
        // Retrieve and display user profile information from the database
        UserProfile userProfile = UserProfileManager.getUserProfile(currentUser);

        if (userProfile != null) {
            displayBasicUserProfile(userProfile);

            System.out.println("Options:");
            System.out.println("1. Change Personal Information");
            System.out.println("2. See Personal Bests");
            System.out.println("3. Exit");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    changePersonalInformation(userProfile);
                    break;
                case "2":
                    seePersonalBests(userProfile);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void displayBasicUserProfile(UserProfile userProfile) {
        System.out.println("User Profile for " + currentUser);
        System.out.println("First Name: " + userProfile.getFirstName());
        System.out.println("Last Name: " + userProfile.getLastName());
        System.out.println("Age: " + userProfile.getAge());
        System.out.println("Gender: " + userProfile.getGender());
        System.out.println("Weight: " + userProfile.getWeight());
        System.out.println("Goals: " + userProfile.getGoals());
        System.out.println("Fitness Level: " + userProfile.getFitnessLevel());
    }



    private static void changePersonalInformation(UserProfile userProfile) {
        System.out.println("Options to Change Personal Information:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Age");
        System.out.println("4. Gender");
        System.out.println("5. Weight");
        System.out.println("6. Goals");
        System.out.println("7. Fitness Level");
        System.out.println("8. Back");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                updateField("First Name", "FirstName", userProfile);
                break;
            case "2":
                updateField("Last Name", "LastName", userProfile);
                break;
            case "3":
                updateField("Age", "Age", userProfile);
                break;
            case "4":
                updateField("Gender", "Gender", userProfile);
                break;
            case "5":
                updateField("Weight", "Weight", userProfile);
                break;
            case "6":
                updateField("Goals", "Goals", userProfile);
                break;
            case "7":
                updateField("Fitness Level", "FitnessLevel", userProfile);
                break;
            case "8":
                // Do nothing, go back
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void printPersonalBests(UserProfile userProfile) {
        System.out.println("Personal Bests:");

        // Fetch and display personal bests from the database
        List<String> personalBests = userProfile.getPersonalBestsFromDatabase();
        if (personalBests.isEmpty()) {

        } else {
            for (int i = 0; i < personalBests.size(); i++) {
                System.out.println((i + 1) + ". " + personalBests.get(i));
            }
        }
    }


    private static void updateField(String fieldName, String databaseField, UserProfile userProfile) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new " + fieldName + ": ");
        String newValue = scanner.nextLine();

        // Update the field in the user profile
        switch (databaseField) {
            case "FirstName":
                userProfile.setFirstName(newValue);
                break;
            case "LastName":
                userProfile.setLastName(newValue);
                break;
            case "Age":
                userProfile.setAge(Integer.parseInt(newValue));
                break;
            case "Gender":
                userProfile.setGender(newValue);
                break;
            case "Weight":
                userProfile.setWeight(Integer.parseInt(newValue));
                break;
            case "Goals":
                userProfile.setGoals(newValue);
                break;
            case "FitnessLevel":
                userProfile.setFitnessLevel(newValue);
                break;
            default:
                System.out.println("Invalid database field.");
                return;
        }

        // Update the field in the database
        userProfile.updateField(databaseField, newValue);
    }




    private static void seePersonalBests(UserProfile userProfile) {
        printPersonalBests(userProfile);
        // Fetch and display personal bests from the database
        List<String> personalBests = userProfile.getPersonalBestsFromDatabase();
        if (personalBests.isEmpty()) {
            System.out.println("No personal bests found.");
        } else {
            for (int i = 0; i < personalBests.size(); i++) {
                System.out.println((i + 1) + ". " + personalBests.get(i));
            }
        }

        System.out.println("1. Update a Personal Best");
        System.out.println("2. Back");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                updatePersonalBest(userProfile);
                break;
            case "2":
                // Do nothing, go back
                break;
            default:
                System.out.println("Invalid option.");
        }
    }


    private static void updatePersonalBest(UserProfile userProfile) {
        System.out.println("Options to Update Personal Bests:");
        System.out.println("1. One RM Squat");
        System.out.println("2. One RM Deadlift");
        System.out.println("3. One RM Bench");
        System.out.println("4. Fastest Mile");
        System.out.println("5. Fastest MPH");
        System.out.println("6. Longest Distance");
        System.out.println("7. Most Weekly Distance");
        System.out.println("8. Back");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                updatePersonalBestField("One RM Squat", "OneRMSquat", userProfile);
                break;
            case "2":
                updatePersonalBestField("One RM Deadlift", "OneRMDeadLift", userProfile);
                break;
            case "3":
                updatePersonalBestField("One RM Bench", "OneRMBench", userProfile);
                break;
            case "4":
                updatePersonalBestField("Fastest Mile", "FastestMile", userProfile);
                break;
            case "5":
                updatePersonalBestField("Fastest MPH", "FastestMPH", userProfile);
                break;
            case "6":
                updatePersonalBestField("Longest Distance", "LongestDistance", userProfile);
                break;
            case "7":
                updatePersonalBestField("Most Weekly Distance", "MostWeeklyDistance", userProfile);
                break;
            case "8":
                // Do nothing, go back
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void updatePersonalBestField(String fieldName, String databaseField, UserProfile userProfile) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new " + fieldName + ": ");
        String newValue = scanner.nextLine();

        // Update the field in the user profile
        userProfile.updatePersonalBestField(databaseField, Integer.parseInt(newValue));



        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "UPDATE PersonalBests SET " + databaseField + " = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(newValue));
                statement.setString(2, currentUser);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    private static void getWorkout(UserProfile userProfile) {
        Scanner scanner = new Scanner(System.in);

        // Display muscle type options
        System.out.println("Select a muscle type:");
        System.out.println("1. Legs");
        System.out.println("2. Chest");
        System.out.println("3. Back");
        System.out.println("4. Shoulders");
        System.out.println("5. Arms");
        System.out.println("6. Core");

        // Prompt user for input
        System.out.print("Enter the number corresponding to the muscle type: ");
        int muscleType = scanner.nextInt();

        // Validate user input
        if (muscleType < 1 || muscleType > 6) {
            System.out.println("Invalid selection. Please enter a number between 1 and 6.");
            return;
        }

        // Map the user input to the muscle type
        String selectedMuscle = mapMuscleType(muscleType);

        // Generate workout plan based on the selected muscle type, fitness goal, and goal type
        WorkoutPlan workoutPlan = WorkoutPlan.generateWorkouts(userProfile, selectedMuscle);

        // Display the workout plan
        System.out.println("Workout Plan for " + currentUser + " - Muscle Type: " + selectedMuscle);
        System.out.println(workoutPlan);
    }

    // Method to map user input to muscle type
    private static String mapMuscleType(int muscleType) {
        switch (muscleType) {
            case 1:
                return "Legs";
            case 2:
                return "Chest";
            case 3:
                return "Back";
            case 4:
                return "Shoulders";
            case 5:
                return "Arms";
            case 6:
                return "Core";
            default:
                return ""; // Handle invalid input
        }
    }

    private static void getDiet(String currentUser) {
        DietManager dietManager = new DietManager();
        dietManager.getDiet(currentUser);
    }
}
