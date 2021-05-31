package parseCommands;
import dataStorage.PlayerState;
import parseExceptions.NotInInventory;

import java.util.ArrayList;

public class ParseInvCMD {
	private String inventoryObject;
	private String inventoryDescription;
	private int inventoryPosition;

	public ParseInvCMD(PlayerState playerState, ArrayList<String> commandList) throws NotInInventory {
		searchInventory(playerState, commandList);
	}

	private void searchInventory(PlayerState playerState, ArrayList<String> commandList) throws NotInInventory {
		//Search every term in the command to see if it matches with the inventory
		for(String singleToken : commandList) {
			int inventoryPosition = playerState.findInInventory(singleToken);
			if (inventoryPosition != -1) {
				this.inventoryObject = playerState.getInvObject(inventoryPosition);
				this.inventoryDescription = playerState.getInvDesc(inventoryPosition);
				this.inventoryPosition = inventoryPosition;
				//If an object is found in the inventory, stop searching
				return;
			}
		}
		//If no words match, throw an error
		throw new NotInInventory();
	}

	public String getInvObject(){
		return inventoryObject;
	}

	public String getInvObjectDesc(){
		return inventoryDescription;
	}

	public int getInvObjectPosition(){
		return inventoryPosition;
	}
}
