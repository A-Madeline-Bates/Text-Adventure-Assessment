package CMDClasses;

import Parse.CallEntityCMD;

public class CMDGet extends ExecutableCMD implements CMDType{
	public CMDGet(CallEntityCMD parseTerm){
		System.out.println("in CMDGet");
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
