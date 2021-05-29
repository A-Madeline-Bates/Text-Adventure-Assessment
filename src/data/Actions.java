package data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class Actions {
	final JSONArray actions;
	private ArrayList<Integer> validActions = new ArrayList<Integer>();

	public Actions(JSONArray actions) {
		this.actions = actions;
	}

	public ArrayList<Integer> findAction(String nextToken){
		for(int i=0; i<actions.size(); i++){
			JSONObject jsonAction = (JSONObject) actions.get(i);
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for (Object actionTrigger : actionTriggers) {
				String trigger = (String) actionTrigger;
				if (nextToken.equalsIgnoreCase(trigger)) {
					//If we've found a valid action position, add it to an array
					validActions.add(i);
				}
			}
		}
		return validActions;
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
