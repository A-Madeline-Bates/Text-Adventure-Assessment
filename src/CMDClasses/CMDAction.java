package CMDClasses;
import Data.Actions;
import Data.Entities;
import Data.PlayerState;
import Parse.ParseActionCommand;
import org.json.simple.JSONArray;

public class CMDAction extends ExecutableCMD implements CMDType {
	ParseActionCommand parseAction;
	Actions actionClass;
	Entities entityClass;

	public CMDAction(ParseActionCommand parseAction, Actions actionClass, Entities entityClass, PlayerState playerState){
		this.parseAction = parseAction;
		this.actionClass = actionClass;
		this.entityClass = entityClass;
		this.playerState = playerState;
		execute();
	}

	public void execute() {
		int actionPosition = parseAction.getActionPosition();
		consumeItems(actionPosition);
		createItems(actionPosition);
	}

	/********************************************************
	 *******************   CONSUME ITEMS   *******************
	 ********************************************************/

	private void consumeItems(int actionPosition){
		JSONArray subjectsArray = actionClass.getConsumed(actionPosition);
		for (Object o : subjectsArray) {
			String object = (String) o;
			if(object.equalsIgnoreCase("health")){
				playerState.removeFromHealth();
			}
			else {
				consumeSubject(object);
			}
		}
	}

	//this could be a switch?
	private void consumeSubject(String object){
		String locationType = parseAction.getSubjectLocationType(object);
		if (locationType.equalsIgnoreCase("inventory")) {
			int invObjectPosition = parseAction.getSubjectPosition(object);
			playerState.consumedFromInventory(invObjectPosition);
		}
		//otherwise must be furniture, artefact or character
		else {
			removeFromLocation(object, locationType);
		}
	}

	private void removeFromLocation(String object, String locationType){
		int objectPosition = parseAction.getSubjectPosition(object);
		int mapLocation = parseAction.getSubjectPosition(object);
		entityClass.removeObjectFromLocation(mapLocation, objectPosition, locationType);
	}

	/********************************************************
	 *******************   CREATE ITEMS   *******************
	 ********************************************************/

	private void createItems(int actionPosition){
		JSONArray subjectsArray = actionClass.getProduced(actionPosition);
		for (Object o : subjectsArray) {
			String object = (String) o;
			//THIS IS A WEIRD WAY OF GOING ABOUT THINGS
			entityClass.findNewLocation(object);
			int locationPosition = entityClass.getNewLocationCoordinate();
			//
			if(object.equalsIgnoreCase("health")){
				playerState.addToHealth();
			}
			//if locationPosition doesn't return -1, it must be a location
			else if(locationPosition != -1){
				//getNewLocationString() was set up by us calling findNewLocation earlier. We are using it rather than
				//object to assure we're using id and not description
				entityClass.createPath(playerState.getCurrentLocationName(), entityClass.getNewLocationString());
			}
			//must be an object
			//MAYBE CHANGE THIS
			else {
				//There are no descriptions in the json, so the description is just the name of the object
				playerState.addToInventory(object, object);
			}
		}
	}

	/********************************************************
	 ********************   EXIT MESSAGE   *******************
	 ********************************************************/

	public String getExitMessage(){
		return actionClass.getMessage(parseAction.getActionPosition());
	}
}
