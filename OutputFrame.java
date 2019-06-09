package proj;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class OutputFrame extends JFrame implements Observer {
	final int WIDTH = 400, HEIGHT = 300;
	
	private JTextArea outputArea;
	
	private AbstractList myArray = new ArrayOfShapeWithIterator();
	private AbstractList.AbstractIterator myIterator = myArray.createIterator();

	private Shape myShapes[];
	
	public OutputFrame() {
		super("Rectangles");
		outputArea = new JTextArea(20, 30);
		add(outputArea);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}
	
	public void displayResult() {
		outputArea.setText("");
		
		myIterator.first();
		
		while(!myIterator.isDone()) {
			//just need to check Rectangle because Square extends Rectangle
			if(myIterator.currentItem() instanceof Rectangle) {
				outputArea.append(myIterator.currentItem().toString()); 
			}
			myIterator.next();
	    }
	}
	
	public void update(Observable o, Object arg) {
		myShapes = (Shape[]) arg;//easier to create a new object casted once than casting 3 or 4 times 
		
		myArray.setIndex(0);//needed a way to reset the array index pointer without creating a new object
		
		//since there is no gap between objects, very easy to find the last entry, array[i] = null
		for(int i = 0; i < myShapes.length; i ++) {
			if(myShapes[i] != null) {
				myArray.append(myShapes[i]);
			}
			else {
				break;
			}
		}
		displayResult();
	}
}