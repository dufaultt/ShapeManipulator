package proj;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {
	private final double PI = 3.14;

	// Constructor creates a black circle with arg1 50  with the reference point at (200, 200)
	public Circle(Color currentColor, int index) {
		referencePoint = new Coordinates(200, 200);  // new figure is at the center of frame
		shapeColor = currentColor;  
		arg1 = 50;
		arg2 = arg1;
		setComponentList();
		addComponent(index);
	}
	
	public void setArg1(int arg1) {
		this.arg1 = arg1;
		this.arg2 = arg1;
	}
	
	public void setArg2(int arg2) {
		this.arg1 = arg2;
		this.arg2 = arg2;		
	}
	
	public double calculateArea() { 
		return (PI * arg1 * arg1/4);
	}
	
	//Method onThePerimeter checks if the user pressed the mouse button on the perimeter
	//If so, it returns true; otherwise it return false.
	public boolean onThePerimeter(Coordinates mousePosition) {
		// the perimeter is selected if the distance from the point (x, y) to the center  is between radius - 5 and radius + 5
		Coordinates centreOfCircle = new Coordinates(referencePoint.getX() + arg1/2,referencePoint.getY() + arg1/2);
		int distanceFromCenter = (int) distanceBetweenPoints(mousePosition, centreOfCircle);

		if((distanceFromCenter >= arg1/2 - 5) && (distanceFromCenter <= arg1/2 + 5)) {
			return true;
		} 
		else {
			return false;
		}
	}

	//checks if the position of the mouse where the user pressed the left mouse button is within the shape 
	//(at least 5 pixels away from the perimeter. If so, the flag shapeSelected is set and the method returns true.
	//Otherwise, the flag shapeSelected is reset and the method returns false.
	public boolean shapeIsSelected(Coordinates positionOfMouse) { 
		// Check if the user pressed the mouse button inside the shape
		Coordinates centreOfCircle = computeCenterOfCircle(referencePoint, arg1);
		double distance = distanceBetweenPoints(positionOfMouse, centreOfCircle);

		if(distance < arg1/2 - 5) {
			shapeSelected = true;
			lastMousePosition = positionOfMouse;
			return true; 
		} 
		else {
			shapeSelected = false;
			return false;
		}
	}

	//changes the arg1 of the circle, if currentPhase is 3 .
	//0, means the user is moving the shape
	//1, a new shape is being created
	//2, the the color of the new shape is being modified,
	//3, the arg1 of the circle is being modified,
	public void changeShape(int currentPhase, int size) {
		if(currentPhase == 3){
			int newReferencePointX = referencePoint.getX() + (arg1 - size)/2;
			int newReferencePointY = referencePoint.getY() + (arg1 - size)/2;
			referencePoint = new Coordinates(newReferencePointX, newReferencePointY);
			arg1 = size; 
		} 
	}

	private double distanceBetweenPoints(Coordinates point1, Coordinates point2) {
		return (Math.sqrt((double) ((point1.getX() - point2.getX())*(point1.getX() - point2.getX()) + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY()))));
	} 
	
	public Coordinates computeCenterOfCircle(Coordinates referencePoint, int arg1) {
		int xValueOfCenter = referencePoint.getX() + arg1/2;
		int yValueOfCenter = referencePoint.getY() + arg1/2;
		
		return new Coordinates(xValueOfCenter, yValueOfCenter);
	}
	
	public void showMe(Graphics g) {
		g.setColor(shapeColor);
		g.drawOval(referencePoint.getX(), referencePoint.getY(), arg1, arg2);
	}
	
	public String toString(){
		return ("Circle with reference point " + referencePoint + " having arg1 " + arg1 + "\n");
	}
}