package parse;
import data.PlayerState;
import parseExceptions.NotInInventory;
import parseExceptions.ParseException;

import java.util.ArrayList;

public class ParseInvCommand {
	private String inventoryObject;
	private String inventoryDescription;
	private int inventoryPosition;

	public ParseInvCommand(PlayerState playerState, ArrayList<String> commandList) throws ParseException {
		searchInventory(playerState, commandList);
	}

	private void searchInventory(PlayerState playerState, ArrayList<String> commandList) throws NotInInventory {
		//Search every term in the command to see if it matches with the inventory
		for(String singleToken : commandList) {
			int inventoryPosition = playerState.findInInventory(singleToken);
			if (inventoryPosition != -1) {
				this.inventoryObject = playerState.getInventoryObject(inventoryPosition);
				this.inventoryDescription = playerState.getInventoryDescription(inventoryPosition);
				this.inventoryPosition = inventoryPosition;
				//If an object is found in the inventory, stop searching
				return;
			}
		}
		//If no words match, throw an error
		throw new NotInInventory();
	}

	public String getInventoryObject(){
		return inventoryObject;
	}

	public String getInventoryObjectDescription(){
		return inventoryDescription;
	}

	public int getInventoryObjectPosition(){
		return inventoryPosition;
	}
}
