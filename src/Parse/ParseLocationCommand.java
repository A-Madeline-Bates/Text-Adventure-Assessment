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
		validateNewLocation(commandEnd, entityClass, playerState);
	}

	private void validateNewLocation(String commandEnd, Entities entityClass, PlayerState playerState) throws ParseException{
		//This find whether the location is in the Dot file
		entityClass.findLocation(commandEnd);
		int searchPosition = entityClass.getLocationCoordinate();
		if(searchPosition != -1){
			//This finds whether there is a path in the dot file attached to the location (i.e, the location is accessible)
			if(entityClass.isLocationAccessible(playerState.getCurrentLocationName(), entityClass.getLocationString())) {
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
