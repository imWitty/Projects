package core;

import java.util.ArrayList;
import java.util.Random;

public class CheckersComputerPlayer {
    private char player;
    private CheckersLogic game;

    public CheckersComputerPlayer(char player, CheckersLogic game) {
        this.player = player;
        this.game = game;
    }

    public String generateComputerMove(char[][] board) {
        ArrayList<String> validMoves = new ArrayList<>();

        // Iterate through the board to find all valid moves for the computer player
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == player) {
                    // Generate possible moves for this piece
                    String fromPosition = "" + (8 - row) + (char)('a' + col);
                    ArrayList<String> possibleMoves = generatePossibleMoves(board, row, col);

                    // Add valid moves to the list
                    validMoves.addAll(possibleMoves);
                }
            }
        }

        // Randomly select a valid move
        if (!validMoves.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(validMoves.size());
            return validMoves.get(randomIndex);
        }

        // If no valid moves are available, return a placeholder move
        return "0a-0b"; // Placeholder move
    }

    private ArrayList<String> generatePossibleMoves(char[][] board, int row, int col) {
        ArrayList<String> possibleMoves = new ArrayList<>();

        // Check diagonal moves (single step)
        int[][] directions = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (game.isValidPosition(newRow, newCol) && board[newRow][newCol] == '_') {
                String fromPosition = "" + (8 - row) + (char)('a' + col);
                String toPosition = "" + (8 - newRow) + (char)('a' + newCol);
                possibleMoves.add(fromPosition + "-" + toPosition);
            }
        }

        return possibleMoves;
    }
}
