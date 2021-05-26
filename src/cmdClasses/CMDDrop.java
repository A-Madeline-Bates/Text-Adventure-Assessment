package cmdClasses;
import data.Entities;
import data.PlayerState;
import parse.ParseInvCommand;

public class CMDDrop extends ExecutableCMD implements CMDType{
	final Entities entities;
	final ParseInvCommand parsedInventory;

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
		entities.addArtefact(droppedObject, droppedObjectDescription, playerState.getCurrentLocation());
	}

	public String getExitMessage(){
		return "You dropped a " + parsedInventory.getInventoryObject() + ".\n";
	}
}
