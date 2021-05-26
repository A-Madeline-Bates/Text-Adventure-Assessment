package parse;
import data.Entities;
import data.PlayerState;
import parseExceptions.LocationDoesNotExist;
import parseExceptions.LocationIsNotAccessible;
import parseExceptions.ParseException;
import tokeniser.Tokeniser;

public class ParseLocationCommand {
	private int locationPosition;

	public ParseLocationCommand(Entities entityClass, PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		validateNewLocation(commandEnd, entityClass, playerState);
	}

	private void validateNewLocation(String commandEnd, Entities entityClass, PlayerState playerState) throws LocationIsNotAccessible, LocationDoesNotExist {
		//This find whether the location is in the Dot file
		entityClass.locationSearch(commandEnd);
		int searchPosition = entityClass.getLocationResultInt();
		if(searchPosition != -1){
			//This finds whether there is a path in the dot file attached to the location (i.e, the location is accessible)
			if(entityClass.isLocationAccessible(playerState.getCurrentLocationName(), entityClass.getLocationResultId())) {
				//This returns the array position of the artefact we are trying to find.
				this.locationPosition = searchPosition;
			} else{
				throw new LocationIsNotAccessible(commandEnd);
			}
		} else{
			throw new LocationDoesNotExist(commandEnd);
		}
	}

	public int getNewLocationPosition(){
		return locationPosition;
	}
}
