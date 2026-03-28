package projet.tp11.minesweeper.model;

public class TestMineSweeper {

	public static void main(String[] args) {
		
		MineSweeper mineSweeper = new MineSweeper(5, 10);
		System.out.println(mineSweeper.toString());
		System.out.println(mineSweeper.toStringDebug());

		System.out.println(" \n\n\n\n\n\nAprès reveal et toogleFlag :  \n");

		
		mineSweeper.toggleFlag(new Position(2, 2));
		mineSweeper.revealCell(new Position(0, 0));
		System.out.println(mineSweeper.toString());
		System.out.println(mineSweeper.toStringDebug());

	}

}
