package CMDClasses;
import Data.*;
import Parse.ActionWordCMD;

public class CMDAction extends ExecutableCMD implements CMDType {
	public CMDAction(String trigger, ActionWordCMD parseAction){
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
