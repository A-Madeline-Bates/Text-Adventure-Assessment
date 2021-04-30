package Parse;

import Data.Entities;
import Data.Inventory;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class MultiWordCMD {

	public MultiWordCMD(Entities entityClass, Inventory inventory, String searchType, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
	}

	private void searchDot(String commandEnd){
	}
}
