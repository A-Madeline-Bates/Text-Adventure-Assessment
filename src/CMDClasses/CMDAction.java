package CMDClasses;
import Data.Actions;
import Parse.ParseActionCommand;

public class CMDAction extends ExecutableCMD implements CMDType {
	ParseActionCommand parseAction;
	Actions actionClass;

	public CMDAction(ParseActionCommand parseAction, Actions actionClass){
		this.parseAction = parseAction;
		this.actionClass = actionClass;
	}

	public void execute() {
		//consume items
		//create items
			//if not a location or health, put in inventory
	}

	public String getExitMessage(){
		return actionClass.getMessage(parseAction.getActionPosition());
	}
}
