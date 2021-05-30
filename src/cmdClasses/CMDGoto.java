package cmdClasses;
import data.Entities;
import data.PlayerState;
import data.PlayerStore;
import parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD{
	private final ParseLocationCommand parseLocation;
	private final Entities entityClass;
	private final PlayerStore playerStore;

	public CMDGoto(ParseLocationCommand parseLocation, PlayerState playerState, Entities entityClass, PlayerStore playerStore){
		this.parseLocation = parseLocation;
		this.playerState = playerState;
		this.entityClass = entityClass;
		this.playerStore = playerStore;
		execute();
	}

	public void execute() {
		int nextLocation = parseLocation.getNewLocationInt();
		playerState.setCurrentLocation(nextLocation);
		playerState.setCurrentLocName(entityClass.findLocationId(nextLocation));
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		return "You are in " + entityClass.findLocationDesc(currentLocation) +
				"\nYou can see: \n" + entityClass.getEntityString(currentLocation, "artefacts") +
				entityClass.getEntityString(currentLocation, "furniture") +
				entityClass.getEntityString(currentLocation, "characters") +
				playerStore.findOtherPlayers(currentLocation, playerState.getPlayerName()) +
				"You can access from here: " + entityClass.getPathsList(currentLocation);
	}
}
