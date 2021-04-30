import CMDClasses.*;
import Data.*;
import Parse.ActionWordCMD;
import Parse.MultiWordCMD;
import ParseExceptions.*;
import Tokeniser.Tokeniser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandFactory {
	Entities entityClass;
	Actions actionClass;
	Inventory inventory;

	public CommandFactory(Entities entityClass, Actions actionClass, Inventory inventory){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
		this.inventory = inventory;
	}

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser) throws ParseException{
		String nextToken = tokeniser.nextToken();
		if(nextToken == null){
			throw new CommandMissing();
		}
		else{
			return singleCmdSwitch(nextToken, tokeniser);
		}
	}

	private CMDType singleCmdSwitch(String nextToken, Tokeniser tokeniser) throws ParseException{
		//This method looks all commands which only want one word of input
		//checkForExtra() will throw an assert error if there is extra tokens in the input command
		switch (nextToken.toUpperCase()) {
			case "INVENTORY":
			case "INV":
				tokeniser.checkForExtra();
				return new CMDInventory(inventory);
			case "LOOK":
				tokeniser.checkForExtra();
				return new CMDLook(entityClass);
			default:
				return multiCmdSwitch(nextToken);
		}
	}

	private CMDType multiCmdSwitch(String nextToken) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "GET":
				return new CMDGet(new MultiWordCMD(entityClass, inventory, "artefacts"));
			case "DROP":
				return new CMDDrop(new MultiWordCMD(entityClass, inventory, "inventory"));
			case "GOTO":
				return new CMDGoto(new MultiWordCMD(entityClass, inventory, "locations"));
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
					ActionWordCMD parseAction = new ActionWordCMD(entityClass, inventory);
					return new CMDAction(trigger, parseAction);
				}
			}
		}
		throw new InvalidFirstCommand(nextToken);
	}
}
