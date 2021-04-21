package ParseExceptions;

public abstract class ParseException extends Exception{
	protected String token;

	public abstract String toString();
}
