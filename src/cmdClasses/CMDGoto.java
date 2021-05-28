package cmdClasses;
import data.Entities;
import data.PlayerState;
import data.PlayerStore;
import parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD{
	final ParseLocationCommand parseLocation;
	final Entities entityClass;
	final PlayerStore playerStore;

	public CMDGoto(ParseLocationCommand parseLocation, PlayerState playerState, Entities entityClass, PlayerStore playerStore){
		this.parseLocation = parseLocation;
		this.playerState = playerState;
		this.entityClass = entityClass;
		this.playerStore = playerStore;
		execute();
	}

	public void execute() {
		int nextLocation = parseLocation.getNewLocationPosition();
		playerState.setCurrentLocation(nextLocation);
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		return "You are in " + entityClass.findLocationDescription(currentLocation) +
				"\nYou can see: " + entityClass.getEntityString(currentLocation, "artefacts") +
				entityClass.getEntityString(currentLocation, "furniture") +
				entityClass.getEntityString(currentLocation, "characters") +
				playerStore.findOtherPlayers(currentLocation, playerState.getPlayerName()) +
				"You can access from here: " + entityClass.getPathsList(currentLocation);
	}
}
