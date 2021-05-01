package CMDClasses;

import Parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD implements CMDType{
	public CMDGoto(ParseLocationCommand parseTerm){
		System.out.println(parseTerm.getLocationName() + parseTerm.getLocationPosition());
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
