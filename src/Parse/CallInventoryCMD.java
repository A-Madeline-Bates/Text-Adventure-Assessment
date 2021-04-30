package Parse;

import Data.PlayerState;
import ParseExceptions.NotInInventory;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class CallInventoryCMD {
	String inventoryObject;

	public CallInventoryCMD(PlayerState playerState, Tokeniser tokeniser) throws ParseException {
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
