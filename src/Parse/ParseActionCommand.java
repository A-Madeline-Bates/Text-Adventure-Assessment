package Parse;

import Data.Actions;
import Data.Entities;
import Data.PlayerState;
import ParseExceptions.*;
import Tokeniser.Tokeniser;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ParseActionCommand {
	private List<ActionStore> subjectInformation = new ArrayList<ActionStore>();
	int actionPosition;

	public ParseActionCommand(Actions actionsClass, Entities entityClass, PlayerState playerState, int actionPosition, Tokeniser tokeniser) throws ParseException {
//		initialiseSubjectList();
		String commandEnd = tokeniser.getRemainingTokens();
		validateActionObject(commandEnd, actionPosition, actionsClass, entityClass, playerState);
	}

	private void validateActionObject(String commandEnd, int actionPosition, Actions actionsClass, Entities entityClass, PlayerState playerState) throws ParseException {
		JSONArray subjectsArray = actionsClass.getActionSubjects(actionPosition);
		//check whether subjects are in either the inventory or location
		if(areSubjectsPresent(subjectsArray, playerState, entityClass)) {
			//check whether command end is a subject
			if(isCommandValid(subjectsArray, commandEnd)){
				//this is the position of the action we're using in out json- it's previously been validated in commandFactory
				this.actionPosition = actionPosition;
			}
			else{
				throw new ActionSubjectMismatch(commandEnd);
			}
		}
		else{
			throw new ActionSubjectsNotPresent(subjectsArray);
		}
	}

	private boolean areSubjectsPresent(JSONArray subjectsArray, PlayerState playerState, Entities entityClass){
		for (Object o : subjectsArray) {
			String object = (String) o;
			if (!checkForFurniture(entityClass, playerState, object)) {
				if (!checkForArtefacts(entityClass, playerState, object)) {
					if (!checkForInventory(playerState, object)) {
						return false;
					}
				}
			}
		}
		//If the loop never returns false, we can conclude that all subjects are present
		return true;
	}

	private boolean checkForFurniture(Entities entityClass, PlayerState playerState, String object){
		int position = entityClass.returnFurniturePosition(object, playerState.getCurrentLocation());
		if(position != -1){
			addToSubjectArray(entityClass.getArtefactId(), position, "furniture");
			return true;
		}
		return false;
	}

	private boolean checkForArtefacts(Entities entityClass, PlayerState playerState, String object){
		int position = entityClass.returnArtefactPosition(object, playerState.getCurrentLocation());
		if(position != -1){
			addToSubjectArray(entityClass.getArtefactId(), position, "artefacts");
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
		subjectEntry.setSubjectName(subjectName);
		subjectEntry.setPosition(subjectPosition);
		subjectEntry.setLocationType(locationType);
		System.out.println(subjectEntry.getSubjectName() + " * " + subjectEntry.getPosition() + " * " + subjectEntry.getLocationType());
		this.subjectInformation.add(subjectEntry);
	}

	private boolean isCommandValid(JSONArray subjectsArray, String commandEnd) {
		for(int i=0; i<subjectsArray.size(); i++){
			if(subjectsArray.get(i).equals(commandEnd)){
				return true;
			}
		}
		return false;
	}

	public int getActionPosition(){
		return actionPosition;
	}

	public int getSubjectPosition(String subject){
		for(int i=0; i<subjectInformation.size(); i++){
			if(subjectInformation.get(i).getSubjectName().equalsIgnoreCase(subject)){
				return subjectInformation.get(i).getPosition();
			}
		}
		//THIS SHOULDN'T HAPPEN- MAKE MORE ELEGANT?
		return -1;
	}

	public String getSubjectLocationType(String subject){
		for(int i=0; i<subjectInformation.size(); i++){
			if(subjectInformation.get(i).getSubjectName().equalsIgnoreCase(subject)){
				return subjectInformation.get(i).getLocationType();
			}
		}
		//THIS SHOULDN'T HAPPEN- MAKE MORE ELEGANT?
		return "";
	}

//	private void initialiseSubjectList(){
//		ActionStore subjectEntry = new ActionStore();
//		this.subjectInformation.add(subjectEntry);
//	}
}
