import CMDClasses.*;
import Data.Actions;
import Data.Entities;
import Parse.SingleWordCMD;
import ParseExceptions.*;
import Tokeniser.Tokeniser;
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
			return commandSwitch(nextToken, tokeniser);
		}
	}

	private CMDType commandSwitch(String nextToken, Tokeniser tokeniser) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "INVENTORY":
			case "INV":
				return createInvObject(tokeniser);
			case "GET":
				return createGetObject(tokeniser);
			case "DROP":
				return createDropObject(tokeniser);
			case "GOTO":
				return createGotoObject(tokeniser);
			case "LOOK":
				return createLookObject(tokeniser);
			default:
				return findCMDAction(nextToken);
		}
	}

	private CMDType createInvObject(Tokeniser tokeniser){
		return new CMDInventory(entityClass, actionClass);
	}

	private CMDType createGetObject(Tokeniser tokeniser){
		return new CMDGet(entityClass, actionClass);
	}

	private CMDType createDropObject(Tokeniser tokeniser){
		return new CMDDrop(entityClass, actionClass);
	}

	private CMDType createGotoObject(Tokeniser tokeniser){
		return new CMDGoto(entityClass, actionClass);
	}

	private CMDType createLookObject(Tokeniser tokeniser) throws ParseException{
		new SingleWordCMD(tokeniser);
		return new CMDLook(entityClass, actionClass);
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
