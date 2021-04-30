package Tokeniser;

import ParseExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokeniser {
	final ArrayList<String> tokenList = new ArrayList<>();
	//we always want to start to position one because position zero contains the username
	private int ArrayPosition = 1;

	public Tokeniser(String incomingCommand) {
		tokenList.addAll(Arrays.asList(incomingCommand.split("\\s+")));
		tokenList.removeAll(Arrays.asList("", null));
	}

	public String getNextToken() throws ParseException {
		testArrayPosition();
		//This will throw an exception if there's not enough words in the command to fulfill what we need
		String token = tokenList.get(ArrayPosition);
		ArrayPosition++;
		return token;
	}

	public String getRemainingTokens() throws ParseException {
		testArrayPosition();
		String compositeToken = "";
		while (ArrayPosition < tokenList.size()) {
			compositeToken += tokenList.get(ArrayPosition);
			//Add a space into the string, unless it's our final token
			if(ArrayPosition != tokenList.size() - 1){
				compositeToken += " ";
			}
			ArrayPosition++;
		}
		return compositeToken;
	}

	private void testArrayPosition() throws ParseException{
		if(ArrayPosition >= tokenList.size()) {
			throw new TokenMissing();
		}
	}

	public void checkForExtra() throws ParseException{
		//This is called when a string should be complete- if the ArrayPosition was still less than tokenList.size()
		//that would indicate that the incomingCommand is longer than it should be
		if(ArrayPosition < tokenList.size()){
			throw new ExtraCommand();
		}
	}
}