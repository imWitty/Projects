import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPSServer {
    private static final int PORT = 5000;
    private static final int NUM_PLAYERS_PER_GAME = 2;

    private ServerSocket serverSocket;
    private List<GameSession> gameSessions = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public RPSServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running. Waiting for players to connect...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                if (gameSessions.size() < NUM_PLAYERS_PER_GAME) {
                    // Wait for players to connect
                    Socket player1Socket = serverSocket.accept();
                    System.out.println("Player 1 connected: " + player1Socket);

                    // Wait for another player to connect
                    Socket player2Socket = serverSocket.accept();
                    System.out.println("Player 2 connected: " + player2Socket);

                    // Create a new game session or add the players to an existing one
                    GameSession gameSession = findOrCreateGameSession(player1Socket, player2Socket);
                    gameSession.addPlayer(player1Socket);
                    gameSession.addPlayer(player2Socket);

                    // Start a new thread to handle the game session
                    executorService.execute(gameSession);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private GameSession findOrCreateGameSession(Socket player1Socket, Socket player2Socket) {
        for (GameSession gameSession : gameSessions) {
            if (!gameSession.isFull()) {
                return gameSession;
            }
        }
        GameSession newGameSession = new GameSession(player1Socket, player2Socket);
        gameSessions.add(newGameSession);
        return newGameSession;
    }

    public static void main(String[] args) {
        RPSServer server = new RPSServer();
        server.start();
    }
}
