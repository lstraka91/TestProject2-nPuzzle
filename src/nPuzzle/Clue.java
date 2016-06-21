package nPuzzle;

import java.io.Serializable;

public class Clue extends Tile implements Serializable {
	private final int value;

	public Clue(int value) {
		this.value = value;

	}

	public int getValue() {
		return value;
	}

}
