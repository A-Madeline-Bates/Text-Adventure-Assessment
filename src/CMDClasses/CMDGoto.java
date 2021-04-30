package CMDClasses;

import Data.Actions;
import Data.Entities;
import Data.Inventory;
import Parse.MultiWordCMD;

public class CMDGoto extends ExecutableCMD implements CMDType{
	public CMDGoto(MultiWordCMD parseTerm){
		System.out.println("in CMDGoto");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
