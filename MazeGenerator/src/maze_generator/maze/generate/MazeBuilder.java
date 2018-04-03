package maze_generator.maze.generate;

import maze_generator.maze.MazeStructure;
import maze_generator.types.Coordinate;
import maze_generator.types.Movement;
import unalcol.random.integer.IntUniform;
import unalcol.random.util.Shuffle;
import unalcol.types.collection.vector.Vector;

public class MazeBuilder {
	
	public final static int FRONT = 0;
	public final static int RIGHT = 1;
	public final static int BACK  = 2;
	public final static int LEFT  = 3;
	
	public final static int[] dx  = { 0, 1, 0, -1};
	public final static int[] dy  = {-1, 0, 1,  0};
	public final static int[] inv = { 2, 3, 0,  1};
	
	public final static IntUniform dirgen = new IntUniform(4);
	public final static  Shuffle<Integer> shuffle = new Shuffle<Integer>();
	
	public static void addWall(MazeStructure structure, int x, int y, int dir) {
		if(structure.isValidLocation(x, y))
			structure.addWall(x, y, dir);
		if(structure.isValidLocation(x + dx[dir], y + dy[dir]))
			structure.addWall(x + dx[dir], y + dy[dir], inv[dir]);
	}
	
	public static void removeWall(MazeStructure structure, int x, int y, int dir) {
		if(structure.isValidLocation(x, y))
			structure.removeWall(x, y, dir);
		if(structure.isValidLocation(x + dx[dir], y + dy[dir]))
			structure.removeWall(x + dx[dir], y + dy[dir], inv[dir]);
	}
	
	public static Vector<Movement> createPath(boolean[][] goals, Coordinate start) {
		long time = System.currentTimeMillis();
		boolean[][] visited = new boolean[goals.length][goals[0].length];
		Vector<Movement> path = new Vector<Movement>();
		Coordinate front = start;
		visited[start.y][start.x] = true;
		while(path.size() == 0 || !goals[front.y][front.x]) {
			Movement movement = nextMovement(front, visited);
			if(path.size() > 0 && Math.random() < 0.2)
				removeLastMovement(path, visited);
			else
				addMovement(path, movement, visited, goals);
			front = (path.size() > 0) ? performMovement(path.get(path.size() - 1)) : start;
			visited[start.y][start.x] = true;
			if(System.currentTimeMillis() - time > 10000)
				return new Vector<Movement>();
		}
		return path;
	}

	private static Movement nextMovement(Coordinate cell, boolean[][] visited) {
		Movement movement = new Movement(cell, dirgen.generate());
		while(!isValidMovement(movement, visited[0].length, visited.length))
			movement = new Movement(cell, dirgen.generate());
		return movement;
	}

	private static boolean isValidMovement(Movement movement, int xsize, int ysize) {
		Coordinate cell = performMovement(movement);
		return 0 <= cell.x && cell.x < xsize && 0 <= cell.y && cell.y < ysize;
	}

	private static void addMovement(Vector<Movement> path, Movement movement, boolean[][] visited, boolean[][] goals) {
		Coordinate result = performMovement(movement);
		if(goals[result.y][result.x] || !visited[result.y][result.x]) {
			visited[result.y][result.x] = true;
			path.add(movement);
		}
	}

	private static void removeLastMovement(Vector<Movement> path, boolean[][] visited) {
		Coordinate cell = performMovement(path.get(path.size() - 1));
		visited[cell.y][cell.x] = false;
		path.remove(path.size() - 1);
	}

	public static Coordinate performMovement(Movement movement) {
		return new Coordinate(
				movement.cell.x + dx[movement.dir],
				movement.cell.y + dy[movement.dir]
				);
	}
}
