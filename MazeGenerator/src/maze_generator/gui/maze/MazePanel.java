package maze_generator.gui.maze;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import maze_generator.gui.maze.draw.MazeCanvas;
import maze_generator.maze.MazeStructure;
import maze_generator.types.Marker;

public class MazePanel extends JPanel {
	
	private static final long serialVersionUID = -2989518883231470830L;
	
	protected MazeCanvas canvas;
	protected MazeStructure maze;
	protected BorderLayout borderLayoutMaze = new BorderLayout();

	public MazePanel(MazeStructure maze) {
		canvas = new MazeCanvas(maze, 700, 700);
		this.maze = maze;
		this.setLayout(borderLayoutMaze);
		this.add(canvas, BorderLayout.CENTER);
		this.setSize(600, 600);
	}
	
	public void setMazeStructure(MazeStructure structure) {
		canvas.setMazeStructure(structure);
	}

	public void update() {
		canvas.repaint();
	}
	
	public MazeCanvas getCanvas() {
		return canvas;
	}

	public MazeStructure getMazeStructure() {
		return canvas.getMazeStructure();
	}

	public Marker getMarker() {
		return canvas.getMarker();
	}

}
