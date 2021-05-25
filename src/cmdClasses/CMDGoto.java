package cmdClasses;

import data.Entities;
import data.PlayerState;
import parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD implements CMDType{
	final ParseLocationCommand parseLocation;
	final Entities entityClass;

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
		return "You are in " + entityClass.getLocationName(currentLocation) +
				"\nYou can see: " + entityClass.getEntityString(currentLocation, "artefacts") +
				entityClass.getEntityString(currentLocation, "furniture") +
				entityClass.getEntityString(currentLocation, "characters") +
				"You can access from here: " + entityClass.getPaths(currentLocation);
	}
}
