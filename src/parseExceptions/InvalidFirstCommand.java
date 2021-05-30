package parseExceptions;

public class InvalidFirstCommand extends ParseException {

	public String toString(){
		return "No valid first command given. Valid first commands\n are INVENTORY, GET, DROP, GOTO, LOOK\n" +
				"or a valid action command.\n";
	}
}
