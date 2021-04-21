import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokeniser {
	final ArrayList<String> TokenList = new ArrayList<>();
	private int ArrayPosition = 0;

	public Tokeniser(String incomingCommand) {
		TokenList.addAll(Arrays.asList(incomingCommand.split("\\s+")));
		TokenList.removeAll(Arrays.asList("", null));
	}

	public String nextToken() {
		if (ArrayPosition < TokenList.size()) {
			String Token = TokenList.get(ArrayPosition);
			ArrayPosition++;
			return Token;
		} else {
			return null;
		}
	}
}