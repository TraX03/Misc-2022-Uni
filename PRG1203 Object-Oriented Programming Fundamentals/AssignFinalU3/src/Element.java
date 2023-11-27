public class Element {

	private int position;
	private char symbol; 

	//constructors
	public Element() {
	}

	public Element(int position, char symbol) {
		this.position = position;
		this.symbol = symbol;
	}

	//setter getter
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}