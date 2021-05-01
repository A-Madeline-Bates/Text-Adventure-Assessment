package Parse;

import Data.PlayerState;
import ParseExceptions.NotInInventory;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class ParseInvCommand {
	String inventoryObject;

	public ParseInvCommand(PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchInventory(playerState, commandEnd);
	}

	private void searchInventory(PlayerState playerState, String commandEnd) throws ParseException {
		if(playerState.isInInventory(commandEnd)){
			this.inventoryObject = commandEnd;
		}
		else{
			throw new NotInInventory(commandEnd);
		}
	}

	public String getInventoryObject(){
		return inventoryObject;
	}
}
