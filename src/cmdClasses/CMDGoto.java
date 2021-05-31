package cmdClasses;
import data.Entities;
import data.PlayerState;
import data.PlayerStore;
import parse.ParseLocationCMD;

public class CMDGoto extends ExecutableCMD{
	private final ParseLocationCMD parseLocation;
	private final Entities entities;
	private final PlayerStore playStore;

	public CMDGoto(ParseLocationCMD parseLocation, PlayerState playerState, Entities entities, PlayerStore playStore){
		this.parseLocation = parseLocation;
		this.playerState = playerState;
		this.entities = entities;
		this.playStore = playStore;
		execute();
	}

	public void execute() {
		int nextLocation = parseLocation.getNewLocationInt();
		playerState.setCurrentLocation(nextLocation);
		playerState.setCurrentLocName(entities.findLocationId(nextLocation));
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		return "You are in " + entities.findLocationDesc(currentLocation) +
				"\nYou can see: \n" + entities.getEntityString(currentLocation, "artefacts") +
				entities.getEntityString(currentLocation, "furniture") +
				entities.getEntityString(currentLocation, "characters") +
				playStore.findOtherPlayers(currentLocation, playerState.getPlayerName()) +
				"You can access from here: " + entities.getPathsList(currentLocation);
	}
}
