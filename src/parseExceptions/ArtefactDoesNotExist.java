package parseExceptions;

public class ArtefactDoesNotExist extends ParseException {
	public ArtefactDoesNotExist(String token) {
		this.token = token;
	}

	public String toString() {
		return "The artefact you are trying to pick up is not at your location. Artefact specified was " + token + ".";
	}
}
