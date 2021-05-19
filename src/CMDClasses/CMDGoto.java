package CMDClasses;

import Data.Entities;
import Data.PlayerState;
import Parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD implements CMDType{
	ParseLocationCommand parseLocation;
	Entities entityClass;

	public CMDGoto(ParseLocationCommand parseLocation, PlayerState playerState, Entities entityClass){
		this.parseLocation = parseLocation;
		this.playerState = playerState;
		this.entityClass = entityClass;
		execute();
	}

	public void execute() {
		int nextLocation = parseLocation.getNewLocationPosition();
		playerState.setCurrentLocation(nextLocation);
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		String exitMessage =
				"You are in " + entityClass.getLocationName(currentLocation) +
						"\nYou can see: " + entityClass.getLocationAttributes(currentLocation) +
						"You can access from here: " + entityClass.getPaths(currentLocation);
		return exitMessage;
	}
}
