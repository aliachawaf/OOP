package chawaf.alia;

public class HumanPlayer extends Player {

	private String name;

	//constructor
	public HumanPlayer( String name, int mapSize) {
		super(mapSize);
		this.name = name;
	}

	// getters & setters
	public String getName() {
		return name;
	}

	
	@Override
	public String toString(){
		return this.getName().toUpperCase();
	}
		
}