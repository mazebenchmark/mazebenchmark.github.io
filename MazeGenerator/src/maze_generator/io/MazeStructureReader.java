package maze_generator.io;

import java.io.IOException;

import maze_generator.maze.MazeStructure;
import unalcol.io.ShortTermMemoryReader;

public class MazeStructureReader {
	
	public final static int SPACE = ' ';
	public final static int ZERO = '0';
	public final static int POINT = '.';
	public final static int EOF = -1;
	
	public static MazeStructure read(ShortTermMemoryReader reader) throws IOException {
		int xsize = readNumber(reader), ysize = readNumber(reader);
		double connectivity = readDouble(reader);
		MazeStructure structure = new MazeStructure(xsize, ysize, connectivity);
		for(int y = 0; y < ysize; y++)
			for(int x = 0; x < xsize; x++)
				structure.data[y][x] = readNumber(reader);
		return structure;
	}
	
	public static int readNumber(ShortTermMemoryReader reader) throws IOException {
		int number = 0;
		for(int c = reader.read(); c > EOF && c != SPACE; c = reader.read())
			number = (number * 10) + c - ZERO;
		return number;
	}
	
	public static double readDouble(ShortTermMemoryReader reader) throws IOException {
		boolean point = false;
		double number = 0, multiply = 0.1;
		for(int c = reader.read(); c > EOF && c != SPACE; c = reader.read()) {
			if(c == POINT)
				point = true;
			else if(point) {
				number += multiply * (c - ZERO);
				multiply *= 0.1;
			} else
				number = (number * 10) + c - ZERO;
		}
		return number;
	}
}
