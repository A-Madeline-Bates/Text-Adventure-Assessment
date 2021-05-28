package tokeniser;
import parseExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokeniser {
	final ArrayList<String> tokenList = new ArrayList<>();
	//we always want to start to position one because position zero contains the username
	private int arrayPosition = 0;

	public Tokeniser(String incomingCommand) {
		tokenList.addAll(Arrays.asList(incomingCommand.split("\\s+")));
		tokenList.removeAll(Arrays.asList("", null));
	}

//	public ArrayList<String> getCommand(){
//		for(int i=0; i<)
//	}

	public String getNextToken() throws ParseException {
		testArrayPosition();
		//This will throw an exception if there's not enough words in the command to fulfill what we need
		String token = tokenList.get(arrayPosition);
		arrayPosition++;
		return token;
	}

	public String peekNextToken() throws TokenMissing {
		testArrayPosition();
		//This will throw an exception if there's not enough words in the command to fulfill what we need
		String token = tokenList.get(arrayPosition);
		return token;
	}

	public void advanceToken(){
		arrayPosition++;
	}

	public String getRemainingTokens() throws ParseException {
		testArrayPosition();
		StringBuilder compositeToken = new StringBuilder();
		while (arrayPosition < tokenList.size()) {
			compositeToken.append(tokenList.get(arrayPosition));
			//Add a space into the string, unless it's our final token
			if(arrayPosition != tokenList.size() - 1){
				compositeToken.append(" ");
			}
			arrayPosition++;
		}
		return compositeToken.toString();
	}

	private void testArrayPosition() throws TokenMissing {
		if(arrayPosition >= tokenList.size()) {
			throw new TokenMissing();
		}
	}

	public void checkForExtra() throws ExtraCommand {
		//This is called when a string should be complete- if the ArrayPosition was still less than tokenList.size()
		//that would indicate that the incomingCommand is longer than it should be
		if(arrayPosition < tokenList.size()){
			throw new ExtraCommand();
		}
	}
}