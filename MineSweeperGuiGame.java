package projet.tp11.minesweeper.gui;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MineSweeperGuiGame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("MineSweeper");
			MineSweeperPanel panel = new MineSweeperPanel(20);
			frame.add(panel);
			frame.setSize(600, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});

	}
}
