package maze_generator.gui.tools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import maze_generator.gui.maze.MazePanel;
import maze_generator.gui.maze.ToolPanel;
import maze_generator.maze.MazeStructure;
import maze_generator.maze.generate.ExitLocator;
import maze_generator.maze.generate.exit.ThreeWalls;
import maze_generator.maze.generate.wall.GeMGA;

public class MazeEditionPanel extends ToolPanel {

	private static final long serialVersionUID = 9115536734967360331L;
	
	private JLabel jLabelSlider;
	private JSlider slider;
	private JSpinner probSpinner;
	
	private JButton fillButton = new JButton("Fill");
	private JButton clearButton = new JButton("Clear");
	private JButton generateButton = new JButton("Generate");
	
	protected GeMGA generator;
	protected ExitLocator exitGenenerator;
	protected MazeStructure saving;
	
	protected int initialMazeSize = 15;
	protected int maxMazeSize = 30;
	protected int minMazeSize = 10;
	protected double selectionProbability = 0.25;
	protected double probabilityStep = 0.05;
	
	public MazeEditionPanel(MazePanel mazePanel) {
		super("Maze Edition Tools", mazePanel);
		generator = new GeMGA(selectionProbability);
		exitGenenerator = new ThreeWalls(1);
		saving = new MazeStructure(maxMazeSize, maxMazeSize, 0);
		init();
	}
	
	private void init() {
		JPanel sliderPanel = new JPanel();
		addMazeSlider(sliderPanel);
		JPanel generationPanel = new JPanel();
		addProbSpinner(generationPanel);
		addGenerateButton(generationPanel);
		addFillButton(generationPanel);
		addClearButton(generationPanel);
		this.add(sliderPanel);
		this.add(generationPanel);
	}
	
	private void addMazeSlider(JPanel panel) {
		jLabelSlider = new JLabel("Maze Size " + initialMazeSize);
		slider = new JSlider(JSlider.HORIZONTAL, minMazeSize, maxMazeSize, initialMazeSize);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				if(evt.getSource() == slider) {
					jLabelSlider.setText("Maze Size " + slider.getValue());
					maze.copy(saving);
					saving.resize(slider.getValue(), slider.getValue());
					mazePanel.setMazeStructure(saving);
				}
			}
		});
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel.add(jLabelSlider);
		panel.add(slider);
	}

	public void setSliderValue(int size) {
		slider.setValue(size);
	}
	
	private void addProbSpinner(JPanel panel) {
		JLabel label = new JLabel("Probability");
		SpinnerModel model = new SpinnerNumberModel(selectionProbability, 0.0, 1.0, probabilityStep);
		probSpinner = new JSpinner(model);
		probSpinner.setPreferredSize(new Dimension(50, 20));
		probSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				generator = new GeMGA((double) probSpinner.getValue());
			}
		});
		panel.add(label);
		panel.add(probSpinner);
	}

	private void addFillButton(JPanel panel) {
		fillButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				maze.fill(MazeStructure.FILL);
				mazePanel.update();
			}
		});
		panel.add(fillButton);
	}
	
	private void addClearButton(JPanel panel) {
		clearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				maze.fill(MazeStructure.CLEAR);
				mazePanel.update();
			}
		});
		panel.add(clearButton);
	}
	
	private void addGenerateButton(JPanel panel) {
		generateButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				maze = mazePanel.getMazeStructure();
				MazeStructure structure = generator.apply(maze);
				structure = exitGenenerator.apply(structure);
				structure.connectivity = ((double) probSpinner.getValue()) * 100;
				mazePanel.setMazeStructure(structure);
				maze = structure;
			}
		});
		panel.add(generateButton);
	}

}
