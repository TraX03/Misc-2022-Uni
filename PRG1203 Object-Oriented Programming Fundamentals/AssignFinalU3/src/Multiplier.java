import java.util.Random;

public class Multiplier extends Element {

	//constructors
	public Multiplier() {
		Random r = new Random();
		setPosition(r.nextInt(99));
		setSymbol('$');
	}

	public Multiplier(int position, char symbol) {
		super(position, symbol);
		// TODO Auto-generated constructor stub
	}
}