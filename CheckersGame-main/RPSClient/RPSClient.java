import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class RPSClient extends Application {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Stage primaryStage;
    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;

    public RPSClient() {
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Rock-Paper-Scissors Game");

        // Create UI elements
        VBox root = new VBox();
        rockButton = new Button("Rock");
        paperButton = new Button("Paper");
        scissorsButton = new Button("Scissors");

        // Add event handlers for the buttons
        rockButton.setOnAction(event -> makeMove("Rock"));
        paperButton.setOnAction(event -> makeMove("Paper"));
        scissorsButton.setOnAction(event -> makeMove("Scissors"));

        root.getChildren().addAll(rockButton, paperButton, scissorsButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeMove(String move) {
        // Send the player's move to the server
        out.println(move);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
