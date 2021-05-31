package cmdClasses;
import dataStorage.PlayerState;

public abstract class CMDType {
	PlayerState playerState;
	abstract public String getExitMessage();
}
