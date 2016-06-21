package nPuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nPuzzle.exception.WrongFormatException;

public class ConsoleUI implements UserInterface, Serializable {

	private Field field;
	private long startPlayTime;

	@Override
	public void newGameStarted(Field field) {
		startPlayTime = System.currentTimeMillis();

		this.field = field;
		if (field.getSaveGameFile().exists()) {
			field.loadFile();
		}
		field.setState(GameState.PLAYING);
		do {
			update();
			procesInput();

			if (field.getState() == GameState.SOLVED) {
				update();
				System.out.println("You win!!");
				field.getSaveGameFile().delete();
				System.exit(0);
			}
		} while (true);

	}

	@Override
	public void update() {
		Tile tile;
		System.out.println("Time: " + getPlayingTime());
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				tile = field.getTile(i, j);
				if (tile != null) {
					int puzzleCounter = (((Clue) tile).getValue());
					if (field.getPuzzleCount() >= puzzleCounter
							&& puzzleCounter < 10) {
						System.out.print("  " + (((Clue) tile).getValue()));
					} else if (puzzleCounter > 9
							&& puzzleCounter <= field.getPuzzleCount()) {
						System.out.print(" " + (((Clue) tile).getValue()));
					}
				} else {
					System.out.print("  -");

				}
			}
			System.out.println();
		}

	}

	private void procesInput() {

		System.out
				.println("Enter direction W-UP A-LEFT S-DOWN D-RIGHT, exit/x to exit, new to newGame: ");
		try {
			String input = readLine();
			handleInput(input);
		} catch (WrongFormatException e) {

			e.printStackTrace();
		}
	}

	private void handleInput(String input) throws WrongFormatException {
		Pattern pattern = Pattern
				.compile("([Ww]|[Aa]|[Ss]|[Dd]|exit|new|[Xx]){1}");
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {

			switch (matcher.group(0).toLowerCase()) {
			case "exit":
				exitGame();
				break;
			case "x":
				exitGame();
				break;
			case "w":
				field.moveTile(Directions.UP);
				break;
			case "a":
				field.moveTile(Directions.LEFT);
				break;
			case "d":
				field.moveTile(Directions.RIGHT);
				break;
			case "s":
				field.moveTile(Directions.DOWN);
				break;
			case "new":
				Field newField = new Field(field.getRowCount(),
						field.getColumnCount());
				newGameStarted(newField);
				break;
			default:
				break;
			}
		} else {
			throw new WrongFormatException("You type something wrong: " + input);
		}
	}

	private void exitGame() {
		try {

			field.saveFile();
			System.out.println("The Game was saved to file"
					+ field.getSaveGameFile().getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public int getPlayingTime() {
		return (int) (System.currentTimeMillis() - startPlayTime) / 1000;
	}

}
