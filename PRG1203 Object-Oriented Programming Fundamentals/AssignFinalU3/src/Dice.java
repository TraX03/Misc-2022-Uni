import java.util.Random;

public class Dice {

	private static Random random = new Random();
	private static int dice;

	//setter getter
	public static int rollDice() { //will be called for each player's turn & when player meets special elements
		dice = random.nextInt(6)+ 1; //get a number between 1 and 6 (inclusive)
		return dice;
	}

	public static int getDice() {
		return dice;
	}
}
