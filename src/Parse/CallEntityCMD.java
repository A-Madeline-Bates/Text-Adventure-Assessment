package Parse;

import Data.Entities;
import Data.PlayerState;
import ParseExceptions.ArtefactDoesNotExist;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class CallEntityCMD {
	int artefactPosition;

	public CallEntityCMD(Entities entityClass, PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchDot(commandEnd, entityClass, playerState);
	}

	private void searchDot(String commandEnd, Entities entityClass, PlayerState playerState) throws ParseException{
		if(entityClass.returnArtefactPosition(commandEnd, playerState.getCurrentLocation()) != -1){
			//This returns the array position of the artefact we are trying to find.
			this.artefactPosition = entityClass.returnArtefactPosition(commandEnd, playerState.getCurrentLocation());
		} else{
			throw new ArtefactDoesNotExist(commandEnd);
		}
	}

	public int getArtefactPosition(){
		return artefactPosition;
	}
}
