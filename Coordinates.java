package proj;

public class Coordinates {
	private int xCoordinates, yCoordinates; 

	// Constructor
	public Coordinates(int x, int y) {
		xCoordinates = x;
		yCoordinates = y;
	}
	
	public Coordinates(Coordinates coordinate) {
		xCoordinates = coordinate.getX();
		yCoordinates = coordinate.getY();
	}
	
	public void setX(int x) {
		xCoordinates = x;
	}

	public void setY(int y) {
		yCoordinates = y;
	}

	public int getX() { 
		return xCoordinates;
	}

	public int getY() { 
		return yCoordinates;
	}
	
	public String toString() {
		return "(" + xCoordinates + ", " + yCoordinates + ")";
	}
}