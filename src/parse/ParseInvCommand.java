package parse;
import data.PlayerState;
import parseExceptions.NotInInventory;
import parseExceptions.ParseException;
import tokeniser.Tokeniser;

public class ParseInvCommand {
	private String inventoryObject;
	private String inventoryDescription;
	private int inventoryPosition;

	public ParseInvCommand(PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchInventory(playerState, commandEnd);
	}

	private void searchInventory(PlayerState playerState, String commandEnd) throws NotInInventory {
		int inventoryPosition = playerState.findInInventory(commandEnd);
		if(inventoryPosition != -1){
			this.inventoryObject = playerState.getInventoryObject(inventoryPosition);
			this.inventoryDescription = playerState.getInventoryDescription(inventoryPosition);
			this.inventoryPosition = inventoryPosition;
		}
		else{
			throw new NotInInventory(commandEnd);
		}
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
