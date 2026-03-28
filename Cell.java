package projet.tp11.minesweeper.model;

public class Cell {
	private boolean isMine;
	private int adjacentMines;
	private boolean isRevealed;
	private boolean isFlagged;

	public Cell() {
		isMine = false;
		adjacentMines = 0;
		isRevealed = false;
		isFlagged = false;
	}

	public String toString() {
		if (!isRevealed) {
			if (isFlagged)
				return "F";
			return "_";
		}
		// A NETTOYER C'EST DEGUEULASSE LÀ !!!!!
		if (isMine)
			return "*";
		if (adjacentMines == 0)
			return " ";
		return "" + adjacentMines;
	}

	public String toStringDebug() {
		String result = new String();
		result += adjacentMines;
		if (isMine)
			return result + "*";
		return result + "_";
	}

	public void reveal() {
		if (isFlagged)
			return;
		isRevealed = true;
	}

	public void toggleFlag() {
		isFlagged = isFlagged ? false : true;
	}

	public void setMine() {
		isMine = true;
	}

	public void incrementAdjacentMines() {
		adjacentMines++;
	}

	public boolean isMine() {
		return isMine;
	}

	public int getAdjacentMines() {
		return adjacentMines;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public boolean isFlagged() {
		return isFlagged;
	}
}
