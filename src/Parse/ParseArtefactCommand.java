package Parse;

import Data.Entities;
import Data.PlayerState;
import ParseExceptions.ArtefactDoesNotExist;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class ParseArtefactCommand {
	int artefactPosition;
	String artefactName;

	public ParseArtefactCommand(Entities entityClass, PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchDot(commandEnd, entityClass, playerState);
	}

	private void searchDot(String commandEnd, Entities entityClass, PlayerState playerState) throws ParseException{
		int loc = playerState.getCurrentLocation();
		int searchPosition = entityClass.returnArtefactPosition(commandEnd, loc);
		if(searchPosition != -1){
			//This returns the array position of the artefact we are trying to find.
			this.artefactPosition = searchPosition;
			this.artefactName = commandEnd;
		} else{
			throw new ArtefactDoesNotExist(commandEnd);
		}
	}

	public int getArtefactPosition(){
		return artefactPosition;
	}

	public String getArtefactName(){
		return artefactName;
	}
}
