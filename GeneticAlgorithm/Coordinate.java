
public class Coordinate {
	int x;
	int y;
	
//	public Coordinate(){
//		x = 0;
//		y = 0;
//	}
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof Coordinate){
			Coordinate otherCoordinate = (Coordinate) object;
			if(this.x == otherCoordinate.x && this.y == otherCoordinate.y){
				return true;
			}
		}
		return false;
	}
	

}
