package parse;
import data.Entities;
import data.PlayerState;
import parseExceptions.ArtefactDoesNotExist;
import parseExceptions.ParseException;
import tokeniser.Tokeniser;

public class ParseArtefactCommand {
	private int artefactPosition;
	private String artefactName;
	private String artefactDescription;

	public ParseArtefactCommand(Entities entityClass, PlayerState playerState, Tokeniser tokeniser) throws ParseException {
		String commandEnd = tokeniser.getRemainingTokens();
		validateArtefact(commandEnd, entityClass, playerState);
	}

	private void validateArtefact(String commandEnd, Entities entityClass, PlayerState playerState) throws ArtefactDoesNotExist {
		int loc = playerState.getCurrentLocation();
		int searchPosition = entityClass.setEntityInfo(commandEnd, loc, "artefacts");
		if(searchPosition != -1){
			//This returns the array position of the artefact we are trying to find.
			this.artefactPosition = searchPosition;
			this.artefactName = entityClass.getEntityId();
			this.artefactDescription = entityClass.getEntityDescription();
		} else{
			throw new ArtefactDoesNotExist(commandEnd);
		}
	}

	public int getArtefactPosition(){
		return artefactPosition;
	}

	public String getArtefactName(){
		return artefactName;
	}

	public String getArtefactDescription(){
		return artefactDescription;
	}
}
