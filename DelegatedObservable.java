    
package proj;

import java.util.Observable;

//A subclass of Observable that allows delegation.
public class DelegatedObservable extends Observable {
	public void clearChanged() {
		super.clearChanged();
	}
	
	public void setChanged() {
		super.setChanged();
	}
}