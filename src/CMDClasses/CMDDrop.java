package CMDClasses;

import Data.Entities;
import Data.PlayerState;
import Parse.ParseInvCommand;

public class CMDDrop extends ExecutableCMD implements CMDType{
	Entities entities;
	ParseInvCommand parsedInventory;

	public CMDDrop(ParseInvCommand parsedInventory, Entities entities, PlayerState playerState){
		this.entities = entities;
		this.playerState = playerState;
		this.parsedInventory = parsedInventory;
	}

	public void execute() {
//		String droppedObject = parsedInventory.getInventoryObject();
//		playerState.consumedFromInventory(droppedObject);
//		entities.addObjectToLocation(droppedObject);
	}

	public String getExitMessage(){
		return "You dropped a " + parsedInventory.getInventoryObject() + ".";
	}
}
