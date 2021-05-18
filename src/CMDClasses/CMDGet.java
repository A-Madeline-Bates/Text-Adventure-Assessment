package CMDClasses;

import Data.Entities;
import Data.PlayerState;
import Parse.ParseArtefactCommand;

public class CMDGet extends ExecutableCMD implements CMDType{
	ParseArtefactCommand parseTerm;
	Entities entity;

	public CMDGet(ParseArtefactCommand parseTerm, Entities entity, PlayerState playerState){
		this.entity = entity;
		this.parseTerm = parseTerm;
		this.playerState = playerState;
	}

	public void execute() {

	}

	public String getExitMessage(){
		return "in CMDGet";
	}
}
