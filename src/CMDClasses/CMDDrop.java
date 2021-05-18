package CMDClasses;

import Data.Entities;
import Data.PlayerState;
import Parse.ParseInvCommand;

public class CMDDrop extends ExecutableCMD implements CMDType{
	Entities entities;
	ParseInvCommand parsedInventory;

	public CMDDrop(ParseInvCommand parsedInventory, Entities entities, PlayerState playerState){
		this.entities = entities;
		this.playerState = playerState;
		this.parsedInventory = parsedInventory;
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDDrop";
	}
}
