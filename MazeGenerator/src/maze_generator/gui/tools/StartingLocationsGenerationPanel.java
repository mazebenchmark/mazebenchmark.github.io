package maze_generator.gui.tools;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import maze_generator.gui.maze.MazePanel;
import maze_generator.gui.maze.ToolPanel;
import maze_generator.io.MarkerWriter;
import maze_generator.io.MazeStructureReader;
import maze_generator.maze.MazeStructure;
import maze_generator.types.Coordinate;
import maze_generator.types.Marker;
import unalcol.io.ShortTermMemoryReader;
import unalcol.types.collection.vector.Vector;

public class StartingLocationsGenerationPanel extends ToolPanel {
	
	private static final long serialVersionUID = 3807826968167686789L;
	
	private JRadioButton goalsRadio = new JRadioButton("goals removed");
	private JRadioButton rotatedGoalsRadio = new JRadioButton("rotated goals removed");
	private JRadioButton threeCellsRadio = new JRadioButton("nearest, median and furthest");
	private JRadioButton startingLocationsRadio = new JRadioButton("starting locations");
	
	private JButton loadButton = new JButton("Load Mazes");
	private JButton saveButton = new JButton("Save");
	
	Vector<MazeStructure> structures = new Vector<MazeStructure> ();
	Vector<Coordinate> goals = new Vector<Coordinate>();
	
	public StartingLocationsGenerationPanel(MazePanel mazePanel) {
		super("Starting Locations", mazePanel);
		init();
	}

	private void init() {
		JPanel checkBoxPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		addCheckBoxes(checkBoxPanel);
		addButtons(buttonPanel);
		this.add(checkBoxPanel);
		this.add(buttonPanel);
	}

	private void addCheckBoxes(JPanel panel) {
		ButtonGroup startingLocationsGroup = new ButtonGroup();
		startingLocationsGroup.add(goalsRadio);
		startingLocationsGroup.add(rotatedGoalsRadio);
		startingLocationsGroup.add(threeCellsRadio);
		startingLocationsGroup.add(startingLocationsRadio);
		
		JPanel group = new JPanel();
		group.setLayout(new BoxLayout(group, BoxLayout.PAGE_AXIS));
		group.add(new JLabel("Show"));
		group.add(goalsRadio);
		group.add(rotatedGoalsRadio);
		group.add(threeCellsRadio);
		group.add(startingLocationsRadio);
		
		RadioListener listener = new RadioListener(structures, mazePanel);
		goalsRadio.addActionListener(listener);
		rotatedGoalsRadio.addActionListener(listener);
		threeCellsRadio.addActionListener(listener);
		startingLocationsRadio.addActionListener(listener);
		panel.add(group);
	}
	
	class RadioListener implements ActionListener {

		Vector<MazeStructure> structures;
		MazePanel mazePanel;

		public RadioListener(Vector<MazeStructure> structures, MazePanel mazePanel) {
			this.structures = structures;
			this.mazePanel = mazePanel;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getActionCommand().equals("goals removed"))
				removeGoals(findGoals());
			else if(evt.getActionCommand().equals("rotated goals removed"))
				removeGoals(findRotatedGoals());
			else if(evt.getActionCommand().equals("nearest, median and furthest")) {
				removeGoals(findRotatedGoals());
				displayThreeGoals(findGoals());
			} else if(evt.getActionCommand().equals("starting locations")) {
				removeGoals(findRotatedGoals());
				displayThreeGoals(findGoals());
				displayThreeRotatedGoals();
			}
		}

		private void displayThreeRotatedGoals() {
			Marker marker = mazePanel.getMarker();
			for(Coordinate coord: marker.getMarkedCells())
				for(Coordinate cell: structures.get(0).rotate(coord.x, coord.y))
					marker.mark(cell.x, cell.y);
			mazePanel.repaint();
		}

		private void removeGoals(Vector<Coordinate> goals) {
			Marker marker = mazePanel.getMarker();
			marker.fill();
			for(Coordinate coord: goals)
				if(marker.isValid(coord.x, coord.y))
					marker.unmark(coord.x, coord.y);
			mazePanel.repaint();
		}

		private Vector<Coordinate> findGoals() {
			Vector<Coordinate> goals = new Vector<Coordinate>();
			for(MazeStructure structure: structures)
				for(int x = 0; x < structure.xsize; x++)
					for(int y = 0; y < structure.xsize; y++)
						if(structure.isGoal(x, y))
							goals.add(new Coordinate(x, y));
			return goals;
		}
		
		private Vector<Coordinate> findRotatedGoals() {
			Vector<Coordinate> goals = new Vector<Coordinate>();
			for(MazeStructure structure: structures)
				for(int x = 0; x < structure.xsize; x++)
					for(int y = 0; y < structure.xsize; y++)
						if(structure.isGoal(x, y))
							for(Coordinate coordinate: structure.rotate(x, y))
								goals.add(coordinate);
			return goals;
		}
		
		private void displayThreeGoals(Vector<Coordinate> goals) {
			Vector<Coordinate> marked = mazePanel.getMarker().getMarkedCells();
			Vector<Double> distances = measure(marked, goals);
			sort(marked, distances);
			int lenght = marked.size();
			mazePanel.getMarker().clear();
			mazePanel.getMarker().mark(marked.get(0).x, marked.get(0).y);
			mazePanel.getMarker().mark(marked.get(lenght - 1).x, marked.get(lenght - 1).y);
			mazePanel.getMarker().mark(marked.get(lenght / 2).x, marked.get(lenght / 2).y);
			mazePanel.repaint();
		}

		private void sort(Vector<Coordinate> marked, Vector<Double> distances) {
			sort(marked, distances, 0, marked.size() - 1);
		}

		private void sort(Vector<Coordinate> marked, Vector<Double> distances, int a, int b) {
			if(a < b) {
				int piv = a + (int) (Math.random() * (b - a + 1));
				swap(marked, distances, piv, b);
				int j = b - 1, i = a;
				while(j >= i) {
					if(greater(marked, distances, b, i))
						i++;
					else {
						if(greater(marked, distances, b, j))
							swap(marked, distances, i++, j);
						j--;
					}
				}
				swap(marked, distances, i, b);
				sort(marked, distances, a, i - 1);
				sort(marked, distances, i + 1, b);
			}
		}

		private boolean greater(Vector<Coordinate> marked, Vector<Double> distances, int a, int b) {
			if(distances.get(a) > distances.get(b))
				return true;
			if(distances.get(a) < distances.get(b))
				return false;
			return marked.get(a).compare(marked.get(b)) > 0;
		}

		private void swap(Vector<Coordinate> marked, Vector<Double> distances, int piv, int b) {
			Coordinate coord = new Coordinate(marked.get(piv).x, marked.get(piv).y);
			double distance = distances.get(piv);
			marked.set(piv, marked.get(b));
			distances.set(piv, distances.get(b));
			marked.set(b, coord);
			distances.set(b, distance);
		}

		private Vector<Double> measure(Vector<Coordinate> marked, Vector<Coordinate> goals) {
			Vector<Double> distances = new Vector<Double>();
			for(Coordinate coord: marked) {
				double sum = 0;
				for(Coordinate goal: goals)
					sum += Math.abs(coord.x - goal.x) + Math.abs(coord.y - goal.y);
				distances.add(sum / goals.size());
			}
			return distances;
		}
	
	}

	private void addButtons(JPanel panel) {
		addLoadButton(panel);
		addSaveButton(panel);
	}

	private void addLoadButton(JPanel panel) {
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				structures.clear();
				int maxX = 10, maxY = 10;
				for(File file: getFilesInFolder(requestFolder())) {
					try {
						ShortTermMemoryReader reader = new ShortTermMemoryReader(new FileReader(file));
						MazeStructure structure = MazeStructureReader.read(reader);
						maxX = Math.max(maxX, structure.xsize);
						maxY = Math.max(maxY, structure.ysize);
						structures.add(structure);
						goals = new Vector<Coordinate>();
						addGoals(structure);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mazePanel.setMazeStructure(new MazeStructure(maxX, maxY, 0));
				goalsRadio.setSelected(false);
				rotatedGoalsRadio.setSelected(false);
				threeCellsRadio.setSelected(false);
				startingLocationsRadio.setSelected(false);
			}

			private void addGoals(MazeStructure structure) {
				for(int x = 0; x < structure.xsize; x++)
					for(int y = 0; y < structure.ysize; y++)
						if(structure.isGoal(x, y))
							goals.add(new Coordinate(x, y));
			}

			private File[] getFilesInFolder(String folderpath) {
				File folder = new File(folderpath);
				return folder.listFiles();
			}

			private String requestFolder() {
				JFileChooser chooser = new JFileChooser("../dataset");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				return (file != null) ? file.getPath() : "";
			}
		});
		panel.add(loadButton);
	}
	
	private void addSaveButton(JPanel panel) {
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser("../dataset");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showSaveDialog(null);
				File file = chooser.getSelectedFile();
				if(file != null) {
					try {
						FileWriter writer = new FileWriter(file + ".loc");
						MarkerWriter writerManager = new MarkerWriter();
						Marker marker = mazePanel.getMarker();
						writerManager.write(marker, writer);
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
		});
		panel.add(saveButton);
	}
}
