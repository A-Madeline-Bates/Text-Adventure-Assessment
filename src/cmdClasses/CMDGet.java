package cmdClasses;
import data.Entities;
import data.PlayerState;
import parse.ParseArtefactCMD;

public class CMDGet extends CMDType implements ExecutableCMD {
	private final ParseArtefactCMD parseArtefact;
	private final Entities entity;

	public CMDGet(ParseArtefactCMD parseArtefact, Entities entity, PlayerState playerState){
		this.entity = entity;
		this.parseArtefact = parseArtefact;
		this.playerState = playerState;
		execute();
	}

	public void execute() {
		String pickedUpObject = parseArtefact.getArtefactName();
		String pickedUpObjectDesc = parseArtefact.getArtefactDesc();
		playerState.addToInventory(pickedUpObject, pickedUpObjectDesc);
		entity.removeObject(playerState.getCurrentLocation(), parseArtefact.getArtefactPosition(), "artefacts");
	}

	public String getExitMessage(){
		return "You picked up a " + parseArtefact.getArtefactName() + ".\n";
	}
}
