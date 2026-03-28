package projet.tp11.minesweeper.model;

public class Size {
	private int width;
	private int height;
	
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean equals(Object obj) {
		return this.equals(obj);
	}
	
	public String toString() {
		String result = "(";
		result += width + "," + height + ")";
		return result;
	}
}
