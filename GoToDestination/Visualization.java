package goToDestination;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualization extends JFrame{
	int size = 30*30;
	JPanel panel;
	JButton grids[] = new JButton[size];
	Maze maze;
	
	public Visualization(Maze maze){
		this.maze = maze;
		setSize(800,800);
		panel = new JPanel();
		GridLayout gridLayout = new GridLayout(30,30);
		panel.setLayout(gridLayout);
		
		//set the color to show obstacle, detected cells and robot path
		for(int i=0; i < size; i++){
			grids[i] = new JButton();
			int x = i%30;
			int y = i/30;
			Position pos = new Position(x,y);
			if(this.maze.getCellInfo(pos) == 0){
				grids[i].setBackground(Color.red);
				grids[i].setOpaque(true);
				grids[i].setBorderPainted(false);
			}else if (this.maze.getCellInfo(pos) == 9) {
		        grids[i].setBackground(Color.yellow);
		        grids[i].setOpaque(true);
		        grids[i].setBorderPainted(false);
			}else if (this.maze.getCellInfo(pos) == 7){
				grids[i].setBackground(Color.blue);
		        grids[i].setOpaque(true);
		        grids[i].setBorderPainted(false);
			}
			panel.add(grids[i]);
		}
		add(panel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
