import cmdClasses.*;
import data.*;
import data.Entities;
import parse.*;
import parseExceptions.*;
import tokeniser.Tokeniser;

public class CommandFactory {
	final Entities entityClass;
	final Actions actionClass;
	final PlayerState playerState;

	public CommandFactory(Entities entityClass, Actions actionClass, PlayerState playerState){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
		this.playerState = playerState;
	}

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser) throws ParseException, CommandMissing {
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
			case "HEALTH":
				tokeniser.checkForExtra();
				return new CMDHealth(playerState);
			default:
				return multiCmdSwitch(nextToken, tokeniser);
		}
	}

	private CMDType multiCmdSwitch(String nextToken, Tokeniser tokeniser) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "DROP":
				ParseInvCommand parseInv = new ParseInvCommand(playerState, tokeniser);
				return new CMDDrop(parseInv, entityClass, playerState);
			case "GET":
				ParseArtefactCommand parseArtefact = new ParseArtefactCommand(entityClass, playerState, tokeniser);
				return new CMDGet(parseArtefact, entityClass, playerState);
			case "GOTO":
				ParseLocationCommand parseLocation = new ParseLocationCommand(entityClass, playerState, tokeniser);
				return new CMDGoto(parseLocation, playerState, entityClass);
			default:
				return findCMDAction(nextToken, tokeniser);
		}
	}

	private CMDType findCMDAction(String nextToken, Tokeniser tokeniser) throws ParseException {
		int actionPosition = actionClass.findAction(nextToken);
		if(actionPosition != -1) {
			ParseActionCommand parseAction = new ParseActionCommand(actionClass, entityClass, playerState, actionPosition, tokeniser);
			return new CMDAction(parseAction, actionClass, entityClass, playerState);
		}
		throw new InvalidFirstCommand(nextToken);
	}
}
