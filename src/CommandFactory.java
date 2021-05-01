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
	PlayerState playerState;

	public CommandFactory(Entities entityClass, Actions actionClass, PlayerState playerState){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
		this.playerState = playerState;
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
				return new CMDInventory(playerState);
			case "LOOK":
				tokeniser.checkForExtra();
				return new CMDLook(entityClass, playerState);
			default:
				return multiCmdSwitch(nextToken, tokeniser);
		}
	}

	private CMDType multiCmdSwitch(String nextToken, Tokeniser tokeniser) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "DROP":
				return new CMDDrop(entityClass, new ParseInvCommand(playerState, tokeniser));
			case "GET":
				return new CMDGet(new ParseArtefactCommand(entityClass, playerState, tokeniser));
			case "GOTO":
				return new CMDGoto(new ParseLocationCommand(entityClass, playerState, tokeniser));
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
					ParseActionCommand parseAction = new ParseActionCommand(entityClass, playerState);
					return new CMDAction(trigger, parseAction);
				}
			}
		}
		throw new InvalidFirstCommand(nextToken);
	}
}
