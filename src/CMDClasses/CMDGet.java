package CMDClasses;

import Data.Actions;
import Data.Entities;

public class CMDGet extends ExecutableCMD implements CMDType{
	public CMDGet(Entities entityClass, Actions actionClass){
		System.out.println("in CMDGet");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
