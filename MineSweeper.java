package projet.tp11.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class MineSweeper {

	private int nbLines;
	private int nbColumns;
	private List<List<Cell>> grid;

	public MineSweeper(int nbLines, int nbColumns) {
		this.nbColumns = nbColumns;
		this.nbLines = nbLines;
		grid = new ArrayList<List<Cell>>(); // Pas sur que ce soit la bonne manière

		for (int line = 0; line < nbLines; line++) {
			ArrayList<Cell> cells = new ArrayList<Cell>();
			for (int column = 0; column < nbColumns; column++) {
				cells.add(new Cell());
			}
			grid.add(cells);
		}

		placeMines();
	}

	public String toString() {
		String result = new String();
		result = "Affichage du démineur : \n";
		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				result += getCell(new Position(line, column)).toString();
				result += " ";
			}
			result += "\n";
		}
		return result;
	}

	public String toStringDebug() {
		String result = new String();
		result = "Affichage du démineur en mode débug 	: \n";

		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				result += getCell(new Position(line, column)).toStringDebug();
				result += " ";
			}
			result += "\n";

		}
		return result;
	}

	public int countTotalCells() {
		return nbLines * nbColumns;
	}

	private Cell getCell(Position pos) {
		return grid.get(pos.getLine()).get(pos.getColumn());
	}

	public void setMine(int line, int column) {
		grid.get(line).get(column).setMine();
	}

	private void placeMines() {
		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				boolean randomBoolean = Math.random() <= 0.15;// 15% de chances d'être true.
				if (randomBoolean) {
					setMine(line, column);
					increaseAdjacentMines(new Position(line, column));
				}
			}
		}
	}

	public void increaseAdjacentMines(Position pos) {
		List<Position> adjacentPos = getAdjacentPositions(pos);
		int size = adjacentPos.size();
		for (int index = 0; index < size; index++) {
			getCell(adjacentPos.get(index)).incrementAdjacentMines();
		}
	}

	public boolean isValidPosition(Position pos) {
		int line = pos.getLine();
		int column = pos.getColumn();

		if (line > nbLines - 1)
			return false;
		if (column > nbColumns - 1)
			return false;
		if (line < 0)
			return false;
		if (column < 0)
			return false;
		return true;
	}

	public List<Position> getAdjacentPositions(Position pos) {
		int line = pos.getLine();
		int column = pos.getColumn();
		List<Position> result = new ArrayList<Position>();

		boolean topEdge = line == 0;
		boolean bottomEdge = line == nbLines - 1;
		boolean leftEdge = column == 0;
		boolean rightEdge = column == nbColumns - 1;

		boolean topLeftCorner = topEdge && leftEdge;
		boolean topRightCorner = topEdge && rightEdge;
		boolean bottomLeftCorner = bottomEdge && leftEdge;
		boolean bottomRightCorner = bottomEdge && rightEdge;

		if (topLeftCorner) {
			result.add(new Position(line, column + 1));
			result.add(new Position(line + 1, column));
			result.add(new Position(line + 1, column + 1));
		} else if (topRightCorner) {
			result.add(new Position(line, column - 1));
			result.add(new Position(line + 1, column - 1));
			result.add(new Position(line + 1, column));
		} else if (bottomLeftCorner) {
			result.add(new Position(line - 1, column));
			result.add(new Position(line - 1, column + 1));
			result.add(new Position(line, column + 1));
		} else if (bottomRightCorner) {
			result.add(new Position(line - 1, column - 1));
			result.add(new Position(line - 1, column));
			result.add(new Position(line, column - 1));
		} else if (topEdge) {
			result.add(new Position(line, column - 1));
			result.add(new Position(line, column + 1));
			result.add(new Position(line + 1, column - 1));
			result.add(new Position(line + 1, column));
			result.add(new Position(line + 1, column + 1));
		} else if (bottomEdge) {
			result.add(new Position(line - 1, column - 1));
			result.add(new Position(line - 1, column));
			result.add(new Position(line - 1, column + 1));
			result.add(new Position(line, column - 1));
			result.add(new Position(line, column + 1));
		} else if (leftEdge) {
			result.add(new Position(line - 1, column));
			result.add(new Position(line - 1, column + 1));
			result.add(new Position(line, column + 1));
			result.add(new Position(line + 1, column));
			result.add(new Position(line + 1, column + 1));
		} else if (rightEdge) {
			result.add(new Position(line - 1, column - 1));
			result.add(new Position(line - 1, column));
			result.add(new Position(line, column - 1));
			result.add(new Position(line + 1, column - 1));
			result.add(new Position(line + 1, column));
		} else {
			result.add(new Position(line - 1, column - 1));
			result.add(new Position(line - 1, column));
			result.add(new Position(line - 1, column + 1));
			result.add(new Position(line, column - 1));
			result.add(new Position(line, column + 1));
			result.add(new Position(line + 1, column - 1));
			result.add(new Position(line + 1, column));
			result.add(new Position(line + 1, column + 1));
		}
		return result;

	}

	public void revealCell(Position position) {
		if (isGameOver() || isGameWon())
			return;

		Cell cell = getCell(position);

		if (cell.isRevealed())
			return;

		if (cell.isFlagged())
			return;
		
		cell.reveal();
		
		if (getAdjacentMines(position) == 0)
			chainReveal(position);
	}

	private void chainReveal(Position position) {
		List<Position> adjacentPositions = new ArrayList<Position>();
		adjacentPositions = getAdjacentPositions(position);
		for (int index = 0; index < adjacentPositions.size(); index++) {
			revealCell(adjacentPositions.get(index));
		}
	}

	public void toggleFlag(Position position) {
		if (isGameOver() || isGameWon())
			return;
		grid.get(position.getLine()).get(position.getColumn()).toggleFlag();
	}

	public int countRevealedCells() {
		int count = 0;
		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				if (getCell(new Position(line, column)).isRevealed())
					count++;
			}
		}
		return count;

	}

	public int countUnrevealedCells() {
		return countTotalCells() - countRevealedCells();
	}

	public int countMines() {
		int count = 0;
		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				if (getCell(new Position(line, column)).isMine())
					count++;
			}
		}
		return count;
	}

	public boolean isGameWon() {
		return (countRevealedCells() == countTotalCells() - countMines());
	}

	public boolean isGameOver() {
		for (int line = 0; line < nbLines; line++) {
			for (int column = 0; column < nbColumns; column++) {
				Cell currentCell = getCell(new Position(line, column));
				if (currentCell.isMine() && currentCell.isRevealed())
					return true;
			}
		}
		return false;
	}

	public int getNbLines() {
		return nbLines;
	}

	public int getNbColumns() {
		return nbColumns;
	}

	public boolean isMine(Position pos) {
		return getCell(pos).isMine();
	}

	public boolean isRevealed(Position pos) {
		return getCell(pos).isRevealed();
	}

	public int getAdjacentMines(Position pos) {
		return getCell(pos).getAdjacentMines();
	}

	public boolean isFlagged(Position pos) {
		return getCell(pos).isFlagged();
	}
}

/*
 * public void increaseAdjacentMines(int line, int column) { boolean topEdge =
 * line == 0; boolean bottomEdge = line == nbLines - 1; boolean leftEdge =
 * column == 0; boolean rightEdge = column == nbColumns - 1;
 * 
 * boolean topLeftCorner = topEdge && leftEdge; boolean topRightCorner = topEdge
 * && rightEdge; boolean bottomLeftCorner = bottomEdge && leftEdge; boolean
 * bottomRightCorner = bottomEdge && rightEdge; if (topLeftCorner)
 * increaseAdjacentMinesTopLeft(line, column); else if (topRightCorner)
 * increaseAdjacentMinesTopRight(line, column); else if (bottomLeftCorner)
 * increaseAdjacentMinesBottomLeft(line, column); else if (bottomRightCorner)
 * increaseAdjacentMinesBottomRight(line, column); else if (topEdge)
 * increaseAdjacentMinesTop(line, column); else if (bottomEdge)
 * increaseAdjacentMinesBottom(line, column); else if (leftEdge)
 * increaseAdjacentMinesLeft(line, column); else if (rightEdge)
 * increaseAdjacentMinesRight(line, column); else
 * increaseAdjacentMinesFull(line, column); }
 * 
 * public void increaseAdjacentMinesFull(int line, int column) { grid.get(line -
 * 1).get(column - 1).incrementAdjacentMines(); grid.get(line -
 * 1).get(column).incrementAdjacentMines(); grid.get(line - 1).get(column +
 * 1).incrementAdjacentMines(); grid.get(line).get(column -
 * 1).incrementAdjacentMines(); grid.get(line).get(column +
 * 1).incrementAdjacentMines(); grid.get(line + 1).get(column -
 * 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); grid.get(line + 1).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesTopLeft(int line, int column) {
 * grid.get(line).get(column + 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); grid.get(line + 1).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesTopRight(int line, int column) {
 * grid.get(line).get(column - 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column - 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesBottomLeft(int line, int column) {
 * grid.get(line - 1).get(column).incrementAdjacentMines(); grid.get(line -
 * 1).get(column + 1).incrementAdjacentMines(); grid.get(line).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesBottomRight(int line, int column) {
 * grid.get(line - 1).get(column - 1).incrementAdjacentMines(); grid.get(line -
 * 1).get(column).incrementAdjacentMines(); grid.get(line).get(column -
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesTop(int line, int column) {
 * grid.get(line).get(column - 1).incrementAdjacentMines();
 * grid.get(line).get(column + 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column - 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); grid.get(line + 1).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesBottom(int line, int column) { grid.get(line
 * - 1).get(column - 1).incrementAdjacentMines(); grid.get(line -
 * 1).get(column).incrementAdjacentMines(); grid.get(line - 1).get(column +
 * 1).incrementAdjacentMines(); grid.get(line).get(column -
 * 1).incrementAdjacentMines(); grid.get(line).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesLeft(int line, int column) { grid.get(line -
 * 1).get(column).incrementAdjacentMines(); grid.get(line - 1).get(column +
 * 1).incrementAdjacentMines(); grid.get(line).get(column +
 * 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); grid.get(line + 1).get(column +
 * 1).incrementAdjacentMines(); }
 * 
 * public void increaseAdjacentMinesRight(int line, int column) { grid.get(line
 * - 1).get(column - 1).incrementAdjacentMines(); grid.get(line -
 * 1).get(column).incrementAdjacentMines(); grid.get(line).get(column -
 * 1).incrementAdjacentMines(); grid.get(line + 1).get(column -
 * 1).incrementAdjacentMines(); grid.get(line +
 * 1).get(column).incrementAdjacentMines(); }
 */
