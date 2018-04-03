package maze_generator.gui.maze;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import maze_generator.maze.MazeStructure;

public abstract class ToolPanel extends JPanel {

	private static final long serialVersionUID = 3672851493615056749L;
	
	protected MazePanel mazePanel;
	protected MazeStructure maze;
	
	public ToolPanel(String title, MazePanel mazePanel) {
		this.mazePanel = mazePanel;
		this.maze = mazePanel.canvas.getDrawer().getStructure();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(title));
	}
	
}
