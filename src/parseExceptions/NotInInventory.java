package parseExceptions;

public class NotInInventory extends ParseException {

	public String toString() {
		return "The item you are searching for is not in your inventory.\n";
	}
}
