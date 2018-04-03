package maze_generator.types;

import unalcol.types.collection.vector.Vector;

public class Marker {
	boolean[][] marked;
	public int xsize, ysize;
	
	public Marker(int xsize, int ysize) {
		this.marked = new boolean[ysize][xsize];
		this.xsize = xsize;
		this.ysize = ysize;
	}
	
	public void mark(int x, int y) {
		marked[y][x] = true;
	}
	
	public void unmark(int x, int y) {
		marked[y][x] = false;
	}
	
	public void turn(int x, int y) {
		marked[y][x] = !marked[y][x];
	}
	
	public boolean isMarked(int x, int y) {
		return marked[y][x];
	}
	
	public void clear() {
		for(int i = 0; i < marked.length; i++)
			for(int j = 0; j < marked[i].length; j++)
				marked[i][j] = false;
	}
	
	public void fill() {
		for(int i = 0; i < marked.length; i++)
			for(int j = 0; j < marked[i].length; j++)
				marked[i][j] = true;
	}

	public boolean isValid(int x, int y) {
		return 0 <= x && x < xsize && 0 <= y && y < ysize;
	}
	
	public Vector<Coordinate> getMarkedCells() {
		Vector<Coordinate> coordinates = new Vector<Coordinate>();
		for(int y = 0; y < marked.length; y++)
			for(int x = 0; x < marked[y].length; x++)
				if(marked[y][x])
					coordinates.add(new Coordinate(x, y));
		return coordinates;
	}
}
