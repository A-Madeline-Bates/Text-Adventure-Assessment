package CMDClasses;

import Data.Actions;
import Data.Entities;

public class CMDInventory implements CMDType{
	public CMDInventory(Entities entityClass, Actions actionClass){
		System.out.println("in CMDInventory");
	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
