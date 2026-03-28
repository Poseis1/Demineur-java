package projet.tp11.minesweeper.gui;

import projet.tp11.minesweeper.model.MineSweeper;
import projet.tp11.minesweeper.model.Position;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MineSweeperMouseListener extends MouseAdapter {
	MineSweeperPanel panel;
	MineSweeper mineSweeper;

	public MineSweeperMouseListener(MineSweeperPanel panel, MineSweeper mineSweeper) {
		this.panel = panel;
		this.mineSweeper = mineSweeper;
	}
	
	public void mouseClicked(MouseEvent e) {
		int button = e.getButton();
		double x = e.getX();
		double y = e.getY();
		Position gridPosition = panel.getClickedPosition(x, y);
		panel.processClick(gridPosition, button);
		panel.repaint();
	}

}