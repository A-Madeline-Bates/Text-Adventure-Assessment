package parseExceptions;

public class ActionSubjectsNotPresent extends ParseException {

	public String toString() {
		return "Problem with action command. Your command is either missing a valid subject, or you are not in the\n" +
				"presence of an item you need to carry out this action.\n";
	}
}
