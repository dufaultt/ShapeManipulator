package proj;

public abstract class AbstractList {
	public abstract AbstractIterator createIterator();
	public abstract void append(Shape myShape);
	public abstract void setIndex(int i);
	public abstract class AbstractIterator implements MyIterator {}
}