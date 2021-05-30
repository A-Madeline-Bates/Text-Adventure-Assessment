package parseExceptions;

import org.json.simple.JSONArray;

public class ActionSubjectMismatch extends ParseException {
	private JSONArray subjectsArray;

	public ActionSubjectMismatch(JSONArray subjectsArray){
		this.subjectsArray = subjectsArray;
	}

	public String toString() {
		return "Please specify a valid object that can be acted upon with the action word that you used. Valid" +
				"subjects are: " + subjectsArray + "\n";
	}
}
