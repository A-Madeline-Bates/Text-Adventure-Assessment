package data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class Actions {
	private final JSONArray actions;
	private final ArrayList<Integer> validActions = new ArrayList<Integer>();

	public Actions(JSONArray actions) {
		this.actions = actions;
	}

	public ArrayList<Integer> findAction(int commandPosition, ArrayList<String> commandList){
		for(int i=0; i<actions.size(); i++){
			JSONObject jsonAction = (JSONObject) actions.get(i);
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for (Object actionTrigger : actionTriggers) {
				String trigger = (String) actionTrigger;
				if (commandList.get(commandPosition).equalsIgnoreCase(trigger)) {
					//If we've found a valid action position, add it to an array
					validActions.add(i);
				}
				//Action commands can be composed of two words. This is checking if that is the case.
				else if(itItTwoPartAction(trigger, commandPosition, commandList)){
					validActions.add(i);
				}
			}
		}
		return validActions;
	}

	private static boolean itItTwoPartAction(String trigger, int commandPosition, ArrayList<String> commandList){
		//This test is to stop us going over the end of the array. If i+1>commandList.size()-1 (the final array
		// position), that would cause an error. Therefore i must be i<=commandList.size()-2.
		if(commandPosition > (commandList.size() - 2)){
			return false;
		}
		String comparisonString = commandList.get(commandPosition) + " " + commandList.get(commandPosition+1);
		if(comparisonString.equals(trigger)) {
			return true;
		}
		return false;
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
