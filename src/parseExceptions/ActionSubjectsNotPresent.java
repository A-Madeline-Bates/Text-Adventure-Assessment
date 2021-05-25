package parseExceptions;
import org.json.simple.JSONArray;

public class ActionSubjectsNotPresent extends ParseException {
	final JSONArray subjectsArray;
	public ActionSubjectsNotPresent(JSONArray subjectsArray) {
		this.subjectsArray = subjectsArray;
	}

	public String toString() {
		return "You cannot perform this action because one or both of the objects you need are missing. " +
				"The objects needed are " + subjectsArray + ".\n";
	}
}
