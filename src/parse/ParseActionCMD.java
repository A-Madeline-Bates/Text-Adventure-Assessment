package parse;

import data.Actions;
import data.Entities;
import data.PlayerState;
import parseExceptions.*;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ParseActionCMD {
	private final List<ActionStore> subjectInformation = new ArrayList<ActionStore>();
	private int actionPosition;

	public ParseActionCMD(Actions actions, Entities entities, PlayerState playerState, ArrayList<Integer> actionArr,
						  ArrayList<String> commandList) throws ActionSubjectErr {
		validateAction(commandList, actionArr, actions, entities, playerState);
	}

	private void validateAction(ArrayList<String> commandList, ArrayList<Integer> actionArr, Actions actions,
								Entities entities, PlayerState playerState) throws ActionSubjectErr {
		//cycle through possible actionArr and check that the subjects we would need to execute that action
		// are present. If yes, check whether one the relevant subjects are mentioned somewhere in the command
		for(int actionPosition : actionArr) {
			JSONArray subjectsArray = actions.getActionElement(actionPosition, "subjects");
			//check whether subjects are in either the inventory or location
			if (areSubjectsPresent(subjectsArray, playerState, entities)) {
				if(isCommandValid(subjectsArray, commandList, actionPosition)){
					return;
				}
			}
		}
		//if we don't find an action position that returns true for areSubjectsPresent and checkCommandValidity,
		//throw an error
		throw new ActionSubjectErr();
	}

	private boolean isCommandValid(JSONArray subjectsArray, ArrayList<String> commandList, int actionPosition) {
		for(String singleToken : commandList) {
			//check whether the token we're looking at is a subject
			if (isCommandValid(subjectsArray, singleToken)) {
				//this is the position of the action we're using in our json- it's previously been validated
				// in commandFactory
				this.actionPosition = actionPosition;
				//If a usable object is found, stop searching
				return true;
			}
		}
		return false;
	}

	private boolean areSubjectsPresent(JSONArray subjectsArray, PlayerState playerState, Entities entities){
		for (Object o : subjectsArray) {
			String object = (String) o;
			if(!checkEntityTypes(playerState, entities, object)){
				return false;
			}
		}
		//If the loop never returns false, we can conclude that all subjects are present
		return true;
	}

	private boolean checkEntityTypes(PlayerState playerState, Entities entities, String object){
		if (!checkForEntity(entities, playerState, object, "artefacts")) {
			if (!checkForEntity(entities, playerState, object, "furniture")) {
				if(!checkForEntity(entities, playerState, object, "characters")) {
					if (!checkForInventory(playerState, object)) {
						return checkForLocation(entities, playerState, object);
					}
				}
			}
		}
		//In this instance, true means 'we haven't found an issue yet'
		return true;
	}

	private boolean checkForLocation(Entities entities, PlayerState playerState, String object){
		//This find whether the location is in the Dot file
		entities.searchLocations(object);
		int searchPosition = entities.getLocationResultInt();
		if (searchPosition != -1) {
			if (entities.isLocationAccessible(playerState.getCurrentLocName(), entities.getLocationResultId())) {
				//In this instance saving the location's information isn't very useful, because when we come to
				// executing we'll work with paths and not locations, but we'll save the information for consistency
				// anyway
				addToSubjectArray(entities.getEntityId(), searchPosition, "location");
				return true;
			}
		}
		return false;
	}

	private boolean checkForEntity(Entities entities, PlayerState playerState, String object, String objectType){
		int position = entities.entitySearch(object, playerState.getCurrentLocation(), objectType);
		if(position != -1){
			addToSubjectArray(entities.getEntityId(), position, objectType);
			return true;
		}
		return false;
	}

	private boolean checkForInventory(PlayerState playerState, String object){
		int position = playerState.findInInventory(object);
		if(position != -1){
			addToSubjectArray(playerState.getInvObject(position), position, "inventory");
			return true;
		}
		return false;
	}

	private void addToSubjectArray(String subjectName, int subjectPosition, String locationType){
		ActionStore subjectEntry = new ActionStore();
		//Id of the subject
		subjectEntry.setSubjectName(subjectName);
		//Position in whatever data storage type it's in (i.e artefact, furniture, inventory &c)
		subjectEntry.setPosition(subjectPosition);
		//Data storage type (i.e artefact, furniture, inventory &c)
		subjectEntry.setLocationType(locationType);
		this.subjectInformation.add(subjectEntry);
	}

	private static boolean isCommandValid(JSONArray subjectsArray, String commandEnd) {
		for (Object o : subjectsArray) {
			String subject = (String) o;
			if (subject.equalsIgnoreCase(commandEnd)) {
				return true;
			}
		}
		return false;
	}

	public int getActionPosition(){
		return actionPosition;
	}

	public int getSubjectPosition(String subject){
		for (ActionStore actionStore : subjectInformation) {
			if (actionStore.getSubjectName().equalsIgnoreCase(subject)) {
				//Position in whatever data storage type it's in (i.e artefact, furniture, inventory &c)
				return actionStore.getPosition();
			}
		}
		//If we reach this point there has been an error in our files- however, we're not required to
		// catch these errors
		return -1;
	}

	public String getSubjectStore(String subject) {
		for (ActionStore actionStore : subjectInformation) {
			if (actionStore.getSubjectName().equalsIgnoreCase(subject)) {
				//get data storage type (i.e artefact, furniture, inventory &c)
				return actionStore.getLocationType();
			}
		}
		//If we reach this point there has been an error in our files- however, we're not required to
		// catch these errors
		return "";
	}
}
