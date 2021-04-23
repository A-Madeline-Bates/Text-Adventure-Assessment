import CMDClasses.*;
import Data.Actions;
import Data.Entities;
import ParseExceptions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandFactory {
	Entities entityClass;
	Actions actionClass;

	public CommandFactory(Entities entityClass, Actions actionClass){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
	}

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser) throws ParseException{
		String nextToken = tokeniser.nextToken();
		if(nextToken == null){
			throw new CommandMissing();
		}
		else{
			return commandSwitch(nextToken);
		}
	}

	private CMDType commandSwitch(String nextToken) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "INVENTORY":
			case "INV":
				return new CMDInventory(entityClass, actionClass);
			case "GET":
				return new CMDGet(entityClass, actionClass);
			case "DROP":
				return new CMDDrop(entityClass, actionClass);
			case "GOTO":
				return new CMDGoto(entityClass, actionClass);
			case "LOOK":
				return new CMDLook(entityClass, actionClass);
			default:
				return findCMDAction(nextToken);
		}
	}

	private CMDType findCMDAction(String nextToken) throws ParseException{
		for(Object action : actionClass.getActionArray()) {
			JSONObject jsonAction = (JSONObject) action;
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for (Object actionTrigger : actionTriggers) {
				String trigger = (String) actionTrigger;
				if (nextToken.equals(trigger)) {
					return new CMDAction(trigger, entityClass, actionClass);
				}
			}
		}
		throw new InvalidFirstCommand(nextToken);
	}
}
