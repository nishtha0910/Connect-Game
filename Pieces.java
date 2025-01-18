import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Class representing a general game piece.
 * Auhor: Nishtha Chaudhari , 000930353
 */
class Piece {
    protected int row;
    protected int col;
    protected Color color;
    /**
     * Constructor for the Piece class.
     *
     * @param row   The row position of the piece.
     * @param col   The column position of the piece.
     * @param color The color of the piece.
     */
    public Piece(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
    /**
     * Draw the piece on the canvas.
     *
     * @param gc       The GraphicsContext used for drawing.
     * @param cellSize The size of each cell on the canvas.
     */
    public void draw(GraphicsContext gc, int cellSize) {
        gc.setFill(color);
        gc.fillOval(col * cellSize + 2, row * cellSize + 2, cellSize - 4, cellSize - 4);
    }
}
