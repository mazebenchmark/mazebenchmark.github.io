package maze_generator.maze.generate.wall;

import maze_generator.maze.MazeStructure;
import maze_generator.maze.generate.MazeBuilder;
import maze_generator.types.Coordinate;
import maze_generator.types.Movement;
import unalcol.algorithm.Algorithm;
import unalcol.random.integer.IntUniform;
import unalcol.random.raw.RawGenerator;
import unalcol.types.collection.vector.Vector;

public class GeMGA extends Algorithm<MazeStructure, MazeStructure> {

	double selectionProbability;
	Vector<Integer> marked;
	Vector<Integer> nonmarked;
	
	public GeMGA(double selectionProbability) {
		this.selectionProbability = selectionProbability;
	}
	
	@Override
	public void init(MazeStructure input) {
		super.init(input);
		input.fill(MazeStructure.FILL);
		marked = new Vector<Integer>();
		nonmarked = new Vector<Integer>();
		int size = input.xsize * input.ysize;
		for(int i = 0; i < size; i++)
			nonmarked.add(i);
	}

	@Override
	public MazeStructure apply(MazeStructure structure) {
		init(structure);
		boolean[][] goals = new boolean[structure.ysize][structure.xsize];
		Coordinate cell = chooseOne(structure, nonmarked);
		mark(structure, cell, goals);
		while(nonmarked.size() > 0) {
			cell = chooseEither(structure, nonmarked, marked);
			Vector<Movement> path = MazeBuilder.createPath(goals, cell);
			removeWalls(structure, path);
			markCells(structure, goals, path);
		}
		return structure;
	}

	private Coordinate chooseOne(MazeStructure structure, Vector<Integer> data) {
		int index = (new IntUniform(data.size())).next();
		return structure.restore(data.get(index));
	}

	private void mark(MazeStructure structure, Coordinate cell, boolean[][] goals) {
		if(!goals[cell.y][cell.x]) {
			int index = find(structure.parse(cell.x, cell.y), nonmarked);
			goals[cell.y][cell.x] = true;
			marked.add(nonmarked.get(index));
			nonmarked.remove(index);
		}
	}

	private int find(int goal, Vector<Integer> data) {
		int lo = 0, hi = data.size() - 1;
		while(lo < hi) {
			int mid = (lo + hi) >> 1;
			if(data.get(mid) < goal)
				lo = mid + 1;
			else
				hi = mid;
		}
		return (data.size() > 0) ? ((data.get(lo) == goal) ? lo : -1) : -1;
	}
	
	private Coordinate chooseEither(MazeStructure structure, Vector<Integer> option1, Vector<Integer> option2) {
		double option = RawGenerator.get(this).next();
		if(option < selectionProbability)
			return chooseOne(structure, option1);
		return chooseOne(structure, option2);
	}

	private void removeWalls(MazeStructure structure, Vector<Movement> path) {
		for(Movement movement: path)
			MazeBuilder.removeWall(structure, movement.cell.x, movement.cell.y, movement.dir);
	}

	private void markCells(MazeStructure structure, boolean[][] goals, Vector<Movement> path) {
		for(Movement movement: path)
			mark(structure,  movement.cell, goals);
	}

}
