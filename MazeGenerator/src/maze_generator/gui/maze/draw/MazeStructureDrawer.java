package maze_generator.gui.maze.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import maze_generator.maze.MazeStructure;
import maze_generator.maze.generate.MazeBuilder;
import maze_generator.types.Coordinate;

public class MazeStructureDrawer {
	
	protected MazeStructure structure;
	protected MazeDrawSetting setting;

	public MazeStructureDrawer(MazeStructure structure, MazeDrawSetting settings) {
		this.structure = structure;
		this.setting = settings;
	}

	public void drawGrid(Graphics g, Color color) {
		g.setColor(color);
		for(int i = 0; i <= setting.zoom.size(); ++i) {
			Coordinate pos = setting.getCanvasCoordinate(new Coordinate(i + setting.zoom.min.x, i + setting.zoom.min.y));
			g.drawLine(pos.x, setting.MARGIN, pos.x, setting.MARGIN + setting.GRID_SIZE);
			g.drawLine(setting.MARGIN, pos.y, setting.MARGIN + setting.GRID_SIZE, pos.y);
		}
	}
	
	public void drawStructure(Graphics g, Color color) {
		g.setColor(color);
		drawWalls(g);
		drawExits(g);
	}

	public void drawWalls(Graphics g) {
		for(int x = setting.zoom.min.x; x <= setting.zoom.max.x; ++x)
			for(int y = setting.zoom.min.y; y <= setting.zoom.max.y; ++y)
				for(int i = 0; i < 4; ++i) {
					Coordinate coord = new Coordinate(x, y);
					if(!structure.isClear(x, y, i))
						drawWall(g, setting.getCanvasCoordinate(coord), i);
				}
	}
	
	public void drawWall(Graphics g, Coordinate coord, int wall) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		switch(wall) {
			case MazeBuilder.FRONT: g.drawLine(coord.x, coord.y, coord.x + setting.CELL_SIZE, coord.y); break;
			case MazeBuilder.RIGHT: g.drawLine(coord.x + setting.CELL_SIZE, coord.y, coord.x + setting.CELL_SIZE, coord.y + setting.CELL_SIZE); break;
			case MazeBuilder.BACK:  g.drawLine(coord.x, coord.y + setting.CELL_SIZE, coord.x + setting.CELL_SIZE, coord.y + setting.CELL_SIZE); break;
			case MazeBuilder.LEFT:  g.drawLine(coord.x, coord.y, coord.x, coord.y + setting.CELL_SIZE);
		}
	}

	public void drawExits(Graphics g) {
		for(int x = 0; x < structure.xsize; x++)
			for(int y = 0; y < structure.ysize; y++)
				if(structure.isGoal(x, y)) {
					Coordinate coord = new Coordinate(x, y);
					if(setting.isDrawable(coord)) {
						coord = setting.getCanvasCoordinate(coord);
						g.drawLine(coord.x, coord.y, coord.x + setting.CELL_SIZE, coord.y + setting.CELL_SIZE);
						g.drawLine(coord.x, coord.y + setting.CELL_SIZE, coord.x + setting.CELL_SIZE, coord.y);
					}
				}
	}

	public void setWall(Coordinate coord) {
		int delta = setting.CELL_SIZE / 4;
		Coordinate c = setting.getCoordinate(coord);
		Coordinate m = setting.getCanvasCoordinate(c);
		if(coord.y < m.y + delta)
			modifyWallValue(c.x, c.y, 0);
		if(coord.x > m.x + setting.CELL_SIZE - delta)
			modifyWallValue(c.x, c.y, 1);
		if(coord.y > m.y + setting.CELL_SIZE - delta)
			modifyWallValue(c.x, c.y, 2);
		if(coord.x < m.x + delta)
			modifyWallValue(c.x, c.y, 3);
	}
	
	public void modifyWallValue(int x, int y, int dir) {
		if(structure.isValidLocation(x, y)) {
			if(structure.isClear(x, y, dir))
				MazeBuilder.addWall(structure, x, y, dir);
			else
				MazeBuilder.removeWall(structure, x, y, dir);
		}
	}

	public void setExit(Coordinate coord) {
		Coordinate c = setting.getCoordinate(coord);
		if(setting.isDrawable(c)) {
			if(structure.isGoal(c.x, c.y))
				structure.removeExit(c.x, c.y);
			else
				structure.addExit(c.x, c.y);
		}
	}
	
}
