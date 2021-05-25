package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Actions {
	final JSONArray actions;

	public Actions(JSONArray actions) {
		this.actions = actions;
	}

	public int findAction(String nextToken){
		for(int i=0; i<actions.size(); i++){
			JSONObject jsonAction = (JSONObject) actions.get(i);
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for (Object actionTrigger : actionTriggers) {
				String trigger = (String) actionTrigger;
				if (nextToken.equalsIgnoreCase(trigger)) {
					return i;
				}
			}
		}
		return -1;
	}

	public String getMessage(int position){
		JSONObject jsonAction = (JSONObject) actions.get(position);
		return (String) jsonAction.get("narration");
	}

	public JSONArray getActionElement(int position, String elementType){
		JSONObject jsonAction = (JSONObject) actions.get(position);
		return (JSONArray) jsonAction.get(elementType);
	}
}
