package CMDClasses;

import Data.Entities;
import Data.PlayerState;
import Parse.ParseArtefactCommand;

public class CMDGet extends ExecutableCMD implements CMDType{
	ParseArtefactCommand parseArtefact;
	Entities entity;

	public CMDGet(ParseArtefactCommand parseArtefact, Entities entity, PlayerState playerState){
		this.entity = entity;
		this.parseArtefact = parseArtefact;
		this.playerState = playerState;
	}

	public void execute() {
//		String pickedUpObject = parseArtefact.getArtefactName();
//		playerState.addToInventory(pickedUpObject);
//		entity.removeObjectFromLocation(pickedUpObject);
	}

	public String getExitMessage(){
		return "You picked up a " + parseArtefact.getArtefactName() + ".";
	}
}
