import java.util.Random;

public class Current extends Element {

	//constructors
	public Current() {
		Random r = new Random();
		setPosition(r.nextInt(99));//position is randomly generated
		setSymbol('C');
	}

	public Current(int position, char symbol) {
		super(position, symbol);
		// TODO Auto-generated constructor stub
	}
}
