package proj;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	protected int arg1;
	protected int arg2;
	
	protected int composite[] = new int[20];//thinking each shape will keep a list of indexes that refer to a shape in myShapes
	protected int numComponents = 0;

	protected Coordinates referencePoint;//reference point for a square
	protected Coordinates lastMousePosition;

	protected Color shapeColor;// color of a rectangle 
	protected Color originalColor;// Used to save the original color when the color changes temporarily

	protected boolean shapeSelected = false;
	protected boolean perimeterSelected = false;
	
	public abstract void setArg1(int arg1);
	public abstract void setArg2(int arg2);
	public abstract double calculateArea();
	public abstract boolean onThePerimeter(Coordinates mousePosition);
	public abstract boolean shapeIsSelected(Coordinates positionOfMouse);
	public abstract void showMe(Graphics g);
	public abstract String toString();

	public void addComponent(int index) {
		if(numComponents < composite.length) {
			composite[numComponents] = index;
			numComponents ++;
		}
	}
	
	public int getNumComponents() {
		return numComponents;
	}
	public int[] getComponentList() {
		return composite;
	}
	
	public void setComponentList() {
		for(int index = 0; index < composite.length; index ++) {
			composite[index] = -1;
		}
	}
	//simply resets the value of shapeSelected to false.
	public void resetShapeSelected() {
		shapeSelected = false;
	}
	
	//returns true if the user previously selected the shape by pressing the mouse button with the cursor inside the shape.
	public boolean getShapeIsSelected() {
		return shapeSelected;
	}
	public void setShapeIsSelected(boolean bool) {
		shapeSelected = true;
	}
	
	public Coordinates getLastMousePosition() {
		return lastMousePosition;
	}
	
	public void changeColor(Color c) {
		shapeColor = c ;
	}
	
	public void changeColorTemporarily() {	 
		originalColor = shapeColor; 
		shapeColor = Color.yellow ;
		perimeterSelected = true;
	}
	
	public void changeColorBack() {	 
		shapeColor = originalColor; 
		perimeterSelected = false;
	}
	
	public void savePositionWhereUserPressedMouse(int x, int y) {
		lastMousePosition = new Coordinates(x, y);
	}
	
	//allows the user to drag the circle by dragging the mouse button. The shape is first selected by 
	//pressing the mouse button with the cursor inside the shape.Then, if the user drags the mouse, the shape 
	//should move with the mouse. Moving a circle simply means modifying the reference point of the circle so that the
	//circle moves with the mouse position.The idea is that if the x and the y coordinate of the position of the mouse 
	//is moved by specified amounts,the reference point must change by exactly the same amount.
	public void moveShape(Coordinates currentPositionMouse) {	     	
		if(shapeSelected) {		
			// If a shape is selected for a move operation, change the reference point as the mouse is being dragged.
			referencePoint.setX(referencePoint.getX() + currentPositionMouse.getX() - lastMousePosition.getX());
			referencePoint.setY(referencePoint.getY() + currentPositionMouse.getY() - lastMousePosition.getY());
			lastMousePosition = currentPositionMouse;
		}
	}
}