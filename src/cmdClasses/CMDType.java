package cmdClasses;
import data.PlayerState;

public abstract class CMDType {
	PlayerState playerState;
	abstract public String getExitMessage();
}
