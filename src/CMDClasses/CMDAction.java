package CMDClasses;
import Parse.ParseActionCommand;

public class CMDAction extends ExecutableCMD implements CMDType {

	public CMDAction(String trigger, ParseActionCommand parseAction){
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
