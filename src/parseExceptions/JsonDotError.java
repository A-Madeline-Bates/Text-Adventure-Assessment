package parseExceptions;

public class JsonDotError extends ParseException{
	public String toString(){
		return "There is a JSON and/or DOT Error.\n";
	}
}