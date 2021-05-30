package cmdClasses;
import data.Entities;
import data.PlayerState;
import parse.ParseInvCommand;

public class CMDDrop extends ExecutableCMD{
	private final Entities entities;
	private final ParseInvCommand parsedInventory;

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
		entities.addObject(droppedObject, droppedObjectDescription, playerState.getCurrentLocation(), "artefacts");
	}

	public String getExitMessage(){
		return "You dropped a " + parsedInventory.getInventoryObject() + ".\n";
	}
}
