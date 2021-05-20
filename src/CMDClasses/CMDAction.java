package CMDClasses;
import Parse.ParseActionCommand;

public class CMDAction extends ExecutableCMD implements CMDType {

	public CMDAction(ParseActionCommand parseAction){
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
