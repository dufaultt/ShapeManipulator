package proj;
	
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameShowingBigCircles extends JFrame implements Observer {	
	private int numShapes;
	private static int totalShapes = 0;
	
	private AbstractList myArray = new ArrayOfShapeWithIterator();
	private AbstractList.AbstractIterator myIterator = myArray.createIterator();

	private Shape myShapes[];
	
	public FrameShowingBigCircles() {
		super("Big Circles");
		setSize(TesterFrame.WIDTH, TesterFrame.HEIGHT); 
		setVisible(true);    
	}
	
	public void paint(Graphics g) {
		super.paint(g);

		myIterator.first();

		while(!myIterator.isDone()) {
			if(myIterator.currentItem() instanceof Circle) {
				if(myIterator.currentItem().calculateArea() >= 5000.00) {
					myIterator.currentItem().showMe(g);
				}
			}
			myIterator.next();
	    }
		
		if(!(totalShapes == numShapes)) {
			totalShapes = numShapes;
			//System.out.println("N = " + totalShapes);
		}
	}

	public void update(Observable o, Object arg) {		
		myShapes = (Shape[]) arg;//easier to create a new object casted once than casting 3 or 4 times 
		
		myArray.setIndex(0);//needed a way to reset the array index pointer without creating a new object
		numShapes = 0;
		
		//since there is no gap between objects, very easy to find the last entry, array[i] = null
		for(int i = 0; i < myShapes.length; i ++) {
			if(myShapes[i] != null) {
				myArray.append(myShapes[i]);
				numShapes ++;
			}
			else {
				break;
			}
		}
		repaint();
	}
}