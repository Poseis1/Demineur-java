package projet.tp11.minesweeper.model;

public class Coordinates {
	private int x;
	private int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object obj) {
		return this.equals(obj);
	}
	
	public String toString() {
		String result = "(";
		result += x + "," + y + ")";
		return result;
	}
}
