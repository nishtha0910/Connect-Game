import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
/**
 * Class representing the Connect Four game logic.
 * Author: Nishtha Chaudhari , 000930353
 */
public class ConnectFourGame {
    private static final int CELL_SIZE = 40;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int WINNING_COUNT = 4;

    private List<Piece> pieces;
    private boolean playerOneTurn = true;
    /**
     * Constructor for the ConnectFourGame class.
     */
    public ConnectFourGame() {
        pieces = new ArrayList<>();
    }
    /**
     * Initialize the Connect Four game.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void initGame(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        BorderPane layout = new BorderPane();
        VBox controls = new VBox();

        TextField rowInput = new TextField("Enter Row");
        TextField colInput = new TextField("Enter Column");
        Button removeButton = new Button("Remove Piece");

        removeButton.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            //remove button
            public void handle(javafx.event.ActionEvent e) {
                try {
                    int row = Integer.parseInt(rowInput.getText());
                    int col = Integer.parseInt(colInput.getText());
                    removePiece(row, col, gc);
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid input! Please enter valid numbers for row and column.");
                }
            }
        });

        controls.getChildren().addAll(rowInput, colInput, removeButton);
        layout.setCenter(root);
        layout.setRight(controls);

        canvas.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                handleUserClick((int) e.getX(), gc);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(layout));
        drawGrid(gc);
    }

    private void drawGrid(GraphicsContext gc)
    {
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);

        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= ROWS; i++) {
            gc.strokeLine(0, i * CELL_SIZE, COLUMNS * CELL_SIZE, i * CELL_SIZE);
        }
        for (int j = 0; j <= COLUMNS; j++) {
            gc.strokeLine(j * CELL_SIZE, 0, j * CELL_SIZE, ROWS * CELL_SIZE);
        }

        for (Piece piece : pieces) {
            piece.draw(gc, CELL_SIZE);
        }
    }

    private void handleUserClick(int x, GraphicsContext gc) {
        int col = x / CELL_SIZE;
        try {
            int row = getEmptyRow(col);
            if (row != -1) {
                Color pieceColor = playerOneTurn ? Color.RED : Color.YELLOW;
                pieces.add(new ConnectFourPiece(row, col, pieceColor));
                drawGrid(gc);

                if (checkWin(row, col, pieceColor)) {
                    showWinDialog(playerOneTurn ? "Player One" : "Player Two");
                    resetGame(gc);
                } else if (isGridFull()) {
                    showErrorDialog("It's a draw!");
                    resetGame(gc);
                } else {
                    playerOneTurn = !playerOneTurn;
                }
            } else {
                throw new IllegalArgumentException("Column is full!");
            }
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
        }
    }
// to get empty row
    private int getEmptyRow(int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (getPieceAt(i, col) == null) {
                return i;
            }
        }
        return -1;
    }
// get piece
    private Piece getPieceAt(int row, int col) {
        for (Piece piece : pieces) {
            if (piece.row == row && piece.col == col) {
                return piece;
            }
        }
        return null;
    }
// remove piece
    private void removePiece(int row, int col, GraphicsContext gc) {
        Piece pieceToRemove = getPieceAt(row, col);
        if (pieceToRemove != null) {
            pieces.remove(pieceToRemove);
            drawGrid(gc);
        } else {
            showErrorDialog("No piece found at the specified position!");
        }
    }
// to check the win
    private boolean checkWin(int row, int col, Color color) {
        return checkDirection(row, col, color, 1, 0) ||
                checkDirection(row, col, color, 0, 1) ||
                checkDirection(row, col, color, 1, 1) ||
                checkDirection(row, col, color, 1, -1);
    }
// to check the direction
    private boolean checkDirection(int row, int col, Color color, int dRow, int dCol) {
        int count = 1;
        count += countDirection(row, col, color, dRow, dCol);
        count += countDirection(row, col, color, -dRow, -dCol);
        return count >= WINNING_COUNT;
    }

    private int countDirection(int row, int col, Color color, int dRow, int dCol) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
            Piece piece = getPieceAt(r, c);
            if (piece != null && piece.color == color) {
                count++;
            } else {
                break;
            }
            r += dRow;
            c += dCol;
        }
        return count;
    }

    private boolean isGridFull() {
        return pieces.size() == ROWS * COLUMNS;
    }
// to show error when column is full
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
// to show the user that they have won
    private void showWinDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Congratulations, " + winner + "! You win!");
        alert.showAndWait();
    }

    private void resetGame(GraphicsContext gc) {
        pieces.clear();
        playerOneTurn = true;
        drawGrid(gc);
    }
}
