package CMDClasses;

import Data.PlayerState;
import Parse.ParseLocationCommand;

public class CMDGoto extends ExecutableCMD implements CMDType{
	ParseLocationCommand parseLocation;

	public CMDGoto(ParseLocationCommand parseLocation, PlayerState playerState){
		this.parseLocation = parseLocation;
		this.playerState = playerState;
		System.out.println(parseLocation.getNewLocationName() + " " + parseLocation.getNewLocationPosition());
	}

	public void execute() {
//		int nextLocation = parseLocation.getNewLocationPosition();
//		playerState.setCurrentLocation(nextLocation);
	}

	public String getExitMessage(){
		return "in CMDGoto";
	}
}
