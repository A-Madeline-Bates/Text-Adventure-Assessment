package cmdClasses;
import dataStorage.Entities;
import dataStorage.PlayerState;
import parseCommands.ParseInvCMD;

public class CMDDrop extends CMDType implements ExecutableCMD {
	private final Entities entities;
	private final ParseInvCMD parsedInventory;

	public CMDDrop(ParseInvCMD parsedInventory, Entities entities, PlayerState playerState){
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
