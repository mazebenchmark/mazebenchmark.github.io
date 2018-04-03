package maze_generator.io;

import java.io.Writer;

import maze_generator.maze.MazeStructure;
import unalcol.io.Write;

public class MazeStructureWriter extends Write<MazeStructure> {

	@Override
	public void write(MazeStructure structure, Writer writer) throws Exception {
		writer.write(structure.xsize + " " + structure.ysize + " " + structure.connectivity);
		for(int y = 0; y < structure.ysize; y++)
			for(int x = 0; x < structure.xsize; x++)
				writer.write(" " + structure.data[y][x]);
	}

}
