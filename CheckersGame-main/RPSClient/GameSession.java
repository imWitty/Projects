import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameSession implements Runnable {
    private Socket player1Socket;
    private Socket player2Socket;
    private PrintWriter player1Out;
    private PrintWriter player2Out;
    private BufferedReader player1In;
    private BufferedReader player2In;

    public GameSession(Socket player1Socket, Socket player2Socket) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;

        try {
            player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
            player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
            player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean player1Joined = false;
    private boolean player2Joined = false;

    public boolean isFull() {
        return player1Joined && player2Joined;
    }

    public void addPlayer(Socket playerSocket) {
        //  logic to add a player to the game session
        if (!player1Joined) {
            // Add the player to player1 slot
            player1Socket = playerSocket;
            player1Joined = true;
        } else if (!player2Joined) {
            // Add the player to player2 slot
            player2Socket = playerSocket;
            player2Joined = true;
        }
    }

    @Override
    public void run() {
        try {
            player1Out.println("Welcome to Rock-Paper-Scissors!");
            player2Out.println("Welcome to Rock-Paper-Scissors!");

            int player1Score = 0;
            int player2Score = 0;

            for (int round = 1; round <= 5; round++) {
                String player1Move = getPlayerMove(player1In, player1Out, 1);
                String player2Move = getPlayerMove(player2In, player2Out, 2);

                String roundResult = determineRoundWinner(player1Move, player2Move);

                if (roundResult.equals("Player 1 wins")) {
                    player1Score++;
                } else if (roundResult.equals("Player 2 wins")) {
                    player2Score++;
                }

                // Send round result to both players
                player1Out.println("Round " + round + ": " + roundResult);
                player2Out.println("Round " + round + ": " + roundResult);
            }

            // Determine the game winner
            String gameResult = determineGameWinner(player1Score, player2Score);

            // Send game result to both players
            player1Out.println("Game Result: " + gameResult);
            player2Out.println("Game Result: " + gameResult);

            // Ask players if they want to play again or quit
            player1Out.println("Play again? (yes/no)");
            player2Out.println("Play again? (yes/no)");

            // Handle player responses for playing again or quitting

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getPlayerMove(BufferedReader in, PrintWriter out, int playerNumber) throws IOException {
        out.println("Player " + playerNumber + ", enter your move (Rock/Paper/Scissors): ");
        return in.readLine();
    }

    private String determineRoundWinner(String move1, String move2) {
        // Convert both moves to lowercase for case-insensitive comparison
        move1 = move1.toLowerCase();
        move2 = move2.toLowerCase();

        if (move1.equals(move2)) {
            return "It's a tie";
        } else if ((move1.equals("rock") && move2.equals("scissors")) ||
                (move1.equals("scissors") && move2.equals("paper")) ||
                (move1.equals("paper") && move2.equals("rock"))) {
            return "Player 1 wins";
        } else {
            return "Player 2 wins";
        }
    }

    private String determineGameWinner(int player1Score, int player2Score) {
        if (player1Score > player2Score) {
            return "Player 1 wins";
        } else if (player2Score > player1Score) {
            return "Player 2 wins";
        } else {
            return "It's a tie";
        }
    }

}
