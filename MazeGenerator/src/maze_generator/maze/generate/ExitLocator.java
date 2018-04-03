package maze_generator.maze.generate;

import maze_generator.maze.MazeStructure;
import maze_generator.types.Coordinate;
import unalcol.algorithm.Algorithm;
import unalcol.random.integer.IntUniform;

public abstract class ExitLocator extends Algorithm<MazeStructure, MazeStructure> {
	
	protected IntUniform xgen, ygen;
	protected int exits;
	
	public ExitLocator(int exits) {
		this.exits = exits;
		xgen = ygen = new IntUniform(1);
	}

	public int getExits() {
		return exits;
	}

	public void setExits(int exits) {
		this.exits = exits;
	}

	@Override
	public void init(MazeStructure input) {
		super.init(input);
		xgen = new IntUniform(input.xsize);
		ygen = new IntUniform(input.ysize);
	}
	
	@Override
	public MazeStructure apply(MazeStructure structure) {
		init(structure);
		for(int i = 0; i < exits; ++i) {
			Coordinate exit = next(structure);
			structure.addExit(exit.x, exit.y);
		}
		return structure;
	}

	protected abstract Coordinate next(MazeStructure structure);
	
	public Coordinate rand() {
		return new Coordinate(xgen.generate(), ygen.generate());
	}

}
