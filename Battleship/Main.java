import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter start coord : ");
		String start = scanner.nextLine(); 
		Coord startCoord = new Coord(start); 	
		
		System.out.println("enter end coord : ");
		String end = scanner.nextLine(); 
		Coord endCoord = new Coord(end);
		
		ArrayList<Coord> list = new ArrayList<Coord>();
		
		Ship test = new Ship("carrier", 5, startCoord, endCoord, list);

		System.out.println(test.isVertical());
		
		Coord Hit = new Coord("a3"); 
		System.out.println(test.isHit(Hit));
		
		System.out.println(test.toString());
		
	}

}