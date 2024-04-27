package core;

public class CheckersLogic {
    private char[][] board;
    private char currentPlayer;

    public CheckersLogic() {
        board = new char[8][8];
        initializeBoard();
        currentPlayer = 'X';
    }

    private void initializeBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((row + col) % 2 == 0) {
                    if (row < 3) {
                        board[row][col] = 'O'; // 'O' represents Player O's pieces
                    } else if (row > 4) {
                        board[row][col] = 'X'; // 'X' represents Player X's pieces
                    } else {
                        board[row][col] = '_'; // Empty cells
                    }
                } else {
                    board[row][col] = ' '; // Blank cells on the black squares
                }
            }
        }
    }

    boolean isValidPosition(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    private boolean isDiagonalMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDistance = Math.abs(toRow - fromRow);
        int colDistance = Math.abs(toCol - fromCol);
        return rowDistance == colDistance && rowDistance > 0;
    }

    private boolean hasCapture(int fromRow, int fromCol, int toRow, int toCol) {
        int capturedRow = (fromRow + toRow) / 2;
        int capturedCol = (fromCol + toCol) / 2;
        return isValidPosition(capturedRow, capturedCol) && board[capturedRow][capturedCol] != '_' &&
                Character.toUpperCase(board[capturedRow][capturedCol]) != currentPlayer;
    }

    public boolean isWin() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 'X' && !canMove('X')) {
                    return true; // Player O wins
                }
                if (board[row][col] == 'O' && !canMove('O')) {
                    return true; // Player X wins
                }
            }
        }
        return false;
    }

    private boolean canMove(char player) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == player) {
                    if (player == 'X') {
                        if (isValidPosition(row - 1, col - 1) && board[row - 1][col - 1] == '_') {
                            return true;
                        }
                        if (isValidPosition(row - 1, col + 1) && board[row - 1][col + 1] == '_') {
                            return true;
                        }
                        if (hasCapture(row, col, row - 2, col - 2) || hasCapture(row, col, row - 2, col + 2)) {
                            return true;
                        }
                    } else if (player == 'O') {
                        if (isValidPosition(row + 1, col - 1) && board[row + 1][col - 1] == '_') {
                            return true;
                        }
                        if (isValidPosition(row + 1, col + 1) && board[row + 1][col + 1] == '_') {
                            return true;
                        }
                        if (hasCapture(row, col, row + 2, col - 2) || hasCapture(row, col, row + 2, col + 2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidMove(int selectedRow, int selectedCol, int clickedRow, int clickedCol) {
        if (!isValidPosition(selectedRow, selectedCol) || !isValidPosition(clickedRow, clickedCol)) {
            return false;
        }

        char piece = board[selectedRow][selectedCol];
        char target = board[clickedRow][clickedCol];

        if (piece != currentPlayer) {
            return false; // It's not the current player's turn
        }

        int rowDistance = Math.abs(clickedRow - selectedRow);
        int colDistance = Math.abs(clickedCol - selectedCol);

        if (rowDistance != colDistance || rowDistance < 1 || rowDistance > 2) {
            return false; // Invalid move distance or not diagonal
        }

        if (rowDistance == 1) {
            // Single move, not a capture
            if (target != '_') {
                return false; // Target position is not empty
            }
        } else if (rowDistance == 2) {
            // Capture move
            int capturedRow = (selectedRow + clickedRow) / 2;
            int capturedCol = (selectedCol + clickedCol) / 2;

            char capturedPiece = board[capturedRow][capturedCol];
            if (capturedPiece == '_' || Character.toUpperCase(capturedPiece) == currentPlayer) {
                return false; // Invalid capture
            }
        }

        return true;
    }



    public boolean makeMove(int selectedRow, int selectedCol, int clickedRow, int clickedCol) {
        if (!isValidMove(selectedRow, selectedCol, clickedRow, clickedCol)) {
            return false;
        }

        // Move the player's piece to the target position
        board[clickedRow][clickedCol] = board[selectedRow][selectedCol];
        board[selectedRow][selectedCol] = '_';

        // Check for win/lose conditions
        if (isWin()) {
            System.out.println("Player " + currentPlayer + " wins!");
            return true;
        }

        // Switch players
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }



    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }
}