import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing a game piece for Connect Four.
 * Author: Nishtha Chaudhari , 000930353
 */
class ConnectFourPiece extends Piece {
    /**
     * Constructor for the ConnectFourPiece class.
     *
     * @param row   The row position of the piece.
     * @param col   The column position of the piece.
     * @param color The color of the piece.
     */
    public ConnectFourPiece(int row, int col, Color color) {
        super(row, col, color);
    }
    /**
     * Draw the Connect Four piece on the canvas.
     *
     * @param gc       The GraphicsContext used for drawing.
     * @param cellSize The size of each cell on the canvas.
     */
    @Override
    public void draw(GraphicsContext gc, int cellSize) {
        super.draw(gc, cellSize);
    }
}
