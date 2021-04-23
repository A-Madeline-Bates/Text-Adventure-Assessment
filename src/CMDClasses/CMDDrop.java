package CMDClasses;

import Data.Actions;
import Data.Entities;

public class CMDDrop extends ExecutableCMD implements CMDType{
	public CMDDrop(Entities entityClass, Actions actionClass){
		System.out.println("in CMDDrop");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
