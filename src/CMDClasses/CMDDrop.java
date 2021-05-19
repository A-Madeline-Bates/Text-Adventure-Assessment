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
		execute();
	}

	public void execute() {
		String droppedObject = parsedInventory.getInventoryObject();
		String droppedObjectDescription = parsedInventory.getInventoryObjectDescription();
		playerState.consumedFromInventory(parsedInventory.getInventoryObjectPosition());
		entities.addObjectToLocation(droppedObject, droppedObjectDescription, playerState.getCurrentLocation());
	}

	public String getExitMessage(){
		return "You dropped a " + parsedInventory.getInventoryObject() + ".";
	}
}
