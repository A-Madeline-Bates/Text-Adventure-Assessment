import data.Entities;
import data.PlayerState;

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
		//i is one due to a stupid inventory quirk- fix !!!!!
		for(int i=(invSize-1); i>=1; i--){
			String droppedObject = playerState.getInvObject(i);
			String droppedObjectDesc = playerState.getInvDesc(i);
			playerState.consumeFromInv(i);
			entityClass.addObject(droppedObject, droppedObjectDesc, playerState.getCurrentLocation(), "artefacts");
		}
	}

	private static void returnToStart(Entities entityClass, PlayerState playerState){
		playerState.setCurrentLocation(0);
		playerState.setCurrentLocName(entityClass.findFirstLocation());
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
