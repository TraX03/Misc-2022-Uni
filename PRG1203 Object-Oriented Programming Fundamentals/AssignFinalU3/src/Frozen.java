import java.util.Random;

public class Frozen extends Element {

	//constructors
	public Frozen() {
		Random r = new Random();
		setPosition(r.nextInt(99));
		setSymbol('@');
	}

	public Frozen(int position, char symbol) {
		super(position, symbol);
		// TODO Auto-generated constructor stub
	}
}