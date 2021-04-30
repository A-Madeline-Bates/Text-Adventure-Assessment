package CMDClasses;

import Data.Actions;
import Data.Entities;
import Data.Inventory;
import Parse.MultiWordCMD;

public class CMDDrop extends ExecutableCMD implements CMDType{
	public CMDDrop(MultiWordCMD parseTerm){
		System.out.println("in CMDDrop");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
