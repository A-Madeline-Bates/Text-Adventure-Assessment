import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class Test {
	public Test(){
		try {
			testStringConcat();
		} catch(ParseException p){
		}
	}

	private void testStringConcat() throws ParseException{
		//first token is still missed due to blanket policy around try to skip name.
		Tokeniser tokeniser = new Tokeniser("hello there");
		assert(tokeniser.getRemainingTokens().equals("there"));
		tokeniser = new Tokeniser("hello there hello hello");
		assert(tokeniser.getRemainingTokens().equals("there hello hello"));
		tokeniser = new Tokeniser("1 2 3 4 5");
		assert(tokeniser.getRemainingTokens().equals("2 3 4 5"));
		tokeniser = new Tokeniser("a b c d e");
		tokeniser.getNextToken();
		assert(tokeniser.getRemainingTokens().equals("c d e"));
	}
}
