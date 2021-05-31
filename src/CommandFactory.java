import cmdClasses.*;
import dataStorage.*;
import parseCommands.*;
import parseExceptions.*;
import tokeniser.Tokeniser;

import java.util.ArrayList;

public class CommandFactory {
	private final Entities entities;
	private final Actions actions;
	private final PlayerState playerState;
	private final PlayerStore playerStore;
	private CMDType activeCommand;

	public CommandFactory(Entities entities, Actions actions, PlayerState playerState, PlayerStore playerStore){
		this.entities = entities;
		this.actions = actions;
		this.playerState = playerState;
		this.playerStore = playerStore;
	}

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser) throws ParseException {
		tokeniser.createCommandList();
		ArrayList<String> commands = tokeniser.getCommandList();
		for(int i=0; i<commands.size(); i++) {
			if(searchCmd(i, commands)){
				return this.activeCommand;
			}
		}
		//If none of the input matches with a usable command term, throw an error
		throw new InvalidFirstCommand();
	}

	private boolean searchCmd(int i, ArrayList<String> commands) throws ParseException{
		//This method looks at all commands which only want one word of input
		//checkForExtra() will throw an assert error if there is extra tokens in the input command
		switch (commands.get(i).toUpperCase()) {
			case "INVENTORY":
			case "INV":
				this.activeCommand = new CMDInventory(playerState);
				return true;
			case "LOOK":
				this.activeCommand = new CMDLook(entities, playerState, playerStore);
				return true;
			case "HEALTH":
				this.activeCommand = new CMDHealth(playerState);
				return true;
			default:
				return searchMultiCmd(i, commands);
		}
	}

	private boolean searchMultiCmd(int i, ArrayList<String> commands) throws ParseException{
		switch (commands.get(i).toUpperCase()) {
			case "DROP":
				ParseInvCMD parseInv = new ParseInvCMD(playerState, commands);
				this.activeCommand = new CMDDrop(parseInv, entities, playerState);
				return true;
			case "GET":
				ParseArtefactCMD parseArtefact = new ParseArtefactCMD(entities, playerState, commands);
				this.activeCommand = new CMDGet(parseArtefact, entities, playerState);
				return true;
			case "GOTO":
				ParseLocationCMD parseLocation = new ParseLocationCMD(entities, playerState, commands);
				this.activeCommand = new CMDGoto(parseLocation, playerState, entities, playerStore);
				return true;
			default:
				return searchActionCmd(i, commands);
		}
	}

	private boolean searchActionCmd(int i, ArrayList<String> commands) throws ActionSubjectErr {
		//This tries to find a match between the token and the available actions. If the action word "open" appears
		// twice in the dot file, the array position of both instances will be recorded in the actionPositions array.
		ArrayList<Integer> actionPositions = actions.findAction(i, commands);
		//If the size of the array is zero, this token isn't a valid action
		if(actionPositions.size() != 0) {
			ParseActionCMD parseAction = new ParseActionCMD(actions, entities, playerState, actionPositions, commands);
			this.activeCommand = new CMDAction(parseAction, actions, entities, playerState);
			actions.resetActionArr();
			return true;
		}
		return false;
	}
}
