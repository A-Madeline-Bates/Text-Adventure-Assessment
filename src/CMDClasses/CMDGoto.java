package CMDClasses;

import Data.Actions;
import Data.Entities;

public class CMDGoto extends ExecutableCMD implements CMDType{
	public CMDGoto(Entities entityClass, Actions actionClass){
		System.out.println("in CMDGoto");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
