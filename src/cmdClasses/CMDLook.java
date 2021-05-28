package cmdClasses;
import data.Entities;
import data.PlayerState;
import data.PlayerStore;

public class CMDLook extends CMDType{
	final Entities entityClass;
	private final PlayerStore playerStore;

	public CMDLook(Entities entityClass, PlayerState playerState, PlayerStore playerStore){
		this.entityClass = entityClass;
		this.playerState = playerState;
		this.playerStore = playerStore;
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		return "You are in " + entityClass.findLocationDescription(currentLocation) +
				"\nYou can see: " + entityClass.getEntityString(currentLocation, "artefacts") +
				entityClass.getEntityString(currentLocation, "furniture") +
				entityClass.getEntityString(currentLocation, "characters") +
				playerStore.findOtherPlayers(currentLocation) +
		"You can access from here: " + entityClass.getPathsList(currentLocation);
	}
}
