import java.util.Random;

public class Trap extends Element {

	//constructors
	public Trap() { 
		Random r = new Random();
		setPosition(r.nextInt(98 + 1));//+1 to prevent having trap as 1st element
		setSymbol('#');

	}

	public Trap(int position, char symbol) {
		super(position, symbol);
		// TODO Auto-generated constructor stub
	}
}
