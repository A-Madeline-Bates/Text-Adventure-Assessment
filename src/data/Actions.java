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
			//-2 used to specify nothing found yet
			int searchPosition = searchTriggers(actionTriggers, nextToken, i);
			if(searchPosition != -2){
				return searchPosition;
			}
		}
		//-1 used to specify nothing found as the final result
		return -1;
	}

	private int searchTriggers(JSONArray actionTriggers, String nextToken, int i){
		for (Object actionTrigger : actionTriggers) {
			String trigger = (String) actionTrigger;
			if (nextToken.equalsIgnoreCase(trigger)) {
				return i;
			}
		}
		//-2 used to specify nothing found yet
		return -2;
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
