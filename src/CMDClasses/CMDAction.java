package CMDClasses;
import Data.Actions;
import Parse.ParseActionCommand;
import org.json.simple.JSONArray;

public class CMDAction extends ExecutableCMD implements CMDType {
	ParseActionCommand parseAction;
	Actions actionClass;

	public CMDAction(ParseActionCommand parseAction, Actions actionClass){
		this.parseAction = parseAction;
		this.actionClass = actionClass;
		execute();
	}

	public void execute() {
		int actionPosition = parseAction.getActionPosition();
		consumeItems(actionPosition);
		createItems(actionPosition);
		//consume items
		//create items
			//if not a location or health, put in inventory
	}

	private void consumeItems(int actionPosition){
		JSONArray subjectsArray = actionClass.getConsumed(actionPosition);
		for (Object o : subjectsArray) {
			String object = (String) o;
			String locationType = parseAction.getSubjectLocationType(object);
			if(locationType.equalsIgnoreCase("inventory")){
				System.out.println("Deleting " + object + " from the inventory.");
			}
			else if(locationType.equalsIgnoreCase("artefact")){
				int mapLocation = parseAction.getSubjectPosition(object);
				System.out.println("Deleting " + object + " from artefacts in location no. " + mapLocation);
			}
			//otherwise must be furniture
			else{
				int mapLocation = parseAction.getSubjectPosition(object);
				System.out.println("Deleting " + object + " from furniture in location no. " + mapLocation);
			}
		}
	}

	private void createItems(int actionPosition){
		JSONArray subjectsArray = actionClass.getProduced(actionPosition);
		for (Object o : subjectsArray) {
			String object = (String) o;
			System.out.println("Creating " + object);
		}
	}

	public String getExitMessage(){
		return actionClass.getMessage(parseAction.getActionPosition());
	}
}
