package ParseExceptions;

public class ActionSubjectMismatch extends ParseException {
	public ActionSubjectMismatch(String token) {
		this.token = token;
	}

	public String toString() {
		return "The object you are trying to act on is not at your location or in your inventory. " +
				"Object specified was " + token + ".";
	}
}
