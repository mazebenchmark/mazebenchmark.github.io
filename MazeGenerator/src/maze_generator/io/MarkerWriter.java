package maze_generator.io;

import java.io.Writer;

import maze_generator.types.Coordinate;
import maze_generator.types.Marker;
import unalcol.io.Write;
import unalcol.types.collection.vector.Vector;

public class MarkerWriter extends Write<Marker> {
	
	@Override
	public void write(Marker marker, Writer writer) throws Exception {
		Vector<Coordinate> marked = getMarkedCoordinates(marker);
		writer.write(marker.xsize + " " + marker.ysize);
		writer.write(" " + marked.size());
		for(Coordinate coord: marked)
			writer.write(" " + coord.x + " " + coord.y);
	}
	
	private Vector<Coordinate> getMarkedCoordinates(Marker marker) {
		Vector<Coordinate> marked = new Vector<Coordinate>();
		for(int x = 0; x < marker.xsize; x++)
			for(int y = 0; y < marker.ysize; y++)
				if(marker.isMarked(x, y))
					marked.add(new Coordinate(x, y));
		return marked;
	}
}
