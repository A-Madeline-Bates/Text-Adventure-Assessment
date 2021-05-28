import cmdClasses.*;
import data.*;
import data.Entities;
import parse.*;
import parseExceptions.*;
import tokeniser.Tokeniser;

import java.util.ArrayList;

public class CommandFactory {
	private final Entities entityClass;
	private final Actions actionClass;
	private final PlayerState playerState;
	private final PlayerStore playerStore;
	private CMDType activeCommand;

	public CommandFactory(Entities entityClass, Actions actionClass, PlayerState playerState, PlayerStore playerStore){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
		this.playerState = playerState;
		this.playerStore = playerStore;
	}

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser) throws ParseException, CommandMissing {
		tokeniser.createCommandList();
		ArrayList<String> commandList = tokeniser.getCommandList();
		for(String singleToken : commandList) {
			if(searchCmd(singleToken, commandList)){
				return this.activeCommand;
			}
		}
		//If none of the input matches with a usable command term, throw an error
		throw new InvalidFirstCommand();
	}

	private boolean searchCmd(String nextToken, ArrayList<String> commandList) throws ParseException{
		//This method looks at all commands which only want one word of input
		//checkForExtra() will throw an assert error if there is extra tokens in the input command
		switch (nextToken.toUpperCase()) {
			case "INVENTORY":
			case "INV":
				this.activeCommand = new CMDInventory(playerState);
				return true;
			case "LOOK":
				this.activeCommand = new CMDLook(entityClass, playerState, playerStore);
				return true;
			case "HEALTH":
				this.activeCommand = new CMDHealth(playerState);
				return true;
			default:
				return searchMultiCmd(nextToken, commandList);
		}
	}

	private boolean searchMultiCmd(String nextToken, ArrayList<String> commandList) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "DROP":
				ParseInvCommand parseInv = new ParseInvCommand(playerState, commandList);
				this.activeCommand = new CMDDrop(parseInv, entityClass, playerState);
				return true;
			case "GET":
				ParseArtefactCommand parseArtefact = new ParseArtefactCommand(entityClass, playerState, commandList);
				this.activeCommand = new CMDGet(parseArtefact, entityClass, playerState);
				return true;
			case "GOTO":
				ParseLocationCommand parseLocation = new ParseLocationCommand(entityClass, playerState, commandList);
				this.activeCommand = new CMDGoto(parseLocation, playerState, entityClass, playerStore);
				return true;
			default:
				return searchActionCmd(nextToken, commandList);
		}
	}

	private boolean searchActionCmd(String nextToken, ArrayList<String> commandList) throws ParseException {
		int actionPosition = actionClass.findAction(nextToken);
		if(actionPosition != -1) {
			ParseActionCommand parseAction = new ParseActionCommand(actionClass, entityClass, playerState, actionPosition, commandList);
			this.activeCommand = new CMDAction(parseAction, actionClass, entityClass, playerState);
			return true;
		}
		return false;
	}
}
