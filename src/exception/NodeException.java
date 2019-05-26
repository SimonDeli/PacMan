package exception;

public class NodeException extends Exception {

	private static String origMessage = "Impossible to create node";

	public NodeException() {
		this(origMessage);
	}

	public NodeException(String message) {
		super(message);
	}
}
