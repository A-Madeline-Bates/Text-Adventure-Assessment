package Parse;

import Data.Entities;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class CallLocationCMD {

	public CallLocationCMD(Entities entityClass, String searchType, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchDot(commandEnd, entityClass, searchType);
	}

	private void searchDot(String commandEnd, Entities entityClass, String searchType){

	}
}
