package maze_generator.gui.maze.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import maze_generator.maze.MazeStructure;
import maze_generator.types.Marker;

public class MazeCanvas extends JPanel {

	private static final long serialVersionUID = -1890400815638667925L;
	
	protected MazeDrawer drawer;
	
	public MazeCanvas(MazeStructure structure, int width, int height) {
		this.drawer = generateDrawer(structure, width, height);
		init(width, height);
	}

	protected void init(int width, int height) {
		this.setSize(width, height);
		this.setBackground(new Color(255,255,255));
		
		this.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(e.getClickCount() == 1)
						drawer.setWall(e.getX(), e.getY());
					else
						drawer.setExit(e.getX(), e.getY());
				}
				repaint();
		    }
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0)
					drawer.zoomIn(e.getX(), e.getY());
				else if(e.getWheelRotation() > 0)
					drawer.zoomOut(e.getX(), e.getY());
				repaint();
			}
		});
	}
	
	public static MazeDrawer generateDrawer(MazeStructure structure, int width, int height) {
		return new MazeDrawer(structure, width, height);
	}
	
	public MazeDrawer getDrawer() {
		return drawer;
	}

	public void setMazeStructure(MazeStructure structure) {
		drawer.setMazeStructure(structure);
		repaint();
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		drawer.setDimension(width, height);
	}
	
	protected void paintComponent(Graphics g){
	    super.paintComponent(g);
	    if(drawer != null)
	        drawer.paint(g);
	}

	public MazeStructure getMazeStructure() {
		return drawer.getStructure();
	}

	public Marker getMarker() {
		return drawer.getMarker();
	}

}
