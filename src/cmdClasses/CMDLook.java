package cmdClasses;
import data.Entities;
import data.PlayerState;

public class CMDLook extends CMDState implements CMDType{
	final Entities entityClass;

	public CMDLook(Entities entityClass, PlayerState playerState){
		this.entityClass = entityClass;
		this.playerState = playerState;
	}

	public String getExitMessage(){
		int currentLocation = playerState.getCurrentLocation();
		return "You are in " + entityClass.findLocationDescription(currentLocation) +
				"\nYou can see: " + entityClass.getEntityString(currentLocation, "artefacts") +
				entityClass.getEntityString(currentLocation, "furniture") +
				entityClass.getEntityString(currentLocation, "characters") +
		"You can access from here: " + entityClass.getPathsList(currentLocation);
	}
}
