package projet.tp11.minesweeper.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import projet.tp11.minesweeper.model.*;

public class MineSweeperPanel extends JPanel {

    private static final long serialVersionUID = 1L;// jlai mis au pif, jsp cque c'est
    private MineSweeper mineSweeper;
    private int borderSize;

    // c'est pas vraiment des propriétés mais les calculer et les stocker une fois
    // permet de réduire beaucoup les calculs.
    private int cellWidth;
    private int cellHeight;
    private Size border;
    private int textSize;

    private static final int LEFT_CLICK = 1; // sert à l'interraction avec le MouseListener (pas de 'nombre magique')
    private static final int RIGHT_CLICK = 3;

    public MineSweeperPanel(int borderSize) {
        this.mineSweeper = new MineSweeper(10, 10);
        this.borderSize = borderSize;
        this.addMouseListener(new MineSweeperMouseListener(this, mineSweeper));
    }

    private Size getEffectiveBorder() {
        int horizontalBorderSize = borderSize;
        int verticalBorderSize = borderSize;

        int theoricalWidth = 2 * horizontalBorderSize + mineSweeper.getNbColumns() * getCellWidth();
        int theoricalHeight = 2 * verticalBorderSize + mineSweeper.getNbLines() * getCellHeight();
        int offsetWidth = getWidth() - theoricalWidth;
        int offsetHeight = getHeight() - theoricalHeight;

        horizontalBorderSize = horizontalBorderSize + offsetWidth / 2;
        verticalBorderSize = verticalBorderSize + offsetHeight / 2;

        return new Size(horizontalBorderSize, verticalBorderSize);
    }

    public int getCellWidth() {
        return getWidth() / mineSweeper.getNbColumns() - 2 * borderSize / mineSweeper.getNbColumns();
    }

    public int getCellHeight() {
        return getHeight() / mineSweeper.getNbLines() - 2 * borderSize / mineSweeper.getNbLines();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        cellWidth = getCellWidth();
        cellHeight = getCellHeight();
        border = getEffectiveBorder();
        textSize = getTextSize();

        g.setFont(new Font("Arial", Font.PLAIN, textSize));
        drawBorder(g);
        drawMineSweeper(g);
    }

    private void drawMineSweeper(Graphics g) {
        for (int line = 0; line < mineSweeper.getNbLines(); line++) {
            for (int column = 0; column < mineSweeper.getNbColumns(); column++) {
                Position currentPosition = new Position(line, column);
                if (mineSweeper.isFlagged(currentPosition)) {
                    drawFlagged(g, currentPosition);
                } else if (!mineSweeper.isRevealed(currentPosition)) {
                    drawCell(g, currentPosition);
                } else if (mineSweeper.isMine(currentPosition)) {
                    drawMine(g, currentPosition);
                } else {
                    drawRevealed(g, currentPosition);
                }
            }
        }
    }

    private void fillCell(Graphics g, Position pos, Color color) {
        Coordinates coordinates = getDrawingCoordinates(pos);
        g.setColor(color);
        g.fillRect(coordinates.getX(), coordinates.getY(), cellWidth, cellHeight);
    }

    private void outlineCell(Graphics g, Position pos, Color color) {
        Coordinates coordinates = getDrawingCoordinates(pos);
        g.setColor(color);
        g.drawRect(coordinates.getX(), coordinates.getY(), cellWidth, cellHeight);
    }

    private void drawMine(Graphics g, Position position) {
        fillCell(g, position, Color.red);
    }

    private void drawFlagged(Graphics g, Position position) {
        fillCell(g, position, Color.blue);
    }

    private void drawRevealed(Graphics g, Position position) {
        outlineCell(g, position, Color.blue);

        // pas idéal de le mettre là mais ça permet d'avoir un truc plutot clean

        drawAdjacentMines(g, position);

    }

    private Coordinates getDrawingCoordinates(Position pos) {
        int cellX = pos.getColumn() * cellWidth + border.getWidth();
        int cellY = pos.getLine() * cellHeight + border.getHeight();
        return new Coordinates(cellX, cellY);
    }

    private void drawCell(Graphics g, Position position) {
        outlineCell(g, position, Color.black);
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), border.getHeight());
        g.fillRect(0, getHeight() - border.getHeight(), getWidth(), getHeight());
        g.fillRect(getWidth() - border.getWidth(), 0, getWidth(), getHeight());
        g.fillRect(0, 0, border.getWidth(), getHeight());
    }

    public Position getClickedPosition(double x, double y) {
        Size border = getEffectiveBorder();
        double gridX = x - border.getWidth();
        double gridY = y - border.getHeight();

        int resultX = (int) (gridX / getCellWidth());
        int resultY = (int) (gridY / getCellHeight());

        return new Position(resultY, resultX);
    }

    public void processClick(Position pos, int button) {
        if ((!mineSweeper.isValidPosition(pos) || mineSweeper.isRevealed(pos)))
            return;

        if (button == LEFT_CLICK) {
            mineSweeper.revealCell(pos);
        } else if (button == RIGHT_CLICK) {
            mineSweeper.toggleFlag(pos);
        }
    }

    private int getTextSize() {
        return Math.min(getCellWidth(), getCellHeight());
    }

    private void drawAdjacentMines(Graphics g, Position position) {
        Coordinates coordinates = getDrawingCoordinates(position);
        g.setColor(Color.black);

        FontMetrics fm = g.getFontMetrics();
        String text = String.valueOf(mineSweeper.getAdjacentMines(position));
        int tx = coordinates.getX() + (cellWidth - fm.stringWidth(text)) / 2;
        int ty = coordinates.getY() + (cellHeight + fm.getAscent() - fm.getDescent()) / 2;
        g.drawString(text, tx, ty);
    }

}