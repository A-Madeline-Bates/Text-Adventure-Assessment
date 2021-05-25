package parseExceptions;

public class TokenMissing extends ParseException{
	public String toString(){
		return "Input command is too short. Please check the instruction grammar.";
	}
}