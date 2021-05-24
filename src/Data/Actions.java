package Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Actions {
	JSONArray actions;

	public Actions(JSONArray actions) {
		this.actions = actions;
	}

	public int findAction(String nextToken){
		for(int i=0; i<actions.size(); i++){
			JSONObject jsonAction = (JSONObject) actions.get(i);
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for (int j=0; j<actionTriggers.size(); j++) {
				String trigger = (String) actionTriggers.get(j);
				if (nextToken.equals(trigger)) {
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

	public JSONArray getActionSubjects(int position){
		JSONObject jsonAction = (JSONObject) actions.get(position);
		return (JSONArray) jsonAction.get("subjects");
	}

	public JSONArray getConsumed(int position){
		JSONObject jsonAction = (JSONObject) actions.get(position);
		return (JSONArray) jsonAction.get("consumed");
	}

	public JSONArray getProduced(int position){
		JSONObject jsonAction = (JSONObject) actions.get(position);
		return (JSONArray) jsonAction.get("produced");
	}
}
