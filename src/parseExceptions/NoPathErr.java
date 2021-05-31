package parseExceptions;

public class NoPathErr extends ParseException {
	private final String token;

	public NoPathErr(String token) {
		this.token = token;
	}

	public String toString() {
		return "There is no available path to location you are trying to access.\nLocation specified was "
				+ token + ".\n";
	}
}
