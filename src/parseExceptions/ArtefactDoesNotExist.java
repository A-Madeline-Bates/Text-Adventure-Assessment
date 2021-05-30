package parseExceptions;

public class ArtefactDoesNotExist extends ParseException {

	public String toString() {
		return "The artefact you are trying to pick up is not at your location.\n";
	}
}
