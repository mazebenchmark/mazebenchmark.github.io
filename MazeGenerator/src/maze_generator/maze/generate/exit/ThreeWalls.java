package maze_generator.maze.generate.exit;

import maze_generator.maze.MazeStructure;
import maze_generator.maze.generate.ExitLocator;
import maze_generator.types.Coordinate;
import unalcol.random.integer.IntUniform;
import unalcol.types.collection.vector.Vector;

public class ThreeWalls extends ExitLocator {

	Vector<Integer> locations;
	
	public ThreeWalls(int exits) {
		super(exits);
	}

	@Override
	public void init(MazeStructure input) {
		super.init(input);
		int lenght = input.xsize * input.ysize;
		locations = new Vector<Integer>();
		for(int i = 0; i < lenght; ++i) {
			Coordinate loc = input.restore(i);
			int count = 0;
			for(int d = 0; d < 4; ++d)
				if(input.isClear(loc.x, loc.y, d))
					++count;
			if(count == 1)
				locations.add(i);
		}
	}
	
	@Override
	protected Coordinate next(MazeStructure structure) {
		init(structure);
		if(locations.size() > 0)
			return structure.restore(getOne(locations));
		return rand();
	}
	
	public static int getOne(Vector<Integer> values) {
		int index = (new IntUniform(values.size())).next();
		int exit = values.get(index);
		values.remove(index);
		return exit;
	}

}
