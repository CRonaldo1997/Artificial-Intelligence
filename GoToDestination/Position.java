package goToDestination;

public class Position {
	public int x;
	public int y;
	
	public Position(){
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;	
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof Position){
			Position otherPosition = (Position) object;
			if(this.x == otherPosition.x && this.y == otherPosition.y){
				return true;
			}
		}
		return false;
	}
}
