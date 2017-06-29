package goToDestination;

import java.util.Random;

public class Maze {
	public int height;
	public int width;
	public int percentage;
	public int[][] grid;
	
	public Maze(){
	}
	
	//get cell info 
	public int getCellInfo(Position pos){
		return grid[pos.x][pos.y];
	}
	
	public Maze(int height, int width, int percentage){
		this.width = width;
		this.height = height;
		this.percentage = percentage;
		this.grid = new int[width][height];
		this.mazeInitializer();
		this.obstacleGenerator();	
	}
	
	//initially all the cell is 1
	public void mazeInitializer(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				grid[i][j] = 1;
			}
		}	
	}
	
	// generate obstacles based on the percentage
	public void obstacleGenerator(){
		Random  random = new Random();
		int obstacleNum = width * height * percentage/100;
		int count = obstacleNum;
	
		while(count != 0){
			int obstacleX = random.nextInt(width);
			int obstacleY = random.nextInt(height);
			if(obstacleX == 5 && obstacleY == 5){
				continue;
			}
			if(obstacleX == 29 && obstacleY == 29){
				continue;
			}
			if(grid[obstacleX][obstacleY] == 0){
				continue;
			}
			grid[obstacleX][obstacleY] = 0;
			count--;
		}
	}
}
