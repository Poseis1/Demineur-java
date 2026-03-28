package projet.tp11.minesweeper.model;

public class Position {
	private int line;
	private int column;
	
	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
	
	public boolean equals(Object obj) {
		return this.equals(obj);
	}
	
	public String toString() {
		String result = "(";
		result += line + "," + column + ")";
		return result;
	}
}
