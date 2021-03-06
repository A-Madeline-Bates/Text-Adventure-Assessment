import dataStorage.Entities;
import dataStorage.PlayerState;

public class CheckForDeath {
	private boolean isDead;

	public CheckForDeath(Entities entityClass, PlayerState playerState){
		this.isDead = false;
		if(playerState.getHealthLevel()<=0){
			dropAllItems(entityClass, playerState);
			returnToStart(entityClass, playerState);
			playerState.returnFullHealth();
			this.isDead = true;
		}
	}

	private static void dropAllItems(Entities entityClass, PlayerState playerState){
		int invSize = playerState.getInvSize();
		for(int i=(invSize-1); i>=1; i--){
			String objectID = playerState.getInvObject(i);
			String objectDesc = playerState.getInvDesc(i);
			playerState.consumeFromInv(i);
			entityClass.addObject(objectID, objectDesc, playerState.getCurrentLocation(), "artefacts");
		}
	}

	private static void returnToStart(Entities entityClass, PlayerState playerState){
		playerState.setCurrentLocation(0);
		//Set to name of first location
		playerState.setCurrentLocName(entityClass.findLocationId(0));
	}

	public String getExitMessage(){
		if(isDead){
			return "Oh no! Health is zero. You have died and lose all of your items, \nreturn to the start.\n";
		}
		//Not dead- return no message
		else{
			return "";
		}
	}
}
