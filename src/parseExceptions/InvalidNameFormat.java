package parseExceptions;

public class InvalidNameFormat extends ParseException {
	String firstCommand;
	String secondCommand;

	public InvalidNameFormat(String firstCommand, String secondCommand){
		this.firstCommand = firstCommand;
		this.secondCommand = secondCommand;
	}

	public String toString(){
		return "Invalid name format. You must start commands with a name followed by a colon to play. The first" +
				"two tokens you used were " + firstCommand + ", " + secondCommand +".\n";
	}
}
