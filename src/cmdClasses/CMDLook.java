package cmdClasses;
import dataStorage.Entities;
import dataStorage.PlayerState;
import dataStorage.PlayerStore;

public class CMDLook extends CMDType{
	private final Entities entityClass;
	private final PlayerStore playerStore;

	public CMDLook(Entities entityClass, PlayerState playerState, PlayerStore playerStore){
		this.entityClass = entityClass;
		this.playerState = playerState;
		this.playerStore = playerStore;
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
