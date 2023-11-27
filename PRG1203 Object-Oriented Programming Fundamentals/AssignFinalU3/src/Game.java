public class Game {

	private River river;
	private Player currentPlayer;
	private Player opponent;
	private TopScores topScores;
	private int playerTurn = 0;
	private boolean messagePrinted = false;

	//constructors
	public Game() {
		river = new River();
		currentPlayer = new Player();
		opponent = new Player();
		topScores = new TopScores();
	}
	
	//setter getter
	public void setPlayerSymbol(Player player) {
		river.geteList().get(player.getPosition()-1).setSymbol(player.getSymbol());
	}
	
	public void setOriSymbol(Player player, char symbol) {
		river.geteList().get(player.getPosition()-1).setSymbol(symbol);
	}
	
	public char getCurrentSymbol(Player player) {
		return river.geteList().get(player.getPosition()-1).getSymbol();
	}
	
	public Element getIndexSymbol(Player player) {
		return river.geteList().get(currentPlayer.getPosition()-1);
	}
	
	
	//other methods
	//to execute the game
	public void start() {
		
		System.out.println("Welcome to the Boat Racing Game!");
		river.printRiver();
		System.out.println();
		
		Player playerA = new Player('A');
		Player playerB = new Player('B');
		
		System.out.print("Let's start, press enter to roll your dice: ");
		
		//winning condition
		while (playerA.getPosition()-1 <99 || playerB.getPosition()-1 <00) {	
			System.out.println();
			//determine who's turn is it, even playerA odd playerB 
			playerTurn++;
			currentPlayer = playerTurn % 2 == 1 ? playerA : playerB;
			opponent = (currentPlayer == playerA) ? playerB: playerA;
			playerTurn();	
			
			//check if player reaches index 100 of the track  
			if (currentPlayer.getPosition() == 100) {
				break;
			}
		}		
		Player winner = currentPlayer;
		System.out.println("\nCongratulations Player " + winner.getSymbol() + "!");
		System.out.println(winner);
		//print leaderboard
		topScores = new TopScores("scores.txt", winner);
		
		
	}

	//the procedure for player's turn 
	public void playerTurn() {
		playerMove();
		river.printRiver();
		
		//'P' means 2 player on same position
		//if its 'P' then display symbol of the player remain after another player move
		//if its not set it to empty
		if (playerTurn > 1) {
			if (getCurrentSymbol(opponent) == 'P') {
				setPlayerSymbol(opponent);
				setPlayerSymbol(currentPlayer);
			}else {
				setOriSymbol(opponent, '_');;
			}	
		}
	}

	public void playerMove() {
		//to set back the skip symbol after player is gone
		if(currentPlayer.isResetFrozen()){
			currentPlayer.setResetFrozen(false);
			river.setRiverElement(currentPlayer.getPosition()-1, new Frozen());
		}
		
		//multiply or skip or normal
		if (currentPlayer.isFreezeNextTurn()) {
			currentPlayer.setFreezeNextTurn(false); //reset the skipNextTurn flag
			currentPlayer.setResetFrozen(true); //prepare to set back skip symbol next round
			currentPlayer.setTurn(); //count turn even when skip
			System.out.printf("Round %d, Player %c is freezed.\n", currentPlayer.getTurn(), currentPlayer.getSymbol());
			//if at this round when being skip the symbol at current position is 'P' then set 'P'
			//if its not set the currentPlayer symbol
			setOriSymbol(currentPlayer, getCurrentSymbol(currentPlayer) == 'P'? 
					'P' : currentPlayer.getSymbol());
			return;
		}
		else if(currentPlayer.isMultiplyNextTurn()) {
			currentPlayer.setMultiplyNextTurn(false);
			//set back multiply symbol after player move
			river.setRiverElement(currentPlayer.getPosition()-1, new Multiplier());
			currentPlayer.play();
			System.out.printf("Player %c 's dice roll of %d has been multiplied by 2.\n", currentPlayer.getSymbol(), Dice.getDice());
			//if exceed set position 100 else current position
			currentPlayer.setPosition(Math.min(currentPlayer.getPosition()-1 + Dice.getDice() + 1, 100));
		}
		else {
			currentPlayer.play();
		}
		
		//use in checkEffect
		messagePrinted = false;
		checkEffect();
	}
	
	//if player land on what symbol do what
	public void checkEffect() {
		Element thisSymbol = getIndexSymbol(currentPlayer);
		
		//prevent message print again when checkEffect is called second time
		if(!messagePrinted) {
			System.out.printf("Round %d, Player %c rolls: %d, Position at %d\n", 
					currentPlayer.getTurn(), currentPlayer.getSymbol(), Dice.getDice(), currentPlayer.getPosition());	
			messagePrinted = true;
		}
		
		//current and trap
		if(thisSymbol instanceof Current || thisSymbol instanceof Trap) {
			playerProcedure();
			
			if (thisSymbol instanceof Current ) {
				//set back current symbol after player move
				river.setRiverElement(currentPlayer.getPosition()-1, new Current());
				currentPlayer.currentEffect();
			}
			else {
				river.setRiverElement(currentPlayer.getPosition()-1, new Trap());
				currentPlayer.trapEffect();
			}
			//call again to check if the new landing got another symbol
			checkEffect();
			setPlayerSymbol(currentPlayer);	
			
		//frozen
		}else if(thisSymbol instanceof Frozen) {
			System.out.printf("Player %c landed on @. Frozen for their next turn.\n", currentPlayer.getSymbol());
			currentPlayer.setFreezeNextTurn(true);
			setPlayerSymbol(currentPlayer);
			
		//multiplier
		}else if(thisSymbol instanceof Multiplier) {
			System.out.printf("Player %c landed on $. Multiplying their next dice value by 2.\n", currentPlayer.getSymbol());
			currentPlayer.setMultiplyNextTurn(true);
			setPlayerSymbol(currentPlayer);
			
		//same position
		
		//if track has no special effects
		}else{
			//when another player is on same position
			if(getCurrentSymbol(currentPlayer) == opponent.getSymbol()){
				setOriSymbol(currentPlayer,'P');}
			else {
				setPlayerSymbol(currentPlayer);
				}
		}
	}

	public void playerProcedure() {
		//print before update river
		setPlayerSymbol(currentPlayer);
		river.printRiver();
		//reset back to '_' after printing the position 
		setOriSymbol(currentPlayer, '_');
	}
}