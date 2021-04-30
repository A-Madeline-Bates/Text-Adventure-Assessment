package ParseExceptions;

public class NotInInventory extends ParseException {

	public NotInInventory(String token) {
		this.token = token;
	}

	public String toString() {
		return "The item you are searching for is not in your inventory. Item specified was " + token + ".";
	}
}
