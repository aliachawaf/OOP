
public class TestMyList {

	public static void main(String[] args) {

		MyList list = new MyList();
		
		Element e1 = new Element(3);
		Element e2 = new Element(1);
		Element e3 = new Element(4);
		
		list.addLast(e1);
		list.addFirst(e2);
		list.addLast(e3);
		
		e1 = list.removeFirst();
		
		list.addLast(e1);
		
		System.out.println(list);
	}
}
