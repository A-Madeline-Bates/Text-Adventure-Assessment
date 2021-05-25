package parseExceptions;

public class ExtraCommand extends ParseException{
	public String toString(){
		return "Extra command. You have added one instruction more than expected. " +
				"Please check the instruction grammar.\n";
	}
}
