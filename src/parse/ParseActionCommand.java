package parse;

import data.Actions;
import data.Entities;
import data.PlayerState;
import parseExceptions.*;
import tokeniser.Tokeniser;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ParseActionCommand {
	final List<ActionStore> subjectInformation = new ArrayList<ActionStore>();
	private int actionPosition;

	public ParseActionCommand(Actions actionsClass, Entities entityClass, PlayerState playerState, ArrayList<Integer> actionPositions, ArrayList<String> commandList) throws ParseException {
		validateActionObject(commandList, actionPositions, actionsClass, entityClass, playerState);
	}

	private void validateActionObject(ArrayList<String> commandList, ArrayList<Integer> actionPositions, Actions actionsClass, Entities entityClass, PlayerState playerState) throws ActionSubjectMismatch, ActionSubjectsNotPresent {
		//cycle through possible actionPositions and check that the subjects we would need to execute that action
		// are present. If yes, check whether one the relevant subjects are mentioned somewhere in the command
		for(int actionPosition : actionPositions) {
			JSONArray subjectsArray = actionsClass.getActionElement(actionPosition, "subjects");
			//check whether subjects are in either the inventory or location
			if (areSubjectsPresent(subjectsArray, playerState, entityClass)) {
				if(isCommandValid(subjectsArray, commandList, actionPosition)){
					return;
				}
			}
		}
		//if we don't find an action position that returns true for areSubjectsPresent and checkCommandValidity,
		//throw an error
		throw new ActionSubjectsNotPresent();
	}

	private boolean isCommandValid(JSONArray subjectsArray, ArrayList<String> commandList, int actionPosition) throws ActionSubjectMismatch {
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

	private boolean areSubjectsPresent(JSONArray subjectsArray, PlayerState playerState, Entities entityClass){
		for (Object o : subjectsArray) {
			String object = (String) o;
			if(!checkEntityTypes(playerState, entityClass, object)){
				return false;
			}
		}
		//If the loop never returns false, we can conclude that all subjects are present
		return true;
	}

	private boolean checkEntityTypes(PlayerState playerState, Entities entityClass, String object){
		if (!checkForEntity(entityClass, playerState, object, "artefacts")) {
			if (!checkForEntity(entityClass, playerState, object, "furniture")) {
				if(!checkForEntity(entityClass, playerState, object, "characters")) {
					if (!checkForInventory(playerState, object)) {
						return false;
					}
				}
			}
		}
		//In this instance, true means 'we haven't found an issue yet'
		return true;
	}

	private boolean checkForEntity(Entities entityClass, PlayerState playerState, String object, String objectType){
		int position = entityClass.entitySearch(object, playerState.getCurrentLocation(), objectType);
		if(position != -1){
			addToSubjectArray(entityClass.getEntityId(), position, objectType);
			return true;
		}
		return false;
	}

	private boolean checkForInventory(PlayerState playerState, String object){
		int position = playerState.findInInventory(object);
		if(position != -1){
			addToSubjectArray(playerState.getInventoryObject(position), position, "inventory");
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
		//If we reach this point there has been an error in our files- however, we're not required to catch these errors
		return -1;
	}

	public String getSubjectLocationType(String subject) {
		for (ActionStore actionStore : subjectInformation) {
			if (actionStore.getSubjectName().equalsIgnoreCase(subject)) {
				//get data storage type (i.e artefact, furniture, inventory &c)
				return actionStore.getLocationType();
			}
		}
		//If we reach this point there has been an error in our files- however, we're not required to catch these errors
		return "";
	}
}
