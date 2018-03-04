
public class Element {

	private int key;
	private Element next;
	
	//constructor
	public Element(int key) {
		this.key = key;
		next = null;
	}
	
	//getters & setters
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Element getNext() {
		return next;
	}

	public void setNext(Element next) {
		this.next = next;
	}
	
	//methods
	public String toString(){
		return "(" + key + ")";
	}
	
}
