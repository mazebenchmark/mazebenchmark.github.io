package maze_generator.gui;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import maze_generator.gui.maze.MazePanel;
import maze_generator.gui.tools.MazeEditionPanel;
import maze_generator.gui.tools.StartingLocationsGenerationPanel;
import maze_generator.io.MazeStructureReader;
import maze_generator.io.MazeStructureWriter;
import maze_generator.maze.MazeStructure;
import unalcol.io.ShortTermMemoryReader;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8026416994513756565L;
	
	private MazePanel mazePanel;
	private JPanel jToolsPanel = new JPanel();
	private MazeEditionPanel mazeEditionPanel;
	@SuppressWarnings("unused")
	private StartingLocationsGenerationPanel startingLocationsPanel;
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem openMenuItem = new JMenuItem("Open");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	
	protected MazeStructure maze;	
	protected MazeStructureWriter mazeWriter = new MazeStructureWriter();
	protected MazeStructureReader mazeReader = new MazeStructureReader();
	
	public MainFrame() {
		super("Maze Generation Tool");
		setSize(1200, 760);
		init();
	}
	
	private void init() {
		initMenus();
		initMazePanel();
		initToolsPanel();		
		this.addWindowListener( new WindowAdapter(){
			public void windowClosing( WindowEvent e ){
				performExitAction();
			}
	    });
	}

	private void initMenus() {
		fileMenu.setMnemonic(KeyEvent.VK_F);
		setMenuItem(openMenuItem, KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		setMenuItem(saveMenuItem, KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		setMenuItem(exitMenuItem, KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		menubar.add(fileMenu);
		this.setJMenuBar(menubar);
	}
	
    private ActionListener performMenuAction() {
        return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Object item = event.getSource();
	            
	            if(item == exitMenuItem)
	            	performExitAction();
	            else if(item == openMenuItem)
	            	performOpenAction();
	            else if(item == saveMenuItem)
	            	performSaveAction();
			}
    	};
    }
    
    public void performOpenFrameAction(JFrame frame) {
    	frame.setVisible(true);
    }

	public void performExitAction() {
		System.exit(0);
	}

	private void performOpenAction() {
		JFileChooser chooser = new JFileChooser("../dataset");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.showOpenDialog(this);
		File file = chooser.getSelectedFile();
		if(file != null) {
			try {
				ShortTermMemoryReader reader = new ShortTermMemoryReader(new FileReader(file));
				MazeStructure structure = MazeStructureReader.read(reader);
				reader.close();
				mazeEditionPanel.setSliderValue(Math.max(structure.xsize, structure.ysize));
				mazePanel.setMazeStructure(structure);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "There was a problem to read the maze");
				e.printStackTrace(System.out);
			}
		}
	}

	private void performSaveAction() {
		JFileChooser chooser = new JFileChooser("../dataset");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.showSaveDialog(this);
		File file = chooser.getSelectedFile();
		if(file != null) {
			try {
				FileWriter writer = new FileWriter(file + ".mz");
				mazeWriter.write(maze, writer);
				writer.close();
				BufferedImage image=new BufferedImage(mazePanel.getCanvas().getWidth(), mazePanel.getCanvas().getHeight(),BufferedImage.TYPE_INT_RGB);
				Graphics2D g2=(Graphics2D)image.getGraphics();
				mazePanel.getCanvas().paint(g2);
				ImageIO.write(image, "png", new File(file.getPath() + ".png"));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "There was a problem to save the maze");
			}
		}
	}
	
	public void initMazePanel() {
		maze = new MazeStructure(15, 15, 0);
		mazePanel = new MazePanel(maze);
		this.getContentPane().add(mazePanel,  BorderLayout.CENTER);
	}
	
	private void setMenuItem(JMenuItem item, int key, KeyStroke stroke) {
		item.setMnemonic(key);
		item.setAccelerator(stroke);
		item.addActionListener(performMenuAction());
	}
	
	private void initToolsPanel() {
		jToolsPanel.setLayout(new BoxLayout(jToolsPanel, BoxLayout.Y_AXIS));
		mazeEditionPanel = new MazeEditionPanel(mazePanel);
		startingLocationsPanel = new StartingLocationsGenerationPanel(mazePanel);
		jToolsPanel.add(mazeEditionPanel);
		jToolsPanel.add(startingLocationsPanel);
		this.getContentPane().add(jToolsPanel,  BorderLayout.EAST);
	}
}
