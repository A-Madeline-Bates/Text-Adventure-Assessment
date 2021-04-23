package CMDClasses;

import Data.Actions;
import Data.Entities;

public class CMDLook implements CMDType{
	public CMDLook(Entities entityClass, Actions actionClass){
		System.out.println("in CMDLook");
	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
