package proj;

public class ArrayOfShapeWithIterator extends AbstractList {
	protected Shape myShapes[];
	protected int currentElement;


	public ArrayOfShapeWithIterator() {
		myShapes = new Shape[20];
		currentElement = 0;
	}

	public AbstractIterator createIterator() {
        return new Iterator();
	}
	
	public void setIndex(int i) {
		currentElement = i;
	}
	
	public void append(Shape obj) {
		if(currentElement < myShapes.length) {
			myShapes[currentElement] = obj;
			currentElement ++;	
		}
	}
	
	public class Iterator extends AbstractIterator implements MyIterator {
		private boolean endofArray;
		private int size;
		
		public Iterator() {
			endofArray = false;
			size = myShapes.length;
	    }
		
		public void first() {
			currentElement = 0;

			if(myShapes[currentElement] != null) {
				endofArray = false;
			}
			else {
				endofArray = true;
			}
		}

		public void next() {
			if((currentElement < size) && (myShapes[currentElement]!= null)) {
				currentElement ++;
			}
			else {
				endofArray = true;
			}
		}

		public boolean isDone() {
	          return endofArray;
		}

		public Shape currentItem() {
			if(currentElement < size) {
				return myShapes[currentElement];
			}
			else {
				return null;
			}
		}
	}
}