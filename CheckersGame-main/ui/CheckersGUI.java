package ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import core.CheckersLogic; // Import CheckersLogic class

public class CheckersGUI extends Application {
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 80;
    private CheckerPiece selectedPiece = null;
    private CheckersLogic game = new CheckersLogic(); // Create an instance of CheckersLogic

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle square = new Rectangle(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                if ((row + col) % 2 == 0) {
                    square.setFill(Color.RED);
                } else {
                    square.setFill(Color.WHITE);
                }

                root.getChildren().add(square);

                if ((row + col) % 2 != 0) {
                    CheckerPiece piece;
                    if (row < 3) {
                        piece = new CheckerPiece('X', Color.BLACK); // 'X' represents player 1
                    } else if (row > 4) {
                        piece = new CheckerPiece('O', Color.WHITE); // 'O' represents player 2
                    } else {
                        continue; // Empty square
                    }

                    piece.setTranslateX(col * SQUARE_SIZE + SQUARE_SIZE / 2);
                    piece.setTranslateY(row * SQUARE_SIZE + SQUARE_SIZE / 2);

                    // Attach a click event handler to each piece
                    piece.setOnMouseClicked(event -> handlePieceClick(piece));
                    root.getChildren().add(piece);
                }
            }
        }

        primaryStage.setTitle("Checkers Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handlePieceClick(CheckerPiece piece) {
        if (selectedPiece == null) {
            selectedPiece = piece;
            piece.setStroke(Color.YELLOW); // Highlight the selected piece
        } else if (selectedPiece == piece) {
            selectedPiece.setStroke(Color.BLACK); // Deselect the piece
            selectedPiece = null;
        } else {
            // Swap positions of the selected piece and the clicked piece
            double selectedX = selectedPiece.getTranslateX();
            double selectedY = selectedPiece.getTranslateY();
            double clickedX = piece.getTranslateX();
            double clickedY = piece.getTranslateY();

            selectedPiece.setTranslateX(clickedX);
            selectedPiece.setTranslateY(clickedY);
            piece.setTranslateX(selectedX);
            piece.setTranslateY(selectedY);

            selectedPiece.setStroke(Color.BLACK); // Deselect the piece
            selectedPiece = null;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static class CheckerPiece extends javafx.scene.shape.Circle {
        private final char player;

        CheckerPiece(char player, Color color) {
            this.player = player;
            setRadius(SQUARE_SIZE / 2 - 10);
            setFill(color);
            setStroke(Color.BLACK);
            setStrokeWidth(2);
        }

        public char getPlayer() {
            return player;
        }
    }
}
