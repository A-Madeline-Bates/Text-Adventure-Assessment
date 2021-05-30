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
		String droppedObject = parsedInventory.getInvObject();
		String droppedObjectDesc = parsedInventory.getInvObjectDesc();
		playerState.consumeFromInv(parsedInventory.getInvObjectPosition());
		entities.addObject(droppedObject, droppedObjectDesc, playerState.getCurrentLocation(), "artefacts");
	}

	public String getExitMessage(){
		return "You dropped a " + parsedInventory.getInvObject() + ".\n";
	}
}
