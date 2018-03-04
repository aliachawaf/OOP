public class MyList {

	private Element head;

	// constructor
	public MyList() {
		this.head = null;
	}

	// getters & setters
	public Element getHead() {
		return head;
	}

	public void setHead(Element head) {
		this.head = head;
	}

	// methods
	public boolean isEmpty() {
		return this.head == null;
	}

	public String toString() {

		String display = "->";
		while (!this.isEmpty()) {
			display = display + head.toString() + "->";
			head = head.getNext();
		}
		return display;
	}

	public void addFirst(Element e) {
		e.setNext(this.head);
		this.head = e;
	}

	public Element removeFirst() {
		if (!this.isEmpty()) {
			Element firstElement = this.head;
			this.head = this.head.getNext(); // firstElement deleted
			firstElement.setNext(null);

			return firstElement;
		}

		else {
			return this.head;
		}
	}

	public void addLast(Element e) {

		if (isEmpty()) {
			this.head = e;
		} else {
			Element currentElement = this.head;

			while (currentElement.getNext() != null) {
				currentElement = currentElement.getNext();
			}

			currentElement.setNext(e);
		}
	}

	public Element removeLast() {

		if (!this.isEmpty()) {
			Element currentElement = this.head;

			while (currentElement.getNext().getNext() != null) {
				currentElement = currentElement.getNext();
			}

			Element lastElement = currentElement.getNext();
			currentElement.setNext(null);
			return lastElement;
		} else {
			return this.head;
		}
	}

	public Element findKey(int key) {

		if (isEmpty()) {
			return this.head;
		} else {

			Element currentElement = this.head;
			boolean keyFound = false;

			while (currentElement != null && !keyFound) {

				if (currentElement.getKey() == key) {
					keyFound = true;
				} else {
					currentElement = currentElement.getNext();
				}
			}

			return currentElement;
		}
	}

}
