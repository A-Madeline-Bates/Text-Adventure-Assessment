package Parse;

import Data.Entities;
import Data.PlayerState;
import ParseExceptions.LocationDoesNotExist;
import ParseExceptions.LocationIsNotAccessible;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class ParseLocationCommand {
	int locationPosition;
	String locationName;

	public ParseLocationCommand(Entities entityClass, PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchDot(commandEnd, entityClass, playerState);
	}

	private void searchDot(String commandEnd, Entities entityClass, PlayerState playerState) throws ParseException{
		//This find whether the location is in the Dot file
		entityClass.findLocation(commandEnd);
		int searchPosition = entityClass.getLocationCoordinate();
		if(searchPosition != -1){
			//This finds whether the path is in the dot file attached to the location (i.e, is accessible)
			if(entityClass.isLocationAccessible(playerState.getCurrentLocationName(), commandEnd)) {
				//This returns the array position of the artefact we are trying to find.
				this.locationPosition = searchPosition;
				this.locationName = commandEnd;
			} else{
				throw new LocationIsNotAccessible(commandEnd);
			}
		} else{
			throw new LocationDoesNotExist(commandEnd);
		}
	}

	public int getLocationPosition(){
		return locationPosition;
	}

	public String getLocationName(){
		return locationName;
	}
}
