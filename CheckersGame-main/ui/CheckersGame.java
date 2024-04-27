package ui;

import java.util.Scanner;

import core.CheckersComputerPlayer;
import core.CheckersLogic;

public class CheckersGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Checkers!");

        // Choose the interface (1 for text console)
        int interfaceChoice = chooseInterface(scanner);

        if (interfaceChoice == 1) {
            // Start the text console interface
            startTextConsoleGame(scanner);

            // After the text console game ends, ask if the user wants to switch to GUI
            System.out.print("Do you want to switch to GUI? (yes/no): ");
            String switchToGUI = scanner.next();

            if ("yes".equalsIgnoreCase(switchToGUI)) {
                // Launch the GUI interface
                launchGUI();
            } else {
                System.out.println("Exiting the game.");
            }
        } else {
            // Invalid choice
            System.out.println("Invalid choice. Exiting the game.");
        }

        scanner.close();
    }

    private static int chooseInterface(Scanner scanner) {
        System.out.println("Choose your interface:");
        System.out.println("1. Play with Text Console");
        System.out.println("2. Play with GUI");
        System.out.print("Enter your choice (1 or 2): ");

        return scanner.nextInt();
    }

    private static void startTextConsoleGame(Scanner scanner) {
        CheckersLogic game = new CheckersLogic();
        boolean gameOver = false;

        // Choose the opponent
        int opponentChoice = chooseOpponent(scanner);

        if (opponentChoice == 1) {
            // Play against another player
            while (!gameOver) {
                // Implement the player vs. player game logic here
            }
        } else if (opponentChoice == 2) {
            // Play against the computer
            CheckersComputerPlayer computerPlayer = new CheckersComputerPlayer('O', game);

            while (!gameOver) {
                // Implement the player vs. computer game logic here
            }
        } else {
            System.out.println("Invalid choice. Exiting the game.");
        }
    }

    private static int chooseOpponent(Scanner scanner) {
        System.out.println("Choose your opponent:");
        System.out.println("1. Play against another player");
        System.out.println("2. Play against the computer");
        System.out.print("Enter your choice (1 or 2): ");

        return scanner.nextInt();
    }

    private static void launchGUI() {
        // Implement code to launch the JavaFX Checkers GUI
        // (You can call the JavaFX Application class here)
    }
}
