package maze_generator.types;

public class Movement {
	public Coordinate cell;
	public int dir;
	
	public Movement(Coordinate cell, int dir) {
		this.cell = cell;
		this.dir = dir;
	}
}
