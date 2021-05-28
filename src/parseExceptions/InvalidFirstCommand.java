package parseExceptions;

public class InvalidFirstCommand extends ParseException {

	public InvalidFirstCommand(){
	}

	public String toString(){
		return "No valid first command given. Valid first commands are INVENTORY, GET, DROP, GOTO, LOOK\n" +
				"or a valid action command.\n";
	}
}
