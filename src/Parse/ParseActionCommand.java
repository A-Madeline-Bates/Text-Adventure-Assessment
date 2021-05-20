package Parse;

import Data.Entities;
import Data.PlayerState;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class ParseActionCommand {

	public ParseActionCommand(Entities entityClass, PlayerState playerState, int actionPosition, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		validateActionObject(commandEnd);
	}

	private void validateActionObject(String commandEnd){

	}
}
