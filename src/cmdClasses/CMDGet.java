package cmdClasses;
import data.Entities;
import data.PlayerState;
import parse.ParseArtefactCommand;

public class CMDGet extends ExecutableCMD{
	final ParseArtefactCommand parseArtefact;
	final Entities entity;

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
		entity.removeArtefact(playerState.getCurrentLocation(), parseArtefact.getArtefactPosition(), "artefacts");
	}

	public String getExitMessage(){
		return "You picked up a " + parseArtefact.getArtefactName() + ".\n";
	}
}
