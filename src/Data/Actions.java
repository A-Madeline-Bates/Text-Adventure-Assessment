package Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Actions {
	JSONArray actions;

	public Actions(JSONArray actions) {
		this.actions = actions;
	}

	public JSONArray getActionArray(){
		return actions;
	}
}
