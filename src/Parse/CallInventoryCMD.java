package Parse;

import Data.Inventory;
import ParseExceptions.NotInInventory;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;

public class CallInventoryCMD {
	String inventoryObject;

	public CallInventoryCMD(Inventory inventory, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		searchInventory(inventory, commandEnd);
	}

	private void searchInventory(Inventory inventory, String commandEnd) throws ParseException {
		if(inventory.isInInventory(commandEnd)){
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
