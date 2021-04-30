package CMDClasses;

import Data.Entities;
import Parse.CallInventoryCMD;

public class CMDDrop extends ExecutableCMD implements CMDType{
	Entities entities;
	CallInventoryCMD parsedInventory;

	public CMDDrop(Entities entities, CallInventoryCMD parsedInventory){
		this.entities = entities;
		this.parsedInventory = parsedInventory;
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
