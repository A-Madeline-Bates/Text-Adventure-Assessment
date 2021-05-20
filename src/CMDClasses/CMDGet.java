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
		execute();
	}

	public void execute() {
		String pickedUpObject = parseArtefact.getArtefactName();
		String pickedUpObjectDescription = parseArtefact.getArtefactDescription();
		playerState.addToInventory(pickedUpObject, pickedUpObjectDescription);
		entity.removeObjectFromLocation(playerState.getCurrentLocation(), parseArtefact.getArtefactPosition());
	}

	public String getExitMessage(){
		return "You picked up a " + parseArtefact.getArtefactName() + ".\n";
	}
}
