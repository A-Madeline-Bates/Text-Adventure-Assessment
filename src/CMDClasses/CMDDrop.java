package CMDClasses;

import Data.Entities;
import Parse.ParseInvCommand;

public class CMDDrop extends ExecutableCMD implements CMDType{
	Entities entities;
	ParseInvCommand parsedInventory;

	public CMDDrop(Entities entities, ParseInvCommand parsedInventory){
		this.entities = entities;
		this.parsedInventory = parsedInventory;
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
