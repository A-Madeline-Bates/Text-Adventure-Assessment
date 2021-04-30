package Parse;

import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class SingleWordCMD {
	public SingleWordCMD(Tokeniser tokeniser) throws ParseException {
		//checkForExtra() will throw an assert error if there is extra tokens in the input command
		tokeniser.checkForExtra();
	}
}
