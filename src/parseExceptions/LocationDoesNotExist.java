package parseExceptions;

public class LocationDoesNotExist extends ParseException {

	public String toString() {
		return "The location you are trying to go to does not exist.\n";
	}
}
