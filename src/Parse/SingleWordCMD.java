package Parse;

import Tokeniser.Tokeniser;

public class SingleWordCMD {
	public SingleWordCMD(Tokeniser tokeniser){
		//checkForExtra() will throw an assert error if there is extra tokens in the input command
		tokeniser.checkForExtra();
	}
}
