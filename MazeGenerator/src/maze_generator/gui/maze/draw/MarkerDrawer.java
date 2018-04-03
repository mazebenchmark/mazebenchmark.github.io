package maze_generator.gui.maze.draw;

import java.awt.Color;
import java.awt.Graphics;

import maze_generator.types.Coordinate;
import maze_generator.types.Marker;

public class MarkerDrawer {
	private Marker marker;
	private MazeDrawSetting setting;

	public MarkerDrawer(Marker marker, MazeDrawSetting setting) {
		this.marker = marker;
		this.setting = setting;
	}

	public void draw(Graphics g, Color color) {
		g.setColor(color);
		for(int x = 0; x < marker.xsize; x++)
			for(int y = 0; y < marker.ysize; y++)
				if(marker.isMarked(x, y)) {
					Coordinate coord = new Coordinate(x, y);
					if(setting.isDrawable(coord)) {
						coord = setting.getCanvasCoordinate(coord);
						g.fillRect(coord.x, coord.y, setting.CELL_SIZE, setting.CELL_SIZE);
					}
				}
	}

}
