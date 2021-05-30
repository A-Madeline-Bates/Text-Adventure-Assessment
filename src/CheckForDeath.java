import data.Entities;
import data.PlayerState;

public class CheckForDeath {
	private final boolean isDead;

	public CheckForDeath(Entities entityClass, PlayerState playerState){
		if(playerState.getHealthLevel()<=0){
			dropAllItems(entityClass, playerState);
			returnToStart(entityClass, playerState);
			this.isDead = true;
		}
		//Not dead
		else{
			this.isDead = false;
		}
	}

	private void dropAllItems(Entities entityClass, PlayerState playerState){
		int invSize = playerState.getInventorySize();
		for(int i=0; i<invSize; i++){
			String droppedObject = playerState.getInventoryObject(i);
			String droppedObjectDesc = playerState.getInventoryDescription(i);
			playerState.consumedFromInventory(i);
			entityClass.addObject(droppedObject, droppedObjectDesc, playerState.getCurrentLocation(), "artefacts");
		}
	}

	private static void returnToStart(Entities entityClass, PlayerState playerState){
		playerState.setCurrentLocation(0);
		playerState.setCurrentLocationName(entityClass.findFirstLocation());
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
