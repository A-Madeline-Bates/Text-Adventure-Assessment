package cmdClasses;
import data.Actions;
import data.Entities;
import data.PlayerState;
import parse.ParseActionCommand;
import org.json.simple.JSONArray;

public class CMDAction extends ExecutableCMD {
	private final ParseActionCommand parseAction;
	private final Actions actionClass;
	private final Entities entityClass;

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
		JSONArray subjectsArray = actionClass.getActionElement(actionPosition, "consumed");
		for (Object obj : subjectsArray) {
			String object = (String) obj;
			if(object.equalsIgnoreCase("health")){
				playerState.removeFromHealth();
			}
			else if(isItLocation(object)){
				entityClass.removePath(playerState.getCurrentLocName(), entityClass.getLocationResultId());
			}
			else {
				//Otherwise it must be an object
				consumeSubject(object);
			}
		}
	}

	private void consumeSubject(String object) {
		String locationType = parseAction.getSubjectStore(object);
		if (locationType.equalsIgnoreCase("inventory")) {
			//get position in inventory
			int invObjectPosition = parseAction.getSubjectPosition(object);
			playerState.consumeFromInv(invObjectPosition);
		}
		//otherwise must be furniture, artefact or character
		else {
			removeFromLocation(object, locationType);
		}
	}

	private void removeFromLocation(String object, String locationType){
		int objectPosition = parseAction.getSubjectPosition(object);
		int mapLocation = playerState.getCurrentLocation();
		entityClass.removeObject(mapLocation, objectPosition, locationType);
	}

	private boolean isItLocation(String object){
		entityClass.searchLocations(object);
		int locationPosition = entityClass.getLocationResultInt();
		if(locationPosition != -1){
			return true;
		}
		return false;
	}

	/********************************************************
	 *******************   CREATE ITEMS   *******************
	 ********************************************************/

	private void createItems(int actionPosition){
		JSONArray subjectsArray = actionClass.getActionElement(actionPosition, "produced");
		for (Object obj : subjectsArray) {
			String object = (String) obj;
			if(object.equalsIgnoreCase("health")){
				playerState.addToHealth();
			}
			else if(isItLocation(object)){
				//getNewLocationString() was set up by us calling findNewLocation earlier. We are using it rather than
				//object to assure we're using id and not description
				entityClass.createPath(playerState.getCurrentLocName(), entityClass.getLocationResultId());
			}
			else {
				//Otherwise it must be an object- we will need to find this and move it in the entity file
				addToLocation(object);
			}
		}
	}

	private void addToLocation(String object){
		//The produced item should be in the 'unplaced' items or somewhere else in the .dot file- we're not
		// obliged to check for this, so we're assuming it is there.
		int locationQuantity = entityClass.findLocQuantity();
		for(int i=0; i<locationQuantity; i++){
			//Check all three entity types
			findInEntities(i, object, "furniture");
			findInEntities(i, object, "artefacts");
			findInEntities(i, object, "characters");
			//If nothing is found, this would be an error with input file, which we are not obliged to catch.
			// Therefore we will do nothing.
		}
	}

	private void findInEntities(int i, String object, String locationType){
		int objectLocation = entityClass.entitySearch(object, i, locationType);
		if(objectLocation != -1){
			//These are both set by calling entitySearch
			String id = entityClass.getEntityId();
			String description = entityClass.getEntityDescription();
			//add object to current location
			entityClass.addObject(id, description, playerState.getCurrentLocation(), locationType);
			//remove object from previous location- i is the relevant location, and objectLocation is the array
			//of the object
			entityClass.removeObject(i, objectLocation, locationType);
		}
	}

	/********************************************************
	 ********************   EXIT MESSAGE   *******************
	 ********************************************************/

	public String getExitMessage(){
		return actionClass.getMessage(parseAction.getActionPosition()) + "\n";
	}
}
