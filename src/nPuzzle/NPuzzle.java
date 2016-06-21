package nPuzzle;

public class NPuzzle {

	private NPuzzle() {
		UserInterface userInterface;
		userInterface = new ConsoleUI();
		Field field = new Field(3, 3);
		userInterface.newGameStarted(field);
	}

	public static void main(String[] args) {
		new NPuzzle();

	}

}
