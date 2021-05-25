package parseExceptions;

public class InvalidFirstCommand extends ParseException {

	public InvalidFirstCommand(String token){
		this.token = token;
	}

	public String toString(){
		return "Invalid first command. Valid first commands are INVENTORY, GET, DROP, GOTO, LOOK\n" +
				"or a valid action command. Command used was " + token + ".\n";
	}
}
