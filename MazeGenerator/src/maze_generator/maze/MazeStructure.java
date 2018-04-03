package maze_generator.maze;

import maze_generator.types.Coordinate;
import unalcol.types.collection.vector.Vector;

public class MazeStructure {
	public int xsize, ysize;
	public int data[][];
	public double connectivity;
	
	public static int EXITBIT = 4;
	public static int FILL = 15;
	public static int CLEAR = 0;
	
	public MazeStructure(int xsize, int ysize, double connectivity) {
		this.xsize = xsize;
		this.ysize = ysize;
		this.data = new int[ysize][xsize];
		this.connectivity = connectivity;
	}

	public void fill(int value) {
		for(int x = 0; x < xsize; x++)
			for(int y = 0; y < ysize; y++)
				data[y][x] = value;
	}
	
	public boolean isClear(int x, int y, int dir) {
		return (data[y][x] & (1 << dir)) == 0;
	}
	
	public int parse(int x, int y) {
		return y * xsize + x;
	}
	
	public Coordinate restore(int value) {
		return new Coordinate(value % xsize, value / xsize);
	}
	
	public void addWall(int x, int y, int dir) {
		data[y][x] |= (1 << dir);
	}
	
	public void removeWall(int x, int y, int dir) {
		int bit = data[y][x] & (1 << dir);
		data[y][x] ^= bit;
	}
	
	public boolean isValidLocation(int x, int y) {
		return x >= 0 && x < xsize && y >= 0 && y < ysize;
	}
	
	public void addExit(int x, int y) {
		data[y][x] |= (1 << EXITBIT);
	}
	
	public void removeExit(int x, int y) {
		int bit = data[y][x] & (1 << EXITBIT);
		data[y][x] ^= bit;
	}
	
	public void resize(int xsize, int ysize) {
		int[][] result = new int[ysize][xsize];
		int xcopy = Math.min(this.xsize, xsize);
		int ycopy = Math.min(this.ysize, ysize);
		for(int x = 0; x < xcopy; x++)
			for(int y = 0; y < ycopy; y++)
				result[y][x] = data[y][x];
		this.xsize = xsize;
		this.ysize = ysize;
		this.data = result;
	}
	
	public void copy(MazeStructure structure) {
		int xcopy = Math.min(xsize, structure.xsize);
		int ycopy = Math.min(ysize, structure.ysize);
		for(int x = 0; x < xcopy; x++)
			for(int y = 0; y < ycopy; y++)
				data[y][x] = structure.data[y][x];
	}
	
	@Override
	public MazeStructure clone() {
		MazeStructure structure = new MazeStructure(xsize, ysize, connectivity);
		copy(structure);
		return structure;
	}

	public boolean isGoal(int x, int y) {
		return (data[y][x] & (1 << EXITBIT)) > 0;
	}

	public Vector<Coordinate> rotate(int x, int y) {
		Vector<Coordinate> rotations = new Vector<Coordinate>();
		rotations.add(new Coordinate(x, y));
		rotations.add(new Coordinate(xsize - y - 1, x));
		rotations.add(new Coordinate(xsize - x - 1, ysize - y - 1));
		rotations.add(new Coordinate(y, ysize - x - 1));
		return rotations;
	}
}
