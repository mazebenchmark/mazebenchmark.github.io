package maze_generator.types;

public class Coordinate {
	public int x, y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int compare(Coordinate coord) {
		if(x != coord.x)
			return (x < coord.x) ? -1 : 1;
		if(y != coord.y)
			return (y < coord.y) ? -1 : 1;
		return 0;
	}

	public double dist(Coordinate coord) {
		return Math.sqrt((double) (((x - coord.x) * (x - coord.x)) + ((y - coord.y) * (y - coord.y))));
	}
}
