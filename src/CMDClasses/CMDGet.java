package CMDClasses;

import Data.Actions;
import Data.Entities;
import Data.Inventory;
import Parse.MultiWordCMD;

public class CMDGet extends ExecutableCMD implements CMDType{
	public CMDGet(MultiWordCMD parseTerm){
		System.out.println("in CMDGet");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
