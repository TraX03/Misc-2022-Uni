import java.util.Scanner;

public class Player {

	private String name;
	private int position = 0;
	private int turn = 0;
	private char symbol;
	private boolean freezeNextTurn, multiplyNextTurn, resetFrozen;

	//constructors
	public Player() {
	}

	public Player(String name, int turn) {
		this.name = name;
		this.turn = turn;
	}
	
	public Player(char sym) {
		info(sym);
		setSymbol(sym);
	}

	//setter getter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void setTurn() {
		turn++;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	//to check the status of player whether their turn is to be skipped
	public boolean isFreezeNextTurn() {
		return freezeNextTurn;
	}

	//to set the skip status of player if they encounter Frozen @ element
	public void setFreezeNextTurn(boolean freezeNextTurn) {
		this.freezeNextTurn = freezeNextTurn;
	}
	
	//for condition checking to reset @ element
	public boolean isResetFrozen() {
		return resetFrozen;
	}

	//to set & reset @ element status after player has left the space
	public void setResetFrozen(boolean resetFrozen) {
		this.resetFrozen = resetFrozen;
	}

	//to check the multiply status of player
	public boolean isMultiplyNextTurn() {
		return multiplyNextTurn;
	}

	//to set Multiply status of player
	public void setMultiplyNextTurn(boolean multiplyNextTurn) {
		this.multiplyNextTurn = multiplyNextTurn;
	}
	


	//other methods
	//to get player's name
	public void info(char sym) {
		Scanner sc = new Scanner(System.in);

		do {
			System.out.printf("Player %c enter name: ", sym);
			name = sc.nextLine().trim();

			if(name.isEmpty()) {
				System.out.println("Name cannot be empty!");
			}
		}while(name.isEmpty());
		
	}
	
	//player move, roll dice for their turn and move to new position
	public void play() {
		Scanner sc = new Scanner(System.in);
		String enter = sc.nextLine();
		
		if (enter.isEmpty()) { //use isEmpty() to check for an empty input		
			position += Dice.rollDice();
			turn++;
			//Math.min takes two arguments and returns the smaller value
			setPosition(Math.min(position, 100));
		}
		else {
			System.out.println("Press enter only to roll the dice!");
			play();
		}
	}
	
	//if player hit 'C' 
	public void currentEffect() {
		System.out.println("You hit a current! Advancing forward... ");
		int playerPosition = getPosition() + Dice.rollDice();
		setPosition(Math.min(playerPosition, 100));
		System.out.println("Step forward " + Dice.getDice());
	}

	//if player hit '#'
	public void trapEffect() {
		System.out.println("You are caught by a trap! Falling backwards... ");
		int playerPosition = getPosition() - Dice.rollDice();
		setPosition(Math.max(playerPosition, 1));
		System.out.println("Step back " + Dice.getDice());
	}
	
	//toString
	public String toString() {
		return String.format("%s : %d turns", getName(), getTurn());
	}
}
