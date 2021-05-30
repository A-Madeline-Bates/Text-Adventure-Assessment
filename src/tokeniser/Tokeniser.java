package tokeniser;
import parseExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokeniser {
	final ArrayList<String> tokenList = new ArrayList<>();
	private ArrayList<String> commandList;
	private int arrayPosition = 0;

	public Tokeniser(String incomingCommand) {
		//split by spaces and common punctuation (most punctuation isn't useful in any of the grammar but a
		// user could conceivably insert punctuation, so we're effectively throwing it out here)
		tokenList.addAll(Arrays.asList(incomingCommand.split("[,.!?()]|\\s+")));
		tokenList.removeAll(Arrays.asList("", null));
	}

	//Used to access player names
	public String getNextToken() throws ParseException {
		testArrayPosition();
		//This will throw an exception if there's not enough words in the command to fulfill what we need
		String token = tokenList.get(arrayPosition);
		arrayPosition++;
		return token;
	}

	private void testArrayPosition() throws TokenMissing {
		if(arrayPosition >= tokenList.size()) {
			throw new TokenMissing();
		}
	}

	//Strips the player's name from the token list, returning only the commands
	public void createCommandList(){
		int nameEnd = arrayPosition-1;
		ArrayList<String> commandList = new ArrayList<>(tokenList);
		for(int i=nameEnd; i>=0; i--){
			commandList.remove(i);
		}
		this.commandList = commandList;
	}

	public ArrayList<String> getCommandList(){
		return commandList;
	}
}