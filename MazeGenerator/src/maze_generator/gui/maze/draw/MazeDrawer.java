package maze_generator.gui.maze.draw;

import java.awt.Color;
import java.awt.Graphics;

import maze_generator.maze.MazeStructure;
import maze_generator.types.Coordinate;
import maze_generator.types.Marker;

public class MazeDrawer {
	
	public final static Color[] colors = new Color[] {
	      Color.black,  Color.red,      Color.green,     Color.cyan,
	      Color.yellow, Color.pink,     Color.orange,    Color.magenta,
	      Color.gray,   Color.darkGray, Color.lightGray, Color.blue
	  };
	
	protected MazeStructureDrawer structureDrawer;
	protected MarkerDrawer markerDrawer;
	protected MazeDrawSetting setting;
	protected int width, height;
	private MazeStructure structure;
	private Marker marker;
	
	public MazeDrawer(MazeStructure structure, int width, int height) {
		this.structure = structure;
		setDimension(width, height);
	}
		
	public MazeStructure getStructure() {
		return this.structure;
	}
	
	public Marker getMarker() {
		return marker;
	}

	public void setMazeStructure(MazeStructure structure) {
		this.structure = structure;
		setDimension(this.width, this.height);
	}
	
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
		this.setting = new MazeDrawSetting(width, height, structure.xsize, structure.ysize);
		this.structureDrawer = new MazeStructureDrawer(structure, setting);
		this.marker = new Marker(structure.xsize, structure.ysize);
		this.markerDrawer = new MarkerDrawer(marker, setting);
	}

	public void paint(Graphics g) {
		if(structure != null ){
			structureDrawer.drawGrid(g, Color.lightGray);
			structureDrawer.drawStructure(g, Color.blue);
			markerDrawer.draw(g, Color.BLACK);
		}
	}
	
	public void setWall(int x, int y) {
		structureDrawer.setWall(new Coordinate(x, y));
	}

	public void setExit(int x, int y) {
		structureDrawer.setExit(new Coordinate(x, y));
	}

	public void zoomIn(int x, int y) {
		setting.zoomIn(new Coordinate(x, y));
	}

	public void zoomOut(int x, int y) {
		setting.zoomOut(new Coordinate(x, y));
	}

}
