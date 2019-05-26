package exception;

public class DijkstraException extends Exception {
	private static String origMessage = "Impossible to proceed Dijkstra algorithm.";

	public DijkstraException() {
		this(origMessage);
	}

	public DijkstraException(String message) {
		super(message);
	}
}
