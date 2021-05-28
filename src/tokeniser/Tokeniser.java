package tokeniser;
import parseExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokeniser {
	final ArrayList<String> tokenList = new ArrayList<>();
	private ArrayList<String> commandList;
	//we always want to start to position one because position zero contains the username
	private int arrayPosition = 0;

	public Tokeniser(String incomingCommand) {
		tokenList.addAll(Arrays.asList(incomingCommand.split("\\s+")));
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

//	public String peekNextToken() throws TokenMissing {
//		testArrayPosition();
//		//This will throw an exception if there's not enough words in the command to fulfill what we need
//		String token = tokenList.get(arrayPosition);
//		return token;
//	}
//
//	public void advanceToken(){
//		arrayPosition++;
//	}
//
//	public String getRemainingTokens() throws ParseException {
//		testArrayPosition();
//		StringBuilder compositeToken = new StringBuilder();
//		while (arrayPosition < tokenList.size()) {
//			compositeToken.append(tokenList.get(arrayPosition));
//			//Add a space into the string, unless it's our final token
//			if(arrayPosition != tokenList.size() - 1){
//				compositeToken.append(" ");
//			}
//			arrayPosition++;
//		}
//		return compositeToken.toString();
//	}

//	public void checkForExtra() throws ExtraCommand {
//		//This is called when a string should be complete- if the ArrayPosition was still less than tokenList.size()
//		//that would indicate that the incomingCommand is longer than it should be
//		if(arrayPosition < tokenList.size()){
//			throw new ExtraCommand();
//		}
//	}
}