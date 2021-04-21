import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokeniser {
	final ArrayList<String> tokenList = new ArrayList<>();
	//we always want to start to position one because position zero contains the username
	private int ArrayPosition = 1;

	public Tokeniser(String incomingCommand) {
		tokenList.addAll(Arrays.asList(incomingCommand.split("\\s+")));
		tokenList.removeAll(Arrays.asList("", null));
	}

	public String nextToken() {
		if (ArrayPosition < tokenList.size()) {
			String token = tokenList.get(ArrayPosition);
			ArrayPosition++;
			return token;
		} else {
			return null;
		}
	}
}