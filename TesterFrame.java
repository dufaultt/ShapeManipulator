package proj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TesterFrame extends JFrame {
	public static final int WIDTH = 1000, HEIGHT = 500; // the width and height of the frame
	
	//myShapes is an array of all the shapes (circles, rectangles and squares) that we have In this version we will 
	//handle only a maximum of 20 shapes.
	private Shape myShapes[] = new Shape[20];
	private Shape currentShape;
	private Color currentColor = Color.black;

	private int currentPhase = 0;
	private int numShapes = 0;
	
	int selected[] = new int[20];//will be used for convenience
	int numSelected = 0;
	boolean duplicateFlag = false;

	private DelegatedObservable obs = new DelegatedObservable();

	public void redisplay() {
		repaint();
		obs.setChanged();
		obs.notifyObservers(myShapes);
	}
	
	private class EditorPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int index = 0; index < numShapes; index++) {
				myShapes[index].showMe(g);
			}
		}
	}

	private EditorPanel buildEditorPanel() {
		EditorPanel myPanel = new EditorPanel();
		
		myPanel.setBackground(Color.WHITE);
		
		myPanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				for(int index = 0; index < numShapes; index ++) {
					myShapes[index].resetShapeSelected();
				}
			}
			
			public void mousePressed(MouseEvent e) {
				Coordinates currentMousePosition = new Coordinates(e.getX(), e.getY());
				
				if(currentPhase == 0) {
					for(int index = 0; index < numShapes; index ++) {
						myShapes[index].shapeIsSelected(currentMousePosition);
					}
				}
				else {
					for(int index = 0; index < numShapes; index ++) {
						if(myShapes[index].onThePerimeter(currentMousePosition) == true) {
							myShapes[index].changeColorTemporarily();
							
							duplicateFlag = false;
								
							for(int index2 = 0; index2 < numShapes; index2 ++) {
								if(index == selected[index2]) {
									duplicateFlag = true;
								}
							}
							
							if(duplicateFlag == false) {
								selected[numSelected] = index;
								numSelected ++;
							}
						}
					}
				}		
			redisplay();
			}   	                      
		});

		myPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {	
				for(int index = 0; index < numShapes; index ++) { 
					if(myShapes[index].getShapeIsSelected()) {
						int componentList[] = myShapes[index].getComponentList();
						int numComponents = myShapes[index].getNumComponents();
						
						Coordinates shapeCoordinate = new Coordinates(myShapes[index].getLastMousePosition());
						myShapes[componentList[0]].moveShape(new Coordinates(e.getX(), e.getY()));

						Coordinates distanceDifference = new Coordinates(myShapes[index].getLastMousePosition().getX() - shapeCoordinate.getX(),
																		 myShapes[index].getLastMousePosition().getY() - shapeCoordinate.getY());

						for(int index2 = 1; index2 < numComponents; index2 ++) {
							myShapes[componentList[index2]].setShapeIsSelected(true);
							myShapes[componentList[index2]].moveShape(new Coordinates(myShapes[componentList[index2]].getLastMousePosition().getX() + distanceDifference.getX(), 
																					  myShapes[componentList[index2]].getLastMousePosition().getY() + distanceDifference.getY()));
							myShapes[componentList[index2]].resetShapeSelected();
						}
					}
				}	
			redisplay(); 
			}
		});
		
		return myPanel;
	}

	private JPanel buildShapeChooserPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 9));
		
		JButton compoundFigureButton = new JButton("Compound");
		JButton squareButton = new JButton("Square");
		JButton rectangleButton = new JButton("Rectangle");
		JButton circleButton = new JButton("Circle");
		
		final JLabel messageLabel = new JLabel(" ");
		final JTextField inputArea = new JTextField(5);
		
		final JRadioButton yesButton = new JRadioButton("Yes");
		yesButton.setEnabled(false);
		final JRadioButton noButton = new JRadioButton("No");
		noButton.setEnabled(false);

		ButtonGroup radioButtonGroupForSelectingShape = new ButtonGroup();
		radioButtonGroupForSelectingShape.add(yesButton);
		radioButtonGroupForSelectingShape.add(noButton);
		
		compoundFigureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentPhase == 0) { 
					currentPhase = 1;
					yesButton.setSelected(true);
					messageLabel.setText("Compunding Shapes");
					
					numSelected = 0;//reset index for selected Shapes, will overwrite existing entries (doesn't matter)

					for(int index = 0; index < selected.length; index ++) {
						selected[index] = -1;
					}
				} 
				else { 
					currentPhase = 0;
					noButton.setSelected(true);
					messageLabel.setText("Done Compunding");
					
					for(int index1 = 0; index1 < numSelected; index1 ++) {
						int temp[] = myShapes[selected[index1]].getComponentList();
						
						for(int index2 = 0; index2 < numSelected; index2 ++) {
							duplicateFlag = false;
							
							for(int index3 = 0; index3 < temp.length; index3 ++) {
								if((selected[index2] == temp[index3])) {
									duplicateFlag = true;
								}
							}
							if(duplicateFlag == false) {
								myShapes[selected[index1]].addComponent(selected[index2]);
							}
						}
					}

					//setting everything back to default values
					for(int index = 0; index < numShapes; index ++) {
						myShapes[index].changeColorBack();
						myShapes[index].resetShapeSelected();
					}
				}		
				redisplay();
			}
		});

		squareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numShapes < myShapes.length) {	
					currentShape = new Square(currentColor, numShapes);
					messageLabel.setText("Square Width?");
					newShape();
			    }
			}
		});
		
		rectangleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(numShapes < myShapes.length) {	
					currentShape = new Rectangle(currentColor, numShapes);
					messageLabel.setText("Rectangle Width?");
					newShape();
				}
			}
		});

		circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numShapes < myShapes.length) {	
					currentShape = new Circle(currentColor, numShapes);
					messageLabel.setText("Circle Diameter?");
					newShape();
				}
			}
		});

		inputArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(messageLabel.getText().equals("Rectangle Height?")) {
					try {
						currentShape.setArg2(Integer.parseInt(inputArea.getText()));
					}
					catch(Exception e1) {
					}
				}
				else {
					try {
						currentShape.setArg1(Integer.parseInt(inputArea.getText()));
					}
					catch(Exception e1) {
					}
							
					if(messageLabel.getText().equals("Rectangle Width?")) {
						messageLabel.setText("Rectangle Height?");
					}
				}
				inputArea.setText("");
				redisplay();
			}
		});
		
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		buttonPanel.add(compoundFigureButton);
		buttonPanel.add(squareButton);
		buttonPanel.add(rectangleButton);
		buttonPanel.add(circleButton);
		buttonPanel.add(messageLabel);
		buttonPanel.add(inputArea);
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		return buttonPanel;
	} 

	//includes 3 radio buttons so that users can select red, blue or green in addition to the original 
	//black color for the newly created shape. The user can select one of these buttons to change the color 
	//for the newly created shape. We have used a  straight-forward anonymous handler for events in each radio button.
	private JPanel buildColorChooserPanel() {
		JPanel radioButtonPanel = new JPanel();
		
		JRadioButton blackButton = new JRadioButton("Black");
		JRadioButton redButton = new JRadioButton("Red");
		JRadioButton greenButton = new JRadioButton("Green");
		JRadioButton blueButton = new JRadioButton("Blue");

		ButtonGroup radioButtonGroupForChoosingColor = new ButtonGroup();
		radioButtonGroupForChoosingColor.add(blackButton);
		radioButtonGroupForChoosingColor.add(blueButton);
		radioButtonGroupForChoosingColor.add(greenButton);
		radioButtonGroupForChoosingColor.add(redButton);
		
		blackButton.setSelected(true);

		blackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = Color.black;
			}
		});
		
		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = Color.red;
			}
		});

		greenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = Color.green;
			}
		});

		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = Color.blue;
			}
		});
		
		radioButtonPanel.add(blackButton);
		radioButtonPanel.add(blueButton);
		radioButtonPanel.add(greenButton);
		radioButtonPanel.add(redButton);

		return radioButtonPanel;
	}
	
	public TesterFrame() {			
		OutputFrame showSquaresAndRectangles = new OutputFrame();
		FrameShowingBigCircles aFrameToDisplayBigCircles = new FrameShowingBigCircles();

		EditorPanel myPanel = buildEditorPanel();
		JPanel shapeButtonPanel = buildShapeChooserPanel();
		JPanel colorChooserPanel = buildColorChooserPanel();
		
		obs.addObserver(showSquaresAndRectangles);
		obs.addObserver(aFrameToDisplayBigCircles);
		
		add(colorChooserPanel, BorderLayout.NORTH);
		add(shapeButtonPanel, BorderLayout.SOUTH);
		add(myPanel, BorderLayout.CENTER);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}
	
	public void newShape() {
	    myShapes[numShapes] = currentShape;
		numShapes++;
		redisplay();
	}
	
	public void addObserver(Observer o) {
		obs.addObserver(o);
	}
	
	public void deleteObserver(Observer o) {
		obs.deleteObserver(o);
	}

	public static void main(String a[]) {
		@SuppressWarnings("unused")
		TesterFrame aFrame = new TesterFrame();
	}
}