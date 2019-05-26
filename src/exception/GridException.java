package exception;

public class GridException extends Exception {
	private static String origMessage = "There is a probleme concerning the grid.";

	public GridException() {
		this(origMessage);
	}

	public GridException(String message) {
		super(message);
	}
}
