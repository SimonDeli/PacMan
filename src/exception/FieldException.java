package exception;

public class FieldException extends Exception {
	private static String origMessage = "Impossible to create field.";

	public FieldException() {
		this(origMessage);
	}

	public FieldException(String message) {
		super(message);
	}
}
