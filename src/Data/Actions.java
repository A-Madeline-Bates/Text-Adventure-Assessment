package Data;

import CMDClasses.CMDAction;
import Parse.ParseActionCommand;
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
}
