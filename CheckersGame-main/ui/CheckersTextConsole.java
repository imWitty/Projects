package ui;

import java.util.Scanner;
import core.CheckersLogic;
import core.CheckersComputerPlayer;

public class CheckersTextConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CheckersLogic game = new CheckersLogic();
        boolean gameOver = false;

        System.out.println("Begin Game. Enter 'P' if you want to play against another player; enter 'C' to play against the computer.");
        System.out.print(">>");
        char choice = scanner.nextLine().charAt(0);

        if (choice == 'P') {
            while (!gameOver) {
                displayBoard(game.getBoard());

                System.out.println("Player " + game.getCurrentPlayer() + " - your turn.");
                System.out.print("Choose a cell position of piece to be moved (e.g., 3a): ");
                String fromPosition = scanner.nextLine();
                System.out.print("Choose the new position (e.g., 4b): ");
                String toPosition = scanner.nextLine();

                int fromRow = 8 - (fromPosition.charAt(0) - '0');
                int fromCol = fromPosition.charAt(1) - 'a';
                int toRow = 8 - (toPosition.charAt(0) - '0');
                int toCol = toPosition.charAt(1) - 'a';

                if (game.isValidMove(fromRow, fromCol, toRow, toCol)) {
                    game.makeMove(fromRow, fromCol, toRow, toCol);
                    if (game.isWin()) {
                        displayBoard(game.getBoard());
                        System.out.println("Player " + game.getCurrentPlayer() + " Won the Game");
                        gameOver = true;
                    }
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }

            scanner.close();
        } else if (choice == 'C') {
            // Play against the computer
            CheckersComputerPlayer computerPlayer = new CheckersComputerPlayer('O', game);

            System.out.println("Start game against computer. You are Player X, and the Computer is Player O.");

            while (!gameOver) {
                displayBoard(game.getBoard());

                if (game.getCurrentPlayer() == 'X') {
                    System.out.print("Your turn. Enter move (e.g., 3a-4b): ");
                    String move = scanner.nextLine();

                    int selectedRow = 8 - (move.charAt(0) - '0');
                    int selectedCol = move.charAt(1) - 'a';
                    int clickedRow = 8 - (move.charAt(3) - '0');
                    int clickedCol = move.charAt(4) - 'a';

                    if (game.isValidMove(selectedRow, selectedCol, clickedRow, clickedCol)) {
                        game.makeMove(selectedRow, selectedCol, clickedRow, clickedCol);
                        if (game.isWin()) {
                            displayBoard(game.getBoard());
                            System.out.println("Player " + game.getCurrentPlayer() + " Won the Game");
                            gameOver = true;
                        }
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                } else {
                    // Computer's turn
                    String computerMove = computerPlayer.generateComputerMove(game.getBoard());
                    int selectedRow = 8 - (computerMove.charAt(0) - '0');
                    int selectedCol = computerMove.charAt(1) - 'a';
                    int clickedRow = 8 - (computerMove.charAt(3) - '0');
                    int clickedCol = computerMove.charAt(4) - 'a';

                    game.makeMove(selectedRow, selectedCol, clickedRow, clickedCol);

                    displayBoard(game.getBoard());
                    System.out.println("Computer's move: " + computerMove);

                    if (game.isWin()) {
                        displayBoard(game.getBoard());
                        System.out.println("Player " + game.getCurrentPlayer() + " Won the Game");
                        gameOver = true;
                    }
                }
            }
        } else {
            System.out.println("Invalid choice. Exiting the game.");
        }

        scanner.close();
    }

    private static void displayBoard(char[][] board) {
        System.out.println("\n   a b c d e f g h");

        for (int row = 0; row < board.length; row++) {
            System.out.print(8 - row + " ");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print("|" + board[row][col]);
            }
            System.out.println("|");
        }

        System.out.println("   a b c d e f g h\n");
    }
}
