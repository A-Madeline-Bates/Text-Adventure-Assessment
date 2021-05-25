package parseExceptions;

public class LocationDoesNotExist extends ParseException {
	public LocationDoesNotExist(String token) {
		this.token = token;
	}

	public String toString() {
		return "The location you are trying to does not exist. Location specified was " + token + ".";
	}
}
