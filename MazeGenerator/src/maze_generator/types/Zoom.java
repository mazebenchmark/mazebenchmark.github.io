package maze_generator.types;

public class Zoom {
	
	public Coordinate min, max;
	public int maxValue;

	public Zoom(int size) {
		this.maxValue = size - 1;
		min = new Coordinate(0, 0);
		max = new Coordinate(size - 1, size - 1);
	}
	
	public boolean isInside(Coordinate coord) {
		return min.x <= coord.x && coord.x <= max.x && min.y <= coord.y && coord.y <= max.y;
	}
	
	public void zoomIn(Coordinate coord) {
		if(size() > 5) {
			switch(getNearest(coord, getBoundaries())) {
			case 0:	max.x--; max.y--; break;
			case 1:	min.x++; max.y--; break;
			case 2:	max.x--; min.y++; break;
			case 3:	min.x++; min.y++;
			}
		}
	}
	
	public void zoomOut(Coordinate coord) {
		if(size() <= maxValue)
			switch(getNearest(coord, getBoundaries())) {
			case 0:
				if(min.x > 0) min.x--; else max.x++;
				if(min.y > 0) min.y--; else max.y++;
				break;
			case 1:
				if(max.x < maxValue) max.x++; else min.x--;
				if(min.y > 0) min.y--; else max.y++;
				break;
			case 2:
				if(min.x > 0) min.x--; else max.x++;
				if(max.y < maxValue) max.y++; else min.y--;
				break;
			case 3:
				if(max.x < maxValue) max.x++; else min.x--;
				if(max.y < maxValue) max.y++; else min.y--;
			}
	}
	
	public int size() {
		return max.x - min.x + 1;
	}
	
	public int getNearest(Coordinate coord, Coordinate[] coords) {
		double dist = coord.dist(coords[0]);
		int idx = 0;
		for(int i = 1; i < coords.length; ++i)
			if(coord.dist(coords[i]) < dist) {
				dist = coord.dist(coords[i]);
				idx = i;
			}
		return idx;
	}
	
	public Coordinate[] getBoundaries() {
		Coordinate[] boundaries = new Coordinate[4];
		boundaries[0] = min;
		boundaries[1] = new Coordinate(max.x, min.y);
		boundaries[2] = new Coordinate(min.x, max.y);
		boundaries[3] = max;
		return boundaries;
	}
	
}
