import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TopScores {
	private ArrayList<Player> topPlayers = new ArrayList<>();
	private String fileName;

	//constructors
	public TopScores() {
	}

	public TopScores(String fileName, Player winner) {
		this.fileName = fileName;
		updateScores(winner);
		displayScores();
	}

	//setters and getters
	public ArrayList<Player> getTopPlayers() {
		return topPlayers;
	}

	public void setTopPlayers(ArrayList<Player> topPlayers) {
		this.topPlayers = topPlayers;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void updateScores(Player winner) {
		readScoresFromFile();
		topPlayers.add(winner);
		while (topPlayers.size() > 5) {
			//if the size of topPlayers is greater than 5, remove the lowest score
			Collections.sort(topPlayers, Comparator.nullsLast(Comparator.comparingInt(Player::getTurn)));
			topPlayers.remove(topPlayers.size() - 1);
		}
		writeScoresToFile(); // Save the changes to the file after updating the top players
	}

	private String getFormattedTopScores() {
		//string is not mutable so we choose this
		StringBuilder records = new StringBuilder();
		//arrays.sort: sort list in ascending order
		//nullsLast: places null values at the end of the sorted list
		//Player::getScore: to extract the score of each Player object
		Collections.sort(topPlayers, Comparator.nullsLast(Comparator.comparingInt(Player::getTurn)));
		records.append("\nTop Scores:\n");
		records.append("------------\n");
		for (int i = 0; i < topPlayers.size(); i++) {
			Player playerResult = topPlayers.get(i);
			if (playerResult != null) {
				records.append((i + 1) + ". " + playerResult.getName() + " - " + playerResult.getTurn() + "\n");
			}
		}
		records.append("------------\n");
		return records.toString();
	}

	public void displayScores() {
		try {
			Scanner reader = new Scanner(new FileReader(fileName));
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				System.out.println(data);
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error reading scores from file: " + e.getMessage());
		}
	}

	//other methods
	private void readScoresFromFile() {
		try {
			Scanner reader = new Scanner(new FileReader(fileName));
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				//split the line into name and turn parts, they are separated by a hyphen
				String[] parts = data.split(" - ");
				if (parts.length == 2) {
					String nameWithNum = parts[0].trim();
					String name = nameWithNum.substring(nameWithNum.indexOf(" ") + 1);
					int turn = Integer.parseInt(parts[1].trim());
					//create a new Player object and add it to the topPlayers ArrayList
					Player player = new Player(name, turn);
					topPlayers.add(player);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error reading scores from file: " + e.getMessage());
		}
	}

	private void writeScoresToFile() {
		try {
			FileWriter myWriter = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(myWriter);
			String formattedTopScores = getFormattedTopScores(); //get the formatted top scores as a string
			bw.write(formattedTopScores); //write the top scores string to the file
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("Error writing scores to file: " + e.getMessage());
		}
	}
}
