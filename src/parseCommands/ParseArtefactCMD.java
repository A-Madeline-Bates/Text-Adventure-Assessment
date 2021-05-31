package parseCommands;
import dataStorage.Entities;
import dataStorage.PlayerState;
import parseExceptions.ArtefactDoesNotExist;

import java.util.ArrayList;

public class ParseArtefactCMD {
	private int artefactPosition;
	private String artefactName;
	private String artefactDescription;

	public ParseArtefactCMD(Entities entityClass, PlayerState playerState, ArrayList<String> commandList)
			throws ArtefactDoesNotExist {
		validateArtefact(commandList, entityClass, playerState);
	}

	private void validateArtefact(ArrayList<String> commandList, Entities entityClass, PlayerState playerState)
			throws ArtefactDoesNotExist {
		int location = playerState.getCurrentLocation();
		//Search every term in the command to see if it matches with the artefacts at the location
		for(String singleToken : commandList) {
			int searchPosition = entityClass.entitySearch(singleToken, location, "artefacts");
			if (searchPosition != -1) {
				//This returns the array position of the artefact we are trying to find.
				this.artefactPosition = searchPosition;
				this.artefactName = entityClass.getEntityId();
				this.artefactDescription = entityClass.getEntityDescription();
				//If a matching artefact is found in the location, stop searching
				return;
			}
		}
		//If no words match, throw an error
		throw new ArtefactDoesNotExist();
	}

	public int getArtefactPosition(){
		return artefactPosition;
	}

	public String getArtefactName(){
		return artefactName;
	}

	public String getArtefactDesc(){
		return artefactDescription;
	}
}
