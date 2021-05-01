package CMDClasses;

import Parse.CallEntityCMD;

public class CMDGet extends ExecutableCMD implements CMDType{
	public CMDGet(CallEntityCMD parseTerm){
		System.out.println(parseTerm.getArtefactPosition() + " " + parseTerm.getArtefactName());
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDGet";
	}
}
