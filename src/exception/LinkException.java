package exception;

public class LinkException extends Exception {
	private static String origMessage = "Impossible to create links";

	public LinkException() {
		this(origMessage);
	}

	public LinkException(String message) {
		super(message);
	}
}
