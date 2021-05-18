package CMDClasses;
import Data.Entities;
import Data.PlayerState;

public class CMDLook extends CMDState implements CMDType{
	Entities entityClass;

	public CMDLook(Entities entityClass, PlayerState playerState){
		this.entityClass = entityClass;
		this.playerState = playerState;
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
