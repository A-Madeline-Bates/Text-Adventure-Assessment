package parse;
import data.Entities;
import data.PlayerState;
import parseExceptions.LocationDoesNotExist;
import parseExceptions.LocationIsNotAccessible;

import java.util.ArrayList;

public class ParseLocationCommand {
	private int locationPosition;

	public ParseLocationCommand(Entities entityClass, PlayerState playerState, ArrayList<String> commandList) throws LocationIsNotAccessible, LocationDoesNotExist {
		validateNewLocation(commandList, entityClass, playerState);
	}

	private void validateNewLocation(ArrayList<String> commandList, Entities entityClass, PlayerState playerState) throws LocationIsNotAccessible, LocationDoesNotExist {
		//Search every term in the command to see if it matches with the locations
		for(String singleToken : commandList) {
			//This find whether the location is in the Dot file
			entityClass.searchLocations(singleToken);
			int searchPosition = entityClass.getLocationResultInt();
			if (searchPosition != -1) {
				checkAccessibility(singleToken, searchPosition, entityClass, playerState);
				//If an location is found, stop searching
				return;
			}
		}
		//If no locations match, throw an error
		throw new LocationDoesNotExist();
	}

	private void checkAccessibility(String singleToken, int searchPosition, Entities entityClass, PlayerState playerState) throws LocationIsNotAccessible {
		//This finds whether there is a path in the dot file attached to the location (i.e, the location is accessible)
		if (entityClass.isLocationAccessible(playerState.getCurrentLocName(),  entityClass.getLocationResultId())) {
			//This returns the array position of the artefact we are trying to find.
			this.locationPosition = searchPosition;
		} else {
			throw new LocationIsNotAccessible(singleToken);
		}
	}

	public int getNewLocationInt(){
		return locationPosition;
	}
}
