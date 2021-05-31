package parseExceptions;

public class NoPathErr extends ParseException {
	private String token;

	public NoPathErr(String token) {
		this.token = token;
	}

	public String toString() {
		return "There is no available path to location you are trying to access. Location specified was "
				+ token + ".\n";
	}
}
