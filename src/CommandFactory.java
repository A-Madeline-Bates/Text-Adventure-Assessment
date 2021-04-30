import CMDClasses.*;
import Data.*;
import Parse.*;
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
		String nextToken = tokeniser.getNextToken();
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
				return multiCmdSwitch(nextToken, tokeniser);
		}
	}

	private CMDType multiCmdSwitch(String nextToken, Tokeniser tokeniser) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "DROP":
				return new CMDDrop(entityClass, new CallInventoryCMD(inventory, tokeniser));
			case "GET":
				return new CMDGet(new MultiWordCMD(entityClass, inventory, "artefacts", tokeniser));
			case "GOTO":
				return new CMDGoto(new MultiWordCMD(entityClass, inventory, "locations", tokeniser));
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
