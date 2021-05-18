package CMDClasses;

import Data.PlayerState;
import Parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD implements CMDType{
	ParseLocationCommand parseTerm;

	public CMDGoto(ParseLocationCommand parseTerm, PlayerState playerState){
		this.parseTerm = parseTerm;
		this.playerState = playerState;
		System.out.println(parseTerm.getLocationName() + parseTerm.getLocationPosition());
	}

	public void execute() {
		//Change location in playerState
	}

	public String getExitMessage(){
		return "in CMDGoto";
	}
}
