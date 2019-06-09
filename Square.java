    
package proj;

import java.awt.Color;

public class Square extends Rectangle {
	public Square(Color currentColor, int index) {
		super(currentColor, index);
	}
	
	public void setArg1(int arg1) {
		this.arg1 = arg1;
		this.arg2 = arg1;
	}
	
	public void setArg2(int arg2) {
		this.arg1 = arg2;
		this.arg2 = arg2;
	}
	
	public String toString() {
		return ("Square with reference point " + referencePoint + " having width " + arg1 + " and height " + arg2 + "\n");
	}
}