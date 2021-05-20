package Parse;

import Data.Actions;
import Data.Entities;
import Data.PlayerState;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;
import org.json.simple.JSONArray;

public class ParseActionCommand {
	int actionPosition;

	public ParseActionCommand(Actions actionsClass, Entities entityClass, PlayerState playerState, int actionPosition, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		validateActionObject(commandEnd, actionPosition, actionsClass, entityClass, playerState);
	}

	private void validateActionObject(String commandEnd, int actionPosition, Actions actionsClass, Entities entityClass, PlayerState playerState){
		JSONArray subjectsArray = actionsClass.getActionSubjects(actionPosition);
		//check whether subjects are in either the inventory or location
		if(areSubjectsPresent(subjectsArray, playerState, entityClass)) {
			//check whether command end is a subject
			if(isCommandValid(subjectsArray, commandEnd)){

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

	}

	private boolean isCommandValid(JSONArray subjectsArray, String commandEnd){

	}

	public int getActionPosition(){
		return actionPosition;
	}
}
