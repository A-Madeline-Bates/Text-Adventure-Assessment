package parseCommands;
import dataStorage.Entities;
import dataStorage.PlayerState;
import parseExceptions.LocationDoesNotExist;
import parseExceptions.NoPathErr;

import java.util.ArrayList;

public class ParseLocationCMD {
	private int locationPosition;

	public ParseLocationCMD(Entities entities, PlayerState playerState, ArrayList<String> commandList)
			throws NoPathErr, LocationDoesNotExist {
		validateNewLocation(commandList, entities, playerState);
	}

	private void validateNewLocation(ArrayList<String> commandList, Entities entities, PlayerState playerState)
			throws NoPathErr, LocationDoesNotExist {
		//Search every term in the command to see if it matches with the locations
		for(String token : commandList) {
			//This find whether the location is in the Dot file
			entities.searchLocations(token);
			int searchLoc = entities.getLocationResultInt();
			if (searchLoc != -1) {
				canWeAccess(token, searchLoc, entities, playerState);
				//If a location is found, stop searching
				return;
			}
		}
		//If no locations match, throw an error
		throw new LocationDoesNotExist();
	}

	private void canWeAccess(String token,int searchLoc,Entities entities,PlayerState playerState) throws NoPathErr {
		//This finds whether there is a path in the dot file attached to the location (i.e, the location is accessible)
		if (entities.isLocationAccessible(playerState.getCurrentLocName(),  entities.getLocationResultId())) {
			//This returns the array position of the artefact we are trying to find.
			this.locationPosition = searchLoc;
		} else {
			throw new NoPathErr(token);
		}
	}

	public int getNewLocationInt(){
		return locationPosition;
	}
}
