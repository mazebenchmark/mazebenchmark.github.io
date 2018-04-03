package maze_generator.gui.maze.draw;

import maze_generator.types.Coordinate;
import maze_generator.types.Zoom;

public class MazeDrawSetting {
	
	public int DIMENSION;
	public int CELL_SIZE;
	public int DRAW_AREA_SIZE;
	public int MARGIN;
	public int GRID_SIZE;
	public int EYE_SIZE;
	
	protected Zoom zoom;
	
	public MazeDrawSetting(int width, int height, int xsize, int ysize) {
		DRAW_AREA_SIZE = Math.min(width, height);
		zoom = new Zoom(Math.max(xsize, ysize));
		calculateDrawSettings();
	}
	
	protected void calculateDrawSettings() {
		DIMENSION = zoom.size();
		CELL_SIZE = Math.floorDiv(DRAW_AREA_SIZE, DIMENSION + 1);
		MARGIN = CELL_SIZE / 2;
		GRID_SIZE = CELL_SIZE * DIMENSION;
		EYE_SIZE = Math.max(1, CELL_SIZE / 4);
	}
	
	public Zoom getZoom() {
		return zoom;
	}

	public void setZoom(Zoom zoom) {
		this.zoom = zoom;
		calculateDrawSettings();
	}
	
	public boolean isDrawable(Coordinate coord) {
		return zoom.isInside(coord);
	}

	public Coordinate getCanvasCoordinate(Coordinate coord) {
		return new Coordinate(calculateCanvasValue(coord.x - zoom.min.x), calculateCanvasValue(coord.y - zoom.min.y));
	}
	
	protected int calculateCanvasValue(int value) {
		return value * CELL_SIZE + MARGIN;
	}
	
	public Coordinate getCoordinate(Coordinate coord) {
		return new Coordinate(calculateCoordValue(coord.x) + zoom.min.x, calculateCoordValue(coord.y) + zoom.min.y);
	}
	
	protected int calculateCoordValue(int value) {
		return (value - MARGIN) / CELL_SIZE;
	}

	public void zoomIn(Coordinate coord) {
		zoom.zoomIn(getCoordinate(coord));
		calculateDrawSettings();
	}

	public void zoomOut(Coordinate coord) {
		zoom.zoomOut(getCoordinate(coord));
		calculateDrawSettings();
	}

}
