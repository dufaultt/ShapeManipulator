package proj;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {
	// Constructor creates a black rectangle with size 50 X 50 with upper left point at (200, 200)
	public Rectangle(Color currentColor, int index) {
		referencePoint = new Coordinates(200, 200);  
		shapeColor = currentColor;
		arg1 = 50;
		arg2 = 50;
		setComponentList();
		addComponent(index);
	}
	
	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}
	
	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}
	
	public double calculateArea() { 
		return (arg1 * arg2);
	}
	
	//Method onThePerimeter checks if the user pressed the mouse button on the perimeter
	//If so, it returns true; otherwise it return false.
	public boolean onThePerimeter(Coordinates mousePosition) {
		int xWhereMousePressed = mousePosition.getX();
		int yWhereMousePressed = mousePosition.getY();

		//If the position where the user pressed the mouse button is within 5 pixels of any side of the rectangle, 
		//the method will return true; Otherwise, it will return false.
		if(((xWhereMousePressed >= referencePoint.getX() - 5) && (xWhereMousePressed <= (referencePoint.getX() + arg1 + 5)) &&
			(yWhereMousePressed >= referencePoint.getY() - 5) && (yWhereMousePressed <= (referencePoint.getY() + 5)))) 
				return true;// top edge is edge # 0

		else if(((xWhereMousePressed >= referencePoint.getX() - 5) && (xWhereMousePressed <= (referencePoint.getX() + 5)) && 
			     (yWhereMousePressed >= referencePoint.getY() - 5) && (yWhereMousePressed <= (referencePoint.getY() + arg2 + 5))))
			 	return true;// left edge is edge # 1

		else if(((xWhereMousePressed >= referencePoint.getX() - 5) && (xWhereMousePressed <= (referencePoint.getX() + arg1 + 5)) &&
			     (yWhereMousePressed >= referencePoint.getY() + arg1 - 5) && (yWhereMousePressed <= (referencePoint.getY()+ arg2 + 5)))) 
				return true;//bottom edge-edge # 2

		else if(((xWhereMousePressed >= referencePoint.getX() + arg2 - 5) && (xWhereMousePressed <= (referencePoint.getX() + arg1 + 5)) &&
			     (yWhereMousePressed >= referencePoint.getY() - 5) && (yWhereMousePressed <= (referencePoint.getY() + arg2 + 5)))) 
				return true;// right edge-edge # 3
		else 
				return false;
	}

	//checks if the position of the mouse where the user pressed the left mouse button is within the shape 
	//(at least 5 pixels away from the perimeter. If so, the flag shapeSelected is set and the method returns true.
	//Otherwise, the flag shapeSelected is reset and the method returns false.
	public boolean shapeIsSelected(Coordinates positionOfMouse) { 
		// Check if the user pressed the mouse button inside the shape
		int x = positionOfMouse.getX();
		int y = positionOfMouse.getY();
		
		if((x >= referencePoint.getX() + 5) && (x <= referencePoint.getX() + arg1 - 5) && 
		   (y >= referencePoint.getY() + 5) && (y <= referencePoint.getY() + arg2 - 5)) {
			shapeSelected = true;
			lastMousePosition = positionOfMouse;
			return true;
		}
		else {
			shapeSelected = false;
			return false;
		}
	} 
	
	public void showMe(Graphics g) {
		g.setColor(shapeColor);
		g.drawRect(referencePoint.getX(), referencePoint.getY(), arg1, arg2);		
	}
	
	public String toString() {
		return ("Rectangle with reference point " + referencePoint + " having width " + arg1 + " and height " + arg2 + "\n");
	}
}