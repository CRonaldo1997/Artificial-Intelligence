package goToDestination;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class Robot {
	public Position curPosition;
	public int curOrientation;
	public Position startPosition;
	public Position destPosition;
	public Stack <Position> pathRecorder;
	public ArrayList<Position> pathRecorderArray;
	public ArrayList<Integer> orientationRecorder;
	public int steps;
	public LinkedList<Position> leftFrontCells;
	public LinkedList<Position> frontCells;
	public LinkedList<Position> rightFrontCells;
	public Position positionToCheck;
	public Maze selfMaze;
	
	public Robot(){
		this.curPosition = new Position(5,5);
		this.positionToCheck = new Position(5,5);
		this.curOrientation = Orientation.SOUTH;
		this.startPosition = new Position(5,5);
		this.destPosition = new Position(29,29);
		this.pathRecorder = new Stack<Position>();
		this.pathRecorder.add(this.startPosition);
		this.steps = 0;
		this.leftFrontCells = new LinkedList<Position>();
		this.rightFrontCells = new LinkedList<Position>();
		this.frontCells = new LinkedList<Position>();
		this.pathRecorderArray = new ArrayList<Position>();
		this.orientationRecorder = new ArrayList<Integer>();
		this.selfMaze = new Maze(30,30,10);	//maze size 30*30, obstacle percentage: 10%
		this.selfMaze.grid[5][5] = 9;
	}
	
	//to check whether or not the position is in the maze
	public boolean isInMaze(){
		if(positionToCheck.x < 0 || positionToCheck.x > 29 || positionToCheck.y < 0 || positionToCheck.y > 29){
			return false;
		}
		return true;
	}
	
	//to check whether or not the position is an obstacle
	public boolean isObstacle(){
		if (this.isInMaze()==false){
			return false;
		}
		if(selfMaze.getCellInfo(positionToCheck) == 0){
			System.out.println("in isObstacle(), the obstacle of postionToCheck is "+positionToCheck.x+","+positionToCheck.y);
			return true;
		}else{
			return false;
		}
	}
	
	//go back to previous position
	public void goBack(){
		this.pathRecorder.pop();
		System.out.println("the top of the stack is"+this.pathRecorder.peek().x+","+this.pathRecorder.peek().y);
		Position lastPosition = this.pathRecorder.peek();
		this.curPosition = new Position(lastPosition.x,lastPosition.y);
	}
	
	//detect the path in front, right front and left front
	public void detectPath(){
		this.frontCells.clear();
		this.rightFrontCells.clear();
		this.leftFrontCells.clear();
		this.frontPath();
		this.rightFrontPath();
		this.leftFrontPath();
	}
	
	//detect front path
	public void frontPath(){
		this.positionToCheck = new Position(this.curPosition.x,this.curPosition.y);
		this.nextFrontCell();
		System.out.println("in frontPath(),positionToCheck is "+positionToCheck.x+","+positionToCheck.y);
		while(!isObstacle() && isInMaze()){
			this.frontCells.add(new Position(positionToCheck.x,positionToCheck.y));
			this.selfMaze.grid[positionToCheck.x][positionToCheck.y] = 9;
			this.nextFrontCell();
		}
		System.out.println("after frontPath(),this frontPath size is "+this.frontCells.size()+" the front cells are:");
		this.printNonObstacleCells(frontCells);
	}
	
	//detect right front path
	public void rightFrontPath(){
		this.positionToCheck = new Position(this.curPosition.x,this.curPosition.y);
		this.nextRightFrontCell();
		System.out.println("in rightFrontPath(),positionToCheck is "+positionToCheck.x+","+positionToCheck.y);
		while(!isObstacle() && isInMaze()){
			this.rightFrontCells.add(new Position(positionToCheck.x,positionToCheck.y));
			this.selfMaze.grid[positionToCheck.x][positionToCheck.y] = 9;
			this.nextRightFrontCell();
		}
		System.out.println("after rightFrontPath(),this rightFrontPath size is "+this.rightFrontCells.size()+" the front cells are:");
		this.printNonObstacleCells(rightFrontCells);
	}
	
	//detect left front path
	public void leftFrontPath(){
		this.positionToCheck = new Position(this.curPosition.x,this.curPosition.y);
		this.nextLeftFrontCell();
		System.out.println("in leftFrontPath(),positionToCheck is "+positionToCheck.x+","+positionToCheck.y);
		while(!isObstacle() && isInMaze()){
			this.leftFrontCells.add(new Position(positionToCheck.x,positionToCheck.y));
			this.selfMaze.grid[positionToCheck.x][positionToCheck.y] = 9;
			this.nextLeftFrontCell();
		}
		System.out.println("after leftfrontPath(),this leftfrontPath size is "+this.leftFrontCells.size()+" the front cells are:");
		this.printNonObstacleCells(leftFrontCells);
	}
	
	public void nextFrontCell(){
		switch(this.curOrientation){
		case Orientation.NORTH:
			this.positionToCheck.y += -1;
			break;
		case Orientation.NORTHEAST:
			this.positionToCheck.x += 1;
			this.positionToCheck.y += -1;
			break;
		case Orientation.EAST:
			this.positionToCheck.x += 1;
			break;
		case Orientation.SOUTHEAST:
			this.positionToCheck.x += 1;
			this.positionToCheck.y += 1;
			break;
		case Orientation.SOUTH:
			this.positionToCheck.y += 1;
			break;
		case Orientation.SOUTHWEST:
			this.positionToCheck.x += -1;
			this.positionToCheck.y += 1;
			break;
		case Orientation.WEST:
			this.positionToCheck.x += -1;
			break;
		case Orientation.NORTHWEST:
			this.positionToCheck.x += -1;
			this.positionToCheck.y += -1;
			break;
		default:
			break;
		}
	}
	
	public void nextRightFrontCell(){
		switch(this.curOrientation){
		case Orientation.NORTH:
			this.positionToCheck.x += 1;
			this.positionToCheck.y += -1;
			break;
		case Orientation.NORTHEAST:
			this.positionToCheck.x += 1;
			break;
		case Orientation.EAST:
			this.positionToCheck.x += 1;
			this.positionToCheck.y += 1;
			break;
		case Orientation.SOUTHEAST:
			this.positionToCheck.y += 1;
			break;
		case Orientation.SOUTH:
			this.positionToCheck.y += 1;
			this.positionToCheck.x += -1;
			break;
		case Orientation.SOUTHWEST:
			this.positionToCheck.x += -1;
			break;
		case Orientation.WEST:
			this.positionToCheck.x += -1;
			this.positionToCheck.y += -1;
			break;
		case Orientation.NORTHWEST:
			this.positionToCheck.y += -1;
			break;
		default:
			System.out.println("Wrong orientation");
			break;
		}
	}
	
	public void nextLeftFrontCell(){
		switch(this.curOrientation){
		case Orientation.NORTH:
			this.positionToCheck.x += -1;
			this.positionToCheck.y += -1;
			break;
		case Orientation.NORTHEAST:
			this.positionToCheck.y += -1;
			break;
		case Orientation.EAST:
			this.positionToCheck.x += 1;
			this.positionToCheck.y += -1;
			break;
		case Orientation.SOUTHEAST:
			this.positionToCheck.x += 1;
			break;
		case Orientation.SOUTH:
			this.positionToCheck.y += 1;
			this.positionToCheck.x += 1;
			break;
		case Orientation.SOUTHWEST:
			this.positionToCheck.y += 1;
			break;
		case Orientation.WEST:
			this.positionToCheck.x += -1;
			this.positionToCheck.y += 1;
			break;
		case Orientation.NORTHWEST:
			this.positionToCheck.x += -1;
			break;
		default:
			System.out.println("Wrong orientation");
			break;
		}
	}
	
	//print out the path in pathRecorder
	public void printPath(){
		Iterator<Position> it = pathRecorder.iterator();
		while(it.hasNext()){
			Position path = it.next();
			this.selfMaze.grid[path.x][path.y]=7;
			System.out.println("Path is "+ path.x + " , "+ path.y);
		}
	}
	
	//print out the available cells in the 3 directions
	public void printNonObstacleCells(LinkedList<Position> cells){
		Iterator<Position> it = cells.iterator();
		while(it.hasNext()){
			Position cell = it.next();
			System.out.println("non obstacle cells are "+cell.x+" , "+cell.y);
		}
	}
	
	public void turnLeft(){
		this.curOrientation = (this.curOrientation-1+8)%8;//this?
		this.steps++;
	}
	
	public void turnRight(){
		this.curOrientation = (this.curOrientation+1+8)%8;
		this.steps++;
	}
	
	//move forward based on the current orientation
	public void moveForward(){
		switch (this.curOrientation) {
		case Orientation.NORTH:
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHEAST:
			this.curPosition.x += 1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.EAST:
			this.curPosition.x += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHEAST:
			this.curPosition.x += 1;
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTH:
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHWEST:
			this.curPosition.x += -1;
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.WEST:
			this.curPosition.x += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHWEST:
			this.curPosition.x += -1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		default:
			break;
		}
	}
	
	//move to right front cell based on the current orientation 
	public void moveRight(){
		switch (this.curOrientation) {
		case Orientation.NORTH:
			this.turnRight();
			this.curPosition.x += 1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHEAST:
			this.turnRight();
			this.curPosition.x += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.EAST:
			this.turnRight();
			this.curPosition.x += 1;
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHEAST:
			this.turnRight();
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTH:
			this.turnRight();
			this.curPosition.y += 1;
			this.curPosition.x += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHWEST:
			this.turnRight();
			this.curPosition.x += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.WEST:
			this.turnRight();
			this.curPosition.x += -1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHWEST:
			this.turnRight();
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		default:
			System.out.println("Wrong orientation");
			break;
		}
	}
	
	//move to the left front cell based on the current orientation
	public void moveLeft(){
		switch (this.curOrientation) {
		case Orientation.NORTH:
			this.turnLeft();
			this.curPosition.x += -1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHEAST:
			this.turnLeft();
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.EAST:
			this.turnLeft();
			this.curPosition.x += 1;
			this.curPosition.y += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHEAST:
			this.turnLeft();
			this.curPosition.x += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTH:
			this.turnLeft();
			System.out.println("in turnLeft(), current orientation is "+ this.curOrientation);
			this.curPosition.y += 1;
			this.curPosition.x += 1;
			System.out.println("in turnLeft(), current position is "+ this.curPosition.x+","+this.curPosition.y);
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.SOUTHWEST:
			this.turnLeft();
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.WEST:
			this.turnLeft();
			this.curPosition.x += -1;
			this.curPosition.y += 1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		case Orientation.NORTHWEST:
			this.turnLeft();
			this.curPosition.x += -1;
			this.pathRecorder.add(new Position(curPosition.x,curPosition.y));
			this.selfMaze.grid[curPosition.x][curPosition.y]=8;
			this.steps++;
			break;
		default:
			System.out.println("Wrong orientation");
			break;
		}
	}
	
	//mark the cell the robot ever been 
	public boolean everBeenHere(){
		System.out.println("Ever been here!");
		return this.selfMaze.grid[this.curPosition.x][this.curPosition.y]==8;
	}
	
	//rule base to find the path
	public void findDestinationRules(){
		this.detectPath();

		//if destination in front, go straightforward to the destination
		if (this.frontCells.contains(this.destPosition)){
			while(!this.curPosition.equals(this.destPosition)){
				this.moveForward();
				System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			}
				System.out.println("Destination found!");
				return;
		}
		
		//if destination in right front, turn right and then go straightforward to the destination.
		if (this.rightFrontCells.contains(this.destPosition)){
			this.turnRight();
			while(!this.curPosition.equals(this.destPosition)){
				this.moveForward();

				System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			}
				System.out.println("Destination found!");
				return;
		}
		
		//if destination in left front, turn left and then go straightforward to the destination.
		if (this.leftFrontCells.contains(this.destPosition)){
			System.out.println("in leftFrontCell contains");
			this.turnLeft();
			while(!this.curPosition.equals(this.destPosition)){
				this.moveForward();
				System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			}
				System.out.println("Destination found!");
				return;
		}
		
		//if front path is not blocked then go straight
		if (!this.frontCells.isEmpty()){
			this.moveForward();
			System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			System.out.println("Move front!");
			return;
		}
		
		//if front cell is blocked then go to left front cell if it's available
		if (this.frontCells.isEmpty() && !this.leftFrontCells.isEmpty()){
			this.moveLeft();
			System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			System.out.println("Move right!");
			return;
		}
		
		//if front cell and left front cell are blocked then go to right front cell if it's available
		if (this.frontCells.isEmpty() && this.leftFrontCells.isEmpty() && !this.rightFrontCells.isEmpty()){
			this.moveRight();
			System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			System.out.println("Move left!");
			return;
		}
		
		//if all cells in the 3 directions are blocked then make a left turn and try again.
		if (this.frontCells.isEmpty() && this.rightFrontCells.isEmpty() && this.leftFrontCells.isEmpty()){
			System.out.println("turn left!");
			this.turnLeft();
			System.out.println("current position is "+this.curPosition.x+","+this.curPosition.y);
			return;
		}
	}
	
	public static void main(String[] args){
		Robot robot = new Robot();
		System.out.println("Current position is "+ robot.curPosition.x +","+ robot.curPosition.y);
		new Visualization(robot.selfMaze);
		while(!robot.curPosition.equals(robot.destPosition)){
		robot.findDestinationRules();
		}
		robot.printPath();
		System.out.println("After findDestinationRules(),Current position is "+ robot.curPosition.x +","+ robot.curPosition.y);
		System.out.println("There are "+robot.steps+" steps to go to the destination");
		new Visualization(robot.selfMaze);
	}
	
}
